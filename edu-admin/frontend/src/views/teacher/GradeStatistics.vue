<template>
  <div class="page">
    <h1 class="page-title">成绩统计</h1>
    <div class="panel">
      <div class="toolbar">
        <el-select
          v-model="teachingClassId"
          placeholder="全部授课教学班"
          clearable
          filterable
          style="width: 360px"
          @change="load"
          @clear="load"
        >
          <el-option
            v-for="item in teachingClassOptions"
            :key="item.id"
            :label="formatTeachingClass(item)"
            :value="item.id"
          />
        </el-select>
        <el-button type="primary" @click="load">查询</el-button>
      </div>

      <el-table :data="rows" border empty-text="暂无成绩统计">
        <el-table-column prop="course_code" label="课程编号" min-width="120" />
        <el-table-column prop="course_name" label="课程名称" min-width="150" />
        <el-table-column prop="teaching_class_code" label="教学班" min-width="150" />
        <el-table-column prop="submitted_count" label="已录入人数" width="110" />
        <el-table-column prop="avg_score" label="平均分" width="100" />
        <el-table-column prop="max_score" label="最高分" width="100" />
        <el-table-column prop="min_score" label="最低分" width="100" />
        <el-table-column prop="pass_rate" label="及格率(%)" width="110" />
      </el-table>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { reportApi, teachingClassApi } from '../../api/modules'
import { useAuthStore } from '../../stores/auth'

const auth = useAuthStore()
const teachingClassId = ref(null)
const teachingClassOptions = ref([])
const rows = ref([])

function formatTeachingClass(item) {
  return `${item.courseName || '课程'} - ${item.teachingClassCode}`
}

async function loadTeachingClasses() {
  teachingClassOptions.value = await teachingClassApi.options({ teacherId: auth.user?.relatedId })
}

async function load() {
  rows.value = await reportApi.gradeStatistics({
    teacherId: auth.user?.relatedId,
    teachingClassId: teachingClassId.value || undefined
  })
}

onMounted(async () => {
  await loadTeachingClasses()
  await load()
})
</script>
