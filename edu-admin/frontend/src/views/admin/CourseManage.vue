<template><CrudPage title="课程管理" endpoint="/courses" :fields="fields" /></template>
<script setup>
import CrudPage from '../../components/CrudPage.vue'
const fields = [
  { prop: 'id', label: 'ID', edit: false },
  { prop: 'collegeId', label: '学院ID', type: 'number' },
  { prop: 'courseCode', label: '课程编号' },
  { prop: 'courseName', label: '课程名称' },
  { prop: 'credit', label: '学分', type: 'number' },
  { prop: 'courseType', label: '性质', default: 'required', options: [{ label: '必修', value: 'required' }, { label: '选修', value: 'elective' }, { label: '通识', value: 'general' }] },
  { prop: 'courseStatus', label: '状态', default: 'active', options: [{ label: '启用', value: 'active' }, { label: '停用', value: 'inactive' }] }
]
</script>
