<template>
  <el-dialog size="30%" :title="ifCreate ? '新增采购单' : '编辑采购单'" :visible.sync="drawer" :direction="direction"
    :close-on-click-modal="false" :close-on-press-escape="false" :show-close="false" :wrapperClosable="false"
    :append-to-body='true' width="1200px">

    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-row>
        <el-col :span="10">
          <el-form-item label="门店" prop="shopCode">
            <el-select size="middle" v-model="ruleForm.shopCode" placeholder="门店" style="width:100%;" clearable
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
              ref="goodsSelect">
              <el-option @click.native="setGoodsName(item)" v-for="item in goodsOptions" :key="item.goodsCode"
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
              ref="supplierSelect">
              <el-option @click.native="setSupplierName" v-for="item in supplierOptions" :key="item.supplierKey"
                :label="item.supplierName" :value="item.supplierCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="入库类型" prop="type">
            <el-select size="small" v-model="ruleForm.type" placeholder="入库类型" clearable disabled>
              <el-option label="采购入库" :value="0"></el-option>
              <el-option label="调货入库" :value="1"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10">
          <el-form-item label="计划入库数" prop="inputPlan">
            <el-input v-model="ruleForm.inputPlan" clearable placeholder="计划入库数" :min="0" type="Number"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="入库价格" prop="inputPrice">
            <el-input v-model="ruleForm.inputPrice" clearable placeholder="入库价格" :min="0" type="Number"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <!-- 还没完成呢！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！ -->
        <el-col :span="10">
          <el-form-item label="门店操作员" prop="shopPeopleCode">
            <!-- <el-input v-model="ruleForm.shopPeopleCode" clearable placeholder="门店操作员"></el-input> -->
            <el-select size="middle" v-model="ruleForm.shopPeopleCode" placeholder="门店操作员" style="width:100%;"
              clearable>
              <el-option v-for="item in userOptions" :key="item.userId" :label="item.userName" :value="item.userCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <span style="margin-left: 8%;">起止日期</span>
          <el-date-picker style="width:310px;margin-left: 10px;" v-model="value2" type="daterange" align="right"
            size="large" unlink-panels range-separator="至" start-placeholder="预计日期" end-placeholder="最迟日期"
            :picker-options="pickerOptions" @click.native="setTime" value-format="yyyy-MM-dd HH:mm:ss">
          </el-date-picker>
        </el-col>
      </el-row>
      <el-row>
        <!-- <el-col :span="10">
          <el-form-item label="退货原因" prop="returnReason" v-if="ruleForm.type == 1">
            <el-input v-model="ruleForm.returnReason" clearable placeholder="退货原因" type="textarea"></el-input>
          </el-form-item>
        </el-col> -->
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
import { goodslist, SupplierdataById } from '@/api/data'
import { UserList } from '@/api/api'
import { ShopInventoryList } from "@/api/warehouse";

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
        type: 0,
        shopPeopleCode: "",
        inventoryPeopleCode: "",
        isDeleted: "",
        returnReason: "",
        inputShopCode: "",
        inputShopName: "",
      },
      shopOptions: [],
      goodsOptions: [],
      positionOptions: [],
      supplierOptions: [],
      inventoryOptions: [],
      userOptions: [],
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
        }],
        disabledDate: (time) => {
          return new Date(time).getTime() < new Date().getTime() - 1 * 24 * 3600 * 1000
            || new Date(time).getTime() > new Date().getTime() + 30 * 24 * 3600 * 1000 * 6;
        }
      },
      value2: '',
      rules: {
        shopCode: [
          { required: true, message: '请选择门店', trigger: 'blur' },
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
        inputPlan: [
          { required: true, message: '请设置计划入库数', trigger: 'blur' },
        ],
        inputPrice: [
          { required: true, message: '请设置入库价格', trigger: 'blur' },
        ],
        type: [
          { required: true, message: '请设置入库类型', trigger: 'blur' },
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
      this.ruleForm.shopPeopleCode = this.rowData.shopPeopleCode
      this.ruleForm.inventoryPeopleCode = this.rowData.inventoryPeopleCode
      this.ruleForm.isDeleted = this.rowData.isDeleted
      this.ruleForm.returnReason = this.rowData.returnReason
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
    },
    unique(arr) {
      const res = new Map();
      return arr.filter((arr) => !res.has(arr.shopKey) && res.set(arr.shopKey, 1));
    },
    getshoplist() {
      ShopInventoryList().then(res => {
        if (res.data.code == 200) {
          // this.shopOptions = res.data.data
          this.shopOptions = this.unique(res.data.data)
        } else {
          this.$message.error("获取失败!");
        }
      })
    },
    getgoodslist() {
      goodslist().then(res => {
        if (res.data.code == 200) {
          // this.goodsOptions = res.data.data
          this.goodsOptions = []
          res.data.data.forEach(item => {
            if (item.state == 1) {
              this.goodsOptions.push(item)
            }
          })
        } else {
          this.$message.error("获取失败!");
        }
      });
    },
    getSupplierlist(supplierKey) {
      SupplierdataById({ supplierKey: supplierKey }).then(res => {
        this.supplierOptions = []
        if (res.data.code == 200) {
          this.supplierOptions.push(res.data.data)
        } else {
          this.$message.error("获取失败!");
        }
      });
    },
    setShopName() {
      this.ruleForm.shopName = this.$refs.selection.selectedLabel
    },
    setSupplierName() {
      this.ruleForm.supplierName = this.$refs.supplierSelect.selectedLabel
    },
    setGoodsName(item) {
      // console.log("goods",item)
      this.ruleForm.goodsName = this.$refs.goodsSelect.selectedLabel
      this.ruleForm.inputPrice = item.priceLatestPurchase
      this.getSupplierlist(item.supplierKey)
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
          let data = {
            shopCode: this.ruleForm.shopCode,
            shopName: this.ruleForm.shopName,
            goodsCode: this.ruleForm.goodsCode,
            goodsName: this.ruleForm.goodsName,
            isDeleted: this.ruleForm.isDeleted,
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
            status: 0,
            type: this.ruleForm.type,
            shopPeopleCode: this.ruleForm.shopPeopleCode,
            inventoryPeopleCode: this.ruleForm.inventoryPeopleCode,
            returnReason: this.ruleForm.returnReason,
            inputShopCode: this.ruleForm.inputShopCode,
            inputShopName: this.ruleForm.inputShopName,
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
            isDeleted: this.ruleForm.isDeleted,
            inventoryCode: this.ruleForm.inventoryCode,
            positionCode: this.ruleForm.positionCode,
            createTime: this.ruleForm.createTime,
            deadlineTime: this.ruleForm.deadlineTime,
            vehicleCode: this.ruleForm.vehicleCode,
            status: 0,
            type: this.ruleForm.type,
            shopPeopleCode: this.ruleForm.shopPeopleCode,
            inventoryPeopleCode: this.ruleForm.inventoryPeopleCode,
            inputShopCode: this.ruleForm.inputShopCode,
            inputShopName: this.ruleForm.inputShopName,
            returnReason: this.ruleForm.returnReason
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