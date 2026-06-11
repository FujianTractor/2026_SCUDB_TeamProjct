<template><CrudPage title="用户管理" endpoint="/users" :fields="fields" /></template>
<script setup>
import { onMounted, ref } from 'vue'
import CrudPage from '../../components/CrudPage.vue'
import { crudApi } from '../../api/modules'

const teacherMap = ref(new Map())
const studentMap = ref(new Map())

function formatRelatedUser(row) {
  if (!row.relatedId) return '无'
  if (row.userType === 'teacher') {
    return `教师：${teacherMap.value.get(row.relatedId) || row.relatedId}`
  }
  if (row.userType === 'student') {
    return `学生：${studentMap.value.get(row.relatedId) || row.relatedId}`
  }
  return '管理员'
}

const fields = [
  { prop: 'id', label: 'ID', edit: false },
  { prop: 'username', label: '用户名' },
  { prop: 'passwordHash', label: '密码哈希', table: false },
  { prop: 'realName', label: '真实姓名' },
  {
    prop: 'userType',
    label: '用户类型',
    default: 'student',
    options: [
      { label: '管理员', value: 'admin' },
      { label: '教师', value: 'teacher' },
      { label: '学生', value: 'student' }
    ]
  },
  { prop: 'relatedId', label: '关联对象（教师/学生）', type: 'number', formatter: formatRelatedUser },
  {
    prop: 'userStatus',
    label: '状态',
    default: 'active',
    options: [
      { label: '启用', value: 'active' },
      { label: '禁用', value: 'disabled' }
    ]
  }
]

onMounted(async () => {
  const [teachers, students] = await Promise.all([
    crudApi('/teachers').page({ pageNum: 1, pageSize: 100 }),
    crudApi('/students').page({ pageNum: 1, pageSize: 100 })
  ])

  teacherMap.value = new Map((teachers.records || []).map((item) => [item.id, `${item.teacherName}（${item.teacherNo}）`]))
  studentMap.value = new Map((students.records || []).map((item) => [item.id, `${item.studentName}（${item.studentNo}）`]))
})
</script>
