<template>
  <div class="page">
    <h1 class="page-title">我的选课</h1>
    <div class="panel">
      <el-table :data="rows" border empty-text="暂无选课记录">
        <el-table-column prop="courseName" label="课程名称" min-width="150" />
        <el-table-column prop="courseCode" label="课程编号" min-width="120" />
        <el-table-column prop="credit" label="学分" width="90" />
        <el-table-column prop="teacherName" label="任课教师" min-width="120" />
        <el-table-column prop="semesterName" label="学期" min-width="170" />
        <el-table-column prop="teachingClassCode" label="教学班" min-width="150" />
        <el-table-column prop="selectionStatus" label="状态" width="100" />
        <el-table-column prop="selectedAt" label="选课时间" min-width="170" />
        <el-table-column label="操作" fixed="right" width="100">
          <template #default="{ row }">
            <el-popconfirm title="确认退课？" @confirm="drop(row)">
              <template #reference>
                <el-button type="danger" link :disabled="row.selectionStatus !== 'selected'">退课</el-button>
              </template>
            </el-popconfirm>
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
  if (!auth.user?.relatedId) {
    rows.value = []
    return
  }
  rows.value = await courseSelectionApi.myCourses({ studentId: auth.user.relatedId })
}

async function drop(row) {
  if (!auth.user?.relatedId) return
  await courseSelectionApi.drop({ studentId: auth.user.relatedId, teachingClassId: row.teachingClassId })
  ElMessage.success('退课成功')
  load()
}

onMounted(load)
</script>
