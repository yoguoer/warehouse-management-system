<template>
    <el-drawer size="30%" :title="ifCreate ? '新增银行' : '编辑银行'" :visible.sync="drawer" :direction="direction" :append-to-body='true'
      :close-on-press-escape="false" :show-close="false" :wrapperClosable="false">
  
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-row>
          <el-row>
            <el-col :span="5">
              <el-form-item label="银行全称" prop="bankName">
                <el-input v-model="ruleForm.bankName" placeholder="银行全称" clearable></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="户主名" prop="accountName">
                <el-input v-model="ruleForm.accountName" placeholder="户主名" clearable></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="银行账号" prop="accountNumber">
                <el-input v-model="ruleForm.accountNumber" placeholder="银行账号" clearable></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="5">
              <el-form-item label="联系电话" prop="accountTel">
                <el-input v-model="ruleForm.accountTel" placeholder="联系电话" clearable></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="税号" prop="taxNumber">
                <el-input v-model="ruleForm.taxNumber" placeholder="税号" clearable></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="所属供应商" prop="supplierBillingCode">
                <el-select size="middle" v-model="ruleForm.supplierBillingCode" placeholder="所属供应商" style="width:270px;"
                  clearable>
                  <el-option v-for="item in options" :key="item.supplierCode" :label="item.supplierName"
                    :value="item.supplierCode">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
        </el-row>
        <el-row>
          <el-form-item style="display:inline-block;float:right;margin:10px 40%">
            <el-button type="primary" @click="save('ruleForm')" v-if="ifCreate == false">保存</el-button>
            <el-button type="primary" @click="create('ruleForm')" v-else>立即创建</el-button>
            <!-- <el-button @click="resetForm('ruleForm')">重置</el-button> -->
            <el-button @click="close()">关闭</el-button>
          </el-form-item>
        </el-row>
      </el-form>
  
    </el-drawer>
  </template>
  
  <script>
  import { bankUpdate, bankAdd, Supplierlist } from '@/api/data'
  
  export default {
    name: 'guestEdit',
    data() {
      return {
        direction: 'btt',
        ifCreate: false,
        options: [],
        ruleForm: {
          bankName: "",
          accountName: "",
          accountNumber: "",
          taxNumber: "",
          accountTel: "",
          billingKey: "",
          supplierBillingCode: ""
        },
        rules: {
          bankName: [
            { required: true, message: '请输入银行全称', trigger: 'blur' },
          ],
          accountName: [
            { required: true, message: '请输入户主名', trigger: 'blur' },
          ],
          accountNumber: [
            { required: true, message: '请输入银行账号', trigger: 'blur' },
          ],
          taxNumber: [
            { required: true, message: '请输入税号', trigger: 'blur' },
          ],
          accountTel: [
            { required: true, message: '请输入联系电话', trigger: 'blur' },
          ],
          supplierBillingCode: [
            { required: true, message: '请输入选择所属供应商', trigger: 'blur' },
          ]
        }
      }
    },
    props: {
      drawer: {
        default: false,
      },
      rowData: {},
    },
    watch: {
  
    },
    created() {
      this.getSupplierlist()
      console.log(this.rowData)
      if (this.rowData.billingKey) {
        this.ruleForm.bankName = this.rowData.bankName
        this.ruleForm.accountName = this.rowData.accountName
        this.ruleForm.accountNumber = this.rowData.accountNumber
        this.ruleForm.taxNumber = this.rowData.taxNumber
        this.ruleForm.accountTel = this.rowData.accountTel
        this.ruleForm.supplierBillingCode = this.rowData.supplierBillingCode
        this.ruleForm.billingKey = this.rowData.billingKey
      } else {
        this.ifCreate = true
      }
    },
    methods: {
      close() {
        this.$parent.drawer = false
        this.reset()
      },
      getSupplierlist() {
        Supplierlist().then(res => {
          if (res.data.code == 200) {
            this.options = res.data.data
          } else {
            this.$message.error("获取失败!");
          }
        });
      },
      save(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            bankUpdate(this.ruleForm).then(res => {
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
            bankAdd(this.ruleForm).then(res => {
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
          bankName: "",
          accountName: "",
          accountNumber: "",
          taxNumber: "",
          accountTel: "",
          billingKey: "",
          supplierBillingCode: ""
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