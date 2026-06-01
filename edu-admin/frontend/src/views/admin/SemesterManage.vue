<template><CrudPage title="学期管理" endpoint="/semesters" :fields="fields" /></template>
<script setup>
import CrudPage from '../../components/CrudPage.vue'
const fields = [
  { prop: 'id', label: 'ID', edit: false },
  { prop: 'semesterCode', label: '学期代码' },
  { prop: 'semesterName', label: '学期名称' },
  { prop: 'startDate', label: '开始日期' },
  { prop: 'endDate', label: '结束日期' },
  { prop: 'isCurrent', label: '当前学期', type: 'number', default: 0 }
]
</script>
