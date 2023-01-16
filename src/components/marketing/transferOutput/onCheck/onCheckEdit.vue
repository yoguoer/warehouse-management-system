<template>
  <el-dialog size="30%" :title="ifCreate ? '新增' : '处理调货入库申请'" :visible.sync="drawer" :direction="direction"
    :close-on-press-escape="false" :show-close="false" :wrapperClosable="false" :append-to-body='true' width="1200px">
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="150px" class="demo-ruleForm">
      <el-row>
        <el-col :span="10">
          <el-form-item label="调货入库门店" prop="shopCode">
            <el-select size="middle" v-model="ruleForm.shopCode" placeholder="调货入库门店" style="width:100%;" clearable
              disabled ref="selection">
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
          <el-form-item label="调货出库门店" prop="outputShopCode">
            <el-select size="middle" v-model="ruleForm.outputShopCode" placeholder="调货出库门店" style="width:100%;" disabled
              clearable ref="inputShopSelect">
              <el-option @click.native="setoutputShopName" v-for="item in inputShopOptions" :key="item.shopKey"
                :label="item.shopName" :value="item.shopCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="出库类型" prop="type">
            <el-select size="small" v-model="ruleForm.type" placeholder="出库类型" clearable disabled>
              <el-option label="零售出库" :value="0"></el-option>
              <el-option label="客户订购" :value="1"></el-option>
              <el-option label="调货出库" :value="2"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10">
          <el-form-item label="申请数" prop="outputPlan">
            <el-input v-model="ruleForm.outputPlan" clearable placeholder="申请数" disabled type="Number"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="入库价格" prop="outputPrice">
            <el-input v-model="ruleForm.outputPrice" clearable placeholder="入库价格" disabled type="Number"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10">
          <span style="margin-left: 8%;">起止日期</span>
          <el-date-picker style="width:310px;margin-left: 10px;" v-model="value2" type="daterange" align="right"
            disabled size="large" unlink-panels range-separator="至" start-placeholder="预计日期" end-placeholder="最迟日期"
            :picker-options="pickerOptions" @click.native="setTime" value-format="yyyy-MM-dd HH:mm:ss">
          </el-date-picker>
        </el-col>
        <el-col :span="10">
          <el-form-item label="审批" prop="checkStatus">
            <el-select size="small" v-model="ruleForm.checkStatus" placeholder="审批结果" clearable>
              <el-option label="未审批" :value="0" disabled @click.native="ifShow = false">
                <span style="float: left">
                  <i class="el-icon-minus"></i> 未审批
                </span>
              </el-option>
              <el-option label="同意" :value="1" @click.native="ifShow = true">
                <span style="float: left">
                  <i class="el-icon-check"></i> 同意
                </span>
              </el-option>
              <el-option label="驳回" :value="2" @click.native="ifShow = false">
                <span style="float: left">
                  <i class="el-icon-close"></i> 驳回
                </span>
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10">
          <el-form-item label="审批意见" prop="description">
            <el-input v-model="ruleForm.description" clearable placeholder="审批意见" type="textarea"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="10" v-if="ifShow">
          <el-form-item label="车辆" prop="vehicleCode">
            <el-select size="middle" v-model="ruleForm.vehicleCode" placeholder="车辆" style="width:100%;" clearable>
              <el-option v-for="item in vehicleOptions" :key="item.vehicleKey" :label="item.vehicleCode"
                :value="item.vehicleCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row v-if="ifShow">
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
      <el-row v-if="ifShow">
        <el-col :span="10">
          <el-form-item label="仓库" prop="inventoryCode">
            <el-select size="middle" v-model="ruleForm.inventoryCode" placeholder="仓库" style="width:100%;" clearable
              ref="inventorySelect">
              <el-option @click.native="setPosition" v-for="item in inventoryOptions" :key="item.inventoryKey"
                :label="item.inventoryName" :value="item.inventoryCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="库位" prop="positionCode">
            <el-select size="middle" v-model="ruleForm.positionCode" placeholder="库位" style="width:100%;" clearable>
              <el-option v-for="item in positionOptions" :key="item.positionCode" :label="item.positionCode"
                :value="item.positionCode" clearable placeholder="库位">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div class="dialog_footer">
      <el-button type="primary" @click="save('ruleForm')" v-if="ifCreate == false">提交</el-button>
      <el-button type="primary" @click="create('ruleForm')" v-else>提交</el-button>
      <el-button @click="close()">关闭</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { transferCheckUpdate, transferCheckAdd } from '@/api/check'
