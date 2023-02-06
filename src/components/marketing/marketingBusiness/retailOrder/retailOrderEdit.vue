<template>
  <el-dialog size="30%" :title="ifCreate ? '新增零售单' : '编辑零售单'" :visible.sync="drawer" :direction="direction"
    :close-on-click-modal="false" :close-on-press-escape="false" :show-close="false" :wrapperClosable="false"
    :append-to-body='true' width="1200px">

    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-row>
        <el-col :span="10">
          <el-form-item label="门店" prop="shopCode">
            <el-select size="middle" v-model="ruleForm.shopCode" placeholder="门店" style="width:100%;" clearable
              ref="selection">
              <el-option @click.native="setShopName(item)" v-for="item in shopOptions" :key="item.shopCode"
                :label="item.shopName" :value="item.shopCode" :disabled="(item.shopStatus==6||item.shopStatus==1)?false:true">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="商品" prop="goodsCode">
            <el-select size="middle" v-model="ruleForm.goodsCode" placeholder="商品" style="width:100%;" clearable
              ref="goodsSelect">
              <el-option @click.native="setGoodsName(item)" v-for="item in goodsOptions" :key="item.goodsKey"
                :label="item.goodsName" :value="item.goodsCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10">
          <el-form-item label="客户" prop="customerCode">
            <!-- <el-select size="middle" v-model="ruleForm.customerCode" placeholder="客户" style="width:100%;" clearable
              @click.native="setCustomerName" ref="customerSelect">
              <el-option v-for="item in customerOptions" :key="item.customerKey" :label="item.customerName"
                :value="item.customerCode">
              </el-option>
            </el-select> -->
            <el-input v-model="ruleForm.customerName" clearable placeholder="客户" disabled></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="仓库" prop="inventoryCode">
            <el-select size="middle" v-model="ruleForm.inventoryCode" placeholder="仓库" style="width:100%;" clearable
              ref="inventorySelect">
              <el-option @click.native="setPosition" v-for="item in inventoryOptions" :key="item.inventoryKey"
                :label="item.inventoryName" :value="item.inventoryCode" :disabled="(item.status==1)?false:true">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10">
          <el-form-item label="库位" prop="positionCode">
            <el-select size="middle" v-model="ruleForm.positionCode" placeholder="库位" style="width:100%;" clearable>
              <el-option v-for="item in positionOptions" :key="item.positionCode" :label="item.positionCode"
                :value="item.positionCode" clearable placeholder="库位">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="计划数" prop="outputPlan">
            <el-input v-model="ruleForm.outputPlan" clearable placeholder="计划数" :min="0" type="Number"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10">
          <el-form-item label="出库价格" prop="outputPrice">
            <el-input v-model="ruleForm.outputPrice" clearable placeholder="出库价格" :min="0" type="Number"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="出库类型" prop="type">
            <el-select size="small" v-model="ruleForm.type" placeholder="出库类型" clearable>
              <el-option label="零售出库" :value="0"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10">
          <el-form-item label="门店操作员" prop="shopPeopleCode">
            <el-select size="middle" v-model="ruleForm.shopPeopleCode" placeholder="门店操作员" style="width:100%;"
              clearable>
              <el-option v-for="item in userOptions" :key="item.userId" :label="item.userName" :value="item.userCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="仓库操作员" prop="inventoryPeopleCode">
            <el-select size="middle" v-model="ruleForm.inventoryPeopleCode" placeholder="仓库操作员" style="width:100%;"
              clearable>
              <el-option v-for="item in userOptions1" :key="item.userId" :label="item.userName" :value="item.userCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10">
          <el-form-item label="车辆" prop="vehicleCode">
            <el-select size="middle" v-model="ruleForm.vehicleCode" placeholder="车辆" style="width:100%;" clearable>
              <el-option v-for="item in vehicleOptions" :key="item.vehicleCode" :label="item.vehicleCode"
                :value="item.vehicleCode">
              </el-option>
            </el-select>
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
import { outputWarehouseUpdate, outputWarehouseAdd } from '@/api/marketing'
import { CustomerList, positionList, vehicleList } from '@/api/data'
import { getByshopCode } from '@/api/warehouse'
import { UserList } from '@/api/api'
import moment from 'moment'
import { detailWarehouseUpdate, detailWarehouseAdd, shopkeeperWarehouseByShopCode,shopkeeperWarehouseList } from '@/api/warehouse'
// import { ShopInventoryList } from "@/api/warehouse";

