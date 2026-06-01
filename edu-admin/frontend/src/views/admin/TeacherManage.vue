<template><CrudPage title="教师管理" endpoint="/teachers" :fields="fields" /></template>
<script setup>
import CrudPage from '../../components/CrudPage.vue'
const fields = [
  { prop: 'id', label: 'ID', edit: false },
  { prop: 'collegeId', label: '学院ID', type: 'number' },
  { prop: 'teacherNo', label: '工号' },
  { prop: 'teacherName', label: '姓名' },
  { prop: 'gender', label: '性别', options: [{ label: '男', value: 'male' }, { label: '女', value: 'female' }] },
  { prop: 'title', label: '职称' },
  { prop: 'phone', label: '电话' },
  { prop: 'email', label: '邮箱' },
  { prop: 'teacherStatus', label: '状态', default: 'active', options: [{ label: '在职', value: 'active' }, { label: '退休', value: 'retired' }] }
]
</script>
