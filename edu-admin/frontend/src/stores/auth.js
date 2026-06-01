import { defineStore } from 'pinia'
import { authApi } from '../api/modules'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: localStorage.getItem('token') || '',
    user: JSON.parse(localStorage.getItem('user') || 'null'),
    roles: JSON.parse(localStorage.getItem('roles') || '[]'),
    menus: JSON.parse(localStorage.getItem('menus') || '[]')
  }),
  getters: {
    isLogin: (state) => Boolean(state.token),
    hasRole: (state) => (role) => state.roles.includes(role)
  },
  actions: {
    async login(form) {
      const data = await authApi.login(form)
      this.token = data.token
      this.user = data
      this.roles = data.roles || []
      this.menus = data.menus || []
      localStorage.setItem('token', this.token)
      localStorage.setItem('user', JSON.stringify(data))
      localStorage.setItem('roles', JSON.stringify(this.roles))
      localStorage.setItem('menus', JSON.stringify(this.menus))
    },
    logout() {
      this.token = ''
      this.user = null
      this.roles = []
      this.menus = []
      localStorage.clear()
    }
  }
})
