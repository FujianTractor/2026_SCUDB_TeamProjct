<template><CrudPage title="教学班管理" endpoint="/teaching-classes" :fields="fields" /></template>
<script setup>
import CrudPage from '../../components/CrudPage.vue'
const fields = [
  { prop: 'id', label: 'ID', edit: false },
  { prop: 'courseId', label: '课程ID', type: 'number' },
  { prop: 'teacherId', label: '教师ID', type: 'number' },
  { prop: 'semesterId', label: '学期ID', type: 'number' },
  { prop: 'teachingClassCode', label: '教学班代码' },
  { prop: 'capacity', label: '容量', type: 'number' },
  { prop: 'selectedCount', label: '已选人数', type: 'number', default: 0 },
  { prop: 'classStatus', label: '状态', default: 'open' }
]
</script>
