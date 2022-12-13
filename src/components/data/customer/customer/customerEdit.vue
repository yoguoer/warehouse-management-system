<template>
  <el-drawer size="98%" :title="ifCreate ? '新增客户信息' : '客户编辑'" :visible.sync="drawer" :direction="direction"
    :close-on-press-escape="false" :show-close="false" :wrapperClosable="false">

    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <h4 class="msg-title">基础信息</h4>
      <div style="width:1000px;">
        <el-row>
          <el-col :span="8">
            <el-form-item label="联系人名称" prop="customerName">
              <el-input v-model="ruleForm.customerName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="客户编号" prop="customerNumber">
              <el-input v-model="ruleForm.customerNumber"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="所属分类" v-model="ruleForm.categoryKey">
              <listBoxF>
                <template slot="content">
                  <treeselect class="treeSelect-option" v-model="value" :multiple="multiple" :normalizer="normalizer"
                    style="width:250px;" :options="list" placeholder="请选择" @select="selectNode" />
                </template>
              </listBoxF>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="8">
            <el-form-item label="联系电话" prop="customerTel">
              <el-input v-model="ruleForm.customerTel"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="联系邮箱" prop="customerEmail">
              <el-input v-model="ruleForm.customerEmail"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-form-item label="联系地址" prop="address" v-model="ruleForm.address">
              <!-- <el-input v-model="item.contactTypeFlex3"></el-input> -->
              <checkAddress ref="address" v-model="ruleForm.address" :form="ruleForm.address" />
            </el-form-item>
          </el-col>
        </el-row>
      </div>

      <!-- <div><span class="msg-title">联系人信息</span><el-button type="primary" plain size="small" icon="el-icon-plus">添加联系人</el-button></div> -->
      <div><span class="msg-title">常用联系人</span></div>
      <div v-for="(item, index) in tempList" :key="index" style="width:1300px">
        <div class="toAddcontact">
          <el-form :model="item" :rules="addRules" ref="addForm" label-width="100px" class="add-ruleForm">
            <el-row>
              <el-col :span="10">
                <el-form-item label="联系人编号" prop="contactNumber">
                  <el-input v-model="item.contactNumber"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="联系人姓名" prop="contactName">
                  <el-input v-model="item.contactName"></el-input>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="10">
                <el-form-item label="联系电话" prop="contactTel">
                  <el-input v-model="item.contactTel"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="联系邮箱" prop="contactEmail">
                  <el-input v-model="item.contactEmail"></el-input>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row>
              <el-col>
                <el-form-item label="联系地址" prop="address" v-model="item.address">
                  <checkAddress ref="address" v-model="item.address" :key="index" :form="item.address" />
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <div style="color: #409EFF;float:right">
            <span style="margin:20px;cursor:pointer" @click="toAdd(index)">添加</span>
            <span style="margin:20px;cursor:pointer" @click="toDelete(index)" v-if="tempList.length > 1">删除</span>
          </div>
        </div>

      </div>

      <el-form-item style="display:inline-block;float:right;margin:10px 20%">
        <el-button type="primary" style="margin:50px 20px;cursor:pointer" @click="save('ruleForm')"
          v-if="ifCreate == false">保存</el-button>
        <el-button type="primary" style="margin:50px 20px;cursor:pointer" @click="create('ruleForm')" v-else>立即创建
        </el-button>
        <!-- <el-button @click="resetForm('ruleForm')">重置</el-button> -->
        <el-button style="margin:50px 20px;cursor:pointer" @click="close()">关闭</el-button>
      </el-form-item>

    </el-form>

  </el-drawer>
</template>

<script>
import checkAddress from '@/components/public/checkAddress.vue'
import listBoxF from '@/components/public/listBoxF/listBoxF.vue'
/**
  * 树形组件 用于选择框
  */
// import the component
import Treeselect from '@riophae/vue-treeselect'
// import the styles
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import { Customeradd, Customerupdate, getCategoryTree } from '@/api/data'

