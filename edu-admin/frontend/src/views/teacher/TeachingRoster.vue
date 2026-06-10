<template>
  <div class="page">
    <h1 class="page-title">教学班学生名单</h1>
    <div class="panel">
      
      
      <div class="toolbar" style="margin-bottom: 15px; display: flex; align-items: center;">
   
        <el-select 
          v-model="selectedTeachingClassId" 
          placeholder="请选择或搜索教学班" 
          filterable 
          clearable
          style="width: 250px; margin-right: 10px;"
          @change="handleClassChange"
        >
          <el-option 
            v-for="item in teachingClassOptions" 
            :key="item.id" 
            :label="`${item.teachingClassCode} (${item.courseName})`" 
            :value="item.id" 
          />
        </el-select>

       
        <el-input 
          v-model="searchKeyword" 
          placeholder="请输入学生姓名或学号" 
          clearable 
          style="width: 250px; margin-right: 10px;"
          @keyup.enter="load"
        />
        
    
        <el-button type="primary" @click="load">查询</el-button>
        <el-button @click="resetSearch">重置</el-button>
      </div>

   
      <el-table :data="rows" border style="width: 100%">
        <el-table-column prop="studentNo" label="学号" />
        <el-table-column prop="studentName" label="姓名" />
        <el-table-column prop="className" label="行政班" />
        <el-table-column prop="status" label="状态" />
      </el-table>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'

import { courseSelectionApi } from '../../api/modules' 

const selectedTeachingClassId = ref(null) 
const searchKeyword = ref('')             
const rows = ref([])                    


const teachingClassOptions = ref([])

async function load() {
  if (!selectedTeachingClassId.value) {
    ElMessage.warning('请先选择一个教学班')
    return
  }
  
  try {
    const res = await courseSelectionApi.roster({ 
      teachingClassId: selectedTeachingClassId.value,
      keyword: searchKeyword.value
    })
    rows.value = res || []
  } catch (error) {
    console.error(error)
    ElMessage.error('获取学生名单失败')
  }
}


function handleClassChange() {
  searchKeyword.value = ''
  load()
}


function resetSearch() {
  searchKeyword.value = ''
  if (selectedTeachingClassId.value) {
    load()
  } else {
    rows.value = []
  }
}

onMounted(async () => {

  // 例如：teachingClassOptions.value = await teachingClassApi.myClasses()
})
</script>
