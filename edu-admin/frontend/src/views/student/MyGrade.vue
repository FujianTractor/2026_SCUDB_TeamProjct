<template>
  <div class="page">
    <h1 class="page-title">我的成绩</h1>
    
    <!-- 成绩明细表格 -->
    <div class="panel">
      <el-table :data="grades" border style="width: 100%">
        <el-table-column prop="semester" label="学期" />
        <el-table-column prop="courseName" label="课程名称" />
        <el-table-column prop="credits" label="学分" />
        <el-table-column prop="score" label="成绩" />
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

onMounted(async () => {
  if (!auth.user?.relatedId) {
    ElMessage.error('用户信息缺失，请重新登录')
    return
  }
  
  try {
    // 调用刚才新增的 myGrades 接口获取包含学期、课程名、学分的详细数据
    const res = await gradeApi.myGrades({ studentId: auth.user.relatedId })
    grades.value = res || []
  } catch (error) {
    console.error(error)
    ElMessage.error('获取成绩失败')
  }
})
</script>
