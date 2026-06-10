<template>
  <CrudPage title="课程管理" endpoint="/courses" :fields="fields" />
</template>

<script setup>
import { ref, onMounted, watch } from 'vue' 
import CrudPage from '../../components/CrudPage.vue'

const teacherList = ref([])

const fetchTeachers = async () => {
  try {
    const response = await fetch('/api/teachers')
    const data = await response.json()
    if (data && data.data) {
      teacherList.value = data.data
    } else {
      teacherList.value = []
    }
  } catch (error) {
    console.error('获取教师列表失败:', error)
  }
}

onMounted(() => {
  fetchTeachers()
})

const fields = ref([
  { prop: 'id', label: 'ID', edit: false },
  { prop: 'collegeId', label: '学院ID', type: 'number' },
  { prop: 'courseCode', label: '课程编号' },
  { prop: 'courseName', label: '课程名称' },
  { prop: 'credit', label: '学分', type: 'number' },
  { prop: 'courseType', label: '性质', default: 'required', options: [{ label: '必修', value: 'required' }, { label: '选修', value: 'elective' }, { label: '通识', value: 'general' }] },
  { prop: 'courseStatus', label: '状态', default: 'active', options: [{ label: '启用', value: 'active' }, { label: '停用', value: 'inactive' }] },
  {
    prop: 'teacherId',
    label: '授课教师',
    type: 'select',
    options: []
  }
])


const updateTeacherOptions = () => {
  const teacherField = fields.value.find(f => f.prop === 'teacherId')
  if (teacherField) {
    teacherField.options = teacherList.value.map(t => ({
      label: t.teacherName || t.name, 
      value: t.id
    }))
  }
}


watch(teacherList, updateTeacherOptions)

</script>
