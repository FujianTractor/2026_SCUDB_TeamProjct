<template><CrudPage title="学生管理" endpoint="/students" :fields="fields" /></template>
<script setup>
import CrudPage from '../../components/CrudPage.vue'
const fields = [
  { prop: 'id', label: 'ID', edit: false },
  { prop: 'classId', label: '班级ID', type: 'number' },
  { prop: 'studentNo', label: '学号' },
  { prop: 'studentName', label: '姓名' },
  { prop: 'gender', label: '性别', options: [{ label: '男', value: 'male' }, { label: '女', value: 'female' }] },
  { prop: 'phone', label: '电话' },
  { prop: 'email', label: '邮箱' },
  { prop: 'enrollmentYear', label: '入学年份', type: 'number' },
  { prop: 'studentStatus', label: '状态', default: 'normal', options: [{ label: '正常', value: 'normal' }, { label: '休学', value: 'suspended' }, { label: '毕业', value: 'graduated' }] }
]
</script>
