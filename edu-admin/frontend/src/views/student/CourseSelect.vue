<template>
  <div class="page">
    <h1 class="page-title">可选课程</h1>
    <div class="panel">
      <div class="toolbar">
        <el-input
          v-model="keyword"
          placeholder="课程名称、课程编号、教师或教学班"
          clearable
          style="max-width: 360px"
          @keyup.enter="load"
          @clear="load"
        />
        <el-button type="primary" @click="load">查询</el-button>
      </div>
      <el-table :data="rows" border empty-text="暂无可选课程">
        <el-table-column prop="courseName" label="课程名称" min-width="150" />
        <el-table-column prop="courseCode" label="课程编号" min-width="120" />
        <el-table-column prop="teacherName" label="任课教师" min-width="120" />
        <el-table-column prop="semesterName" label="学期" min-width="170" />
        <el-table-column prop="teachingClassCode" label="教学班" min-width="150" />
        <el-table-column prop="capacity" label="容量" width="90" />
        <el-table-column prop="selectedCount" label="已选" width="90" />
        <el-table-column prop="classStatus" label="状态" width="90" />
        <el-table-column label="操作" fixed="right" width="100">
          <template #default="{ row }">
            <el-button
              type="primary"
              :disabled="row.classStatus !== 'open' || row.selectedCount >= row.capacity"
              @click="select(row)"
            >
              选课
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { courseSelectionApi, teachingClassApi } from '../../api/modules'
import { useAuthStore } from '../../stores/auth'

const auth = useAuthStore()
const rows = ref([])
const keyword = ref('')

async function load() {
  rows.value = await teachingClassApi.options({
    keyword: keyword.value || undefined,
    openOnly: true
  })
}

async function select(row) {
  if (!auth.user?.relatedId) {
    ElMessage.error('用户信息缺失，请重新登录')
    return
  }

  try {
    await courseSelectionApi.select({
      studentId: auth.user.relatedId,
      teachingClassId: row.id
    })
    ElMessage.success('选课成功')
    load()
  } catch (error) {
    console.error(error)
  }
}

onMounted(load)
</script>
