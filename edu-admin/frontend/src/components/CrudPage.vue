<template>
  <div class="page">
    <h1 class="page-title">{{ title }}</h1>
    <div class="panel">
      <div class="toolbar">
        <el-input v-model="keyword" clearable :placeholder="searchPlaceholder" style="max-width: 320px" @keyup.enter="load" />
        <div>
          <el-button @click="load">查询</el-button>
          <el-button type="primary" @click="openCreate">新增</el-button>
        </div>
      </div>
      <el-table :data="rows" border>
        <el-table-column v-for="field in fields" :key="field.prop" :prop="field.prop" :label="field.label" min-width="120" />
        <el-table-column label="操作" fixed="right" width="150">
          <template #default="{ row }">
            <el-button link type="primary" @click="openEdit(row)">修改</el-button>
            <el-popconfirm title="确认删除？" @confirm="remove(row.id)">
              <template #reference>
                <el-button link type="danger">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        v-model:current-page="pageNum"
        v-model:page-size="pageSize"
        layout="total, prev, pager, next, sizes"
        :total="total"
        style="margin-top: 14px; justify-content: flex-end"
        @current-change="load"
        @size-change="load"
      />
    </div>

    <el-dialog v-model="visible" :title="editingId ? '修改' : '新增'" width="560px">
      <el-form label-width="110px">
        <el-form-item v-for="field in editableFields" :key="field.prop" :label="field.label">
          <el-input-number v-if="field.type === 'number'" v-model="form[field.prop]" style="width: 100%" />
          <el-select v-else-if="field.options" v-model="form[field.prop]" clearable style="width: 100%">
            <el-option v-for="opt in field.options" :key="opt.value" :label="opt.label" :value="opt.value" />
          </el-select>
          <el-input v-else v-model="form[field.prop]" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="visible = false">取消</el-button>
        <el-button type="primary" @click="save">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { crudApi } from '../api/modules'

const props = defineProps({
  title: String,
  endpoint: String,
  fields: Array,
  searchPlaceholder: { type: String, default: '输入关键字查询' }
})

const api = crudApi(props.endpoint)
const rows = ref([])
const total = ref(0)
const pageNum = ref(1)
const pageSize = ref(10)
const keyword = ref('')
const visible = ref(false)
const editingId = ref(null)
const form = ref({})
const editableFields = computed(() => props.fields.filter((field) => field.edit !== false))

async function load() {
  const data = await api.page({ pageNum: pageNum.value, pageSize: pageSize.value, keyword: keyword.value })
  rows.value = data.records || []
  total.value = data.total || 0
}

function openCreate() {
  editingId.value = null
  form.value = Object.fromEntries(editableFields.value.map((field) => [field.prop, field.default ?? '']))
  visible.value = true
}

function openEdit(row) {
  editingId.value = row.id
  form.value = { ...row }
  visible.value = true
}

async function save() {
  if (editingId.value) {
    await api.update(editingId.value, form.value)
  } else {
    await api.create(form.value)
  }
  ElMessage.success('保存成功')
  visible.value = false
  load()
}

async function remove(id) {
  await api.remove(id)
  ElMessage.success('删除成功')
  load()
}

onMounted(load)
</script>
