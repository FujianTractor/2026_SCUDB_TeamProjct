<template>
  <div class="page">
    <h1 class="page-title">我的成绩</h1>

    <div class="panel" style="margin-bottom: 16px">
      <el-form :inline="true">
        <el-form-item label="课程名称">
          <el-input v-model="courseName" placeholder="输入课程名称" clearable @keyup.enter="fetchGrades" />
        </el-form-item>
        <el-form-item label="学期">
          <el-input v-model="semester" placeholder="输入学期关键字" clearable @keyup.enter="fetchGrades" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="fetchGrades">查询</el-button>
          <el-button @click="resetSearch">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="panel">
      <el-table :data="grades" border style="width: 100%" empty-text="暂无成绩记录">
        <el-table-column prop="semester" label="学期" min-width="170" />
        <el-table-column prop="courseName" label="课程名称" min-width="150" />
        <el-table-column prop="credits" label="学分" width="90" />
        <el-table-column prop="score" label="成绩" width="100" />
      </el-table>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { gradeApi } from '../../api/modules'
import { useAuthStore } from '../../stores/auth'

const auth = useAuthStore()
const grades = ref([])
const courseName = ref('')
const semester = ref('')

async function fetchGrades() {
  if (!auth.user?.relatedId) {
    ElMessage.error('用户信息缺失，请重新登录')
    return
  }

  try {
    grades.value = await gradeApi.myGrades({
      studentId: auth.user.relatedId,
      courseName: courseName.value || undefined,
      semester: semester.value || undefined
    })
  } catch (error) {
    console.error(error)
    ElMessage.error('获取成绩失败')
  }
}

function resetSearch() {
  courseName.value = ''
  semester.value = ''
  fetchGrades()
}

onMounted(fetchGrades)
</script>
