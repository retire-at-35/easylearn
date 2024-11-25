const baseURL = 'http://localhost:8080'
const timeout = 5000

const request = (options = {}) => {
  return new Promise((resolve, reject) => {
    // 请求拦截
    const header = {
      'Content-Type': 'application/json',
      ...options.header
    }
    
    // 获取并添加token到请求头
    const token = uni.getStorageSync('token')
    if (token) {
      header.token = token
    }

    uni.request({
      url: baseURL + options.url,
      method: options.method || 'GET',
      data: options.data,
      header,
      timeout,
      withCredentials: true,
      success: (response) => {
        const res = response.data
        
        // 响应拦截
        if (res.code === 1) {
          // 成功
          resolve(res.data)
        } else {
          // 业务错误
          uni.showToast({
            title: res.msg || '请求失败',
            icon: 'none'
          })
          reject(new Error(res.msg || '请求失败'))
        }
      },
      fail: (error) => {
        // 网络错误或其他错误
        uni.showToast({
          title: '网络异常，请稍后重试',
          icon: 'none'
        })
        reject(error)
      }
    })
  })
}

// 封装常用请求方法
const http = {
  get(url, data = {}) {
    return request({
      url,
      method: 'GET',
      data
    })
  },
  post(url, data = {}) {
    return request({
      url,
      method: 'POST',
      data
    })
  },
  put(url, data = {}) {
    return request({
      url,
      method: 'PUT',
      data
    })
  },
  delete(url, data = {}) {
    return request({
      url,
      method: 'DELETE',
      data
    })
  }
}

export default http
