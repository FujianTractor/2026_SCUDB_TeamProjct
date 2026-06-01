<template><CrudPage title="选课记录管理" endpoint="/course-selections" :fields="fields" /></template>
<script setup>
import CrudPage from '../../components/CrudPage.vue'
const fields = [
  { prop: 'id', label: 'ID', edit: false },
  { prop: 'studentId', label: '学生ID', type: 'number' },
  { prop: 'teachingClassId', label: '教学班ID', type: 'number' },
  { prop: 'selectionStatus', label: '状态', default: 'selected' },
  { prop: 'selectedAt', label: '选课时间', edit: false },
  { prop: 'droppedAt', label: '退课时间', edit: false }
]
</script>
