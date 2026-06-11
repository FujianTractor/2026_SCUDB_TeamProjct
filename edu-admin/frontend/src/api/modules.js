import request from './request'

export const authApi = {
  login: (data) => request.post('/auth/login', data)
}

export const crudApi = (endpoint) => ({
  page: (params) => request.get(endpoint, { params }),
  create: (data) => request.post(endpoint, data),
  update: (id, data) => request.put(`${endpoint}/${id}`, data),
  remove: (id) => request.delete(`${endpoint}/${id}`)
})

export const courseSelectionApi = {
  page: (params) => request.get('/course-selections', { params }),
  myCourses: (params) => request.get('/course-selections/my-courses', { params }),
  options: () => request.get('/course-selections/options'),
  select: (data) => request.post('/course-selections/select', data),
  drop: (data) => request.post('/course-selections/drop', data),
  roster: (params) => request.get('/course-selections/roster', { params }) 
}

export const teachingClassApi = {
  page: (params) => request.get('/teaching-classes', { params }),
  options: (params) => request.get('/teaching-classes/options', { params })
}

export const gradeApi = {
  page: (params) => request.get('/grades', { params }),
  batch: (grades) => request.post('/grades/batch', { grades }),
  inputRoster: (params) => request.get('/grades/input-roster', { params }),
  myGrades: (params) => request.get('/grades/my-grades', { params })
}

export const reportApi = {
  dashboard: () => request.get('/reports/dashboard'),
  schedule: (params) => request.get('/reports/student-schedule', { params }),
  roster: (params) => request.get('/reports/teacher-roster', { params }),
  gradeStatistics: (params) => request.get('/reports/grade-statistics', { params }),
  completedCredits: (params) => request.get('/reports/completed-credits', { params })
}
