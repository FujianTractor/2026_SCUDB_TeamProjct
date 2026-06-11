<template>
  <div class="page">
    <h1 class="page-title">教学班学生名单</h1>
    <div class="panel">
      <div class="toolbar">
        <el-select
          v-model="selectedTeachingClassId"
          placeholder="请选择课程/教学班"
          filterable
          style="width: 340px"
          @change="load"
        >
          <el-option
            v-for="item in teachingClassOptions"
            :key="item.id"
            :label="formatTeachingClass(item)"
            :value="item.id"
          />
        </el-select>

        <el-input
          v-model="searchKeyword"
          placeholder="输入学生姓名或学号"
          clearable
          style="width: 260px"
          @keyup.enter="load"
          @clear="load"
        />

        <el-button type="primary" @click="load">查询</el-button>
        <el-button @click="resetSearch">重置</el-button>
      </div>

      <el-table :data="rows" border style="width: 100%" empty-text="暂无选课学生">
        <el-table-column prop="studentNo" label="学号" />
        <el-table-column prop="studentName" label="姓名" />
        <el-table-column prop="className" label="行政班" />
        <el-table-column prop="status" label="状态" />
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
const selectedTeachingClassId = ref(null)
const searchKeyword = ref('')
const rows = ref([])
const teachingClassOptions = ref([])

function formatTeachingClass(item) {
  return `${item.courseName || '课程'} - ${item.teachingClassCode}`
}

async function loadTeachingClasses() {
  teachingClassOptions.value = await teachingClassApi.options({ teacherId: auth.user?.relatedId })
  if (teachingClassOptions.value.length > 0 && !selectedTeachingClassId.value) {
    selectedTeachingClassId.value = teachingClassOptions.value[0].id
    await load()
  }
}

async function load() {
  if (!selectedTeachingClassId.value) {
    rows.value = []
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

function resetSearch() {
  searchKeyword.value = ''
  load()
}

onMounted(loadTeachingClasses)
</script>
