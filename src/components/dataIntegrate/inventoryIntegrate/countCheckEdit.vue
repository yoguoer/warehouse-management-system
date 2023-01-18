<template>
  <el-dialog size="30%" :title="ifCreate ? '新增' : '编辑'" :visible.sync="drawer" :direction="direction"
    :close-on-press-escape="false" :show-close="false" :wrapperClosable="false" :append-to-body='true' width="1200px">

    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-row>
        <el-col :span="10">
          <el-form-item label="关联库存" prop="shopkeeperWarehouseKey">
            <el-input v-model="ruleForm.shopkeeperWarehouseKey" clearable placeholder="关联库存" disabled></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="审批数" prop="checkNum">
            <el-input v-model="ruleForm.checkNum" clearable placeholder="审批数" type="Number"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10">
          <el-form-item label="审批类型" prop="checkType">
            <el-select size="mini" v-model="ruleForm.checkType" placeholder="审批类型" clearable>
              <el-option label="盘盈" :value="0"></el-option>
              <el-option label="盘亏" :value="1"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div class="dialog_footer">
      <el-button type="primary" @click="save('ruleForm')" v-if="ifCreate == false">保存</el-button>
      <el-button type="primary" @click="create('ruleForm')" v-else>提交审核</el-button>
      <el-button @click="close()">取消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { countCheckAdd, countCheckUpdate } from '@/api/dataIntegrate'
import moment from 'moment'

export default {
  name: 'guestEdit',
  data() {
    return {
      direction: 'btt',
      ifCreate: false,
      ruleForm: {
        countCheckKey:"",
        happenTime: moment().format("YYYY-MM-DD HH:mm:ss"),
        shopkeeperWarehouseKey:"",
        checkStatus: 0,
        checkTime:"",
        checkType:"",
        checkNum:"",
        description:"",
        checkPeople:"",
      },
      rules: {
        shopkeeperWarehouseKey: [
          { required: true, message: '请选择关联库存', trigger: 'blur' },
        ],
      }
    }
  },
  props: {
    drawer: {
      default: true,
    },
    rowData: {},
    shopGoodsList: []
  },
  watch: {

  },
  created() {
    // console.log(this.rowData)
    if (this.rowData.shopkeeperWarehouseKey) {
      this.ruleForm.countCheckKey = this.rowData.countCheckKey
      this.ruleForm.shopkeeperWarehouseKey = this.rowData.shopkeeperWarehouseKey
      this.ruleForm.checkType = this.rowData.checkType
      this.ruleForm.checkNum = this.rowData.checkNum
      if (this.ruleForm.countCheckKey) {
        this.ifCreate = false
        this.ruleForm.happenTime = this.rowData.happenTime
        this.ruleForm.checkTime = this.rowData.checkTime
        this.ruleForm.description = this.rowData.description
        this.ruleForm.checkPeople = this.rowData.checkPeople
      } else {
        this.ifCreate = true
      }
    } else {
      this.ifCreate = true
    }
  },
  methods: {
    close() {
      this.$parent.drawer1 = false
    },
    save(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          countCheckUpdate(this.ruleForm).then(res => {
            if (res.data.code == 200) {
              this.$message.success("编辑成功!");
              this.$parent.success()
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
          let data = {
            countCheckKey: this.ruleForm.countCheckKey,
            happenTime: this.ruleForm.happenTime,
            shopkeeperWarehouseKey: this.ruleForm.shopkeeperWarehouseKey,
            checkStatus: this.ruleForm.checkStatus,
            checkTime: this.ruleForm.checkTime,
            checkType: this.ruleForm.checkType,
            checkNum: this.ruleForm.checkNum,
            description: this.ruleForm.description,
            checkPeople: this.ruleForm.checkPeople
          }
          countCheckAdd(data).then(res => {
            if (res.data.code == 200) {
              this.$message.success("新增成功!");
              this.$parent.success()
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