import axios from 'axios'
import { ElMessage } from 'element-plus'

const request = axios.create({
  baseURL: '/api',
  timeout: 15000
})

request.interceptors.request.use((config) => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

request.interceptors.response.use(
  (response) => {
    const body = response.data
    if (body?.code !== 200) {
      ElMessage.error(body?.message || '请求失败')
      return Promise.reject(new Error(body?.message || '请求失败'))
    }
    return body.data
  },
  (error) => {
    const message = error.response?.data?.message
      || (error.code === 'ECONNABORTED' ? '请求超时，请稍后重试' : '网络异常，请检查后端服务是否正常运行')
    ElMessage.error(message)
    return Promise.reject(new Error(message))
  }
)

export default request