export default {
  name: 'guestEdit',
  data() {
    return {
      currentIndex: 0,
      addStr: "",
      tempList: [
        {
          contactName: "",//联系人
          contactNumber: "",//联系人编号
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
      ],
      ifCreate: true,
      direction: 'btt',
      list: [],
      //当前位置
      tempIndex: 0,
      //联系人信息项
      ruleForm: {
        categoryKey: "",//所属分类
        // customerContactList: [],//联系人信息列表（可能有多个）
        customerKey: "",//客户key
        customerName: "",//客户名称
        customerNumber: "",//客户编号
        customerTel: "",
        customerAddress: "",
        customerEmail: "",
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
      },
      rules: {
        categoryKey: [
          { required: true, message: '请输入所属分类', trigger: 'blur' },
        ],
        customerNumber: [
          { required: true, message: '请输入客户编码', trigger: 'blur' }
        ],
        customerName: [
          { required: true, message: '请输入客户名称', trigger: 'blur' }
        ]
      },

      addForm: [],
      addRules: {
        categoryKey: [
          { required: true, message: '请输入所属分类', trigger: 'blur' },
        ],
        customerNumber: [
          { required: true, message: '请输入客户编码', trigger: 'blur' }
        ],
        customerName: [
          { required: true, message: '请输入客户名称', trigger: 'blur' }
        ],
        contactName: [
          { required: true, message: '请输入联系人姓名', trigger: 'blur' },
        ],
        contactTypeFlex3: [
          { required: true, message: '请输入联系地址', trigger: 'blur' }
        ],
        contactTypeFlex5: [
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
    multiple: {
      type: Boolean,
      default: false
    },
  },

  watch: {
    // 监听选中值的变化
    value: {
      deep: true,
      handler(val) {
        this.$emit('getSelectVal', val)
      }
    },
  },

  components: {
    checkAddress,
    listBoxF,
    Treeselect
  },
  created() {
    this.getTree()
    if (this.rowData.customerKey) {
      this.ifCreate = false
      this.ruleForm.categoryKey = this.rowData.categoryKey
      this.ruleForm.customerName = this.rowData.customerName
      this.ruleForm.customerKey = this.rowData.customerKey
      this.ruleForm.customerNumber = this.rowData.customerNumber
      this.ruleForm.customerTypeKey = this.rowData.customerTypeKey
      this.ruleForm.customerTel = this.rowData.customerTel
      this.ruleForm.customerEmail = this.rowData.customerEmail
      this.ruleForm.address.province = this.rowData.province
      this.ruleForm.address.city = this.rowData.city
      this.ruleForm.address.district = this.rowData.district
      this.ruleForm.address.detail = this.rowData.detail
      //常用联系人非空
      if (this.rowData.customerContactList.length > 0) {
        this.tempList = []
        this.rowData.customerContactList.forEach(item => {
          this.tempList.push({
            contactName: item.contactName,
            address: {
              province: item.province,
              city: item.city,
              district: item.district,
              detail: item.detail,
            },
            contactEmail: item.contactEmail,
            contactTel: item.contactTel,
            contactCustomerKey: item.contactCustomerKey,
            contactNumber: item.contactNumber,
            contactKey: item.contactKey,
          })
        })
      }
      console.log(this.tempList, this.ruleForm)
    } else {
      this.ifCreate = true
    }

    if (this.ruleForm.categoryKey) {
      this.value = this.ruleForm.categoryKey
    } else {
      this.value = null
    }
  },
  methods: {
    // 自定义参数键值名称
    normalizer(node) {
      //去掉children=[]的children属性
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.categoryKey,
        label: node.categoryName,
        children: node.children,
        level: node.level
      }
    },
    selectNode(node) {
      this.ruleForm.categoryKey = node.categoryKey || ''
      this.value = node.categoryKey
      console.log("selectNode(node)：", this.ruleForm)
    },
    getTree() {
      let params = {
        categoryType: "CUSTOMER",
      }
      getCategoryTree(params).then(res => {
        if (res.data.code === 200) {
          res.data.data.forEach(item => {
            if (item.categoryName == '全部') {
              let index = res.data.data.indexOf(item)
              res.data.data.splice(index, 1)
            }
          });
          this.list = res.data.data
          console.log(this.list);
        } else {
          console.log('error');
        }
      })
    },

    //添加客户
    create(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let arr = []
          this.tempList.forEach(item => {
            arr.push({
              contactName: item.contactName,
              province: item.address.province,
              city: item.address.city,
              district: item.address.district,
              detail: item.address.detail,
              contactEmail: item.contactEmail,
              contactTel: item.contactTel,
              contactCustomerKey: item.contactCustomerKey,
              contactNumber: item.contactNumber,
              contactKey: item.contactKey,
            })
          })
          let data = {
            categoryKey: this.ruleForm.categoryKey,
            customerName: this.ruleForm.customerName,
            customerKey: this.ruleForm.customerKey,
            customerNumber: this.ruleForm.customerNumber,
            customerTypeKey: this.ruleForm.customerTypeKey,
            customerTel: this.ruleForm.customerTel,
            customerEmail: this.ruleForm.customerEmail,
            province: this.ruleForm.address.province,
            city: this.ruleForm.address.city,
            district: this.ruleForm.address.district,
            detail: this.ruleForm.address.detail,
            customerContactList: arr
          }
          console.log("#this.ruleForm#", data)
          Customeradd(data)
            .then((res) => {
              if (res.data.code === 200) {
                this.$message.success('创建成功!')
                this.reset()
                this.$parent.success();
              } else {
                this.$message.error(res.msg)
              }
            }).catch((e) => {
              console.log(e);
            });
        } else {
          console.log('error submit!!');
          return false;
        }
      })
    },

    //编辑保存
    save(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let arr = []
          this.tempList.forEach(item => {
            arr.push({
              contactName: item.contactName,
              province: item.address.province,
              city: item.address.city,
              district: item.address.district,
              detail: item.address.detail,
              contactEmail: item.contactEmail,
              contactTel: item.contactTel,
              contactCustomerKey: item.contactCustomerKey,
              contactNumber: item.contactNumber,
              contactKey: item.contactKey,
            })
          })
          let data = {
            categoryKey: this.ruleForm.categoryKey,
            customerName: this.ruleForm.customerName,
            customerKey: this.ruleForm.customerKey,
            customerNumber: this.ruleForm.customerNumber,
            customerTypeKey: this.ruleForm.customerTypeKey,
            customerTel: this.ruleForm.customerTel,
            customerEmail: this.ruleForm.customerEmail,
            province: this.ruleForm.address.province,
            city: this.ruleForm.address.city,
            district: this.ruleForm.address.district,
            detail: this.ruleForm.address.detail,
            customerContactList: arr
          }
          Customerupdate(data)
            .then((res) => {
              if (res.data.code === 200) {
                this.$message.success('修改成功!')
                this.reset()
                this.$parent.success();
              } else {
                this.$message.error(res.msg)
              }
            }).catch((e) => {
              console.log(e);
            });
        } else {
          console.log('error submit!!');
          return false;
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

    toDelete(index) {
      this.tempList.splice(index, 1)
    },
    toAdd(index) {
      console.log(index, this.tempList);
      //每次增加，都要添加一个空的数组来存放新输入的数据
      this.tempList.push({
        contactCustomerKey: "",
        contactInfo: "",//联系信息
        contactName: "",//联系人
        tel: "",
        email: "",
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
      })
      console.log(this.tempList, this.ruleForm)
    },
    reset() {
      this.ruleForm = {
        categoryKey: "",//所属分类
        // customerContactList: [],//联系人信息列表（可能有多个）
        customerKey: "",//客户key
        customerName: "",//客户名称
        customerNumber: "",//客户编号
        customerTel: "",
        customerAddress: "",
        customerEmail: "",
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
      }
      this.tempList = [
        {
          contactName: "",//联系人
          contactNumber: "",//联系人编号
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
      ]
    },
  },
}
</script>
<style lang="scss">
.msg-title {
  font-weight: 500;
  margin-right: 20px;
  line-height: 60px;
}

.el-select {
  width: 100%;
}

.demo-ruleForm {
  padding: 0 80px 50px 80px;
}

.toAddcontact {
  border: 1px solid #eee;
  padding: 20px;
  margin: 20px;
  width: 1000px;
  float: left;

  .add-ruleForm {
    padding: 50px 80px 10px 80px;
  }
}
</style>