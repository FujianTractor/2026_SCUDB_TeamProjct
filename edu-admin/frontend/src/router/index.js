import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '../stores/auth'
import Layout from '../layout/Layout.vue'
import Login from '../views/Login.vue'
import Dashboard from '../views/Dashboard.vue'
import CollegeManage from '../views/admin/CollegeManage.vue'
import MajorManage from '../views/admin/MajorManage.vue'
import ClassManage from '../views/admin/ClassManage.vue'
import StudentManage from '../views/admin/StudentManage.vue'
import TeacherManage from '../views/admin/TeacherManage.vue'
import CourseManage from '../views/admin/CourseManage.vue'
import SemesterManage from '../views/admin/SemesterManage.vue'
import ClassroomManage from '../views/admin/ClassroomManage.vue'
import TeachingClassManage from '../views/admin/TeachingClassManage.vue'
import SelectionManage from '../views/admin/SelectionManage.vue'
import GradeManage from '../views/admin/GradeManage.vue'
import UserManage from '../views/admin/UserManage.vue'
import Statistics from '../views/admin/Statistics.vue'
import MyTeachingClass from '../views/teacher/MyTeachingClass.vue'
import TeachingRoster from '../views/teacher/TeachingRoster.vue'
import GradeInput from '../views/teacher/GradeInput.vue'
import GradeStatistics from '../views/teacher/GradeStatistics.vue'
import CourseSelect from '../views/student/CourseSelect.vue'
import MyCourse from '../views/student/MyCourse.vue'
import MySchedule from '../views/student/MySchedule.vue'
import MyGrade from '../views/student/MyGrade.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/login', component: Login },
    {
      path: '/',
      component: Layout,
      redirect: '/dashboard',
      children: [
        { path: 'dashboard', component: Dashboard },
        { path: 'admin/colleges', component: CollegeManage },
        { path: 'admin/majors', component: MajorManage },
        { path: 'admin/classes', component: ClassManage },
        { path: 'admin/students', component: StudentManage },
        { path: 'admin/teachers', component: TeacherManage },
        { path: 'admin/courses', component: CourseManage },
        { path: 'admin/semesters', component: SemesterManage },
        { path: 'admin/classrooms', component: ClassroomManage },
        { path: 'admin/teaching-classes', component: TeachingClassManage },
        { path: 'admin/selections', component: SelectionManage },
        { path: 'admin/grades', component: GradeManage },
        { path: 'admin/users', component: UserManage },
        { path: 'admin/statistics', component: Statistics },
        { path: 'teacher/classes', component: MyTeachingClass },
        { path: 'teacher/roster', component: TeachingRoster },
        { path: 'teacher/grade-input', component: GradeInput },
        { path: 'teacher/grade-statistics', component: GradeStatistics },
        { path: 'student/course-select', component: CourseSelect },
        { path: 'student/my-course', component: MyCourse },
        { path: 'student/schedule', component: MySchedule },
        { path: 'student/grade', component: MyGrade }
      ]
    }
  ]
})

router.beforeEach((to) => {
  const auth = useAuthStore()
  if (to.path !== '/login' && !auth.isLogin) {
    return '/login'
  }
})

export default router
