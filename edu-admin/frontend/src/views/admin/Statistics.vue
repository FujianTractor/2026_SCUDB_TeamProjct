<template>
  <div class="page">
    <h1 class="page-title">统计报表</h1>
    <div class="metric-grid">
      <div v-for="item in stats" :key="item.metric" class="metric">
        <span>{{ labels[item.metric] || item.metric }}</span>
        <strong>{{ item.value }}</strong>
      </div>
    </div>
    <div ref="chartRef" class="panel chart"></div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import * as echarts from 'echarts'
import { reportApi } from '../../api/modules'

const chartRef = ref()
const stats = ref([])
const labels = { studentCount: '学生', teacherCount: '教师', courseCount: '课程', selectionCount: '选课' }

onMounted(async () => {
  stats.value = await reportApi.dashboard()
  const chart = echarts.init(chartRef.value)
  chart.setOption({
    xAxis: { type: 'category', data: stats.value.map((item) => labels[item.metric] || item.metric) },
    yAxis: { type: 'value' },
    series: [{ type: 'bar', data: stats.value.map((item) => item.value), itemStyle: { color: '#2563eb' } }]
  })
})
</script>

<style scoped>
.chart {
  height: 360px;
  margin-top: 18px;
}
</style>
