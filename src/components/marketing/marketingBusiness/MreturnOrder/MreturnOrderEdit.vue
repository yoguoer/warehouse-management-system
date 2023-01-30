<template>
  <el-dialog size="30%" :title="ifCreate ? '新增' : '审批退货单'" :visible.sync="drawer" :direction="direction"
    :close-on-click-modal="false" :close-on-press-escape="false" :show-close="false" :wrapperClosable="false"
    :append-to-body='true' width="1200px">

    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="150px" class="demo-ruleForm">
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
          <el-form-item label="客户" prop="customerCode">
            <el-select size="middle" v-model="ruleForm.customerCode" placeholder="客户" style="width:100%;" clearable
              disabled ref="supplierSelect">
              <el-option @click.native="setSupplierName" v-for="item in customerOptions" :key="item.supplierKey"
                :label="item.customerName" :value="item.customerCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="仓库" prop="inventoryCode">
            <el-select size="middle" v-model="ruleForm.inventoryCode" placeholder="仓库" style="width:100%;" clearable
              disabled ref="inventorySelect">
              <el-option @click.native="setPosition" v-for="item in inventoryOptions" :key="item.inventoryKey"
                :label="item.inventoryName" :value="item.inventoryCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10">
          <el-form-item label="库位" prop="positionCode">
            <el-select size="middle" v-model="ruleForm.positionCode" placeholder="库位" style="width:100%;" clearable
              disabled>
              <el-option v-for="item in positionOptions" :key="item.positionCode" :label="item.positionCode"
                :value="item.positionCode" clearable placeholder="库位">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="计划数" prop="outputPlan">
            <el-input v-model="ruleForm.outputPlan" clearable placeholder="计划数" disabled type="Number"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10">
          <el-form-item label="出库价格" prop="outputPrice">
            <el-input v-model="ruleForm.outputPrice" clearable placeholder="出库价格" disabled type="Number"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="实际数" prop="outputActual">
            <el-input v-model="ruleForm.outputActual" clearable placeholder="实际数" disabled type="Number"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10">
          <el-form-item label="出库类型" prop="type">
            <el-select size="small" v-model="ruleForm.type" placeholder="出库类型" clearable disabled>
              <el-option label="零售出库" :value="0"></el-option>
              <el-option label="客户订购" :value="1"></el-option>
              <el-option label="调货出库" :value="2"></el-option>
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
          <el-form-item label="门店操作员" prop="shopPeopleCode">
            <el-select size="middle" v-model="ruleForm.shopPeopleCode" placeholder="门店操作员" style="width:100%;" clearable
              disabled>
              <el-option v-for="item in userOptions" :key="item.userId" :label="item.userName" :value="item.userCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="仓库操作员" prop="inventoryPeopleCode">
            <el-select size="middle" v-model="ruleForm.inventoryPeopleCode" placeholder="仓库操作员" style="width:100%;"
              disabled clearable>
              <el-option v-for="item in userOptions1" :key="item.userId" :label="item.userName" :value="item.userCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10">
          <el-form-item label="申请退货数" prop="checkNum">
            <el-input v-model="ruleForm.checkNum" clearable placeholder="申请退货数" disabled type="Number"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="退货原因" prop="returnReason">
            <el-input v-model="ruleForm.returnReason" clearable placeholder="退货原因" type="textarea" disabled></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10">
          <el-form-item label="车辆" prop="vehicleCode">
            <el-select size="middle" v-model="ruleForm.vehicleCode" placeholder="车辆" style="width:100%;" clearable>
              <el-option v-for="item in vehicleOptions" :key="item.vehicleKey" :label="item.vehicleCode"
                :value="item.vehicleCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="10" v-if="ifShow">
          <el-form-item label="审批结果" prop="checkStatus">
            <el-select size="small" v-model="ruleForm.checkStatus" placeholder="审批结果" clearable>
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
      </el-row>
      <el-row>
        <el-col :span="10" v-if="ifShow">
          <el-form-item label="审批意见" prop="description">
            <el-input v-model="ruleForm.description" clearable placeholder="审批意见" type="textarea"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="10" v-if="ruleForm.checkStatus == 1">
          <el-form-item label="是否有残次品" prop="isDefects">
            <el-select size="small" v-model="ruleForm.isDefects" placeholder="是否有残次品" clearable>
              <el-option label="否" :value="0">
              </el-option>
              <el-option label="是" :value="1">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10" v-if="ruleForm.isDefects == 1">
          <el-form-item label="残次品数" prop="defectsNum">
            <el-input v-model="ruleForm.defectsNum" clearable placeholder="残次品数"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div class="dialog_footer">
      <el-button type="primary" @click="save('ruleForm')" v-if="ifCreate == false">提交</el-button>
      <el-button type="primary" @click="create('ruleForm')" v-else>立即创建</el-button>
      <el-button @click="close()">取消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { returnCheckUpdate } from '@/api/check'