import { outputWarehouseUpdate, outputWarehouseAdd } from '@/api/marketing'
import { goodslist, vehicleList, positionList } from '@/api/data'
import { UserList } from '@/api/api'
import moment from 'moment'
import { ShopInventoryList } from '@/api/warehouse'
import { getByshopCode } from '@/api/warehouse'
import { inputWarehouseUpdate } from '@/api/purchasing'
import { detailWarehouseUpdate, detailWarehouseAdd, shopkeeperWarehouseList, } from '@/api/warehouse'

export default {
  name: 'guestEdit',
  data() {
    return {
      direction: 'btt',
      ifShow: false,
      ifCreate: false,
      ruleForm: {
        transferCheckKey: "",
        checkNum: "",
        checkStatus: "",
        description: "",
        happenTime: "",
        checkTime: "",
        outputWarehouseKey: "",
        inputWarehouseKey: "",
        goodsCode: "",
        returnNum: "",
        shopCode: "",
        shopName: "",
        goodsName: "",
        supplierCode: "",
        supplierName: "",
        outputPlan: "",
        outputPrice: "",
        outputActual: "",
        inventoryCode: "",
        positionCode: "",
        createTime: "",
        deadlineTime: "",
        vehicleCode: "",
        status: 4,
        type: 2,
        shopPeopleCode: "",
        inventoryPeopleCode: "",
        isDeleted: 0,
        returnReason: "",
        outputShopName: "",
        outputShopCode: "",
        inputShopCode: "",
      },
      shopOptions: [],
      goodsOptions: [],
      positionOptions: [],
      inputShopOptions: [],
      inventoryOptions: [],
      vehicleOptions: [],
      userOptions: [],
      userOptions1: [],
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
        shopCode: [
          { required: true, message: '请选择调货入库门店', trigger: 'blur' },
        ],
        outputShopCode: [
          { required: true, message: '请选择调货出库门店', trigger: 'blur' },
        ],
        goodsCode: [
          { required: true, message: '请选择商品', trigger: 'blur' },
        ],
        supplierCode: [
          { required: true, message: '请选择供应商', trigger: 'blur' },
        ],
        inventoryCode: [
          { required: true, message: '请选择仓库', trigger: 'blur' },
        ],
        outputPlan: [
          { required: true, message: '请设置申请数', trigger: 'blur' },
        ],
        outputPrice: [
          { required: true, message: '请设置入库价格', trigger: 'blur' },
        ],
        type: [
          { required: true, message: '请设置入库类型', trigger: 'blur' },
        ],
        description: [
          { required: true, message: '请输入审批意见', trigger: 'blur' },
        ],
        checkStatus: [
          { required: true, message: '请审批', trigger: 'blur' },
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
  },
  watch: {

  },
  created() {
    this.getshoplist()
    this.getgoodslist()
    this.getUserList()
    this.getvehicleList()
    if (this.rowData.transferCheckKey) {
      this.ruleForm.inputWarehouseKey = this.rowData.inputWarehouseKey
      this.ruleForm.shopCode = this.rowData.shopCode
      this.ruleForm.shopName = this.rowData.shopName
      this.ruleForm.goodsCode = this.rowData.goodsCode
      this.ruleForm.goodsName = this.rowData.goodsName
      this.ruleForm.supplierCode = this.rowData.supplierCode
      this.ruleForm.supplierName = this.rowData.supplierName
      this.ruleForm.outputPlan = this.rowData.inputPlan
      this.ruleForm.outputPrice = this.rowData.inputPrice
      this.ruleForm.outputActual = this.rowData.outputActual
      this.ruleForm.inventoryCode = this.rowData.inventoryCode
      this.ruleForm.positionCode = this.rowData.positionCode
      this.ruleForm.createTime = this.rowData.createTime
      this.ruleForm.deadlineTime = this.rowData.deadlineTime
      this.ruleForm.vehicleCode = this.rowData.vehicleCode
      // this.ruleForm.status = this.rowData.status
      // this.ruleForm.type = this.rowData.type
      // this.ruleForm.shopPeopleCode = this.rowData.shopPeopleCode
      // this.ruleForm.inventoryPeopleCode = this.rowData.inventoryPeopleCode
      this.ruleForm.isDeleted = this.rowData.isDeleted
      this.ruleForm.returnReason = this.rowData.returnReason
      this.ruleForm.outputShopCode = this.rowData.inputShopCode
      this.ruleForm.inputShopCode = this.rowData.inputShopCode
      this.ruleForm.outputShopName = this.rowData.inputShopName
      this.ruleForm.transferCheckKey = this.rowData.transferCheckKey
      this.ruleForm.checkNum = this.rowData.checkNum
      this.ruleForm.checkStatus = this.rowData.checkStatus
      this.ruleForm.description = this.rowData.description
      this.ruleForm.happenTime = this.rowData.happenTime
      this.ruleForm.checkTime = this.rowData.checkTime
      this.ruleForm.outputWarehouseKey = this.rowData.outputWarehouseKey
      this.ruleForm.returnNum = this.rowData.returnNum
      this.value2 = [this.rowData.createTime, this.rowData.deadlineTime]
    }
    if (this.ruleForm.outputWarehouseKey) {
      this.ifCreate = false
    } else {
      this.ifCreate = true
    }
    this.getInventoryByshopCode()
    console.log(this.ruleForm)
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
      ShopInventoryList().then(res => {
        if (res.data.code == 200) {
          this.shopOptions = res.data.data
          this.inputShopOptions = res.data.data
        } else {
          this.$message.error("获取失败!");
        }
      })
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
    getInventoryByshopCode() {
      getByshopCode({ shopCode: this.ruleForm.shopCode }).then(res => {
        if (res.data.code == 200) {
          this.inventoryOptions = res.data.data
        } else {
          this.$message.error("获取失败!");
        }
      });
    },
    setPosition() {
      let choosenItem = this.inventoryOptions.filter(item => {
        return item.inventoryCode == this.ruleForm.inventoryCode
      });
      positionList({ inventoryKey: choosenItem[0].inventoryKey }).then(res => {
        if (res.data.code == 200) {
          this.positionOptions = res.data.data
        } else {
          this.$message.error("获取失败!");
        }
      });
    },
    setShopName() {
      this.getInventoryByshopCode()
      this.ruleForm.inputShopName = this.$refs.selection.selectedLabel
    },
    setoutputShopName() {
      this.ruleForm.outputShopName = this.$refs.inputShopSelect.selectedLabel
    },
    setGoodsName() {
      this.ruleForm.goodsName = this.$refs.goodsSelect.selectedLabel
    },
    setTime() {
      this.ruleForm.createTime = this.value2[0]
      this.ruleForm.deadlineTime = this.value2[1]
    },
    close() {
      this.$parent.drawer = false
    },
    create(formName) {
      this.ruleForm.createTime = this.value2[0]
      this.ruleForm.deadlineTime = this.value2[1]
      if (this.ruleForm.checkStatus == 0) {
        this.$message.error("请选择同意或驳回!");
      } else {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if (this.ruleForm.checkStatus == 1) {
              this.ruleForm.outputActual = this.ruleForm.checkNum
              let data = {
                shopCode: this.ruleForm.outputShopCode,
                shopName: this.ruleForm.outputShopCode,
                goodsCode: this.ruleForm.goodsCode,
                goodsName: this.ruleForm.goodsName,
                isDeleted: this.ruleForm.isDeleted,
                supplierCode: this.ruleForm.supplierCode,
                supplierName: this.ruleForm.supplierName,
                outputPlan: this.ruleForm.outputPlan,
                outputPrice: this.ruleForm.outputPrice,
                outputActual: this.ruleForm.outputActual,
                inventoryCode: this.ruleForm.inventoryCode,
                positionCode: this.ruleForm.positionCode,
                createTime: this.ruleForm.createTime,
                deadlineTime: this.ruleForm.deadlineTime,
                vehicleCode: this.ruleForm.vehicleCode,
                status: 5,
                type: 2,
                shopPeopleCode: this.ruleForm.shopPeopleCode,
                inventoryPeopleCode: this.ruleForm.inventoryPeopleCode,
                returnNum: this.ruleForm.returnNum,
                returnReason: this.ruleForm.returnReason,
                outputShopCode: this.ruleForm.shopCode,
                outputShopName: this.ruleForm.shopName,
                outputWarehouseKey: this.ruleForm.outputWarehouseKey
              }
              outputWarehouseAdd(data).then(res => {
                if (res.data.code == 200) {
                  let data = {
                    transferCheckKey: this.ruleForm.transferCheckKey,
                    description: this.ruleForm.description,
                    goodsCode: this.ruleForm.goodsCode,
                    inputWarehouseKey: this.ruleForm.inputWarehouseKey,
                    outputWarehouseKey: res.data.data.outputWarehouseKey,
                    checkStatus: this.ruleForm.checkStatus,
                    happenTime: this.ruleForm.happenTime,
                    checkTime: moment().format("YYYY-MM-DD HH:mm:ss"),
                    checkNum: this.ruleForm.checkNum,
                    outputShopCode: this.ruleForm.outputShopCode,
                    inputShopCode: this.ruleForm.inputShopCode,
                  }
                  transferCheckUpdate(data).then(res => {
                    if (res.data.code == 200) {
                      this.$message.success("编辑成功!");
                      this.$parent.success()
                      this.$forceUpdate()
                    } else {
                      this.$message.error("编辑失败!");
                    }
                  })
                  let postData = {
                    inputActual: this.ruleForm.outputActual,
                    vehicleCode: this.ruleForm.vehicleCode,
                    status: 2,
                    inputWarehouseKey: this.ruleForm.inputWarehouseKey
                  }
                  inputWarehouseUpdate(postData).then(res => {
                    console.log(res.data.data)
                  })
                  let detailData = {
                    inputOutputKey: this.ruleForm.shopCode,
                    shopkeeperWarehouseKey: "",
                    type: 1,
                    // 交易类型(0采购入库、1采购退货出库、2零售出库、3零售退货入库、4客户订购出库、5客户订购退货入库、6调货入库、7调货出库)
                    transType: 7,
                    quantity: this.ruleForm.checkNum,
                    startNum: "",
                    finalNum: "",
                    atTime: moment().format("YYYY-MM-DD HH:mm:ss"),
                    status:this.rowData.status,
                    detailWarehouseKey: "",
                    shopCode: this.ruleForm.shopCode,
                    goodsCode: this.ruleForm.goodsCode,
                  }
                  detailWarehouseAdd(detailData).then(res => {
                    console.log(res.data.data)
                  })
                } else {
                  this.$message.error("编辑失败!");
                }
              })
            } else {//********************驳回****************
              let data = {
                transferCheckKey: this.ruleForm.transferCheckKey,
                description: this.ruleForm.description,
                goodsCode: this.ruleForm.goodsCode,
                inputWarehouseKey: this.ruleForm.inputWarehouseKey,
                outputWarehouseKey: this.ruleForm.outputWarehouseKey,
                checkStatus: this.ruleForm.checkStatus,
                happenTime: this.ruleForm.happenTime,
                checkTime: moment().format("YYYY-MM-DD HH:mm:ss"),
                checkNum: this.ruleForm.checkNum,
                outputShopCode: this.ruleForm.outputShopCode,
                inputShopCode: this.ruleForm.inputShopCode,
              }
              transferCheckUpdate(data).then(res => {
                if (res.data.code == 200) {
                  this.$message.success("编辑成功!");
                  this.$parent.success()
                  this.$forceUpdate()
                } else {
                  this.$message.error("编辑失败!");
                }
              })
            }
          } else {
            console.log('error submit!!');
            return false;
          }
        })
      }
    },
    save(formName) {
      this.ruleForm.createTime = this.value2[0]
      this.ruleForm.deadlineTime = this.value2[1]
      if (this.ruleForm.checkStatus == 0) {
        this.$message.error("请选择同意或驳回!");
      } else {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if (this.ruleForm.checkStatus == 1) {
              this.ruleForm.outputActual = this.ruleForm.checkNum
              let data = {
                shopCode: this.ruleForm.outputShopCode,
                shopName: this.ruleForm.outputShopCode,
                goodsCode: this.ruleForm.goodsCode,
                goodsName: this.ruleForm.goodsName,
                isDeleted: this.ruleForm.isDeleted,
                supplierCode: this.ruleForm.supplierCode,
                supplierName: this.ruleForm.supplierName,
                outputPlan: this.ruleForm.outputPlan,
                outputPrice: this.ruleForm.outputPrice,
                outputActual: this.ruleForm.outputActual,
                inventoryCode: this.ruleForm.inventoryCode,
                positionCode: this.ruleForm.positionCode,
                createTime: this.ruleForm.createTime,
                deadlineTime: this.ruleForm.deadlineTime,
                vehicleCode: this.ruleForm.vehicleCode,
                status: 5,
                type: 2,
                shopPeopleCode: this.ruleForm.shopPeopleCode,
                inventoryPeopleCode: this.ruleForm.inventoryPeopleCode,
                returnNum: this.ruleForm.returnNum,
                returnReason: this.ruleForm.returnReason,
                outputShopCode: this.ruleForm.shopCode,
                outputShopName: this.ruleForm.shopName,
                outputWarehouseKey: this.ruleForm.outputWarehouseKey
              }
              outputWarehouseUpdate(data).then(res => {
                if (res.data.code == 200) {
                  let data = {
                    transferCheckKey: this.ruleForm.transferCheckKey,
                    description: this.ruleForm.description,
                    goodsCode: this.ruleForm.goodsCode,
                    inputWarehouseKey: this.ruleForm.inputWarehouseKey,
                    outputWarehouseKey: this.ruleForm.outputWarehouseKey,
                    checkStatus: this.ruleForm.checkStatus,
                    happenTime: this.ruleForm.happenTime,
                    checkTime: moment().format("YYYY-MM-DD HH:mm:ss"),
                    checkNum: this.ruleForm.checkNum,
                    outputShopCode: this.ruleForm.outputShopCode,
                    inputShopCode: this.ruleForm.inputShopCode,
                  }
                  transferCheckUpdate(data).then(res => {
                    if (res.data.code == 200) {
                      this.$message.success("编辑成功!");
                      this.$parent.success()
                      this.$forceUpdate()
                    } else {
                      this.$message.error("编辑失败!");
                    }
                  })
                  let postData = {
                    inputActual: this.ruleForm.outputActual,
                    vehicleCode: this.ruleForm.vehicleCode,
                    status: 2,
                    inputWarehouseKey: this.ruleForm.inputWarehouseKey
                  }
                  inputWarehouseUpdate(postData).then(res => {
                    console.log(res.data.data)
                  });
                  let detailData = {
                    inputOutputKey: this.ruleForm.shopCode,
                    shopkeeperWarehouseKey: "",
                    type: 1,
                    // 交易类型(0采购入库、1采购退货出库、2零售出库、3零售退货入库、4客户订购出库、5客户订购退货入库、6调货入库、7调货出库)
                    transType: 7,
                    quantity: this.ruleForm.checkNum,
                    startNum: "",
                    finalNum: "",
                    atTime: moment().format("YYYY-MM-DD HH:mm:ss"),
                    status:this.rowData.status,
                    detailWarehouseKey: "",
                    shopCode: this.ruleForm.shopCode,
                    goodsCode: this.ruleForm.goodsCode,
                  }
                  detailWarehouseAdd(detailData).then(res => {
                    console.log(res.data.data)
                  })
                } else {
                  this.$message.error("编辑失败!");
                }
              })
            } else {//********************驳回****************
              let data = {
                transferCheckKey: this.ruleForm.transferCheckKey,
                description: this.ruleForm.description,
                goodsCode: this.ruleForm.goodsCode,
                inputWarehouseKey: this.ruleForm.inputWarehouseKey,
                outputWarehouseKey: this.ruleForm.outputWarehouseKey,
                checkStatus: this.ruleForm.checkStatus,
                happenTime: this.ruleForm.happenTime,
                checkTime: moment().format("YYYY-MM-DD HH:mm:ss"),
                checkNum: this.ruleForm.checkNum,
                outputShopCode: this.ruleForm.outputShopCode,
                inputShopCode: this.ruleForm.inputShopCode,
              }
              transferCheckUpdate(data).then(res => {
                if (res.data.code == 200) {
                  this.$message.success("编辑成功!");
                  this.$parent.success()
                  this.$forceUpdate()
                } else {
                  this.$message.error("编辑失败!");
                }
              })
            }
          } else {
            console.log('error submit!!');
            return false;
          }
        })
      }
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