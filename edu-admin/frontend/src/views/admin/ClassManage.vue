<template><CrudPage title="班级管理" endpoint="/classes" :fields="fields" /></template>
<script setup>
import CrudPage from '../../components/CrudPage.vue'
const fields = [
  { prop: 'id', label: 'ID', edit: false },
  { prop: 'majorId', label: '专业ID', type: 'number' },
  { prop: 'classCode', label: '班级代码' },
  { prop: 'className', label: '班级名称' },
  { prop: 'gradeYear', label: '年级', type: 'number' }
]
</script>
