<template><CrudPage title="成绩管理" endpoint="/grades" :fields="fields" /></template>
<script setup>
import CrudPage from '../../components/CrudPage.vue'
const fields = [
  { prop: 'id', label: 'ID', edit: false },
  { prop: 'selectionId', label: '选课ID', type: 'number' },
  { prop: 'score', label: '成绩', type: 'number' },
  { prop: 'gradePoint', label: '绩点', type: 'number' },
  { prop: 'gradeStatus', label: '状态', default: 'submitted' }
]
</script>
