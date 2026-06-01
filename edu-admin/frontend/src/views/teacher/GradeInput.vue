<template>
  <div class="page">
    <h1 class="page-title">成绩录入</h1>
    <div class="panel">
      <div class="toolbar">
        <el-input-number v-model="teachingClassId" placeholder="教学班ID" />
        <el-button @click="load">加载名单</el-button>
        <el-button type="primary" @click="save">批量保存</el-button>
      </div>
      <el-table :data="rows" border>
        <el-table-column prop="student_no" label="学号" />
        <el-table-column prop="student_name" label="姓名" />
        <el-table-column prop="course_name" label="课程" />
        <el-table-column label="成绩" width="180">
          <template #default="{ row }">
            <el-input-number v-model="row.score" :min="0" :max="100" />
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import { gradeApi, reportApi } from '../../api/modules'
import { useAuthStore } from '../../stores/auth'

const auth = useAuthStore()
const teachingClassId = ref(1)
const rows = ref([])

async function load() {
  const roster = await reportApi.roster({ teacherId: auth.user?.relatedId || 1, teachingClassId: teachingClassId.value })
  rows.value = roster.map((row) => ({ ...row, selectionId: row.selection_id, score: row.score || 90 }))
}

async function save() {
  await gradeApi.batch(rows.value.map((row) => ({ selectionId: row.selectionId, score: row.score })))
  ElMessage.success('成绩已保存')
}
</script>
