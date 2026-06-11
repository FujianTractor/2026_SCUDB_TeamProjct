<template><CrudPage title="专业管理" endpoint="/majors" :fields="fields" /></template>
<script setup>
import { onMounted, ref } from 'vue'
import CrudPage from '../../components/CrudPage.vue'
import { crudApi } from '../../api/modules'

const fields = ref([
  { prop: 'id', label: 'ID', edit: false },
  { prop: 'collegeId', label: '学院', type: 'number', options: [] },
  { prop: 'majorCode', label: '专业代码' },
  { prop: 'majorName', label: '专业名称' },
  {
    prop: 'degreeType',
    label: '培养层次',
    default: '本科',
    options: [
      { label: '本科', value: '本科' },
      { label: '硕士', value: '硕士' },
      { label: '博士', value: '博士' }
    ]
  }
])

onMounted(async () => {
  const data = await crudApi('/colleges').page({ pageNum: 1, pageSize: 100 })
  fields.value[1].options = (data.records || []).map((item) => ({
    label: item.collegeName,
    value: item.id
  }))
})
</script>
