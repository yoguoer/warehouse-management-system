<template>
  <el-drawer size="50%" :title="ifCreate ? '新增联系人' : '联系人编辑'" :visible.sync="drawer" :direction="direction"
    :close-on-press-escape="false" :show-close="false" :wrapperClosable="false" :append-to-body='true' >

    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-row>
        <el-col :span="7">
          <el-form-item label="客户" prop="contactCustomerKey">
            <el-select v-model="ruleForm.contactCustomerKey" placeholder="客户">
              <el-option v-for="item in options" :key="item.customerKey" :label="item.customerName"
                :value="item.customerKey"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="7">
          <el-form-item label="联系人编号" prop="contactCode">
            <el-input v-model="ruleForm.contactCode"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="7">
          <el-form-item label="联系人名称" prop="contactName">
            <el-input v-model="ruleForm.contactName"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="7">
          <el-form-item label="联系邮箱" prop="contactEmail">
            <el-input v-model="ruleForm.contactEmail"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="7">
          <el-form-item label="联系电话" prop="contactTel">
            <el-input v-model="ruleForm.contactTel"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col>
          <el-form-item label="联系地址" prop="address" v-model="ruleForm.address">
            <checkAddress v-model="ruleForm.address" :form="ruleForm.address" ref="address" />
          </el-form-item>
        </el-col>
      </el-row>

      <el-form-item style="float:right;margin-right:20%">
        <el-button type="primary" @click="save('ruleForm')" v-if="ifCreate == false">保存</el-button>
        <el-button type="primary" @click="create('ruleForm')" v-else>立即创建</el-button>
        <!-- <el-button @click="resetForm('ruleForm')">重置</el-button> -->
        <el-button @click="close()">关闭</el-button>
      </el-form-item>

    </el-form>

  </el-drawer>
</template>

<script>
import checkAddress from '@/components/public/checkAddress.vue'
import { CustomerList, Contactadd, Contactupdate } from '@/api/data'

export default {
  name: 'guestEdit',
  data() {
    return {
      options: [],
      addStr: '',
      ifCreate: true,
      direction: 'btt',
      //联系人信息项
      ruleForm: {
        // contactInfo: "",//联系信息
        contactName: "",//联系人
        contactCode: "",//联系人编号
        address: {
          // 省
          province: "",
          // 市
          city: "",
          // 区
          district: "",
          //详细地址
          detail: ""
        },//联系地址
        contactEmail: "",//联系邮箱
        contactTel: "",//联系电话
        contactCustomerKey: "",//联系人key
        contactKey: ""//客户key
      },

      rules: {
        contactCustomerKey: [
          { required: true, message: '请选择客户', trigger: 'blur' },
        ],
        contactName: [
          { required: true, message: '请输入联系人姓名', trigger: 'blur' },
        ],
        contactCode: [
          { required: true, message: '请输入联系人编号', trigger: 'blur' },
        ],
        address: [
          { required: true, message: '请输入联系地址', trigger: 'blur' }
        ],
        contactTel: [
          { required: true, message: '请输入联系电话', trigger: 'blur' }
        ],
        contactEmail: [
          { required: true, message: '请输入联系电话', trigger: 'blur' }
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
    ruleForm: {
      // deep:true,
      handler() {
        this.ruleForm.address = this.$refs.address.form
        console.log(this.ruleForm.address)
      }
    }
  },
  components: {
    checkAddress
  },
  created() {
    this.getCustomer()
    this.row = this.$route.params.rowData || this.rowData
    if (this.rowData.contactKey) {
      this.ifCreate = false
      this.ruleForm.contactName = this.rowData.contactName
      this.ruleForm.address.province = this.rowData.province
      this.ruleForm.address.city = this.rowData.city
      this.ruleForm.address.district = this.rowData.district
      this.ruleForm.address.detail = this.rowData.detail
      this.ruleForm.contactEmail = this.rowData.contactEmail
      this.ruleForm.contactTel = this.rowData.contactTel
      this.ruleForm.contactCustomerKey =  this.rowData.contactCustomerKey
      this.ruleForm.contactCode = this.rowData.contactCode
      this.ruleForm.contactKey = this.rowData.contactKey
    } else {
      this.ifCreate = true
    }
  },
  methods: {
    getCustomer() {
      CustomerList({}).then(res => {
        if (res.data.code === 200) {
          this.options = res.data.data
        } else {
          console.log('error');
        }
      })
    },
    //重置
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    //关闭
    close() {
      this.$emit('close')
      this.value = null
      this.reset()
    },

    //添加联系人
    create(formName) {
      let data = {
        contactName: this.ruleForm.contactName,
        province: this.ruleForm.address.province,
        city: this.ruleForm.address.city,
        district: this.ruleForm.address.district,
        detail: this.ruleForm.address.detail,
        contactEmail: this.ruleForm.contactEmail,
        contactTel: this.ruleForm.contactTel,
        contactCustomerKey: this.ruleForm.contactCustomerKey,
        contactKey: this.ruleForm.contactKey,
        contactCode: this.ruleForm.contactCode
      }

      console.log(this.ruleForm.address)
      this.$refs[formName].validate((valid) => {
        if (valid) {
          console.log("#this.form#", data)

          Contactadd(data)
            .then((res) => {
              if (res.data.code === 200) {
                this.$message.success('创建成功!')
                this.reset()
                this.$parent.success();
              } else {
                this.$message.error(res.msg)
              }
            })
            .catch((e) => {
              console.log(e);
            });

        } else {
          console.log('error submit!!');
          return false;
        }
      })
    },

    //更新联系人
    save(formName) {
      let data = {
        contactName: this.ruleForm.contactName,
        province: this.ruleForm.address.province,
        city: this.ruleForm.address.city,
        district: this.ruleForm.address.district,
        detail: this.ruleForm.address.detail,
        contactEmail: this.ruleForm.contactEmail,
        contactTel: this.ruleForm.contactTel,
        contactCustomerKey: this.ruleForm.contactCustomerKey,
        contactKey: this.ruleForm.contactKey,
        contactCode: this.ruleForm.contactCode
      }

      console.log(this.ruleForm.address)
      this.$refs[formName].validate((valid) => {
        if (valid) {
          console.log("#this.form#", data)

          Contactupdate(data)
            .then((res) => {
              if (res.data.code === 200) {
                this.$message.success('修改成功!')
                this.reset()
                this.$parent.success();
              } else {
                this.$message.error(res.msg)
              }
            })
            .catch((e) => {
              console.log(e);
            });

        } else {
          console.log('error submit!!');
          return false;
        }
      })
    },
    reset() {
      this.ruleForm = {
        // contactInfo: "",//联系信息
        contactName: "",//联系人
        // contactType: "",//联系类型 TEL, ADDRESS, EMAIL
        contactCode: "",//联系人编号
        address: {
          // 省
          province: "",
          // 市
          city: "",
          // 区
          district: "",
          //详细地址
          detail: ""
        },//联系地址
        contactEmail: "",//联系邮箱
        contactTel: "",//联系电话
        contactCustomerKey: "",//联系人key
        contactKey: ""//客户key
      }
    },
    getResult() {
      if (!this.ruleForm.address.district) {
        this.addStr = ''
      } else if (this.ruleForm.address.district && this.ruleForm.address.detail) {
        this.addStr = '' + this.ruleForm.address.province + "," + this.ruleForm.address.city + "," + this.ruleForm.address.district + "," + this.ruleForm.address.detail
      } else {
        this.addStr = '' + this.ruleForm.address.province + "," + this.ruleForm.address.city + "," + this.ruleForm.address.district
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