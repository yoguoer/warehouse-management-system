<template>
  <el-dialog size="30%" :title="ifCreate ? '新增审核' : '编辑审核'" :visible.sync="drawer" :direction="direction"
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
            <el-input v-model="ruleForm.checkNum" clearable placeholder="审批数" type="Number" :min="0"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10">
          <el-form-item label="审批类型" prop="checkType">
            <el-select size="mini" v-model="ruleForm.checkType" placeholder="审批类型" disabled>
              <el-option label="盘盈" :value="0"></el-option>
              <el-option label="盘亏" :value="1"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="10" v-if="!ifCreate">
          <el-form-item label="审批人" prop="checkPeople">
            <el-select size="middle" v-model="ruleForm.checkPeople" placeholder="审批人" style="width:100%;" clearable
              disabled>
              <el-option v-for="item in checkPeopleOptions" :key="item.userId" :label="item.userName"
                :value="item.userCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row  v-if="!ifCreate">
        <el-col :span="10">
          <el-form-item label="审批结果" prop="checkStatus">
            <el-select size="small" v-model="ruleForm.checkStatus" placeholder="审批结果" clearable disabled>
              <el-option label="未审批" :value="0" disabled>
                <span style="float: left">
                  <i class="el-icon-minus"></i> 未审批
                </span>
              </el-option>
              <el-option label="同意" :value="1">
                <span style="float: left">
                  <i class="el-icon-check"></i> 同意
                </span>
              </el-option>
              <el-option label="驳回" :value="2">
                <span style="float: left">
                  <i class="el-icon-close"></i> 驳回
                </span>
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="审批意见" prop="description">
            <el-input v-model="ruleForm.description" clearable placeholder="审批意见" type="textarea" disabled></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div class="dialog_footer">
      <el-button type="primary" @click="save('ruleForm')" v-if="ifCreate == false">保存修改</el-button>
      <el-button type="primary" @click="create('ruleForm')" v-else>提交审核</el-button>
      <el-button @click="close()">取消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { countCheckAdd, countCheckUpdate } from '@/api/dataIntegrate'
import moment from 'moment'
import { UserList } from '@/api/api'

export default {
  name: 'guestEdit',
  data() {
    return {
      direction: 'btt',
      ifCreate: false,
      ruleForm: {
        countCheckKey: "",
        happenTime: moment().format("YYYY-MM-DD HH:mm:ss"),
        shopkeeperWarehouseKey: "",
        checkStatus: "",
        checkTime: "",
        checkType: "",
        checkNum: "",
        description: "",
        checkPeople: "",
      },
      checkPeopleOptions: [],
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
    this.getUserList()
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
        this.ruleForm.checkStatus = this.rowData.checkStatus
      } else {
        this.ifCreate = true
      }
    } else {
      this.ifCreate = true
    }
  },
  methods: {
    getUserList() {
      UserList({ userType: 1 }).then(res => {
        this.checkPeopleOptions = res.data.data
      }).catch(err => {
        console.log(err)
      });
    },
    close() {
      this.$parent.drawer1 = false
    },
    save(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
        this.ruleForm.checkStatus = 0
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
            checkStatus: 0,
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