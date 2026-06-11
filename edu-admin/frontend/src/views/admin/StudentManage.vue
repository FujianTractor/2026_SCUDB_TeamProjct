<template><CrudPage title="学生管理" endpoint="/students" :fields="fields" /></template>
<script setup>
import { onMounted, ref } from 'vue'
import CrudPage from '../../components/CrudPage.vue'
import { crudApi } from '../../api/modules'

const fields = ref([
  { prop: 'id', label: 'ID', edit: false },
  { prop: 'classId', label: '行政班', type: 'number', options: [] },
  { prop: 'studentNo', label: '学号' },
  { prop: 'studentName', label: '姓名' },
  { prop: 'gender', label: '性别', options: [{ label: '男', value: 'male' }, { label: '女', value: 'female' }] },
  { prop: 'phone', label: '电话' },
  { prop: 'email', label: '邮箱' },
  { prop: 'enrollmentYear', label: '入学年份', type: 'year' },
  {
    prop: 'studentStatus',
    label: '状态',
    default: 'normal',
    options: [
      { label: '正常', value: 'normal' },
      { label: '休学', value: 'suspended' },
      { label: '毕业', value: 'graduated' }
    ]
  }
])

onMounted(async () => {
  const data = await crudApi('/classes').page({ pageNum: 1, pageSize: 100 })
  fields.value[1].options = (data.records || []).map((item) => ({
    label: item.className,
    value: item.id
  }))
})
</script>
