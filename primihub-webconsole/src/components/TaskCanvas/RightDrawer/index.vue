<template>
  <div v-loading="listLoading" class="right-drawer" :class="{'not-clickable': !options.isEditable}">
    <el-form v-if="nodeData" ref="form" :model="nodeData" :rules="rules" label-width="80px" element-loading-spinner="el-icon-loading">
      <template v-if="isDataSelect">
        <el-form-item>
          <p class="organ"><i class="el-icon-office-building" /> <span>发起方：</span> {{ initiateOrgan.organName }}</p>
          <el-button v-if="options.isEditable" type="primary" size="small" plain @click="openDialog(initiateOrgan.organId, 1)">选择资源</el-button>
          <ResourceDec v-if="initiateOrgan.resourceId" :data="initiateOrgan" @change="handleResourceHeaderChange" />
        </el-form-item>
        <el-form-item>
          <template v-if="providerOrganOptions.length>0">
            <span class="organ"><i class="el-icon-office-building" /> <span>协作方：</span> {{ providerOrganName }}</span>
            <div v-if="options.isEditable" class="organ-select">
              <el-select v-model="providerOrganId" placeholder="请选择" size="small" @change="handleProviderOrganChange">
                <el-option
                  v-for="(v,index) in providerOrganOptions"
                  :key="index"
                  :label="v.organName"
                  :value="v.organId"
                />
              </el-select>
              <el-button type="primary" size="small" plain @click="openDialog(providerOrganId,2)">选择资源</el-button>
            </div>
            <ResourceDec v-if="providerOrgans.length > 0" :data="providerOrgans[0]" @change="handleResourceHeaderChange" />
          </template>
          <template v-else>
            <i class="el-icon-office-building" /> <span>暂无审核通过的协作方 </span>
          </template>
        </el-form-item>
      </template>
      <template v-else-if="nodeData.componentCode === 'dataAlign'">
        <el-form-item :label="nodeData.componentTypes[0].typeName">
          <el-select v-model="nodeData.componentTypes[0].inputValue" class="block" placeholder="请选择" @change="handleChange">
            <el-option
              v-for="(v,index) in nodeData.componentTypes[0].inputValues"
              :key="index"
              :label="v.val"
              :value="v.key"
            />
          </el-select>
        </el-form-item>
        <el-row v-if="nodeData.componentTypes[0].inputValue === '2'" :gutter="20">
          <el-col :span="12">
            <el-button @click="openFeaturesDialog(nodeData.componentCode)">选择特征({{ selectedDataAlignFeatures? 1 : 0 }}/{{ featuresOptions.length }})</el-button>
            <div class="feature-container">
              <el-tag v-if="selectedDataAlignFeatures" type="primary" size="mini">{{ selectedDataAlignFeatures }}</el-tag>
            </div>
            <el-form-item />
          </el-col>
          <el-col :span="12">
            <el-select v-model="nodeData.componentTypes[2].inputValue" class="block" :placeholder="nodeData.componentTypes[2].typeName" @change="handleChange">
              <el-option
                v-for="(v) in nodeData.componentTypes[2].inputValues"
                :key="v.key"
                :label="v.val"
                :value="v.key"
              />
            </el-select>
          </el-col>
        </el-row>
      </template>
      <template v-else-if="nodeData.componentCode === 'exception'">
        <el-form-item :label="nodeData.componentTypes[0].typeName">
          <el-select v-model="nodeData.componentTypes[0].inputValue" class="block" placeholder="请选择" @change="handleChange">
            <el-option
              v-for="(v,index) in nodeData.componentTypes[0].inputValues"
              :key="index"
              :label="v.val"
              :value="v.key"
            />
          </el-select>
        </el-form-item>
      </template>
      <template v-else-if="nodeData.componentCode === 'missing'">
        <el-form-item :label="nodeData.componentTypes[0].typeName">
          <el-select v-model="nodeData.componentTypes[0].inputValue" class="block" placeholder="请选择" @change="handleChange">
            <el-option
              v-for="(v,index) in nodeData.componentTypes[0].inputValues"
              :key="index"
              :label="v.val"
              :value="v.key"
            />
          </el-select>
        </el-form-item>
        <template v-if="nodeData.componentTypes[0].inputValue === '1'">
          <el-row :gutter="20">
            <el-col :span="12">
              <el-button @click="openFeaturesDialog(nodeData.componentCode)">选择特征({{ selectedExceptionFeatures? 1 : 0 }}/{{ featuresOptions.length }})</el-button>
              <div class="feature-container">
                <el-tag v-if="selectedExceptionFeatures" type="primary" size="mini">{{ selectedExceptionFeatures }}</el-tag>
              </div>
              <el-form-item />
            </el-col>
            <el-col :span="12">
              <el-select v-model="nodeData.componentTypes[2].inputValue" class="block" :placeholder="nodeData.componentTypes[2].typeName" @change="handleChange">
                <el-option
                  v-for="(v) in nodeData.componentTypes[2].inputValues"
                  :key="v.key"
                  :label="v.val"
                  :value="v.key"
                />
              </el-select>
            </el-col>
          </el-row>
        </template>
      </template>
      <template v-else-if="nodeData.componentCode === 'featuresPoints'">
        <el-form-item :label="nodeData.componentTypes[0].typeName">
          <el-row :gutter="20">
            <el-col :span="12">
              <el-select v-model="nodeData.componentTypes[0].inputValue" class="block" placeholder="请选择" @change="handleChange">
                <el-option
                  v-for="(v,index) in nodeData.componentTypes[0].inputValues"
                  :key="index"
                  :label="v.val"
                  :value="v.key"
                />
              </el-select>
            </el-col>

            <el-col :span="12">
              <el-input-number v-model="nodeData.componentTypes[1].inputValue" controls-position="right" :min="1" :max="100" /> 箱
            </el-col>
          </el-row>
        </el-form-item>
      </template>
      <template v-else>
        <div v-for="item in nodeData.componentTypes" :key="item.typeCode">
          <!-- 模型选择为LR时显示可信第三方 -->
          <template v-if="item.inputType === 'button' && item.typeCode === 'arbiterOrgan'">
            <el-form-item v-if="showArbiterOrgan" :label="item.typeName" :prop="item.typeCode">
              <p class="tips">横向联邦需可信第三方(arbiter方)参与</p>
              <span v-if="arbiterOrganName" class="label-text"><i class="el-icon-office-building" /> {{ arbiterOrganName }}</span>
              <el-button type="primary" size="small" class="block" @click="openProviderOrganDialog">请选择</el-button>
            </el-form-item>
          </template>
          <el-form-item v-else :label="item.typeName" :prop="item.typeCode">
            <template v-if="item.inputType === 'label'">
              <span class="label-text">{{ item.inputValue }}</span>
            </template>
            <template v-if="item.inputType === 'text'">
              <el-input v-model="item.inputValue" size="small" @change="handleChange" />
            </template>
            <template v-if="item.inputType === 'textarea'">
              <el-input v-model="item.inputValue" type="textarea" size="small" @change="handleChange" />
            </template>
            <template v-if="item.inputType === 'radio'">
              <el-radio-group v-model="item.inputValue" @change="handleChange">
                <el-radio v-for="(r,index) in item.inputValues" :key="index" :label="r.key">{{ r.val }}</el-radio>
              </el-radio-group>
            </template>
            <template v-if="item.inputType === 'select'">
              <el-select v-model="item.inputValue" class="block" placeholder="请选择" :value-key="item.typeCode" @change="handleChange">
                <el-option
                  v-for="(v,index) in item.inputValues"
                  :key="index"
                  :label="v.val"
                  :value="v.key"
                />
              </el-select>
            </template>
          </el-form-item>
        </div>
      </template>
    </el-form>
    <el-button v-if="options.showSaveButton" type="primary" @click="save">保存</el-button>
    <!-- add resource dialog -->
    <ModelTaskResourceDialog ref="dialogRef" top="10px" width="800px" :selected-data="selectedResourceId" title="选择资源" :show-tab="participationIdentity === 1" :table-data="resourceList[selectedOrganId]" :visible="dialogVisible" @close="handleDialogCancel" @submit="handleDialogSubmit" />
    <!-- add provider organ dialog -->
    <ArbiterOrganDialog :selected-data="providerOrganIds" :visible.sync="providerOrganDialogVisible" title="添加可信第三方" :data="organData" @submit="handleProviderOrganSubmit" @close="closeProviderOrganDialog" />

    <FeatureSelectDialog :visible.sync="featuresDialogVisible" :data="featuresOptions" :selected-data="selectedFeatures" @submit="handleFeatureDialogSubmit" @close="handleFeatureDialogClose" />
  </div>
