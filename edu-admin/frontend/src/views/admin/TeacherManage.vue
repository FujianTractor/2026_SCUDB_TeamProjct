<template><CrudPage title="教师管理" endpoint="/teachers" :fields="fields" /></template>
<script setup>
import { onMounted, ref } from 'vue'
import CrudPage from '../../components/CrudPage.vue'
import { crudApi } from '../../api/modules'

const fields = ref([
  { prop: 'id', label: 'ID', edit: false },
  { prop: 'collegeId', label: '学院', type: 'number', options: [] },
  { prop: 'teacherNo', label: '工号' },
  { prop: 'teacherName', label: '姓名' },
  { prop: 'gender', label: '性别', options: [{ label: '男', value: 'male' }, { label: '女', value: 'female' }] },
  {
    prop: 'title',
    label: '职称',
    options: [
      { label: '助教', value: '助教' },
      { label: '讲师', value: '讲师' },
      { label: '副教授', value: '副教授' },
      { label: '教授', value: '教授' }
    ]
  },
  { prop: 'phone', label: '电话' },
  { prop: 'email', label: '邮箱' },
  { prop: 'teacherStatus', label: '状态', default: 'active', options: [{ label: '在职', value: 'active' }, { label: '退休', value: 'retired' }] }
])

onMounted(async () => {
  const data = await crudApi('/colleges').page({ pageNum: 1, pageSize: 100 })
  fields.value[1].options = (data.records || []).map((item) => ({
    label: item.collegeName,
    value: item.id
  }))
})
</script>
