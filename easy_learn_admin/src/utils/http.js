import axios from 'axios'
import {useRouter} from 'vue-router'
axios.defaults.withCredentials = true
const instance = axios.create({
  baseURL: 'http://localhost:8080',
  timeout: 5000
})

// axios请求拦截器
instance.interceptors.request.use(config => {
  return config
}, e => Promise.reject(e))


// axios 响应拦截器
instance.interceptors.response.use(
  (response) => {
    const res = response.data;

    // 后端返回的 Result 格式处理
    if (res.code === 1) {
      // 成功：返回 data 数据部分
      return res.data;
    } else {
      // 失败：弹出错误信息
      ElMessage.error(res.msg || '请求失败');
      return Promise.reject(new Error(res.msg || '请求失败'));
    }
  },
  (error) => {
    const { response } = error;
    if (response) {
      if (response.status === 401) {
        // 未认证：跳转到登录页
        const router = useRouter();
        router.push('/login');
        ElMessage.error('未登录或登录已过期，请重新登录');
      }
      else if (response.status === 403) {
        // 未授权：弹出提示信息
        ElMessage.error('权限不足');
      }
      else {
        // 其他 HTTP 错误：弹出状态码和提示信息
        ElMessage.error(`HTTP 错误：${response.status} - ${response.statusText}`);
      }
    } else {
      // 网络异常或超时
      ElMessage.error(error.message || '网络异常，请稍后重试');
    }
    return Promise.reject(error);
  }
);
export default instance
