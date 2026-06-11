<template><CrudPage title="成绩管理" endpoint="/grades" :fields="fields" /></template>
<script setup>
import { onMounted, ref } from 'vue'
import CrudPage from '../../components/CrudPage.vue'
import { courseSelectionApi } from '../../api/modules'

const fields = ref([
  { prop: 'id', label: 'ID', edit: false },
  { prop: 'selectionId', label: '选课记录', type: 'number', options: [] },
  { prop: 'score', label: '成绩', type: 'number' },
  { prop: 'gradePoint', label: '绩点', type: 'number' },
  {
    prop: 'gradeStatus',
    label: '状态',
    default: 'submitted',
    options: [
      { label: '未提交', value: 'unsubmitted' },
      { label: '已提交', value: 'submitted' }
    ]
  }
])

onMounted(async () => {
  const data = await courseSelectionApi.options()
  fields.value[1].options = (data || []).map((item) => ({
    label: `${item.studentName}（${item.studentNo}） - ${item.courseName} - ${item.teachingClassCode} - ${item.semesterName}`,
    value: item.selectionId
  }))
})
</script>
