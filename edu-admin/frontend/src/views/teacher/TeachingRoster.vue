<template>
  <div class="page">
    <h1 class="page-title">教学班学生名单</h1>
    <div class="panel">
      <div class="toolbar">
        <el-input-number v-model="teachingClassId" placeholder="教学班ID" />
        <el-button type="primary" @click="load">查询</el-button>
      </div>
      <el-table :data="rows" border>
        <el-table-column prop="teaching_class_code" label="教学班" />
        <el-table-column prop="course_name" label="课程" />
        <el-table-column prop="student_no" label="学号" />
        <el-table-column prop="student_name" label="姓名" />
        <el-table-column prop="class_name" label="班级" />
      </el-table>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { reportApi } from '../../api/modules'
import { useAuthStore } from '../../stores/auth'

const auth = useAuthStore()
const teachingClassId = ref()
const rows = ref([])

async function load() {
  rows.value = await reportApi.roster({ teacherId: auth.user?.relatedId || 1, teachingClassId: teachingClassId.value })
}
</script>
