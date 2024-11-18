<template>
  <div class="user-container">
    <!-- 顶部操作区 -->
    <div class="operation-area">
      <el-input
        v-model="searchQuery"
        placeholder="请输入用户名搜索"
        class="search-input"
        clearable
        @clear="handleSearch"
        @keyup.enter="handleSearch"
      >
        <template #prefix>
          <el-icon><search /></el-icon>
        </template>
      </el-input>

      <el-button type="primary" @click="handleAdd">
        <el-icon><plus /></el-icon>
        新增管理员
      </el-button>
    </div>

    <!-- 用户列表 -->
    <el-table
      :data="userList"
      style="width: 100%"
      v-loading="loading"
      border
    >
      <el-table-column prop="username" label="用户名" align="center" />
      <el-table-column label="角色" align="center">
        <template #default="{ row }">
          <el-tag
            :type="row.roleList[0]?.roleName === 'ROLE_superadmin' ? 'danger' : 'success'"
          >
            {{ formatRole(row.roleList[0]?.roleName) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200" align="center">
        <template #default="{ row }">
          <el-button
            type="primary"
            link
            @click="handleEdit(row)"
          >
            修改
          </el-button>
          <el-button
            v-if="!isSuperAdmin(row)"
            type="danger"
            link
            @click="handleDelete(row)"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div class="pagination-container">
      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :page-sizes="[10, 20, 30, 50]"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      >
        <template #total>
          共 <span style="color: #409EFF;">{{ total }}</span> 条
        </template>
        <template #sizes>
          <span style="margin-right: 4px;">每页</span>
          <el-select v-model="pageSize" style="width: 100px">
            <el-option
              v-for="size in [10, 20, 30, 50]"
              :key="size"
              :value="size"
              :label="`${size}条/页`"
            />
          </el-select>
        </template>
      </el-pagination>
    </div>

    <!-- 新增/编辑对话框 -->
    <el-dialog
      :title="dialogTitle"
      v-model="dialogVisible"
      width="500px"
    >
      <el-form
        ref="userFormRef"
        :model="userForm"
        :rules="userRules"
        label-width="80px"
      >
        <el-form-item label="用户名" prop="username">
          <el-input v-model="userForm.username" placeholder="请输入用户名" />
        </el-form-item>

        <!-- 新增时显示密码输入框 -->
        <el-form-item label="密码" prop="password" v-if="!userForm.id">
          <el-input v-model="userForm.password" type="password" placeholder="请输入密码" />
        </el-form-item>

        <!-- 编辑时显示修改密码选项 -->
        <template v-else>
          <el-form-item label="修改密码">
            <el-switch v-model="changePassword" @change="handlePasswordChange" />
          </el-form-item>

          <el-form-item
            label="新密码"
            prop="newPassword"
            v-if="changePassword"
          >
            <el-input
              v-model="userForm.newPassword"
              type="password"
              placeholder="请输入新密码"
            />
          </el-form-item>
        </template>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { Search, Plus } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getUserList, addUser, updateUser, deleteUser } from '@/api/user'

// 搜索相关
const searchQuery = ref('')
const handleSearch = () => {
  currentPage.value = 1
  fetchUserList()
}

// 分页相关
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const handleSizeChange = (val) => {
  pageSize.value = val
  currentPage.value = 1
  fetchUserList()
}
const handleCurrentChange = (val) => {
  currentPage.value = val
  fetchUserList()
}

// 表格相关
const loading = ref(false)
const userList = ref([])

// 获取用户列表
const fetchUserList = async () => {
  loading.value = true
  try {
    const params = {
      page: currentPage.value,
      pageSize: pageSize.value,
      ...(searchQuery.value ? { username: searchQuery.value } : {})
    }
    const res = await getUserList(params)
    userList.value = res.rows || []
    total.value = res.total || 0
  } catch (error) {
    console.error('获取用户列表失败:', error)
    ElMessage.error('获取用户列表失败')
  } finally {
    loading.value = false
  }
}

// 表单相关
const dialogVisible = ref(false)
const dialogTitle = ref('')
const userFormRef = ref()
const userForm = reactive({
  id: '',
  username: '',
  password: '',
  newPassword: ''  // 添加新密码字段
})
const userRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
  ],
  newPassword: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
  ]
}

// 添加修改密码相关的响应式变量
const changePassword = ref(false)

// 新增用户
const handleAdd = () => {
  dialogTitle.value = '新增管理员'
  userForm.id = ''
  userForm.username = ''
  userForm.password = ''
  dialogVisible.value = true
}

// 编辑用户
const handleEdit = (row) => {
  dialogTitle.value = '编辑管理员'
  userForm.id = row.id
  userForm.username = row.username
  userForm.newPassword = ''  // 清空新密码
  changePassword.value = false  // 重置修改密码开关
  dialogVisible.value = true
}

// 删除用户
const handleDelete = (row) => {
  ElMessageBox.confirm(
    '确认删除该管理员吗？',
    '警告',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }
  ).then(async () => {
    try {
      await deleteUser(row.id)
      ElMessage.success('删除成功')
      if (userList.value.length === 1 && currentPage.value > 1) {
        currentPage.value--
      }
      fetchUserList()
    } catch (error) {
      console.error('删除失败:', error)
      ElMessage.error('删除失败')
    }
  }).catch(() => {
    // 取消删除，不做任何操作
  })
}

// 提交表单
const handleSubmit = () => {
  userFormRef.value?.validate(async (valid) => {
    if (valid) {
      try {
        const submitData = {
          username: userForm.username
        }

        if (userForm.id) {
          // 编辑用户
          submitData.id = userForm.id
          if (changePassword.value) {
            submitData.password = userForm.newPassword
          }
          await updateUser(submitData)
          ElMessage.success('修改成功')
        } else {
          // 新增用户
          submitData.password = userForm.password
          await addUser(submitData)
          ElMessage.success('添加成功')
        }
        dialogVisible.value = false
        fetchUserList()
      } catch (error) {
        console.error('操作失败:', error)
        ElMessage.error('操作失败')
      }
    }
  })
}

// 处理密码修改开关变化
const handlePasswordChange = (val) => {
  if (!val) {
    userForm.newPassword = ''
  }
}

// 添加角色格式化方法
const formatRole = (roleName) => {
  switch (roleName) {
    case 'ROLE_superadmin':
      return '超级管理员'
    case 'ROLE_admin':
      return '管理员'
    default:
      return '未知角色'
  }
}

// 添加判断是否是超级管理员的方法
const isSuperAdmin = (user) => {
  return user.roleList.some(role => role.roleName === 'ROLE_superadmin')
}

// 初始化
fetchUserList()
</script>

<style scoped>
.user-container {
  padding: 20px;
  background-color: #fff;
  border-radius: 4px;
}

.operation-area {
  margin-bottom: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.search-input {
  width: 300px;
}

.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}

:deep(.el-dialog__body) {
  padding-top: 20px;
}

:deep(.el-pagination) {
  justify-content: flex-end;
  font-size: 14px;
}

:deep(.el-select .el-input) {
  margin: 0 8px;
}

:deep(.el-pagination .el-select .el-input .el-input__inner) {
  padding-right: 25px;
}

/* 添加角色标签样式 */
:deep(.el-tag) {
  padding: 0 12px;
  height: 28px;
  line-height: 28px;
}
</style>
