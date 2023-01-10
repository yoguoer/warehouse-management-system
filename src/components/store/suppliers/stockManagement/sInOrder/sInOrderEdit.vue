<template>
  <el-dialog size="30%" :title="ifCreate ? '新增' : '编辑'" :visible.sync="drawer" :direction="direction"
    :close-on-press-escape="false" :show-close="false" :wrapperClosable="false" :append-to-body='true' width="1200px">
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-row>
        <el-col :span="10">
          <el-form-item label="门店" prop="shopCode">
            <el-select size="middle" v-model="ruleForm.shopCode" placeholder="门店" style="width:100%;" clearable disabled
              ref="selection">
              <el-option @click.native="setShopName" v-for="item in shopOptions" :key="item.shopKey"
                :label="item.shopName" :value="item.shopCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="商品" prop="goodsCode">
            <el-select size="middle" v-model="ruleForm.goodsCode" placeholder="商品" style="width:100%;" clearable
              disabled ref="goodsSelect">
              <el-option @click.native="setGoodsName" v-for="item in goodsOptions" :key="item.goodsCode"
                :label="item.goodsName" :value="item.goodsCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="10">
          <el-form-item label="供应商" prop="supplierCode">
            <el-select size="middle" v-model="ruleForm.supplierCode" placeholder="供应商" style="width:100%;" clearable
              disabled ref="supplierSelect">
              <el-option @click.native="setSupplierName" v-for="item in supplierOptions" :key="item.supplierKey"
                :label="item.supplierName" :value="item.supplierCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <span style="margin-left: 8%;">起止日期</span>
          <el-date-picker style="width:310px;margin-left: 10px;" v-model="value2" type="daterange" align="right"
            disabled size="large" unlink-panels range-separator="至" start-placeholder="预计日期" end-placeholder="最迟日期"
            :picker-options="pickerOptions" @click.native="setTime" value-format="yyyy-MM-dd HH:mm:ss">
          </el-date-picker>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10">
          <el-form-item label="计划数" prop="inputPlan">
            <el-input v-model="ruleForm.inputPlan" clearable placeholder="计划数" disabled></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="订购价格" prop="inputPrice">
            <el-input v-model="ruleForm.inputPrice" clearable placeholder="订购价格" disabled></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10">
          <el-form-item label="实际数" prop="inputActual">
            <el-input v-model="ruleForm.inputActual" clearable placeholder="实际数"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="退货数" prop="returnNum" v-if="ruleForm.type == 1">
            <el-input v-model="ruleForm.returnNum" clearable placeholder="退货数"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="接收状态" prop="status" v-if="ruleForm.status < 3">
            <el-select size="middle" v-model="ruleForm.status" placeholder="接收状态" style="width:100%;" clearable>
              <el-option v-for="item in statusOptions" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="车辆" prop="vehicleCode" v-if="ruleForm.status == 2">
            <el-select size="middle" v-model="ruleForm.vehicleCode" placeholder="车辆" style="width:100%;" clearable>
              <el-option v-for="item in vehicleOptions" :key="item.vehicleKey" :label="item.vehicleCode"
                :value="item.vehicleCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10">
          <el-form-item label="退货原因" prop="returnReason" v-if="ruleForm.type == 1">
            <el-input v-model="ruleForm.returnReason" clearable placeholder="退货原因" type="textarea" disabled></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div class="dialog_footer">
      <el-button type="primary" @click="save('ruleForm')" v-if="ifCreate == false">保存</el-button>
      <el-button type="primary" @click="create('ruleForm')" v-else>立即创建</el-button>
      <el-button @click="close()">关闭</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { inputWarehouseUpdate, inputWarehouseAdd } from '@/api/purchasing'
import { shoplist, goodslist, inventorylist, Supplierlist, positionList, vehicleList } from '@/api/data'
import { ShopInventoryList } from '@/api/warehouse'
import { UserList } from '@/api/api'

