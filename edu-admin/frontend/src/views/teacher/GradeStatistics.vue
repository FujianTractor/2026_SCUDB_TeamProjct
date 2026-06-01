<template>
  <div class="page">
    <h1 class="page-title">成绩统计</h1>
    <div class="panel">
      <div class="toolbar">
        <el-input-number v-model="courseId" placeholder="课程ID" />
        <el-button type="primary" @click="load">查询</el-button>
      </div>
      <el-table :data="rows" border>
        <el-table-column prop="course_code" label="课程编号" />
        <el-table-column prop="course_name" label="课程名称" />
        <el-table-column prop="teaching_class_code" label="教学班" />
        <el-table-column prop="avg_score" label="平均分" />
        <el-table-column prop="max_score" label="最高分" />
        <el-table-column prop="min_score" label="最低分" />
        <el-table-column prop="pass_rate" label="及格率" />
      </el-table>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { reportApi } from '../../api/modules'

const courseId = ref()
const rows = ref([])
async function load() {
  rows.value = await reportApi.gradeStatistics({ courseId: courseId.value })
}
</script>
