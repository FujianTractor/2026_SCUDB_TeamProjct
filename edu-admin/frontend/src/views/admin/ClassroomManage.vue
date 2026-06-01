<template><CrudPage title="教室管理" endpoint="/classrooms" :fields="fields" /></template>
<script setup>
import CrudPage from '../../components/CrudPage.vue'
const fields = [
  { prop: 'id', label: 'ID', edit: false },
  { prop: 'building', label: '教学楼' },
  { prop: 'roomNo', label: '教室号' },
  { prop: 'capacity', label: '容量', type: 'number' },
  { prop: 'hasMultimedia', label: '多媒体', type: 'number', default: 1 },
  { prop: 'classroomStatus', label: '状态', default: 'available' }
]
</script>
