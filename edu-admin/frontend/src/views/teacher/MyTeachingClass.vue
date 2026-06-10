<template>
  <div class="page">
  
    <CrudPage title="我的授课" endpoint="/teaching-classes" :fields="fields">
    
      <template #action="{ row }">
        <el-button type="primary" link @click="openRoster(row)">查看名单</el-button>
      </template>
    </CrudPage>

    
    <el-dialog v-model="dialogVisible" title="选课学生名单" width="600px">
      <el-table :data="rosterList" border style="width: 100%">
        <el-table-column prop="studentNo" label="学号" />
        <el-table-column prop="studentName" label="姓名" />
        <el-table-column prop="className" label="行政班" />
        <el-table-column prop="status" label="状态" />
      </el-table>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import CrudPage from '../../components/CrudPage.vue'

import { courseSelectionApi } from '../../api/modules' 

const fields = [
  { prop: 'id', label: 'ID', edit: false },
  { prop: 'courseId', label: '课程ID', type: 'number', edit: false },
  { prop: 'teacherId', label: '教师ID', type: 'number', edit: false },
  { prop: 'semesterId', label: '学期ID', type: 'number', edit: false },
  { prop: 'teachingClassCode', label: '教学班代码', edit: false },
  { prop: 'capacity', label: '容量', type: 'number', edit: false },
  { prop: 'selectedCount', label: '已选人数', type: 'number', edit: false }
]


const dialogVisible = ref(false)
const rosterList = ref([])

const openRoster = async (row) => {
  try {
   
    const res = await courseSelectionApi.roster({ teachingClassId: row.id })
    rosterList.value = res || []
    dialogVisible.value = true
  } catch (error) {
    console.error(error)
    ElMessage.
