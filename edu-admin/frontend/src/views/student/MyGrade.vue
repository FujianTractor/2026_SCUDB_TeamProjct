<template>
  <div class="page">
    <h1 class="page-title">我的成绩</h1>
    <div class="metric-grid">
      <div v-for="item in credits" :key="item.student_no" class="metric">
        <span>{{ item.student_name }}</span>
        <strong>{{ item.completed_credit || 0 }} 学分</strong>
        <small>平均绩点 {{ item.avg_grade_point || '-' }}</small>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { reportApi } from '../../api/modules'
import { useAuthStore } from '../../stores/auth'

const auth = useAuthStore()
const credits = ref([])
onMounted(async () => {
  credits.value = await reportApi.completedCredits({ studentId: auth.user?.relatedId || 1 })
})
</script>
