<template>
  <el-drawer size="30%" :title="ifCreate ? '新增联系人' : '联系人编辑'" :visible.sync="drawer" :direction="direction"
    :close-on-press-escape="false" :show-close="false" :wrapperClosable="false">

    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-row>
        <el-row>
          <el-col :span="5">
            <el-form-item label="车辆编码" prop="vehicleCode">
              <el-input v-model="ruleForm.vehicleCode" clearable placeholder="车辆编码"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="车型" prop="vehicleType">
              <el-input v-model="ruleForm.vehicleType" clearable placeholder="车型"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="载重" prop="maxWeight">
              <el-input v-model="ruleForm.maxWeight" type="Number" clearable placeholder="载重"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="5">
            <el-form-item label="装货体积" prop="maxCapacity">
              <el-input v-model="ruleForm.maxCapacity" type="Number" clearable placeholder="装货体积"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="详细说明" prop="detail">
              <el-input v-model="ruleForm.detail" clearable placeholder="详细说明"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="备注" prop="description">
              <el-input v-model="ruleForm.description" clearable placeholder="备注"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-row>
      <el-row>
        <el-form-item style="display:inline-block;float:right;margin:10px 40%">
          <el-button type="primary" @click="save('ruleForm')" v-if="ifCreate == false">保存</el-button>
          <el-button type="primary" @click="create('ruleForm')" v-else>立即创建</el-button>
          <el-button @click="close()">关闭</el-button>
        </el-form-item>
      </el-row>
    </el-form>

  </el-drawer>
</template>

<script>
import { vehicleUpdate, vehicleAdd } from '@/api/data'

export default {
  name: 'guestEdit',
  data() {
    return {
      direction: 'btt',
      ifCreate: false,
      ruleForm: {
        maxWeight: "",
        detail: "",
        description: "",
        maxCapacity: "",
        vehicleCode: "",
        vehicleType: "",
        vehicleKey: ""
      },
      rules: {
        vehicleCode: [
          { required: true, message: '请输入车辆编码', trigger: 'blur' },
        ],
        maxWeight: [
          { required: true, message: '请输入载重', trigger: 'blur' },
        ],
        vehicleType: [
          { required: true, message: '请输入车型', trigger: 'blur' },
        ],
        maxCapacity: [
          { required: true, message: '请输入装货体积', trigger: 'blur' },
        ]
      }
    }
  },
  props: {
    drawer: {
      default: true,
    },
    rowData: {},
  },
  watch: {

  },
  created() {
    if (this.rowData.maxWeight) {
      this.ruleForm.maxWeight = this.rowData.maxWeight
      this.ruleForm.detail = this.rowData.detail
      this.ruleForm.description = this.rowData.description
      this.ruleForm.maxCapacity = this.rowData.maxCapacity
      this.ruleForm.vehicleCode = this.rowData.vehicleCode
      this.ruleForm.vehicleType = this.rowData.vehicleType
      this.ruleForm.vehicleKey = this.rowData.vehicleKey
    } else {
      this.ifCreate = true
    }
  },
  methods: {
    close() {
      this.$parent.drawer = false
      this.reset()
    },
    save(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          vehicleUpdate(this.ruleForm).then(res => {
            if (res.data.code == 200) {
              this.$message.success("编辑成功!");
              this.$parent.success()
              this.$forceUpdate()
            } else {
              this.$message.error("编辑失败!");
            }
          });
        } else {
          console.log('error submit!!');
          return false;
        }
      })
    },
    create(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          vehicleAdd(this.ruleForm).then(res => {
            if (res.data.code == 200) {
              this.$message.success("新增成功!");
              this.$parent.success()
              this.$forceUpdate()
            } else {
              this.$message.error("新增失败!");
            }
          });
        } else {
          console.log('error submit!!');
          return false;
        }
      })
    },
    reset() {
      this.ruleForm = {
        maxWeight: "",
        detail: "",
        description: "",
        maxCapacity: "",
        vehicleCode: "",
        vehicleType: "",
        vehicleKey: ""
      }
    }
  },
}
</script>
<style lang="scss">
.el-select {
  width: 100%;
}

.demo-ruleForm {
  padding: 0 80px 50px 80px;
}
</style>