</template>

<script>
import { getProjectResourceData, getProjectResourceOrgan } from '@/api/model'
import ModelTaskResourceDialog from '@/components/ModelTaskResourceDialog'
import ResourceDec from '@/components/ResourceDec'
import ArbiterOrganDialog from '@/components/ArbiterOrganDialog'
import FeatureSelectDialog from '@/components/FeatureSelectDialog'

export default {
  components: {
    ModelTaskResourceDialog,
    ResourceDec,
    ArbiterOrganDialog,
    FeatureSelectDialog
  },
  props: {
    graphData: {
      type: Object,
      default: () => {}
    },
    nodeData: {
      type: Object,
      default: () => {}
    },
    options: { // 可配置项
      type: Object,
      default: () => {
        return {
          showSaveButton: false, // 是否展示保存
          isEditable: false // 是否可编辑
        }
      }
    }
  },
  data() {
    const modelNameValidate = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入模型名称'))
      } else {
        callback()
      }
    }
    const taskNameValidate = (rule, value, callback) => {
      console.log(value)
      if (value === '') {
        callback(new Error('请输入任务名称'))
      } else {
        callback()
      }
    }
    return {
      emptyMissingData: {
        selectedExceptionFeatures: [],
        inputValue: '',
        inputValues: [],
        typeName: ''
      },
      missingData: [

      ],
      selectedFeaturesCode: '',
      selectedFeaturesIndex: '',
      selectedDataAlignFeatures: null,
      selectedExceptionFeatures: null,
      selectedFeatures: null,
      organData: [],
      arbiterOrganName: '',
      arbiterOrganId: '',
      providerOrganIds: null,
      providerOrganDialogVisible: false,
      featuresDialogVisible: false,
      form: {
        dynamicError: {
          name: ''
        }
      },
      listLoading: false,
      initiateOrgan: {},
      providerOrgans: [],
      providerOrganOptions: [],
      providerOrganId: '',
      providerOrganName: '',
      dialogVisible: false,
      selectedOrganId: '',
      resourceList: [],
      selectedResourceId: '',
      participationIdentity: 2,
      inputValues: [],
      inputValue: '',
      resourceChanged: false,
      rules: {
        modelName: [
          { required: true, trigger: 'blur', validator: modelNameValidate }
        ],
        taskName: [
          { required: true, trigger: 'blur', validator: taskNameValidate }
        ],
        arbiterOrgan: [
          { required: true, trigger: 'change' }
        ]
      }
    }
  },
  computed: {
    isDataSelect() {
      return this.nodeData && this.nodeData.componentCode === 'dataSet'
    },
    isModelSelect() {
      return this.nodeData && this.nodeData.componentCode === 'model'
    },
    showArbiterOrgan() {
      if (this.nodeData && this.nodeData.componentTypes.find(item => item.typeCode === 'modelType')?.inputValue === '3') {
        return true
      } else {
        return false
      }
    },
    featuresOptions() {
      this.getDataSetComValue(this.graphData)
      if (this.providerOrgans.length > 0 && this.providerOrgans[0].fileHandleField && this.initiateOrgan.fileHandleField) {
        let intersection = this.providerOrgans.length > 0 && this.providerOrgans[0].fileHandleField.filter(v => this.initiateOrgan.fileHandleField.includes(v))
        intersection = intersection.map((val, key) => {
          return {
            key: key + '',
            val
          }
        })
        return intersection
      } else {
        return []
      }
    }
  },
  watch: {
    async nodeData(newVal) {
      console.log('watch newVal', newVal)
      if (newVal) {
        if (newVal.componentCode === 'dataSet') {
          this.inputValue = newVal.componentTypes[0].inputValue
          this.getDataSetNodeData()
        } else if (newVal.componentCode === 'model') {
          this.getDataSetComValue(this.graphData)
          this.arbiterOrganId = newVal.componentTypes.find(item => item.typeCode === 'arbiterOrgan')?.inputValue
          this.arbiterOrganName = this.organs.find(item => item.organId === this.arbiterOrganId)?.organName
        } else if (newVal.componentCode === 'dataAlign') {
          this.selectedDataAlignFeatures = this.nodeData.componentTypes[1]?.inputValue !== '' ? this.nodeData.componentTypes[1]?.inputValue : null
          this.selectedFeatures = this.selectedDataAlignFeatures
        } else if (newVal.componentCode === 'missing') {
          this.selectedExceptionFeatures = newVal.componentTypes[1].inputValue !== '' ? newVal.componentTypes[1].inputValue : null
          this.selectedFeatures = this.selectedExceptionFeatures
          console.log('watch selectedExceptionFeatures', this.selectedExceptionFeatures)
        } else {
          this.inputValue = ''
        }
      }
    },
    deep: true,
    immediate: true
  },
  async created() {
    this.projectId = Number(this.$route.query.projectId) || Number(this.$route.params.id)
    await this.getProjectResourceOrgan()
  },
  methods: {
    getDataSetComValue(value) {
      const dataSetCom = value.cells.find(item => item.componentCode === 'dataSet')
      if (dataSetCom) {
        const dataSetComVal = dataSetCom.data.componentTypes[0].inputValue
        this.inputValue = dataSetComVal
        this.getDataSetNodeData()
      }
    },
    async openProviderOrganDialog() {
      if (this.initiateOrgan.organId && this.providerOrgans.length > 0) {
        this.organData = this.organs.filter(item => item.organId !== this.initiateOrgan.organId && item.organId !== this.providerOrgans[0].organId)
        this.providerOrganDialogVisible = true
      } else {
        this.$message({
          message: '请先选择数据集',
          type: 'warning'
        })
      }
    },
    closeProviderOrganDialog(data) {
      this.providerOrganIds = data
      this.providerOrganDialogVisible = false
    },
    handleProviderOrganSubmit(data) {
      const posIndex = this.nodeData.componentTypes.findIndex(item => item.typeCode === 'arbiterOrgan')
      this.providerOrganIds = data.organId
      this.arbiterOrganName = data.organName
      this.arbiterOrganId = data.organId
      this.providerOrganDialogVisible = false
      this.nodeData.componentTypes[posIndex].inputValue = data.organId
      this.$emit('change', this.nodeData)
    },
    getDataSetNodeData() {
      if (this.inputValue !== '') {
        this.inputValue = JSON.parse(this.inputValue)
        const initiateOrgan = this.inputValue.find(item => item.participationIdentity === 1)
        this.initiateOrgan = initiateOrgan || this.initiateOrgan
        const providerOrgans = this.inputValue.filter(item => item.participationIdentity === 2)
        if (providerOrgans.length > 0) {
          this.providerOrgans = providerOrgans
          this.providerOrganId = this.providerOrgans[0]?.organId
          this.providerOrganName = this.providerOrgans[0]?.organName
        } else {
          this.resourceList = []
          this.providerOrgans = []
          this.providerOrganId = ''
          this.providerOrganName = ''
        }
      } else {
        this.resourceList = []
        this.providerOrgans = []
        this.providerOrganId = ''
        this.providerOrganName = ''
        this.initiateOrgan.resourceId = ''
        this.selectedResourceId = ''
        this.inputValue = ''
      }
    },
    handleResourceHeaderChange(data) {
      this.setInputValue(data)
      this.save()
    },
    async openDialog(organId, participationIdentity) {
      this.participationIdentity = participationIdentity
      this.selectedOrganId = organId
      sessionStorage.setItem('organ', JSON.stringify({ organId: this.selectedOrganId, participationIdentity: this.participationIdentity }))
      console.log(organId)
      if (this.selectedOrganId === '') {
        this.$message({
          message: '请先选择机构',
          type: 'warning'
        })
        return
      }
      if (this.inputValue) {
        const currentOrgan = this.inputValue.filter(item => item.organId === organId)[0]
        if (currentOrgan) {
          console.log(this.inputValue)
          this.selectedResourceId = currentOrgan.resourceId
        } else {
          this.selectedResourceId = ''
        }
      }
      await this.getProjectResourceData()
      this.dialogVisible = true
    },
    handleChange() {
      this.$emit('change', this.nodeData)
    },
    handleProviderOrganChange(value) {
      this.providerOrganId = value
      this.providerOrganName = this.providerOrganOptions.filter(item => item.organId === value)[0].organName
    },
    handleDialogCancel() {
      this.dialogVisible = false
    },
    handleDialogSubmit(data) {
      // not selecting resource
      if (!data.resourceId) {
        this.dialogVisible = false
        return
      }
      if (data.fileHandleField.includes('id')) {
        data.fileHandleField = data.fileHandleField.filter(v => v !== 'id')
      }
      if (this.participationIdentity === 1) {
        // is not first select
        if ('resourceId' in this.initiateOrgan && this.initiateOrgan.resourceId !== data.resourceId) {
          this.resourceChanged = true
        }
        data.organName = this.initiateOrgan.organName
        this.initiateOrgan = []
        this.initiateOrgan = data
      } else {
        // is not first select
        if (this.providerOrgans.length > 0 && 'resourceId' in this.providerOrgans[0]) {
          this.resourceChanged = true
        }
        data.organName = this.providerOrganOptions.find(item => item.organId === this.providerOrganId).organName
        this.providerOrgans = []
        this.providerOrgans = [data]
      }
      this.selectedResourceId = data.resourceId
      // set input value
      this.setInputValue(data)
      this.save()
      this.dialogVisible = false
      this.$emit('change', this.nodeData)
    },
    setInputValue(data) {
      // set default feature value
      data.calculationField = data.calculationField ? data.calculationField : data.fileHandleField ? data.fileHandleField[0] : ''
      if (this.inputValue) {
        this.inputValues = this.inputValue
      }
      const posIndex = this.inputValues.findIndex(item => item.participationIdentity === data.participationIdentity)
      const currentData = data
      console.log('currentData', currentData)
      if (posIndex !== -1) {
        this.inputValues.splice(posIndex, 1, currentData)
      } else {
        this.inputValues.push(currentData)
      }
      console.log('inputValues', this.inputValues)
      this.nodeData.componentTypes[0].inputValue = JSON.stringify(this.inputValues)
      this.handleChange()
    },
    async getProjectResourceData() {
      this.resourceList = []
      const params = {
        projectId: this.projectId,
        organId: this.selectedOrganId
      }
      const { code, result } = await getProjectResourceData(params)
      if (code === 0) {
        this.resourceList[this.selectedOrganId] = result
      }
    },
    async getProjectResourceOrgan() {
      this.listLoading = true
      const res = await getProjectResourceOrgan({ projectId: this.projectId })
      if (res.code === 0) {
        this.organs = res.result
        this.providerOrganOptions = this.organs.filter(item => item.participationIdentity === 2)
        this.initiateOrgan = this.organs.filter(item => item.participationIdentity === 1)[0]
      }
      this.listLoading = false
    },
    save() {
      this.$emit('save')
    },
    openFeaturesDialog(code) {
      this.selectedFeaturesCode = code
      if (this.selectedFeaturesCode === 'dataAlign') {
        this.selectedFeatures = this.selectedDataAlignFeatures
      } else if (this.selectedFeaturesCode === 'exception') {
        this.selectedFeatures = this.selectedExceptionFeatures
      }
      this.featuresDialogVisible = true
    },
    handleFeatureDialogSubmit(data) {
      console.log(data)
      if (this.selectedFeaturesCode === 'dataAlign') {
        this.selectedDataAlignFeatures = data
        this.nodeData.componentTypes[1].inputValue = this.selectedDataAlignFeatures
        this.selectedFeatures = this.selectedDataAlignFeatures
      } else if (this.selectedFeaturesCode === 'missing') {
        this.selectedExceptionFeatures = data
        this.nodeData.componentTypes[1].inputValue = this.selectedExceptionFeatures
        this.selectedFeatures = this.selectedExceptionFeatures
        console.log(this.selectedExceptionFeatures)
      }
      console.log(this.nodeData)
      this.featuresDialogVisible = false
      this.handleChange()
    },
    handleFeatureDialogClose() {
      this.featuresDialogVisible = false
    }
  }
}
</script>

