<template>
  <div class="page">
    <h1 class="page-title">我的课表</h1>
    <div class="panel">
      <el-table :data="tableRows" border>
        <el-table-column prop="section" label="节次" width="90" />
        <el-table-column v-for="day in 7" :key="day" :prop="`day${day}`" :label="weekdays[day - 1]" />
      </el-table>
    </div>
  </div>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { reportApi } from '../../api/modules'
import { useAuthStore } from '../../stores/auth'

const auth = useAuthStore()
const rows = ref([])
const weekdays = ['周一', '周二', '周三', '周四', '周五', '周六', '周日']

const tableRows = computed(() => Array.from({ length: 12 }, (_, index) => {
  const section = index + 1
  const row = { section }
  for (let day = 1; day <= 7; day += 1) {
    const courses = rows.value.filter((item) => item.weekday === day && item.start_section <= section && item.end_section >= section)
    row[`day${day}`] = courses.map((item) => `${item.course_name}@${item.building}${item.room_no}`).join('\n')
  }
  return row
}))

onMounted(async () => {
  rows.value = await reportApi.schedule({ studentId: auth.user?.relatedId || 1 })
})
</script>
