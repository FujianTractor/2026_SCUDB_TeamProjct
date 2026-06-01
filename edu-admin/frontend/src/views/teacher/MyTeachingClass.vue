<template><CrudPage title="我的授课" endpoint="/teaching-classes" :fields="fields" /></template>
<script setup>
import CrudPage from '../../components/CrudPage.vue'
const fields = [
  { prop: 'id', label: 'ID', edit: false },
  { prop: 'courseId', label: '课程ID', type: 'number', edit: false },
  { prop: 'teacherId', label: '教师ID', type: 'number', edit: false },
  { prop: 'semesterId', label: '学期ID', type: 'number', edit: false },
  { prop: 'teachingClassCode', label: '教学班代码', edit: false },
  { prop: 'capacity', label: '容量', type: 'number', edit: false },
  { prop: 'selectedCount', label: '已选人数', type: 'number', edit: false }
]
</script>
