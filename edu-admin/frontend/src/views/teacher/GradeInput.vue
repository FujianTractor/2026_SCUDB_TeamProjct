<template>
  <div class="page">
    <h1 class="page-title">成绩录入</h1>
    <div class="panel">
      <div class="toolbar">
        <el-select
          v-model="teachingClassId"
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
        <el-button @click="load">刷新名单</el-button>
        <el-button type="primary" @click="saveAll">批量保存</el-button>
      </div>

      <el-table :data="rows" border empty-text="暂无选课学生">
        <el-table-column prop="studentNo" label="学号" />
        <el-table-column prop="studentName" label="姓名" />
        <el-table-column prop="className" label="行政班" />
        <el-table-column prop="courseName" label="课程" />
        <el-table-column label="成绩" width="190">
          <template #default="{ row }">
            <el-input-number v-model="row.score" :min="0" :max="100" :precision="2" />
          </template>
        </el-table-column>
        <el-table-column label="操作" width="100" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link @click="saveOne(row)">录入</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { gradeApi, teachingClassApi } from '../../api/modules'
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
  if (teachingClassOptions.value.length > 0 && !teachingClassId.value) {
    teachingClassId.value = teachingClassOptions.value[0].id
    await load()
  }
}

async function load() {
  if (!teachingClassId.value) {
    rows.value = []
    return
  }

  rows.value = await gradeApi.inputRoster({
    teacherId: auth.user?.relatedId,
    teachingClassId: teachingClassId.value
  })
}

function buildGradePayload(targetRows) {
  return targetRows
    .filter((row) => row.score !== null && row.score !== undefined && row.score !== '')
    .map((row) => ({ selectionId: row.selectionId, score: row.score }))
}

async function saveOne(row) {
  const grades = buildGradePayload([row])
  if (grades.length === 0) {
    ElMessage.warning('请先填写成绩')
    return
  }
  await gradeApi.batch(grades)
  ElMessage.success('成绩已录入')
  await load()
}

async function saveAll() {
  const grades = buildGradePayload(rows.value)
  if (grades.length === 0) {
    ElMessage.warning('请先填写成绩')
    return
  }
  await gradeApi.batch(grades)
  ElMessage.success('成绩已批量保存')
  await load()
}

onMounted(loadTeachingClasses)
</script>