<style lang="scss" scoped>
p {
  margin-block-start: .2em;
  margin-block-end: .2em;
}
::v-deep .el-form-item__content{
  display: block;
  margin-left: 0!important;
  width: 100%;
}
::v-deep .el-form-item__label{
  float: none;
}
::v-deep .el-input-number{
  box-sizing: border-box;
  width: calc(100% - 20px)
}

.select{
  width: 100%;
}
.right-drawer {
  width: 300px;
  background: #fff;
  padding: 10px 20px;
  overflow-y: scroll;
}
.label-text{
  color: #666;
}
::v-deep .detail-title{
  width: 100px;
}
::v-deep .el-descriptions .is-bordered .el-descriptions-item__cell{
  padding:2px 5px!important;
}
::v-deep .el-checkbox__label{
  font-size: 12px!important;
}
.resource-data{
  font-size: 12px;
  margin-top: 10px;
}
.organ-select{
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.required{
  color: red;
  margin-right: 10px;
  font-size: 20px;
  line-height: 1;
}
.not-clickable{
  cursor: default;
  pointer-events: none;
}
.tips{
  font-size: 12px;
  color: #999;
  line-height: 1;
  margin-bottom: 10px;
}
.block{
  width: 100%;
  display: block;
}
.feature-container{
  margin: 10px 0;
}
</style>
