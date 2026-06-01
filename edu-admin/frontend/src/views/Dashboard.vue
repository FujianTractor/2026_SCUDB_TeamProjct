<template>
  <div class="page">
    <h1 class="page-title">首页</h1>
    <div class="metric-grid">
      <div v-for="item in stats" :key="item.metric" class="metric">
        <span>{{ labels[item.metric] || item.metric }}</span>
        <strong>{{ item.value }}</strong>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { reportApi } from '../api/modules'

const stats = ref([])
const labels = {
  studentCount: '学生人数',
  teacherCount: '教师人数',
  courseCount: '课程数量',
  selectionCount: '有效选课'
}

onMounted(async () => {
  stats.value = await reportApi.dashboard()
})
</script>
