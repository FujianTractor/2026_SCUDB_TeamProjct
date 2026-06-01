<template><CrudPage title="专业管理" endpoint="/majors" :fields="fields" /></template>
<script setup>
import CrudPage from '../../components/CrudPage.vue'
const fields = [
  { prop: 'id', label: 'ID', edit: false },
  { prop: 'collegeId', label: '学院ID', type: 'number' },
  { prop: 'majorCode', label: '专业代码' },
  { prop: 'majorName', label: '专业名称' },
  { prop: 'degreeType', label: '培养层次', default: '本科' }
]
</script>
