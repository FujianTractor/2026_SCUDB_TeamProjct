<template>
  <div class="page">
    <h1 class="page-title">我的成绩</h1>
    
    <!-- 新增：查询工具栏 -->
    <div class="panel" style="margin-bottom: 16px;">
      <el-form :inline="true">
        <el-form-item label="课程名称">
          <el-input v-model="searchKeyword" placeholder="请输入课程名称" clearable />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="fetchGrades">查 询</el-button>
        </el-form-item>
      </el-form>
    </div>

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
// 新增：用于双向绑定搜索框的内容
const searchKeyword = ref('')

// 将获取数据的逻辑抽离成独立函数，方便初次加载和点击查询时复用
const fetchGrades = async () => {
  if (!auth.user?.relatedId) {
    ElMessage.error('用户信息缺失，请重新登录')
    return
  }
  
  try {
    // 传递 studentId 以及可选的 courseName 参数给后端
    const res = await gradeApi.myGrades({ 
      studentId: auth.user.relatedId,
      courseName: searchKeyword.value || undefined
    })
    grades.value = res || []
  } catch (error) {
    console.error(error)
    ElMessage.error('获取成绩失败')
  }
}

onMounted(() => {
  fetchGrades()
})
</script>
