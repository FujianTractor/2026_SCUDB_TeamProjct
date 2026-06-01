<template>
  <div class="page">
    <h1 class="page-title">可选课程</h1>
    <div class="panel">
      <div class="toolbar">
        <el-input v-model="keyword" placeholder="课程名或编号" style="max-width: 300px" @keyup.enter="load" />
        <el-button type="primary" @click="load">查询</el-button>
      </div>
      <el-table :data="rows" border>
        <el-table-column prop="id" label="教学班ID" />
        <el-table-column prop="courseId" label="课程ID" />
        <el-table-column prop="teacherId" label="教师ID" />
        <el-table-column prop="capacity" label="容量" />
        <el-table-column prop="selectedCount" label="已选" />
        <el-table-column prop="classStatus" label="状态" />
        <el-table-column label="操作">
          <template #default="{ row }">
            <el-button type="primary" :disabled="row.selectedCount >= row.capacity" @click="select(row)">选课</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { courseSelectionApi, crudApi } from '../../api/modules'
import { useAuthStore } from '../../stores/auth'

const auth = useAuthStore()
const api = crudApi('/teaching-classes')
const rows = ref([])
const keyword = ref('')

async function load() {
  const data = await api.page({ pageNum: 1, pageSize: 100, keyword: keyword.value })
  rows.value = data.records || []
}

async function select(row) {
  await courseSelectionApi.select({ studentId: auth.user?.relatedId || 1, teachingClassId: row.id })
  ElMessage.success('选课成功')
  load()
}

onMounted(load)
</script>
