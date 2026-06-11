<template><CrudPage title="班级管理" endpoint="/classes" :fields="fields" /></template>
<script setup>
import { onMounted, ref } from 'vue'
import CrudPage from '../../components/CrudPage.vue'
import { crudApi } from '../../api/modules'

const fields = ref([
  { prop: 'id', label: 'ID', edit: false },
  { prop: 'majorId', label: '专业', type: 'number', options: [] },
  { prop: 'classCode', label: '班级代码' },
  { prop: 'className', label: '班级名称' },
  { prop: 'gradeYear', label: '年级', type: 'year' }
])

onMounted(async () => {
  const data = await crudApi('/majors').page({ pageNum: 1, pageSize: 100 })
  fields.value[1].options = (data.records || []).map((item) => ({
    label: item.majorName,
    value: item.id
  }))
})
</script>
