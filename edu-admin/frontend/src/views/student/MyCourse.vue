<template>
  <div class="page">
    <h1 class="page-title">我的选课</h1>
    <div class="panel">
      <el-table :data="rows" border>
        <el-table-column prop="id" label="选课ID" />
        <el-table-column prop="teachingClassId" label="教学班ID" />
        <el-table-column prop="selectionStatus" label="状态" />
        <el-table-column prop="selectedAt" label="选课时间" />
        <el-table-column label="操作">
          <template #default="{ row }">
            <el-button type="danger" :disabled="row.selectionStatus !== 'selected'" @click="drop(row)">退课</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { courseSelectionApi } from '../../api/modules'
import { useAuthStore } from '../../stores/auth'

const auth = useAuthStore()
const rows = ref([])
async function load() {
  const data = await courseSelectionApi.page({ studentId: auth.user?.relatedId || 1, pageNum: 1, pageSize: 100 })
  rows.value = data.records || []
}
async function drop(row) {
  await courseSelectionApi.drop({ studentId: auth.user?.relatedId || 1, teachingClassId: row.teachingClassId })
  ElMessage.success('退课成功')
  load()
}
onMounted(load)
</script>
