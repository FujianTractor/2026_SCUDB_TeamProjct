<template>
  <div class="login">
    <div class="login-card">
      <h1>SCU EduAdmin</h1>
      <p>高校教务管理系统</p>
      <el-form @keyup.enter="submit">
        <el-form-item>
          <el-input v-model="form.username" placeholder="用户名" size="large" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.password" placeholder="密码" type="password" show-password size="large" />
        </el-form-item>
        <el-button type="primary" size="large" style="width: 100%" @click="submit">登录</el-button>
      </el-form>
      <div class="hint">admin / teacher001 / student001，密码 password</div>
    </div>
  </div>
</template>

<script setup>
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/auth'

const router = useRouter()
const auth = useAuthStore()
const form = reactive({ username: 'admin', password: 'password' })

async function submit() {
  await auth.login(form)
  router.push('/dashboard')
}
</script>

<style scoped>
.login {
  min-height: 100vh;
  display: grid;
  place-items: center;
  background: linear-gradient(135deg, #0f172a, #1f4f82);
}
.login-card {
  width: min(420px, calc(100vw - 32px));
  padding: 34px;
  border-radius: 8px;
  background: #fff;
  box-shadow: 0 24px 80px rgba(0,0,0,.22);
}
h1 {
  margin: 0;
  font-size: 28px;
}
p {
  margin: 8px 0 26px;
  color: #64748b;
}
.hint {
  margin-top: 16px;
  color: #64748b;
  font-size: 13px;
}
</style>
