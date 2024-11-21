<template>
  <div class="type-container">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>类型管理</span>
          <el-button type="primary" @click="handleAdd">添加类型</el-button>
        </div>
      </template>

      <el-table
        :data="typeList"
        style="width: 100%"
        v-loading="loading"
      >
        <el-table-column prop="tid" label="ID" width="80" />
        <el-table-column prop="tmtypeName" label="类型名称" />
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button
              size="small"
              type="danger"
              @click="handleDelete(scope.row)"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 添加/编辑对话框 -->
    <el-dialog
      :title="dialogTitle"
      v-model="dialogVisible"
      width="500px"
      @close="handleDialogClose"
    >
      <el-form
        ref="formRef"
        :model="formData"
        :rules="rules"
        label-width="100px"
      >
        <el-form-item label="类型名称" prop="tmtypeName">
          <el-input v-model="formData.tmtypeName" placeholder="请输入类型名称" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSubmit" :loading="submitLoading">
            确定
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { getAllTypes, updateOrSaveType, deleteType } from '@/api/type'
import { ElMessage, ElMessageBox } from 'element-plus'

const typeList = ref([])
const loading = ref(false)

// 对话框相关
const dialogVisible = ref(false)
const dialogTitle = computed(() => formData.tid ? '编辑类型' : '添加类型')
const submitLoading = ref(false)

// 表单相关
const formRef = ref(null)
const formData = ref({
  tid: '',
  tmtypeName: ''
})
const rules = {
  tmtypeName: [
    { required: true, message: '请输入类型名称', trigger: 'blur' },
    { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
  ]
}

// 获取类型列表
const getTypeList = async () => {
  loading.value = true
  try {
    const res = await getAllTypes()
      typeList.value = res
  } catch (error) {
    console.error('获取类型列表出错：', error)
    ElMessage.error('获取类型列表失败')
  } finally {
    loading.value = false
  }
}

// 页面加载时获取数据
onMounted(() => {
  getTypeList()
})

// 处理添加按钮点击
const handleAdd = () => {
  formData.value = {
    tid: '',
    tmtypeName: ''
  }
  dialogVisible.value = true
}

// 处理编辑按钮点击
const handleEdit = (row) => {
  formData.value = {
    tid: row.tid,
    tmtypeName: row.tmtypeName
  }
  dialogVisible.value = true
}

// 处理对话框关闭
const handleDialogClose = () => {
  formRef.value?.resetFields()
  formData.value = {
    tid: '',
    tmtypeName: ''
  }
}

// 处理表单提交
const handleSubmit = async () => {
  if (!formRef.value) return

  await formRef.value.validate(async (valid) => {
    if (valid) {
      submitLoading.value = true
      try {
        await updateOrSaveType(formData.value)
        ElMessage.success('操作成功')
        dialogVisible.value = false
        getTypeList() // 刷新列表
      } catch (error) {
        console.error('保存失败：', error)
      } finally {
        submitLoading.value = false
      }
    }
  })
}

// 处理删除
const handleDelete = (row) => {
  ElMessageBox.confirm(
    '确认删除该类型吗？',
    '警告',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }
  ).then(async () => {
    try {
      await deleteType(row.tid)
      ElMessage.success('删除成功')
      getTypeList() // 刷新列表
    } catch (error) {
      console.error('删除失败：', error)
    }
  })
}
</script>

<style scoped>
.type-container {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
</style>
