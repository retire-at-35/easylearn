<template>
  <view class="exam-container">
    <!-- 题目信息 -->
    <view class="question-info">
      <text class="question-count">第 {{ currentIndex + 1 }} 题 / 共 {{ totalCount }} 题</text>
      <text class="chapter-info">{{ currentQuestion?.chapterName }} - {{ currentQuestion?.sectionName }}</text>
    </view>
    
    <!-- 题目内容 -->
    <view class="question-content">
      <text class="question-text">{{ currentQuestion?.content }}</text>
    </view>
    
    <!-- 选项列表 -->
    <view class="options-list">
      <view 
        v-for="option in currentQuestion?.optionsList" 
        :key="option.oid"
        class="option-item"
        :class="{
          'selected': selectedAnswer.includes(String.fromCharCode(64 + option.pos)),
          'correct': showAnswer && currentQuestion.key.includes(String.fromCharCode(64 + option.pos)),
          'wrong': showAnswer && selectedAnswer.includes(String.fromCharCode(64 + option.pos)) && !currentQuestion.key.includes(String.fromCharCode(64 + option.pos))
        }"
        @tap="handleSelect(option)"
      >
        <text class="option-index">{{ String.fromCharCode(64 + option.pos) }}</text>
        <text class="option-text">{{ option.op }}</text>
      </view>
    </view>
    
    <!-- 底部操作栏 -->
    <view class="bottom-bar">
      <button 
        class="next-btn" 
        type="primary" 
        @tap="handleNext"
      >{{ showAnswer ? '下一题' : '确认答案' }}</button>
    </view>
  </view>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { getQuestions } from '@/api/question'

// 当前题目类型
const questionType = ref(0)
// 题目列表
const questionList = ref([])
// 当前题目索引
const currentIndex = ref(0)
// 选中的答案
const selectedAnswer = ref([])
// 是否显示答案
const showAnswer = ref(false)

// 计算总题目数
const totalCount = computed(() => Object.keys(questionList.value).length)

// 获取当前题目
const currentQuestion = computed(() => {
  const keys = Object.keys(questionList.value)
  return questionList.value[keys[currentIndex.value]] || null
})

// 选择答案
const handleSelect = (option) => {
  if (showAnswer.value) return
  
  const answer = String.fromCharCode(64 + option.pos)
  
  if (questionType.value === 2) { // 多选题
    const index = selectedAnswer.value.indexOf(answer)
    if (index > -1) {
      selectedAnswer.value.splice(index, 1)
    } else {
      selectedAnswer.value.push(answer)
    }
  } else { // 单选题或判断题
    selectedAnswer.value = [answer]
  }
}

// 处理下一题
const handleNext = () => {
  if (!selectedAnswer.value.length) {
    uni.showToast({
      title: '请选择答案',
      icon: 'none'
    })
    return
  }
  
  if (!showAnswer.value) {
    showAnswer.value = true
    return
  }
  
  // 重置状态
  showAnswer.value = false
  selectedAnswer.value = []
  
  // 如果还有下一题
  if (currentIndex.value < totalCount.value - 1) {
    currentIndex.value++
  } else {
    uni.showModal({
      title: '提示',
      content: '已经是最后一题了，是否重新开始？',
      success: (res) => {
        if (res.confirm) {
          currentIndex.value = 0
        } else {
          uni.navigateBack()
        }
      }
    })
  }
}

// 获取题目数据
const loadQuestions = async (type) => {
  try {
    const res = await getQuestions(type)
    questionList.value = res
  } catch (error) {
    uni.showToast({
      title: '获取题目失败',
      icon: 'none'
    })
  }
}

onMounted(() => {
  const pages = getCurrentPages()
  const currentPage = pages[pages.length - 1]
  const type = currentPage.options.type
  
  questionType.value = Number(type)
  loadQuestions(type)
})
</script>

<style lang="scss" scoped>
.exam-container {
  min-height: 100vh;
  background-color: #f5f5f5;
  padding: 20rpx;
  padding-bottom: 120rpx;
  
  .question-info {
    background-color: #fff;
    padding: 20rpx;
    border-radius: 12rpx;
    margin-bottom: 20rpx;
    
    .question-count {
      font-size: 28rpx;
      color: #666;
      margin-right: 20rpx;
    }
    
    .chapter-info {
      font-size: 28rpx;
      color: #999;
    }
  }
  
  .question-content {
    background-color: #fff;
    padding: 30rpx;
    border-radius: 12rpx;
    margin-bottom: 20rpx;
    
    .question-text {
      font-size: 32rpx;
      color: #333;
      line-height: 1.6;
    }
  }
  
  .options-list {
    .option-item {
      background-color: #fff;
      padding: 30rpx;
      border-radius: 12rpx;
      margin-bottom: 20rpx;
      display: flex;
      align-items: center;
      
      &.selected {
        background-color: #e1f0ff;
      }
      
      &.correct {
        background-color: #e8f5e9;
      }
      
      &.wrong {
        background-color: #ffebee;
      }
      
      .option-index {
        width: 60rpx;
        height: 60rpx;
        background-color: #f5f5f5;
        border-radius: 30rpx;
        display: flex;
        justify-content: center;
        align-items: center;
        font-size: 28rpx;
        color: #666;
        margin-right: 20rpx;
      }
      
      .option-text {
        flex: 1;
        font-size: 30rpx;
        color: #333;
      }
    }
  }
  
  .bottom-bar {
    position: fixed;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: #fff;
    padding: 20rpx;
    box-shadow: 0 -2rpx 12rpx rgba(0, 0, 0, 0.1);
    
    .next-btn {
      width: 100%;
    }
  }
}
</style> 