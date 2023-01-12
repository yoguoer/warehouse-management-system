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
          <el-form-item label="计划数" prop="inputPlan">
            <el-input v-model="ruleForm.inputPlan" clearable placeholder="计划数" disabled></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10">
          <el-form-item label="入库价格" prop="inputPrice">
            <el-input v-model="ruleForm.inputPrice" clearable placeholder="入库价格" disabled></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="实际数" prop="inputActual">
            <el-input v-model="ruleForm.inputActual" clearable placeholder="实际数" disabled></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10">
          <el-form-item label="申请退货数" prop="checkNum">
            <el-input v-model="ruleForm.checkNum" clearable placeholder="申请退货数" disabled></el-input>
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
          <el-form-item label="审批" prop="checkStatus">
            <el-select size="small" v-model="ruleForm.checkStatus" placeholder="审批" clearable>
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
            <el-input v-model="ruleForm.description" clearable placeholder="审批意见" type="textarea"></el-input>
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
import { returnCheckUpdate, returnCheckAdd } from '@/api/check'
import { shoplist, goodslist, Supplierlist, positionList } from '@/api/data'
import { ShopInventoryList } from '@/api/warehouse'
import { UserList } from '@/api/api'
import moment from 'moment'

export default {
  name: 'guestEdit',
  data() {
    return {
      direction: 'btt',
      ifCreate: false,
      ruleForm: {
        returnCheckKey: "",
        inputWarehouseKey: "",
        inputOutputKey: "",
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
        isDeleted: "",
        returnReason: "",
        description: "",
        checkType: "",
        checkStatus: "",
        returnNum: "",
        checkNum: "",
        inputShopCode: "",
        inputShopName: "",
      },
      shopOptions: [],
      goodsOptions: [],
      positionOptions: [],
      supplierOptions: [],
      inventoryOptions: [],
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
    this.getUserList()
    if (this.rowData.returnCheckKey) {
      this.ruleForm.inputWarehouseKey = this.rowData.inputWarehouseKey
      this.ruleForm.returnCheckKey = this.rowData.returnCheckKey
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
      this.ruleForm.isDeleted = this.rowData.isDeleted
      this.ruleForm.inventoryPeopleCode = this.rowData.inventoryPeopleCode
      this.ruleForm.returnReason = this.rowData.returnReason
      this.ruleForm.returnNum = this.rowData.returnNum
      this.ruleForm.description = this.rowData.description
      this.ruleForm.checkType = this.rowData.checkType
      this.ruleForm.checkNum = this.rowData.checkNum
      this.ruleForm.inputOutputKey = this.rowData.inputOutputKey
      this.ruleForm.checkStatus = this.rowData.checkStatus
      this.ruleForm.inputShopCode = this.rowData.inputShopCode
      this.ruleForm.inputShopName = this.rowData.inputShopName
      this.value2 = [this.rowData.createTime, this.rowData.deadlineTime]
    } else {
      this.ifCreate = true
    }
  },
  methods: {
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
    setShopName() {
      // console.log(this.$refs.selection.selectedLabel)
      this.getShopInventoryList(this.ruleForm.shopCode)
      this.ruleForm.shopName = this.$refs.selection.selectedLabel
    },
    setSupplierName() {
      // console.log(this.$refs.supplierSelect.selectedLabel)
      this.ruleForm.supplierName = this.$refs.supplierSelect.selectedLabel
    },
    setGoodsName() {
      // console.log(this.$refs.goodsSelect.selectedLabel)
      this.ruleForm.goodsName = this.$refs.goodsSelect.selectedLabel
    },
    setPosition() {
      // console.log(this.$refs.inventorySelect.selectedLabel)
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
    },
    save(formName) {
      this.ruleForm.createTime = this.value2[0]
      this.ruleForm.deadlineTime = this.value2[1]
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.ruleForm.checkStatus == 0) {
            this.$message.error("请选择同意或驳回!");
          } else {
            if (this.ruleForm.checkStatus == 1) {
              this.ruleForm.returnNum = this.ruleForm.checkNum
              this.ruleForm.inputActual = this.ruleForm.inputActual - this.ruleForm.checkNum
            }
            let data = {
              returnCheckKey: this.ruleForm.returnCheckKey,
              description: this.ruleForm.description,
              checkType: this.ruleForm.checkType,
              inputOutputKey: this.ruleForm.inputOutputKey,
              checkStatus: this.ruleForm.checkStatus,
              happenTime: this.ruleForm.happenTime,
              checkNum: this.ruleForm.checkNum,
              checkTime: moment().format("YYYY-MM-DD HH:mm:ss"),
              inputWarehouse: {
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
                isDeleted: this.ruleForm.isDeleted,
                vehicleCode: this.ruleForm.vehicleCode,
                status: this.ruleForm.status,
                type: this.ruleForm.type,
                shopPeopleCode: this.ruleForm.shopPeopleCode,
                inventoryPeopleCode: this.ruleForm.inventoryPeopleCode,
                returnReason: this.ruleForm.returnReason,
                returnNum: this.ruleForm.returnNum,
                inputWarehouseKey: this.ruleForm.inputWarehouseKey,
                inputShopCode: this.ruleForm.inputShopCode,
                inputShopName: this.ruleForm.inputShopName,
              }
            }
            returnCheckUpdate(data).then(res => {
              if (res.data.code == 200) {
                if (res.data.code == 200) {
                  this.$message.success("审批成功!");
                  this.$parent.success()
                  this.$forceUpdate()
                } else {
                  this.$message.error("审批成功!");
                }
              }
            });
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      })
    },
    create(formName) {
      this.ruleForm.createTime = this.value2[0]
      this.ruleForm.deadlineTime = this.value2[1]
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.ruleForm.checkStatus == 0) {
            this.$message.error("请选择同意或驳回!");
          } else {
            if (this.ruleForm.checkStatus == 1) {
              this.ruleForm.returnNum = this.ruleForm.checkNum
              this.ruleForm.inputActual = this.ruleForm.inputActual - this.ruleForm.checkNum
            }
            let data = {
              returnCheckKey: this.ruleForm.returnCheckKey,
              description: this.ruleForm.description,
              checkType: this.ruleForm.checkType,
              inputOutputKey: this.ruleForm.inputOutputKey,
              checkStatus: this.ruleForm.checkStatus,
              happenTime: this.ruleForm.happenTime,
              checkNum: this.ruleForm.checkNum,
              checkTime: moment().format("YYYY-MM-DD HH:mm:ss"),
              inputWarehouse: {
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
                isDeleted: this.ruleForm.isDeleted,
                vehicleCode: this.ruleForm.vehicleCode,
                status: this.ruleForm.status,
                type: this.ruleForm.type,
                shopPeopleCode: this.ruleForm.shopPeopleCode,
                inventoryPeopleCode: this.ruleForm.inventoryPeopleCode,
                returnReason: this.ruleForm.returnReason,
                returnNum: this.ruleForm.returnNum,
                inputWarehouseKey: this.ruleForm.inputWarehouseKey,
                inputShopCode: this.ruleForm.inputShopCode,
                inputShopName: this.ruleForm.inputShopName,
              }
            }
            returnCheckUpdate(data).then(res => {
              if (res.data.code == 200) {
                if (res.data.code == 200) {
                  this.$message.success("审批成功!");
                  this.$parent.success()
                  this.$forceUpdate()
                } else {
                  this.$message.error("审批成功!");
                }
              }
            });
          }
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