export default {
  name: 'guestEdit',
  data() {
    return {
      direction: 'btt',
      ifCreate: false,
      ruleForm: {
        inputWarehouseKey: "",
        shopCode: "",
        shopName: "",
        goodsCode: "",
        goodsName: "",
        supplierCode: "",
        supplierName: "",
        inputPlan: "",
        inputPrice: "",
        inputActual: "",
        inventoryCode: "",
        positionCode: "",
        createTime: "",
        deadlineTime: "",
        vehicleCode: "",
        status: "",
        type: "",
        shopPeopleCode: "",
        inventoryPeopleCode: "",
        returnReason: "",
        isDeleted: "",
        returnNum: ""
      },
      shopOptions: [],
      goodsOptions: [],
      positionOptions: [],
      supplierOptions: [],
      inventoryOptions: [],
      vehicleOptions: [],
      // userOptions:[],
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            end.setTime(end.getTime() + 3600 * 1000 * 24 * 7);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            end.setTime(end.getTime() + 3600 * 1000 * 24 * 30);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            end.setTime(end.getTime() + 3600 * 1000 * 24 * 90);
            picker.$emit('pick', [start, end]);
          }
        }]
      },
      value2: '',
      rules: {
        inputActual: [
          { required: true, message: '请设置实际数', trigger: 'blur' },
        ],
        inputPlan: [
          { required: true, message: '请设置计划数', trigger: 'blur' },
        ],
        inputPrice: [
          { required: true, message: '请设置入库价格', trigger: 'blur' },
        ],
        status: [
          { required: true, message: '请设置接收状态', trigger: 'blur' },
        ],
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
    this.getshoplist()
    this.getgoodslist()
    this.getSupplierlist()
    this.getvehicleList()
    // this.getUserList()
    // this.getinventorylist();
    if (this.rowData.inputWarehouseKey) {
      this.ruleForm.inputWarehouseKey = this.rowData.inputWarehouseKey
      this.ruleForm.shopCode = this.rowData.shopCode
      this.ruleForm.shopName = this.rowData.shopName
      this.ruleForm.goodsCode = this.rowData.goodsCode
      this.ruleForm.goodsName = this.rowData.goodsName
      this.ruleForm.supplierCode = this.rowData.supplierCode
      this.ruleForm.supplierName = this.rowData.supplierName
      this.ruleForm.inputPlan = this.rowData.inputPlan
      this.ruleForm.inputPrice = this.rowData.inputPrice
      this.ruleForm.inputActual = this.rowData.inputActual
      this.ruleForm.inventoryCode = this.rowData.inventoryCode
      this.ruleForm.positionCode = this.rowData.positionCode
      this.ruleForm.createTime = this.rowData.createTime
      this.ruleForm.deadlineTime = this.rowData.deadlineTime
      this.ruleForm.vehicleCode = this.rowData.vehicleCode
      this.ruleForm.status = this.rowData.status
      this.ruleForm.type = this.rowData.type
      this.ruleForm.shopPeopleCode = this.rowData.shopPeopleCode
      this.ruleForm.inventoryPeopleCode = this.rowData.inventoryPeopleCode
      this.ruleForm.isDeleted = this.rowData.isDeleted
      this.ruleForm.returnReason = this.rowData.returnReason
      this.ruleForm.returnNum = this.rowData.returnNum
      this.value2 = [this.rowData.createTime, this.rowData.deadlineTime]
    } else {
      this.ifCreate = true
    }
  },
  methods: {
    getvehicleList() {
      vehicleList().then(res => {
        if (res.data.code == 200) {
          this.vehicleOptions = res.data.data
        } else {
          this.$message.error("获取失败!");
        }
      });
    },
    // getUserList() {
    //   UserList({userType: 2}).then(res => {
    //     this.userOptions = res.data.data
    //     this.$forceUpdate()
    //   }).catch(err => {
    //     console.log(err)
    //   });
    // },
    getshoplist() {
      shoplist().then(res => {
        if (res.data.code == 200) {
          this.shopOptions = res.data.data
        } else {
          this.$message.error("获取失败!");
        }
      });
    },
    getgoodslist() {
      goodslist().then(res => {
        if (res.data.code == 200) {
          this.goodsOptions = res.data.data
        } else {
          this.$message.error("获取失败!");
        }
      });
    },
    getSupplierlist() {
      Supplierlist().then(res => {
        if (res.data.code == 200) {
          this.supplierOptions = res.data.data
        } else {
          this.$message.error("获取失败!");
        }
      });
    },
    getShopInventoryList(item) {
      ShopInventoryList({ shopCode: item }).then(res => {
        if (res.data.code == 200) {
          this.inventoryOptions = res.data.data
        } else {
          this.$message.error("获取失败!");
        }
      });
    },
    // getinventorylist() {
    //   inventorylist()
    //     .then((res) => {
    //       if (res.data.code === 200) {
    //         this.inventoryOptions = res.data.data
    //       } else {
    //         this.$message.error(res.msg);
    //       }
    //     })
    //     .catch((e) => {
    //       console.log(e);
    //     });
    // },
    setShopName() {
      this.getShopInventoryList(this.ruleForm.shopCode)
      this.ruleForm.shopName = this.$refs.selection.selectedLabel
    },
    setSupplierName() {
      this.ruleForm.supplierName = this.$refs.supplierSelect.selectedLabel
    },
    setGoodsName() {
      this.ruleForm.goodsName = this.$refs.goodsSelect.selectedLabel
    },
    setPosition() {
      this.ruleForm.inventoryName = this.$refs.inventorySelect.selectedLabel
      let choosenItem = this.inventoryOptions.filter(item => {
        return item.inventoryCode == this.ruleForm.inventoryCode
      });
      this.getpositionList(choosenItem[0].inventoryKey)
    },
    getpositionList(inventoryKey) {
      positionList({ inventoryKey: inventoryKey }).then(res => {
        if (res.data.code == 200) {
          this.positionOptions = res.data.data
        } else {
          this.$message.error("获取失败!");
        }
      });
    },
    setTime() {
      this.ruleForm.createTime = this.value2[0]
      this.ruleForm.deadlineTime = this.value2[1]
      // console.log(this.ruleForm.createTime, this.ruleForm);
    },
    close() {
      this.$parent.drawer = false
      this.$emit('close')
    },
    save(formName) {
      this.ruleForm.createTime = this.value2[0]
      this.ruleForm.deadlineTime = this.value2[1]
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let data = {
            shopCode: this.ruleForm.shopCode,
            shopName: this.ruleForm.shopName,
            goodsCode: this.ruleForm.goodsCode,
            goodsName: this.ruleForm.goodsName,
            supplierCode: this.ruleForm.supplierCode,
            supplierName: this.ruleForm.supplierName,
            inputPlan: this.ruleForm.inputPlan,
            inputPrice: this.ruleForm.inputPrice,
            inputActual: this.ruleForm.inputActual,
            inventoryCode: this.ruleForm.inventoryCode,
            positionCode: this.ruleForm.positionCode,
            createTime: this.ruleForm.createTime,
            deadlineTime: this.ruleForm.deadlineTime,
            vehicleCode: this.ruleForm.vehicleCode,
            status: this.ruleForm.status,
            type: this.ruleForm.type,
            shopPeopleCode: this.ruleForm.shopPeopleCode,
            inventoryPeopleCode: this.ruleForm.inventoryPeopleCode,
            returnReason: this.ruleForm.returnReason,
            returnNum: this.ruleForm.returnNum,
            isDeleted: this.ruleForm.isDeleted,
            inputWarehouseKey: this.ruleForm.inputWarehouseKey
          }
          inputWarehouseUpdate(data).then(res => {
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
      this.ruleForm.createTime = this.value2[0]
      this.ruleForm.deadlineTime = this.value2[1]
      this.ruleForm.deadlineTime = this.value2[1]
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let data = {
            shopCode: this.ruleForm.shopCode,
            shopName: this.ruleForm.shopName,
            goodsCode: this.ruleForm.goodsCode,
            goodsName: this.ruleForm.goodsName,
            supplierCode: this.ruleForm.supplierCode,
            supplierName: this.ruleForm.supplierName,
            inputPlan: this.ruleForm.inputPlan,
            inputPrice: this.ruleForm.inputPrice,
            inputActual: this.ruleForm.inputActual,
            inventoryCode: this.ruleForm.inventoryCode,
            positionCode: this.ruleForm.positionCode,
            createTime: this.ruleForm.createTime,
            deadlineTime: this.ruleForm.deadlineTime,
            vehicleCode: this.ruleForm.vehicleCode,
            status: this.ruleForm.status,
            isDeleted: this.ruleForm.isDeleted,
            type: this.ruleForm.type,
            shopPeopleCode: this.ruleForm.shopPeopleCode,
            inventoryPeopleCode: this.ruleForm.inventoryPeopleCode,
            returnReason: this.ruleForm.returnReason,
            returnNum: this.ruleForm.returnNum
          }
          inputWarehouseAdd(data).then(res => {
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