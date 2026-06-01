<template><CrudPage title="学院管理" endpoint="/colleges" :fields="fields" /></template>
<script setup>
import CrudPage from '../../components/CrudPage.vue'
const fields = [
  { prop: 'id', label: 'ID', edit: false },
  { prop: 'collegeCode', label: '学院代码' },
  { prop: 'collegeName', label: '学院名称' },
  { prop: 'deanName', label: '院长' },
  { prop: 'phone', label: '电话' }
]
</script>
