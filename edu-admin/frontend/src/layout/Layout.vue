<template>
  <el-container class="shell">
    <el-aside width="232px" class="side">
      <div class="brand">SCU EduAdmin</div>
      <el-menu router :default-active="$route.path" background-color="#111827" text-color="#cbd5e1" active-text-color="#fff">
        <el-menu-item index="/dashboard">首页</el-menu-item>
        <el-sub-menu v-if="auth.hasRole('ADMIN')" index="admin">
          <template #title>管理员</template>
          <el-menu-item v-for="item in adminMenus" :key="item.path" :index="item.path">{{ item.label }}</el-menu-item>
        </el-sub-menu>
        <el-sub-menu v-if="auth.hasRole('TEACHER')" index="teacher">
          <template #title>教师</template>
          <el-menu-item v-for="item in teacherMenus" :key="item.path" :index="item.path">{{ item.label }}</el-menu-item>
        </el-sub-menu>
        <el-sub-menu v-if="auth.hasRole('STUDENT')" index="student">
          <template #title>学生</template>
          <el-menu-item v-for="item in studentMenus" :key="item.path" :index="item.path">{{ item.label }}</el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="topbar">
        <span>{{ auth.user?.realName || auth.user?.username }}</span>
        <el-button text @click="logout">退出</el-button>
      </el-header>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/auth'

const auth = useAuthStore()
const router = useRouter()

const adminMenus = [
  { path: '/admin/colleges', label: '学院管理' },
  { path: '/admin/majors', label: '专业管理' },
  { path: '/admin/classes', label: '班级管理' },
  { path: '/admin/students', label: '学生管理' },
  { path: '/admin/teachers', label: '教师管理' },
  { path: '/admin/courses', label: '课程管理' },
  { path: '/admin/semesters', label: '学期管理' },
  { path: '/admin/classrooms', label: '教室管理' },
  { path: '/admin/teaching-classes', label: '教学班管理' },
  { path: '/admin/selections', label: '选课记录' },
  { path: '/admin/grades', label: '成绩管理' },
  { path: '/admin/users', label: '用户管理' },
  { path: '/admin/statistics', label: '统计报表' }
]
const teacherMenus = [
  { path: '/teacher/classes', label: '我的授课' },
  { path: '/teacher/roster', label: '教学班名单' },
  { path: '/teacher/grade-input', label: '成绩录入' },
  { path: '/teacher/grade-statistics', label: '成绩统计' }
]
const studentMenus = [
  { path: '/student/course-select', label: '可选课程' },
  { path: '/student/my-course', label: '我的选课' },
  { path: '/student/schedule', label: '我的课表' },
  { path: '/student/grade', label: '我的成绩' }
]

function logout() {
  auth.logout()
  router.push('/login')
}
</script>

<style scoped>
.shell {
  min-height: 100vh;
}
.side {
  background: #111827;
}
.brand {
  height: 58px;
  display: flex;
  align-items: center;
  padding: 0 18px;
  color: #fff;
  font-size: 18px;
  font-weight: 800;
  border-bottom: 1px solid rgba(255,255,255,.08);
}
.topbar {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  gap: 14px;
  background: #fff;
  border-bottom: 1px solid #e5e9f2;
}
</style>