import { shoplist, goodslist, CustomerList, positionList, vehicleList } from '@/api/data'
import { getByshopCode } from '@/api/warehouse'
import { UserList } from '@/api/api'
import { detailWarehouseUpdate, detailWarehouseAdd } from '@/api/warehouse'
import moment from 'moment'

export default {
  name: 'guestEdit',
  data() {
    return {
      direction: 'btt',
      ifCreate: false,
      ruleForm: {
        returnCheckKey: "",
        outputWarehouseKey: "",
        shopCode: "",
        shopName: "",
        goodsCode: "",
        goodsName: "",
        customerCode: "",
        customerName: "",
        outputPlan: "",
        outputPrice: "",
        outputActual: "",
        inventoryCode: "",
        positionCode: "",
        createTime: "",
        deadlineTime: "",
        vehicleCode: "",
        status: "",
        type: "",
        checkNum: "",
        checkStatus: "",
        description: "",
        shopPeopleCode: "",
        inventoryPeopleCode: "",
        isDeleted: "",
        returnReason: "",
        returnNum: "",
        checkType: "",
        inputOutputKey: "",
        outputShopCode: "",
        outputShopName: "",
        isDefects: "",
        defectsNum: ""
      },
      shopOptions: [],
      goodsOptions: [],
      positionOptions: [],
      customerOptions: [],
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
        description: [
          { required: true, message: '请输入审批意见', trigger: 'blur' },
        ],
        checkStatus: [
          { required: true, message: '请审批', trigger: 'blur' },
        ],
        isDefects: [
          { required: true, message: '是否有残次品', trigger: 'blur' },
        ],
        defectsNum: [
          { required: true, message: '残次品数', trigger: 'blur' },
        ],
      }
    }
  },
  props: {
    drawer: {
      default: true,
    },
    rowData: {},
    ifShow: {
      default: true,
    },
  },
  watch: {

  },
  created() {
    this.getshoplist()
    this.getgoodslist()
    this.getCustomerList()
    this.getUserList()
    this.getvehicleList()
    if (this.rowData.returnCheckKey) {
      this.ruleForm.inputOutputKey = this.rowData.inputOutputKey
      this.ruleForm.outputWarehouseKey = this.rowData.outputWarehouseKey
      this.ruleForm.returnCheckKey = this.rowData.returnCheckKey
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
      this.ruleForm.status = this.rowData.status
      this.ruleForm.type = this.rowData.type
      this.ruleForm.shopPeopleCode = this.rowData.shopPeopleCode
      this.ruleForm.isDeleted = this.rowData.isDeleted
      this.ruleForm.inventoryPeopleCode = this.rowData.inventoryPeopleCode
      this.ruleForm.returnReason = this.rowData.returnReason
      this.ruleForm.checkNum = this.rowData.checkNum
      this.ruleForm.checkStatus = this.rowData.checkStatus
      this.ruleForm.description = this.rowData.description
      this.ruleForm.returnNum = this.rowData.returnNum
      this.ruleForm.checkType = this.rowData.checkType
      this.ruleForm.outputShopCode = this.rowData.outputShopCode
      this.ruleForm.outputShopName = this.rowData.outputShopName
      this.value2 = [this.rowData.createTime, this.rowData.deadlineTime]
    } else {
      this.ifCreate = true
    }
    this.getShopInventoryList()
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
    setShopName() {
      this.getShopInventoryList()
      this.ruleForm.shopName = this.$refs.selection.selectedLabel
    },
    setCustomerName() {
      this.ruleForm.customerName = this.$refs.customerSelect.selectedLabel
    },
    setGoodsName() {
      console.log(this.$refs.goodsSelect.selectedLabel)
      this.ruleForm.goodsName = this.$refs.goodsSelect.selectedLabel
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
      if (this.ruleForm.checkStatus == 0) {
        this.$message.error("请选择同意或驳回!");
      } else {
        this.ruleForm.createTime = this.value2[0]
        this.ruleForm.deadlineTime = this.value2[1]
        this.$refs[formName].validate((valid) => {
          if (this.ruleForm.checkStatus == 1) {
            this.ruleForm.returnNum = this.ruleForm.checkNum
            this.ruleForm.outputActual = this.ruleForm.outputActual - this.ruleForm.checkNum
          }
          if (valid) {
            let data = {
              returnCheckKey: this.ruleForm.returnCheckKey,
              description: this.ruleForm.description,
              checkType: this.ruleForm.checkType,
              inputOutputKey: this.ruleForm.outputWarehouseKey,
              checkStatus: this.ruleForm.checkStatus,
              happenTime: this.ruleForm.happenTime,
              checkNum: this.ruleForm.checkNum,
              checkTime: moment().format("YYYY-MM-DD HH:mm:ss"),
              outputWarehouse: {
                outputWarehouseKey: this.ruleForm.outputWarehouseKey,
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
                isDeleted: this.ruleForm.isDeleted,
                vehicleCode: this.ruleForm.vehicleCode,
                status: this.ruleForm.status,
                type: this.ruleForm.type,
                shopPeopleCode: this.ruleForm.shopPeopleCode,
                inventoryPeopleCode: this.ruleForm.inventoryPeopleCode,
                returnReason: this.ruleForm.returnReason,
                returnNum: this.ruleForm.returnNum,
                outputShopCode: this.ruleForm.outputShopCode,
                outputShopName: this.ruleForm.outputShopName,
              }
            }
            returnCheckUpdate(data).then(res => {
              if (res.data.code == 200) {
                if (this.ruleForm.checkStatus == 1) {
                  let data = {
                    inputOutputKey: this.ruleForm.outputWarehouseKey,
                    shopkeeperWarehouseKey: "",
                    type: 0,
                    // 交易类型(0采购入库、1采购退货出库、2零售出库、3零售退货入库、4客户订购出库、5客户订购退货入库、6调货入库、7调货出库)
                    transType: this.ruleForm.type == 0 ? 3 : 5,
                    quantity: this.ruleForm.checkNum,
                    startNum: "",
                    finalNum: "",
                    atTime: moment().format("YYYY-MM-DD HH:mm:ss"),
                    status: this.rowData.status,
                    detailWarehouseKey: "",
                    shopCode: this.ruleForm.shopCode,
                    goodsCode: this.ruleForm.goodsCode,
                    status: this.rowData.status,
                    isDefects: this.ruleForm.isDefects,
                    defectsNum: this.ruleForm.defectsNum,
                  }
                  detailWarehouseAdd(data).then(res => {
                    if (res.data.code == 200) {
                      this.$message.success("编辑成功!");
                      this.$parent.success()
                      this.$forceUpdate()
                    } else {
                      this.$message.error("编辑失败!");
                    }
                  });
                } else {
                  this.$message.success("新增成功!");
                  this.$parent.success()
                  this.$forceUpdate()
                }
              } else {
                this.$message.error("编辑失败!");
              }
            })
          } else {
            console.log('error submit!!');
            return false;
          }
        })
      }
    },
    create(formName) {
      if (this.ruleForm.checkStatus == 0) {
        this.$message.error("请选择同意或驳回!");
      } else {
        this.ruleForm.createTime = this.value2[0]
        this.ruleForm.deadlineTime = this.value2[1]
        this.$refs[formName].validate((valid) => {
          if (this.ruleForm.checkStatus == 1) {
            this.ruleForm.returnNum = this.ruleForm.checkNum
            this.ruleForm.outputActual = this.ruleForm.outputActual - this.ruleForm.checkNum
          }
          if (valid) {
            let data = {
              returnCheckKey: this.ruleForm.returnCheckKey,
              description: this.ruleForm.description,
              checkType: this.ruleForm.checkType,
              inputOutputKey: this.ruleForm.outputWarehouseKey,
              checkStatus: this.ruleForm.checkStatus,
              happenTime: this.ruleForm.happenTime,
              checkNum: this.ruleForm.checkNum,
              checkTime: moment().format("YYYY-MM-DD HH:mm:ss"),
              outputWarehouse: {
                outputWarehouseKey: this.ruleForm.outputWarehouseKey,
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
                isDeleted: this.ruleForm.isDeleted,
                vehicleCode: this.ruleForm.vehicleCode,
                status: this.ruleForm.status,
                type: this.ruleForm.type,
                shopPeopleCode: this.ruleForm.shopPeopleCode,
                inventoryPeopleCode: this.ruleForm.inventoryPeopleCode,
                returnReason: this.ruleForm.returnReason,
                returnNum: this.ruleForm.returnNum,
                outputShopCode: this.ruleForm.outputShopCode,
                outputShopName: this.ruleForm.outputShopName,
              }
            }
            returnCheckAdd(data).then(res => {
              if (res.data.code == 200) {
                if (this.ruleForm.checkStatus == 1) {
                  let data = {
                    inputOutputKey: this.ruleForm.outputWarehouseKey,
                    shopkeeperWarehouseKey: "",
                    type: 0,
                    // 交易类型(0采购入库、1采购退货出库、2零售出库、3零售退货入库、4客户订购出库、5客户订购退货入库、6调货入库、7调货出库)
                    transType: this.ruleForm.type == 0 ? 3 : 5,
                    quantity: this.ruleForm.checkNum,
                    startNum: "",
                    finalNum: "",
                    atTime: moment().format("YYYY-MM-DD HH:mm:ss"),
                    status: this.rowData.status,
                    detailWarehouseKey: "",
                    shopCode: this.ruleForm.shopCode,
                    goodsCode: this.ruleForm.goodsCode,
                    status: this.rowData.status
                  }
                  detailWarehouseAdd(data).then(res => {
                    if (res.data.code == 200) {
                      this.$message.success("编辑成功!");
                      this.$parent.success()
                      this.$forceUpdate()
                    } else {
                      this.$message.error("编辑失败!");
                    }
                  });
                } else {
                  this.$message.success("新增成功!");
                  this.$parent.success()
                  this.$forceUpdate()
                }
              } else {
                this.$message.error("编辑失败!");
              }
            })
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