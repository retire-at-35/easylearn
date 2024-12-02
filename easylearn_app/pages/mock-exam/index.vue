<template>
  <view class="exam-container">
    <!-- 题目信息 -->
    <view class="question-info">
      <text class="question-count">第 {{ currentIndex + 1 }} 题 / 共 {{ totalCount }} 题</text>
      <text class="chapter-info">{{ currentQuestion?.chapterName }} - {{ currentQuestion?.sectionName }}</text>
      <!-- 添加答题卡按钮 -->
      <view class="card-btn" @tap="showAnswerCard = true">答题卡</view>
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

    <!-- 答题卡弹出层 -->
    <view class="answer-card" v-if="showAnswerCard">
      <view class="card-mask" @tap="showAnswerCard = false"></view>
      <view class="card-content">
        <view class="card-header">
          <text class="card-title">答题卡</text>
          <text class="card-close" @tap="showAnswerCard = false">×</text>
        </view>
        <view class="card-body">
          <view 
            v-for="(_, index) in totalCount" 
            :key="index"
            class="question-item"
            :class="{
              'answered': answeredQuestions[index],
              'current': currentIndex === index
            }"
            @tap="jumpToQuestion(index)"
          >
            {{ index + 1 }}
          </view>
        </view>
        <view class="card-footer">
          <view class="legend-item">
            <view class="legend-color current"></view>
            <text>当前题目</text>
          </view>
          <view class="legend-item">
            <view class="legend-color answered"></view>
            <text>已答题</text>
          </view>
          <view class="legend-item">
            <view class="legend-color"></view>
            <text>未答题</text>
          </view>
        </view>
      </view>
    </view>
  </view>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { getExamQuestions } from '@/api/question'

// 题目列表
const questionList = ref([])
// 当前题目索引
const currentIndex = ref(0)
// 选中的答案
const selectedAnswer = ref([])
// 是否显示答案
const showAnswer = ref(false)
// 是否显示答题卡
const showAnswerCard = ref(false)
// 已答题目记录
const answeredQuestions = ref({})

// 计算总题目数
const totalCount = computed(() => questionList.value.length)

// 获取当前题目
const currentQuestion = computed(() => questionList.value[currentIndex.value])

// 选择答案
const handleSelect = (option) => {
  if (showAnswer.value) return
  
  const answer = String.fromCharCode(64 + option.pos)
  const questionType = currentQuestion.value.tid
  
  if (questionType === 2) { // 多选题
    const index = selectedAnswer.value.indexOf(answer)
    if (index > -1) {
      selectedAnswer.value.splice(index, 1)
    } else {
      selectedAnswer.value.push(answer)
    }
    selectedAnswer.value.sort() // 排序，保证答案顺序一致
  } else { // 单选题或判断题
    selectedAnswer.value = [answer]
  }
}

// 跳转到指定题目
const jumpToQuestion = (index) => {
  currentIndex.value = index
  showAnswerCard.value = false
  if (!answeredQuestions.value[index]) {
    showAnswer.value = false
    selectedAnswer.value = []
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
    // 记录已答题目
    answeredQuestions.value[currentIndex.value] = true
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
          answeredQuestions.value = {}
        } else {
          uni.navigateBack()
        }
      }
    })
  }
}

// 获取题目数据
const loadQuestions = async () => {
  try {
    const res = await getExamQuestions()
    questionList.value = res
  } catch (error) {
    uni.showToast({
      title: '获取题目失败',
      icon: 'none'
    })
  }
}

onMounted(() => {
  loadQuestions()
})
</script>

<style lang="scss" scoped>
.exam-container {
  min-height: 100vh;
  background-color: #f5f5f5;
  padding: 20rpx;
  padding-bottom: 120rpx;
  
  .question-info {
    position: relative;
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

    .card-btn {
      position: absolute;
      right: 20rpx;
      top: 50%;
      transform: translateY(-50%);
      padding: 10rpx 20rpx;
      background-color: #007AFF;
      color: #fff;
      border-radius: 8rpx;
      font-size: 24rpx;
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

  .answer-card {
    position: fixed;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
    z-index: 100;
    
    .card-mask {
      position: absolute;
      left: 0;
      right: 0;
      top: 0;
      bottom: 0;
      background-color: rgba(0, 0, 0, 0.5);
    }
    
    .card-content {
      position: absolute;
      left: 50%;
      top: 50%;
      transform: translate(-50%, -50%);
      width: 80%;
      max-height: 80vh;
      background-color: #fff;
      border-radius: 16rpx;
      overflow: hidden;
      
      .card-header {
        padding: 30rpx;
        border-bottom: 1px solid #eee;
        display: flex;
        justify-content: space-between;
        align-items: center;
        
        .card-title {
          font-size: 32rpx;
          font-weight: bold;
        }
        
        .card-close {
          font-size: 40rpx;
          color: #999;
          padding: 0 20rpx;
        }
      }
      
      .card-body {
        padding: 30rpx;
        display: flex;
        flex-wrap: wrap;
        gap: 20rpx;
        max-height: 60vh;
        overflow-y: auto;
        
        .question-item {
          width: 80rpx;
          height: 80rpx;
          display: flex;
          justify-content: center;
          align-items: center;
          background-color: #f5f5f5;
          border-radius: 8rpx;
          font-size: 28rpx;
          color: #666;
          
          &.answered {
            background-color: #e8f5e9;
            color: #4caf50;
          }
          
          &.current {
            background-color: #007AFF;
            color: #fff;
          }
        }
      }
      
      .card-footer {
        padding: 20rpx 30rpx;
        border-top: 1px solid #eee;
        display: flex;
        justify-content: space-around;
        
        .legend-item {
          display: flex;
          align-items: center;
          font-size: 24rpx;
          color: #666;
          
          .legend-color {
            width: 30rpx;
            height: 30rpx;
            margin-right: 10rpx;
            border-radius: 4rpx;
            background-color: #f5f5f5;
            
            &.answered {
              background-color: #e8f5e9;
            }
            
            &.current {
              background-color: #007AFF;
            }
          }
        }
      }
    }
  }
}
</style> 