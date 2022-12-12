<template>
  <el-drawer size="98%" :title="ifCreate ? '新增供应商' : '供应商编辑'" :visible.sync="drawer" :direction="direction"
    :close-on-press-escape="false" :show-close="false" :wrapperClosable="false">

    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <h4 class="msg-title">基础信息</h4>
      <el-row>
        <el-col :span="6">
          <el-form-item label="供应商编号" prop="supplierNumber">
            <el-input v-model="ruleForm.supplierNumber"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="供应商名称" prop="supplierName">
            <el-input v-model="ruleForm.supplierName"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="备注" prop="superlierDescription">
            <el-input v-model="ruleForm.superlierDescription"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="6">
          <el-form-item label="所属分类" v-model="ruleForm.categoryKey" prop="categoryKey">
            <listBoxF>
              <template slot="content">
                <treeselect class="treeSelect-option" v-model="value" :normalizer="normalizer" :options="list" 
                  placeholder="请选择" @select="selectNode" style="width:335px;" />
              </template>
            </listBoxF>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <span class="msg-title">供应商地址</span>
        <div v-for="(item, index) in addressList" :key="index" style="width:100%">
          <div class="toAddcontact">
            <el-form :model="item" :rules="rules1" ref="addressForm" label-width="100px" class="add-ruleForm">
              <el-row>
                <el-col :span="10">
                  <el-form-item label="地址类型" prop="addressType">
                    <el-input v-model="item.addressType"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col>
                  <el-form-item label="供应商地址" prop="address" v-model="item.address">
                    <checkAddress v-model="item.address" :form="item.address" :key="index" ref="address" />
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form>
            <div style="color: #409EFF;float:right">
              <span style="margin:20px;cursor:pointer" @click="addressAdd()">添加</span>
              <span style="margin:20px;cursor:pointer" @click="addressDelete(index)"
                v-if="addressList.length > 1">删除</span>
            </div>
          </div>
        </div>
      </el-row>

      <el-row>
        <span class="msg-title">联系人信息</span>
        <div v-for="(item, index) in contactList" :key="index" style="width:1300px">
          <div class="toAddcontact">
            <el-form :model="item" :rules="rules2" ref="contactForm" label-width="100px" class="add-ruleForm">
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
              <span style="margin:20px;cursor:pointer" @click="toDelete(index)" v-if="contactList.length > 1">删除</span>
            </div>
          </div>
        </div>
      </el-row>

      <el-row>
        <span class="msg-title">开票信息</span>
        <div v-for="(item, index) in bankList" :key="index" style="width:1300px">
          <div class="toAddcontact">
            <el-form :model="item" ref="bankForm" :rules="rules3" label-width="100px" class="add-ruleForm">
              <el-row>
                <el-col :span="10">
                  <el-form-item label="开户银行" prop="bankName">
                    <el-input v-model="item.bankName"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="10">
                  <el-form-item label="银行账号" prop="accountNumber">
                    <el-input v-model="item.accountNumber"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="10">
                  <el-form-item label="账户名" prop="accountName">
                    <el-input v-model="item.accountName"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="10">
                  <el-form-item label="联系电话" prop="accountTel">
                    <el-input v-model="item.accountTel"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="10">
                  <el-form-item label="税号" prop="taxNumber">
                    <el-input v-model="item.taxNumber"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="10" style="display:none">
                  <el-form-item label="开票key" prop="billingKey">
                    <el-input v-model="item.billingKey"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form>
            <div style="color: #409EFF;float:right">
              <span style="margin:20px;cursor:pointer" @click="bankAdd(index)">添加</span>
              <span style="margin:20px;cursor:pointer" @click="bankDelete(index)" v-if="bankList.length > 1">删除</span>
            </div>
          </div>
        </div>
      </el-row>

      <el-form-item style="float:right;margin-right:20%;">
        <el-button type="primary" @click="save('ruleForm')" v-if="ifCreate == false">保存</el-button>
        <el-button type="primary" @click="create('ruleForm')" v-else>立即创建</el-button>
        <!-- <el-button @click="resetForm('ruleForm')">重置</el-button> -->
        <el-button @click="close()">关闭</el-button>
      </el-form-item>

    </el-form>

  </el-drawer>
</template>


<script>
import listBoxF from '@/components/public/listBoxF/listBoxF.vue'
import checkAddress from '@/components/public/checkAddress.vue'
import { Supplieradd, getCategoryTree, Supplierupdate } from '@/api/data'
/**
 * 树形组件 用于选择框
 */
// import the component
import Treeselect from '@riophae/vue-treeselect'
// import the styles
import '@riophae/vue-treeselect/dist/vue-treeselect.css'

