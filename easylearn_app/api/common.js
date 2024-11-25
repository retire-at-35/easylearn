import http from '@/utils/request'
// 上传头像
export const uploadAvatar = (filePath) => {
  return new Promise((resolve, reject) => {
    uni.uploadFile({
      url: 'http://localhost:8080/app/upload',
      filePath: filePath,
      name: 'file',
      header: {
        token: uni.getStorageSync('token')
      },
      success: (uploadFileRes) => {
        const result = JSON.parse(uploadFileRes.data)
        if (result.code === 1) {
          resolve(result.data)
        } else {
          reject(new Error(result.msg || '上传失败'))
        }
      },
      fail: (error) => {
        reject(error)
      }
    })
  })
} 