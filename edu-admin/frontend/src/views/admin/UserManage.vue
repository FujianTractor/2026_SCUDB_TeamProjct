<template><CrudPage title="用户管理" endpoint="/users" :fields="fields" /></template>
<script setup>
import CrudPage from '../../components/CrudPage.vue'
const fields = [
  { prop: 'id', label: 'ID', edit: false },
  { prop: 'username', label: '用户名' },
  { prop: 'passwordHash', label: '密码哈希' },
  { prop: 'realName', label: '真实姓名' },
  { prop: 'userType', label: '用户类型', default: 'student' },
  { prop: 'relatedId', label: '关联ID', type: 'number' },
  { prop: 'userStatus', label: '状态', default: 'active' }
]
</script>