export default {
  name: 'supplyEdit',
  data() {
    return {
      ifCreate: true,
      direction: 'btt',
      list: [],
      //联系人列表
      contactList: [
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
          contactSupplierKey: "",//联系人key
          contactKey: ""//客户key
        },
      ],
      //地址列表
      addressList: [
        {
          addressType: "",
          addressKey:"",
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
          // supplierAddressKey:""
        }
      ],
      //银行列表
      bankList: [
        {
          bankName: "",
          accountNumber: "",
          accountName:"",
          accountTel:"",
          billingKey: "",
          taxNumber: ""
        }
      ],
      //整个总的表单
      ruleForm: {
        supplierKey: "",
        supplierName: "",
        categoryKey: "",
        superlierDescription: "",
        supplierNumber: "",
        supplierBillingList: [],
        supplierAddressList: [],
        supplierContactList: []
      },
      //{people:"联系人",sName:"反反复复",sId:"xs56342",num:"1233211234567",city:"城市",type:"类型type",msg:"2358",mType:"刚刚",type:"contactEmail",bankNum:"1915411",bank:"男",position:"组长",address:"广州天河区"},
      rules: {
        supplierName: [
          { required: true, message: '请输入供应商名称', trigger: 'blur' },
        ],
        supplierNumber: [
          { required: true, message: '请输入供应商编号', trigger: 'blur' },
        ],
        categoryKey: [
          { required: true, message: '请选择所属分类', trigger: 'blur' },
        ]
      },
      rules1: {
        addressType: [
          { required: true, message: '请输入地址类型', trigger: 'blur' },
        ],
        address: [
          { required: true, message: '请选择地址', trigger: 'blur' },
        ]
      },
      rules2: {
        contactNumber: [
          { required: true, message: '请输入联系人编号', trigger: 'blur' },
        ],
        contactName: [
          { required: true, message: '请输入联系人姓名', trigger: 'blur' },
        ],
        contactTel: [
          { required: true, message: '请输入联系电话', trigger: 'blur' },
        ]
      },
      rules3: {
        accountNumber: [
          { required: true, message: '请输入账号', trigger: 'blur' },
        ],
        accountName: [
          { required: true, message: '请输入名', trigger: 'blur' },
        ],
        bankName: [
          { required: true, message: '请输入开户银行', trigger: 'blur' },
        ],
        taxNumber: [
          { required: true, message: '请输入税号', trigger: 'blur' },
        ]
      },
    }
  },
  props: {
    drawer: {
      default: true,
    },
    rowData: {},
  },
  components: {
    checkAddress,
    listBoxF,
    Treeselect
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
  created() {
    this.getTree()
    console.log("编辑rowData", this.rowData)
    //初始化
    if (!!this.rowData.supplierKey) {
      this.ifCreate = false
      this.ruleForm.categoryKey = this.rowData.categoryKey
      this.ruleForm.supplierName = this.rowData.supplierName
      this.ruleForm.superlierDescription = this.rowData.superlierDescription
      this.ruleForm.supplierNumber = this.rowData.supplierNumber
      this.ruleForm.supplierKey = this.rowData.supplierKey

      //常用联系人非空-需要遍历处理
      if (this.rowData.supplierContactList.length > 0) {
        this.contactList = []
        this.rowData.supplierContactList.forEach(item => {
          this.contactList.push({
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

      // 供应商地址-需要遍历处理
      if (this.rowData.supplierAddressList.length > 0) {
        this.addressList = []
        this.rowData.supplierAddressList.forEach(item => {
          let add = {
            addressType: "",
            addressKey:"",
            address: {
              // 省
              province: "",
              // 市
              city: "",
              // 区
              district: "",
              //详细地址
              detail: ""
            }
          }
          add.addressType = item.addressType
          add.addressKey=item.addressKey
          add.address.province = item.province
          add.address.city = item.city
          add.address.district = item.district
          add.address.detail = item.detail
          this.addressList.push(add)
        })
      }

      //开票信息
      if (this.rowData.supplierBillingList.length > 0) {
        this.bankList = this.rowData.supplierBillingList
      }
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
      // console.log("selectNode(node)：", this.ruleForm)
    },

    getTree() {
      let params = {
        categoryType: "SUPPLIER",
      }
      getCategoryTree(params).then(res => {
        if (res.data.code === 200) {
          res.data.data.forEach(item => {
              if(item.categoryName=='全部'){
                let index=res.data.data.indexOf(item)
                res.data.data.splice(index,1)
              }
            });
          this.list = res.data.data
          // console.log(this.list);
        } else {
          console.log('error');
        }
      })
    },

    //重置
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },

    close() {
      this.reset()
      this.$emit('close')
      this.value = null
    },

    //编辑保存
    save(formName) {
      // console.log(this.contactList)
      //遍历，取联系人列表
      this.contactList.forEach(item => {
        this.ruleForm.supplierContactList.push({
          contactName: item.contactName,
          province: item.address.province,
          city: item.address.city,
          district: item.address.district,
          detail: item.address.detail,
          contactEmail: item.contactEmail,
          contactTel: item.contactTel,
          contactSupplierKey: item.contactSupplierKey,
          contactNumber: item.contactNumber,
          contactKey: item.contactKey,
        })
      })

      //遍历，取供应商地址列表
      this.addressList.forEach(item => {
        this.ruleForm.supplierAddressList.push(
          {
            addressType: item.addressType,
            addressKey:item.addressKey,
            city: item.address.city,
            detail: item.address.detail,
            district: item.address.district,
            province: item.address.province,
          })
      })

      //遍历，取开票信息列表
      this.bankList.forEach(item => {
        this.ruleForm.supplierBillingList.push(item)
      })

      //表单校验
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let data = this.ruleForm
          // console.log("#this.ruleForm#", data)

          Supplierupdate(data)
            .then((res) => {
              if (res.data.code == 200) {
                this.$message.success("创建成功!");
                this.$parent.success();
                this.reset()
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

    //创建
    create(formName) {
      //遍历，取联系人列表
      this.contactList.forEach(item => {
        this.ruleForm.supplierContactList.push({
          contactName: item.contactName,
          province: item.address.province,
          city: item.address.city,
          district: item.address.district,
          detail: item.address.detail,
          contactEmail: item.contactEmail,
          contactTel: item.contactTel,
          contactSupplierKey: item.contactSupplierKey,
          contactNumber: item.contactNumber,
          contactKey: item.contactKey,
        })
      })

      //遍历，取供应商地址列表
      this.addressList.forEach(item => {
        this.ruleForm.supplierAddressList.push(
          {
            addressType: item.addressType,
            addressKey:item.addressKey,
            city: item.address.city,
            detail: item.address.detail,
            district: item.address.district,
            province: item.address.province,
          })
      })

      //遍历，取开票信息列表
      this.bankList.forEach(item => {
        this.ruleForm.supplierBillingList.push(item)
      })

      //表单校验
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let data = this.ruleForm
          // console.log("#this.ruleForm#", data)

          Supplieradd(data)
            .then((res) => {
              if (res.data.code == 200) {
                this.$message.success("创建成功!");
                this.$parent.success();
                this.reset()
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
      //联系人列表
      this.contactList = [
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
          contactSupplierKey: "",//联系人key
          contactKey: ""//客户key
        },
      ],
        //地址列表
        this.addressList = [
          {
            addressType: "",
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
            // supplierAddressKey:""
          }
        ],
        //银行列表
        this.bankList = [
          {
            bankName: "",
            accountNumber: "",
            accountName:"",
            accountTel:"",
            billingKey: "",
            taxNumber: ""
          }
        ],
        //整个总的表单
        this.ruleForm = {
          supplierKey: "",
          supplierName: "",
          categoryKey: "",
          superlierDescription: "",
          supplierNumber: "",
          supplierBillingList: [],
          supplierAddressList: [],
          supplierContactList: []
        }
    },

    toDelete(index) {
      this.contactList.splice(index, 1)
    },
    bankDelete(index) {
      this.bankList.splice(index, 1)
    },
    addressDelete(index) {
      this.addressList.splice(index, 1)
    },

    toAdd(index) {
      // console.log(index,this.contactList);
      //每次增加，都要添加一个空的数组来存放新输入的数据
      this.contactList.push({
        contactSupplierKey: "",
        contactKey:"",
        contactName: "",//联系人
        contactTel: "",
        contactEmail: "",
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
      // console.log(this.contactList, this.ruleForm)
    },
    addressAdd() {
      //每次增加，都要添加一个空的数组来存放新输入的数据
      this.addressList.push({
        addressType: "",
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
        // supplierAddressKey:""
      })
      // console.log(this.addressList, this.ruleForm)
    },
    bankAdd() {
      //每次增加，都要添加一个空的数组来存放新输入的数据
      this.bankList.push({
        bankName: "",
        accountNumber: "",
        accountName:"",
        accountTel:"",
        billingKey:"",
        taxNumber: ""
      })
      // console.log(this.bankList, this.ruleForm)
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

.msg-title {
  font-weight: 500;
  margin-right: 20px;
  line-height: 60px;
}

.list-box-f .cont {
  width: 260px;
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
  width: 1300px;
  float: left;

  .add-ruleForm {
    padding: 50px 80px 10px 80px;
  }
}
</style>