<template><CrudPage title="教学班管理" endpoint="/teaching-classes" :fields="fields" /></template>
<script setup>
import { onMounted, ref } from 'vue'
import CrudPage from '../../components/CrudPage.vue'
import { crudApi } from '../../api/modules'

const fields = ref([
  { prop: 'id', label: 'ID', edit: false },
  { prop: 'courseId', label: '课程', type: 'number', options: [] },
  { prop: 'teacherId', label: '任课教师', type: 'number', options: [] },
  { prop: 'semesterId', label: '学期', type: 'number', options: [] },
  { prop: 'teachingClassCode', label: '教学班代码' },
  { prop: 'capacity', label: '容量', type: 'number' },
  { prop: 'selectedCount', label: '已选人数', type: 'number', default: 0 },
  {
    prop: 'classStatus',
    label: '状态',
    default: 'open',
    options: [
      { label: '开放', value: 'open' },
      { label: '关闭', value: 'closed' }
    ]
  }
])

onMounted(async () => {
  const [courses, teachers, semesters] = await Promise.all([
    crudApi('/courses').page({ pageNum: 1, pageSize: 100 }),
    crudApi('/teachers').page({ pageNum: 1, pageSize: 100 }),
    crudApi('/semesters').page({ pageNum: 1, pageSize: 100 })
  ])

  fields.value[1].options = (courses.records || []).map((item) => ({
    label: `${item.courseName}（${item.courseCode}）`,
    value: item.id
  }))
  fields.value[2].options = (teachers.records || []).map((item) => ({
    label: `${item.teacherName}（${item.teacherNo}）`,
    value: item.id
  }))
  fields.value[3].options = (semesters.records || []).map((item) => ({
    label: item.semesterName,
    value: item.id
  }))
})
</script>
