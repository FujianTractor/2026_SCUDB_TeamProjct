<template>
  <div class="page">
    <h1 class="page-title">我的授课</h1>
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

      <el-table :data="rows" border empty-text="暂无授课教学班">
        <el-table-column prop="courseName" label="课程名称" min-width="150" />
        <el-table-column prop="courseCode" label="课程编号" min-width="120" />
        <el-table-column prop="teachingClassCode" label="教学班" min-width="150" />
        <el-table-column prop="semesterName" label="学期" min-width="170" />
        <el-table-column prop="capacity" label="容量" width="90" />
        <el-table-column prop="selectedCount" label="已选人数" width="100" />
        <el-table-column prop="classStatus" label="状态" width="100" />
        <el-table-column label="操作" fixed="right" width="120">
          <template #default="{ row }">
            <el-button type="primary" link @click="openRoster(row)">查看名单</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="720px">
      <el-table :data="rosterList" border style="width: 100%" empty-text="暂无选课学生">
        <el-table-column prop="studentNo" label="学号" />
        <el-table-column prop="studentName" label="姓名" />
        <el-table-column prop="className" label="行政班" />
        <el-table-column prop="status" label="状态" />
      </el-table>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { courseSelectionApi, teachingClassApi } from '../../api/modules'
import { useAuthStore } from '../../stores/auth'

const auth = useAuthStore()
const keyword = ref('')
const rows = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('选课学生名单')
const rosterList = ref([])

async function load() {
  rows.value = await teachingClassApi.options({
    teacherId: auth.user?.relatedId,
    keyword: keyword.value || undefined
  })
}

async function openRoster(row) {
  try {
    const res = await courseSelectionApi.roster({ teachingClassId: row.id })
    rosterList.value = res || []
    dialogTitle.value = `${row.courseName} - ${row.teachingClassCode} 选课学生名单`
    dialogVisible.value = true
  } catch (error) {
    console.error(error)
    ElMessage.error('获取学生名单失败')
  }
}

onMounted(load)
</script>
