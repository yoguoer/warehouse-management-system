<template>
    <el-drawer size="80%" :title="ifCreate ? '新增供应商' : '编辑供应商'" :visible.sync="drawer" :direction="direction"
      :close-on-press-escape="false" :show-close="false" :wrapperClosable="false">
  
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <h4 class="msg-title">基础信息</h4>
        <el-row>
          <el-col :span="6">
            <el-form-item label="供应商编号" prop="superlierNumber">
              <el-input v-model="ruleForm.superlierNumber" clearable placeholder="供应商编号"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="供应商名称" prop="supplierName">
              <el-input v-model="ruleForm.supplierName" clearable placeholder="供应商名称"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="所属分类" v-model="ruleForm.categoryKey" prop="categoryKey">
              <listBoxF style="width:335px">
                <template slot="content">
                  <treeselect class="treeSelect-option" v-model="value" :normalizer="normalizer" :options="list" clearable style="width:335px"
                    placeholder="请选择" @select="selectNode"/>
                </template>
              </listBoxF>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="备注" prop="superlierDescription">
              <el-input v-model="ruleForm.superlierDescription" clearable placeholder="备注"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
  
        <!-- <el-row>
          <span class="msg-title">供应商地址</span>
          <div v-for="(item, index) in addressList" :key="index" style="width:100%">
            <div class="toAddcontact">
              <el-form :model="item" :rules="rules1" ref="addressForm" label-width="100px" class="add-ruleForm">
                <el-row>
                  <el-col :span="10">
                    <el-form-item label="地址类型" prop="addressType">
                      <el-input v-model="item.addressType" clearable placeholder="地址类型"></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="10">
                    <el-form-item label="供应商地址" prop="address" v-model="item.address">
                      <checkAddress :form="item.address" :key="index" ref="address" />
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
          <div v-for="(item, index) in tempList" :key="index" style="width:1300px">
            <div class="toAddcontact">
              <el-form :model="item" :rules="rules2" ref="contactForm" label-width="100px" class="add-ruleForm">
                <el-row>
                  <el-col :span="10">
                    <el-form-item label="联系人姓名" prop="contactName">
                      <el-input v-model="item.contactName" clearable placeholder="联系人姓名"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="10">
                    <el-form-item label="联系信息" prop="contactInfo">
                      <el-input v-model="item.contactInfo" clearable placeholder="联系信息"></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="10">
                    <el-form-item label="联系电话" prop="tel">
                      <el-input v-model="item.tel" clearable placeholder="联系电话"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="10">
                    <el-form-item label="联系邮箱" prop="email">
                      <el-input v-model="item.email" clearable placeholder="联系邮箱"></el-input>
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
        </el-row>
  
        <el-row>
          <span class="msg-title">开票信息</span>
          <div v-for="(item, index) in bankList" :key="index" style="width:1300px">
            <div class="toAddcontact">
              <el-form :model="item" ref="bankForm" :rules="rules3" label-width="100px" class="add-ruleForm">
                <el-row>
                  <el-col :span="10">
                    <el-form-item label="开户银行" prop="accountBankName">
                      <el-input v-model="item.accountBankName" clearable placeholder="开户银行"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="10">
                    <el-form-item label="银行账号" prop="accountNumber">
                      <el-input v-model="item.accountNumber" clearable placeholder="银行账号"></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="10">
                    <el-form-item label="税号" prop="taxNumber">
                      <el-input v-model="item.taxNumber" clearable placeholder="税号"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="10" style="display:none">
                    <el-form-item label="开票key" prop="supplierKey">
                      <el-input v-model="item.supplierKey" clearable placeholder="开票key"></el-input>
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
        </el-row> -->
  
        <el-row>
          <el-form-item style="display:inline-block;float:right;margin:10px 30%">
            <el-button type="primary" style="margin:50px 20px;cursor:pointer" @click="save('ruleForm')"
              v-if="ifCreate == false">保存</el-button>
            <el-button type="primary" style="margin:50px 20px;cursor:pointer" @click="create('ruleForm')"
              v-else>立即创建</el-button>
            <!-- <el-button @click="resetForm('ruleForm')">重置</el-button> -->
            <el-button style="margin:50px 20px;cursor:pointer" @click="close()">关闭</el-button>
          </el-form-item>
        </el-row>
  
      </el-form>
  
    </el-drawer>
  </template>
  
  
  <script>
  import listBoxF from '@/components/public/listBoxF/listBoxF.vue'
  // import checkAddress from '@/components/public/checkAddress.vue'
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
        // //联系人列表
        // tempList: [
        //   {
        //     // supplierContactKey:"",
        //     contactInfo: "",//联系信息
        //     contactType: "",//联系类型
        //     contactName: "",//联系人
        //     tel: "",
        //     email: "",
        //     address: {
        //       // 省
        //       province: "",
        //       // 市
        //       city: "",
        //       // 区
        //       district: "",
        //       //详细地址
        //       detail: ""
        //     },//联系地址
        //   }
        // ],
        // //地址列表
        // addressList: [
        //   {
        //     addressType: "",
        //     address: {
        //       // 省
        //       province: "",
        //       // 市
        //       city: "",
        //       // 区
        //       district: "",
        //       //详细地址
        //       detail: ""
        //     },//联系地址
        //     // supplierAddressKey:""
        //   }
        // ],
        // //银行列表
        // bankList: [
        //   {
        //     accountBankName: "",
        //     accountNumber: "",
        //     // supplierKey:"",
        //     taxNumber: ""
        //   }
        // ],
        //整个总的表单
        ruleForm: {
          // supplierKey: "",
          supplierName: "",
          categoryKey: "",
          superlierDescription: "",
          superlierNumber: "",
          // supplierBillingList: [],
          // supplierAddressList: [],
          // supplierContactList: []
        },
        //{people:"联系人",sName:"反反复复",sId:"xs56342",num:"1233211234567",city:"城市",type:"类型type",msg:"2358",mType:"刚刚",type:"email",bankNum:"1915411",bank:"男",position:"组长",address:"广州天河区"},
        rules: {
          supplierName: [
            { required: true, message: '请输入供应商名称', trigger: 'blur' },
          ],
          superlierNumber: [
            { required: true, message: '请输入供应商编号', trigger: 'blur' },
          ],
          categoryKey: [
            { required: true, message: '请选择所属分类', trigger: 'blur' },
          ]
        },
        // rules1: {
        //   addressType: [
        //     { required: true, message: '请输入地址类型', trigger: 'blur' },
        //   ],
        //   address: [
        //     { required: true, message: '请选择地址', trigger: 'blur' },
        //   ]
        // },
        // rules2: {
        //   contactName: [
        //     { required: true, message: '请输入联系人姓名', trigger: 'blur' },
        //   ],
        //   tel: [
        //     { required: true, message: '请输入联系电话', trigger: 'blur' },
        //   ]
        // },
        // rules3: {
        //   accountNumber: [
        //     { required: true, message: '请输入账号', trigger: 'blur' },
        //   ],
        //   accountBankName: [
        //     { required: true, message: '请输入开户银行', trigger: 'blur' },
        //   ],
        //   taxNumber: [
        //     { required: true, message: '请输入税号', trigger: 'blur' },
        //   ]
        // },
      }
    },
    props: {
      drawer: {
        default: true,
      },
      rowData: {},
    },
    components: {
      // checkAddress,
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
      //初始化
      if (this.rowData) {
        this.ifCreate = false
        this.ruleForm.categoryKey = this.rowData.categoryKey
        this.ruleForm.supplierName = this.rowData.supplierName
        this.ruleForm.superlierDescription = this.rowData.superlierDescription
        this.ruleForm.superlierNumber = this.rowData.superlierNumber
        this.ruleForm.supplierKey = this.rowData.supplierKey
        console.log("this.ruleForm", this.ruleForm)
        // //常用联系人非空
        // if (this.rowData.supplierContactList.length != 0) {
        //   this.tempList = []
        //   let arr = []
        //   this.rowData.supplierContactList.forEach(item => {
        //     if (arr.length == 0) {
        //       arr.push(item.supplierContactKey)
        //     }
        //     arr.forEach(t => {
        //       if (t != item.supplierContactKey) {
        //         arr.push(item.supplierContactKey)
        //       }
        //     })
        //     console.log("去重！！！", arr)
  
        //     let current = {
        //       supplierContactKey: "",
        //       contactInfo: "",
        //       contactName: "",
        //       tel: "",
        //       email: "",
        //       address: {
        //         province: "",
        //         city: "",
        //         district: "",
        //         detail: ""
        //       }
        //     }
        //     current.contactInfo = item.contactInfo
        //     current.contactName = item.contactName
        //     current.supplierContactKey = item.supplierContactKey
        //     //挨个赋值
        //     if (item.contactType == "EMAIL") {
        //       current.email = item.contactTypeFlex1
        //     }
        //     if (item.contactType == "TEL") {
        //       current.tel = item.contactTypeFlex1
        //     }
        //     if (item.contactType == "ADDRESS") {
        //       current.address.province = item.contactTypeFlex1
        //       current.address.city = item.contactTypeFlex2
        //       current.address.district = item.contactTypeFlex3
        //       current.address.detail = item.contactTypeFlex34
        //     }
        //     this.tempList.push(current)
        //   })
        // }
  
      //   // 供应商地址-需要遍历处理
      //   if (this.rowData.supplierAddressList.length > 0) {
      //     this.addressList = []
      //     this.rowData.supplierAddressList.forEach(item => {
      //       let add = {
      //         addressType: "",
      //         address: {
      //           // 省
      //           province: "",
      //           // 市
      //           city: "",
      //           // 区
      //           district: "",
      //           //详细地址
      //           detail: ""
      //         }
      //       }
      //       add.addressType = item.adddressType
      //       add.address.province = item.provinceCode
      //       add.address.city = item.cityCode
      //       add.address.district = item.districtCode
      //       add.address.detail = item.address
      //       this.addressList.push(add)
      //     })
      //     console.log("addressList", this.addressList)
      //   }
  
      //   //开票信息
      //   if (this.rowData.supplierBillingList.length > 0) {
      //     this.bankList = this.rowData.supplierBillingList
      //   }
      //   // 联系信息
      //   if (this.rowData.supplierBillingList.length > 0) {
      //     this.supplierContactList = this.rowData.supplierBillingList
      //   }
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
        // console.log("selectNode(node)：",this.ruleForm)
      },
  
      getTree() {
        let params = {
          categoryType: "SUPPLIER",
        }
        getCategoryTree(params).then(res => {
          if (res.code === 200) {
            res.data.forEach(item => {
              if (item.categoryName == '全部') {
                let index = res.data.indexOf(item)
                res.data.splice(index, 1)
              }
            });
            this.list = res.data
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
        // console.log(this.tempList)
        // //遍历，取联系人列表
        // this.tempList.forEach(item => {
        //   if (item.email != "") {
        //     this.ruleForm.supplierContactList.push({
        //       supplierContactKey: item.supplierContactKey,
        //       contactInfo: item.contactInfo,
        //       contactName: item.contactName,
        //       contactType: "EMAIL",
        //       contactTypeFlex1: item.email
        //     })
        //   }
        //   if (item.tel != "") {
        //     this.ruleForm.supplierContactList.push({
        //       supplierContactKey: item.supplierContactKey,
        //       contactInfo: item.contactInfo,
        //       contactName: item.contactName,
        //       contactType: "TEL",
        //       contactTypeFlex1: item.tel
        //     })
        //   }
        //   if (item.address.district != "") {
        //     this.ruleForm.supplierContactList.push({
        //       supplierContactKey: item.supplierContactKey,
        //       contactInfo: item.contactInfo,
        //       contactName: item.contactName,
        //       contactType: "ADDRESS",
        //       contactTypeFlex1: item.address.province,
        //       contactTypeFlex2: item.address.city,
        //       contactTypeFlex3: item.address.district,
        //       contactTypeFlex4: item.address.detail,
        //     })
        //   }
        // })
        // //遍历，取供应商地址列表
        // this.addressList.forEach(item => {
        //   this.ruleForm.supplierAddressList.push(
        //     {
        //       addressType: item.addressType,
        //       city: item.address.city,
        //       detail: item.address.detail,
        //       district: item.address.district,
        //       province: item.address.province,
        //     })
        // })
        // //遍历，取开票信息列表
        // this.bankList.forEach(item => {
        //   this.ruleForm.supplierBillingList.push(item)
        // })
        //表单校验
        this.$refs[formName].validate((valid) => {
          if (valid) {
            let data = this.ruleForm
            console.log("#this.ruleForm#", data)
  
            Supplierupdate(JSON.stringify(data))
              .then((res) => {
                if (res.code == 200) {
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
        // //遍历，取联系人列表
        // this.tempList.forEach(item => {
        //   if (item.email != "") {
        //     this.ruleForm.supplierContactList.push({
        //       contactInfo: item.contactInfo,
        //       contactName: item.contactName,
        //       contactType: "EMAIL",
        //       contactTypeFlex1: item.email
        //     })
        //   }
        //   if (item.tel != "") {
        //     this.ruleForm.supplierContactList.push({
        //       contactInfo: item.contactInfo,
        //       contactName: item.contactName,
        //       contactType: "TEL",
        //       contactTypeFlex1: item.tel
        //     })
        //   }
        //   if (item.address.district != "") {
        //     this.ruleForm.supplierContactList.push({
        //       contactInfo: item.contactInfo,
        //       contactName: item.contactName,
        //       contactType: "ADDRESS",
        //       contactTypeFlex1: item.address.province,
        //       contactTypeFlex2: item.address.city,
        //       contactTypeFlex3: item.address.district,
        //       contactTypeFlex4: item.address.detail,
        //     })
        //   }
        // })
        // //遍历，取供应商地址列表
        // this.addressList.forEach(item => {
        //   this.ruleForm.supplierAddressList.push(
        //     {
        //       adddressType: item.addressType,
        //       address: {
        //         province: item.address.province,
        //         city: item.address.city,
        //         detail: item.address.detail,
        //         district: item.address.district
        //       }
        //     })
        // })
        // //遍历，取开票信息列表
        // this.bankList.forEach(item => {
        //   this.ruleForm.supplierBillingList.push(item)
        // })
        //表单校验
        this.$refs[formName].validate((valid) => {
          if (valid) {
            let data = this.ruleForm
            console.log("#this.ruleForm#", data)
  
            Supplieradd(JSON.stringify(data))
              .then((res) => {
                if (res.code == 200) {
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
        // //联系人列表
        // this.tempList = [
        //   {
        //     // supplierContactKey:"",
        //     contactInfo: "",//联系信息
        //     contactType: "",//联系类型
        //     contactName: "",//联系人
        //     tel: "",
        //     email: "",
        //     address: {
        //       // 省
        //       province: "",
        //       // 市
        //       city: "",
        //       // 区
        //       district: "",
        //       //详细地址
        //       detail: ""
        //     },//联系地址
        //   }
        // ],
        //   //地址列表
        //   this.addressList = [
        //     {
        //       addressType: "",
        //       address: {
        //         // 省
        //         province: "",
        //         // 市
        //         city: "",
        //         // 区
        //         district: "",
        //         //详细地址
        //         detail: ""
        //       },//联系地址
        //       // supplierAddressKey:""
        //     }
        //   ],
        //   //银行列表
        //   this.bankList = [
        //     {
        //       accountBankName: "",
        //       accountNumber: "",
        //       // supplierKey:"",
        //       taxNumber: ""
        //     }
        //   ],
          //整个总的表单
          this.ruleForm = {
            // supplierKey: "",
            supplierName: "",
            categoryKey: "",
            superlierDescription: "",
            superlierNumber: "",
            // supplierBillingList: [],
            // supplierAddressList: [],
            // supplierContactList: []
          }
      },
  
      // toDelete(index) {
      //   this.tempList.splice(index, 1)
      // },
      // bankDelete(index) {
      //   this.bankList.splice(index, 1)
      // },
      // addressDelete(index) {
      //   this.addressList.splice(index, 1)
      // },
  
      // toAdd(index) {
      //   // console.log(index,this.tempList);
      //   //每次增加，都要添加一个空的数组来存放新输入的数据
      //   this.tempList.push({
      //     supplierContactKey: "",
      //     contactInfo: "",//联系信息
      //     contactName: "",//联系人
      //     tel: "",
      //     email: "",
      //     address: {
      //       // 省
      //       province: "",
      //       // 市
      //       city: "",
      //       // 区
      //       district: "",
      //       //详细地址
      //       detail: ""
      //     },//联系地址
      //   })
      //   console.log(this.tempList, this.ruleForm)
      // },
      // addressAdd() {
      //   //每次增加，都要添加一个空的数组来存放新输入的数据
      //   this.addressList.push({
      //     addressType: "",
      //     address: {
      //       // 省
      //       province: "",
      //       // 市
      //       city: "",
      //       // 区
      //       district: "",
      //       //详细地址
      //       detail: ""
      //     },//联系地址
      //     // supplierAddressKey:""
      //   })
      //   console.log(this.addressList, this.ruleForm)
      // },
      // bankAdd() {
      //   //每次增加，都要添加一个空的数组来存放新输入的数据
      //   this.bankList.push({
      //     accountBankName: "",
      //     accountNumber: "",
      //     // supplierKey:"",
      //     taxNumber: ""
      //   })
      //   console.log(this.bankList, this.ruleForm)
      // },
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