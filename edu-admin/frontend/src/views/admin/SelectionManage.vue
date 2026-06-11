<template><CrudPage title="选课记录管理" endpoint="/course-selections" :fields="fields" /></template>
<script setup>
import { onMounted, ref } from 'vue'
import CrudPage from '../../components/CrudPage.vue'
import { crudApi, teachingClassApi } from '../../api/modules'

const fields = ref([
  { prop: 'id', label: 'ID', edit: false },
  { prop: 'studentId', label: '学生', type: 'number', options: [] },
  { prop: 'teachingClassId', label: '教学班', type: 'number', options: [] },
  {
    prop: 'selectionStatus',
    label: '状态',
    default: 'selected',
    options: [
      { label: '已选', value: 'selected' },
      { label: '已退课', value: 'dropped' }
    ]
  },
  { prop: 'selectedAt', label: '选课时间', edit: false },
  { prop: 'droppedAt', label: '退课时间', edit: false }
])

onMounted(async () => {
  const [students, teachingClasses] = await Promise.all([
    crudApi('/students').page({ pageNum: 1, pageSize: 100 }),
    teachingClassApi.options()
  ])

  fields.value[1].options = (students.records || []).map((item) => ({
    label: `${item.studentName}（${item.studentNo}）`,
    value: item.id
  }))
  fields.value[2].options = (teachingClasses || []).map((item) => ({
    label: `${item.courseName} - ${item.teachingClassCode}`,
    value: item.id
  }))
})
</script>