export default {
  name: 'guestEdit',
  data() {
    return {
      direction: 'btt',
      ifCreate: false,
      ruleForm: {
        outputWarehouseKey: "",
        shopCode: "",
        shopName: "",
        goodsCode: "",
        goodsName: "",
        customerCode: "retail",
        customerName: "零售客户",
        outputPlan: "",
        outputPrice: "",
        outputActual: "",
        inventoryCode: "",
        positionCode: "",
        createTime: "",
        deadlineTime: "",
        vehicleCode: "",
        status: 5,
        type: 0,
        shopPeopleCode: "",
        inventoryPeopleCode: "",
        returnReason: "",
        isDeleted: "",
        returnNum: "",
        outputShopCode: "",
        outputShopName: "",
      },
      shopOptions: [],
      goodsOptions: [],
      vehicleOptions: [],
      positionOptions: [],
      customerOptions: [],
      inventoryOptions: [],
      userOptions: [],
      userOptions1: [],
      rules: {
        shopCode: [
          { required: true, message: '请选择门店', trigger: 'blur' },
        ],
        goodsCode: [
          { required: true, message: '请选择商品', trigger: 'blur' },
        ],
        customerCode: [
          { required: true, message: '请选择客户', trigger: 'blur' },
        ],
        inventoryCode: [
          { required: true, message: '请选择仓库', trigger: 'blur' },
        ],
        outputPlan: [
          { required: true, message: '请设置计划数', trigger: 'blur' },
        ],
        outputPrice: [
          { required: true, message: '请设置出库价格', trigger: 'blur' },
        ],
        type: [
          { required: true, message: '请设置出库类型', trigger: 'blur' },
        ],
        shopPeopleCode: [
          { required: true, message: '请设置门店操作员', trigger: 'blur' },
        ],
        inventoryPeopleCode: [
          { required: true, message: '请设置仓库操作员', trigger: 'blur' },
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
    this.getshoplist()
    this.getUserList()
    this.getvehicleList()
    if (this.rowData.outputWarehouseKey) {
      this.ruleForm.outputWarehouseKey = this.rowData.outputWarehouseKey
      this.ruleForm.shopCode = this.rowData.shopCode
      this.ruleForm.shopName = this.rowData.shopName
      this.ruleForm.goodsCode = this.rowData.goodsCode
      this.ruleForm.goodsName = this.rowData.goodsName
      this.ruleForm.customerCode = this.rowData.customerCode
      this.ruleForm.customerName = this.rowData.customerName
      this.ruleForm.outputPlan = this.rowData.outputPlan
      this.ruleForm.outputPrice = this.rowData.outputPrice
      this.ruleForm.outputActual = this.rowData.outputActual
      this.ruleForm.inventoryCode = this.rowData.inventoryCode
      this.ruleForm.positionCode = this.rowData.positionCode
      this.ruleForm.createTime = this.rowData.createTime
      this.ruleForm.deadlineTime = this.rowData.deadlineTime
      this.ruleForm.vehicleCode = this.rowData.vehicleCode
      // this.ruleForm.status = this.rowData.status
      this.ruleForm.type = this.rowData.type
      this.ruleForm.isDeleted = this.rowData.isDeleted
      this.ruleForm.shopPeopleCode = this.rowData.shopPeopleCode
      this.ruleForm.inventoryPeopleCode = this.rowData.inventoryPeopleCode
      this.ruleForm.returnReason = this.rowData.returnReason
      this.ruleForm.returnNum = this.rowData.returnNum
      this.ruleForm.outputShopCode = this.rowData.outputShopCode
      this.ruleForm.outputShopName = this.rowData.outputShopName
      // this.value2 = [this.rowData.createTime, this.rowData.deadlineTime]
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
    getUserList() {
      UserList({ userType: 2 }).then(res => {
        this.userOptions = res.data.data
        this.$forceUpdate()
      }).catch(err => {
        console.log(err)
      });
      UserList({ userType: 1 }).then(res => {
        this.userOptions1 = res.data.data
        this.$forceUpdate()
      }).catch(err => {
        console.log(err)
      });
    },
    getshoplist() {
      shopkeeperWarehouseList().then(res => {
        if (res.data.code == 200) {
          this.shopOptions = res.data.data
          res.data.data.forEach(t=>{
            this.shopOptions.forEach(item=>{// 去掉已有仓库的
            if(item.shopCode==t.shopCode){
                let index=this.shopOptions.indexOf(item)
                this.shopOptions.splice(index,1)
              }
            })
          })
        } else {
          this.$message.error("获取失败!");
        }
      })
    },
    getgoodslist(item) {
      shopkeeperWarehouseByShopCode({ shopCode: item.shopCode, shopName: item.shopName }).then(res => {
        if (res.data.code == 200) {
          this.goodsOptions = res.data.data
        } else {
          this.$message.error("获取失败!");
        }
      });
    },
    getCustomerList() {
      CustomerList().then(res => {
        if (res.data.code == 200) {
          this.customerOptions = res.data.data
        } else {
          this.$message.error("获取失败!");
        }
      });
    },
    getShopInventoryList() {
      getByshopCode({ shopCode: this.ruleForm.shopCode }).then(res => {
        if (res.data.code == 200) {
          this.inventoryOptions = res.data.data
          this.getpositionList()
        } else {
          this.$message.error("获取失败!");
        }
      });
    },
    setShopName(item) {
      this.goodsOptions = []
      this.getShopInventoryList()
      this.ruleForm.shopName = this.$refs.selection.selectedLabel
      this.getgoodslist(item)
    },
    setCustomerName() {
      this.ruleForm.customerName = this.$refs.customerSelect.selectedLabel
    },
    setGoodsName(item) {
      console.log(this.$refs.goodsSelect.selectedLabel)
      this.ruleForm.goodsName = this.$refs.goodsSelect.selectedLabel
      this.ruleForm.outputPrice = item.priceRetail
    },
    setPosition() {
      this.ruleForm.inventoryName = this.$refs.inventorySelect.selectedLabel
      let choosenItem = this.inventoryOptions.filter(item => {
        return item.inventoryCode == this.ruleForm.inventoryCode
      });
      this.getpositionList(choosenItem[0].inventoryKey)
    },
    getpositionList() {
      this.inventoryOptions.forEach(item => {
        if (item.inventoryCode == this.ruleForm.inventoryCode) {
          positionList({ inventoryKey: item.inventoryKey }).then(res => {
            if (res.data.code == 200) {
              this.positionOptions = res.data.data
            } else {
              this.$message.error("获取失败!");
            }
          });
        }
      })
    },
    // setTime() {
    //   this.ruleForm.createTime = this.value2[0]
    //   this.ruleForm.deadlineTime = this.value2[1]
    //   // console.log(this.ruleForm.createTime, this.ruleForm);
    // },
    close() {
      this.$parent.drawer = false
    },
    save(formName) {
      this.ruleForm.outputActual = this.ruleForm.outputPlan
      this.ruleForm.createTime = moment().format("YYYY-MM-DD HH:mm:ss");
      this.ruleForm.deadlineTime = moment().format("YYYY-MM-DD HH:mm:ss");
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let data = {
            shopCode: this.ruleForm.shopCode,
            shopName: this.ruleForm.shopName,
            goodsCode: this.ruleForm.goodsCode,
            goodsName: this.ruleForm.goodsName,
            customerCode: this.ruleForm.customerCode,
            customerName: this.ruleForm.customerName,
            outputPlan: this.ruleForm.outputPlan,
            outputPrice: this.ruleForm.outputPrice,
            outputActual: this.ruleForm.outputActual,
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
            returnNum: this.ruleForm.returnNum,
            outputShopCode: this.ruleForm.outputShopCode,
            outputShopName: this.ruleForm.outputShopName,
            outputWarehouseKey: this.ruleForm.outputWarehouseKey
          }
          outputWarehouseUpdate(data).then(res => {
            if (res.data.code == 200) {
              let detailData = {
                inputOutputKey: res.data.data.outputWarehouseKey,
                shopkeeperWarehouseKey: "",
                type: 1,
                // 交易类型(0采购入库、1采购退货出库、2零售出库、3零售退货入库、4客户订购出库、5客户订购退货入库、6调货入库、7调货出库)
                transType: 2,
                quantity: this.ruleForm.outputActual,
                startNum: "",
                finalNum: "",
                atTime: moment().format("YYYY-MM-DD HH:mm:ss"),
                status: this.rowData.status,
                detailWarehouseKey: "",
                shopCode: this.ruleForm.shopCode,
                goodsCode: this.ruleForm.goodsCode,
              }
              detailWarehouseUpdate(detailData).then(res => {
                this.$message.success("新增成功!");
                this.$parent.success()
                this.$forceUpdate()
              })
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
      this.ruleForm.outputActual = this.ruleForm.outputPlan
      this.ruleForm.createTime = moment().format("YYYY-MM-DD HH:mm:ss");
      this.ruleForm.deadlineTime = moment().format("YYYY-MM-DD HH:mm:ss");
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let data = {
            shopCode: this.ruleForm.shopCode,
            shopName: this.ruleForm.shopName,
            goodsCode: this.ruleForm.goodsCode,
            goodsName: this.ruleForm.goodsName,
            customerCode: this.ruleForm.customerCode,
            customerName: this.ruleForm.customerName,
            outputPlan: this.ruleForm.outputPlan,
            outputPrice: this.ruleForm.outputPrice,
            outputActual: this.ruleForm.outputActual,
            inventoryCode: this.ruleForm.inventoryCode,
            positionCode: this.ruleForm.positionCode,
            createTime: this.ruleForm.createTime,
            deadlineTime: this.ruleForm.deadlineTime,
            vehicleCode: this.ruleForm.vehicleCode,
            isDeleted: this.ruleForm.isDeleted,
            status: this.ruleForm.status,
            type: this.ruleForm.type,
            shopPeopleCode: this.ruleForm.shopPeopleCode,
            inventoryPeopleCode: this.ruleForm.inventoryPeopleCode,
            returnNum: this.ruleForm.returnNum,
            outputShopCode: this.ruleForm.outputShopCode,
            outputShopName: this.ruleForm.outputShopName,
            returnReason: this.ruleForm.returnReason
          }
          outputWarehouseAdd(data).then(res => {
            if (res.data.code == 200) {
              let detailData = {
                inputOutputKey: res.data.data.outputWarehouseKey,
                shopkeeperWarehouseKey: "",
                type: 1,
                // 交易类型(0采购入库、1采购退货出库、2零售出库、3零售退货入库、4客户订购出库、5客户订购退货入库、6调货入库、7调货出库)
                transType: 2,
                quantity: this.ruleForm.outputActual,
                startNum: "",
                finalNum: "",
                atTime: moment().format("YYYY-MM-DD HH:mm:ss"),
                status: this.rowData.status,
                detailWarehouseKey: "",
                shopCode: this.ruleForm.shopCode,
                goodsCode: this.ruleForm.goodsCode,
              }
              detailWarehouseAdd(detailData).then(res => {
                this.$message.success("新增成功!");
                this.$parent.success()
                this.$forceUpdate()
              })
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