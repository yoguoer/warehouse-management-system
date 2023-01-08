<template>
  <el-dialog size="30%" :title="ifCreate ? '新增' : '编辑'" :visible.sync="drawer" :direction="direction"
    :close-on-press-escape="false" :show-close="false" :wrapperClosable="false" :append-to-body='true' width="1200px">

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
              ref="supplierSelect">
              <el-option @click.native="setSupplierName" v-for="item in supplierOptions" :key="item.supplierKey"
                :label="item.supplierName" :value="item.supplierCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
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
          <el-form-item label="计划入库数" prop="inputPlan">
            <el-input v-model="ruleForm.inputPlan" clearable placeholder="计划入库数"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10">
          <el-form-item label="入库价格" prop="inputPrice">
            <el-input v-model="ruleForm.inputPrice" clearable placeholder="入库价格"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="实际数" prop="inputActual">
            <el-input v-model="ruleForm.inputActual" clearable placeholder="实际数"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10">
          <el-form-item label="入库类型" prop="type">
            <el-select size="small" v-model="ruleForm.type" placeholder="入库类型" clearable>
              <el-option label="采购入库" :value="0"></el-option>
              <el-option label="退货入库" :value="1"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="状态" prop="status">
            <el-select size="middle" v-model="ruleForm.status" placeholder="状态" style="width:100%;" clearable>
              <el-option v-for="item in statusOptions" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
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
              <el-option v-for="item in vehicleOptions" :key="item.vehicleKey" :label="item.vehicleCode"
                :value="item.vehicleCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <span style="margin-left: 8%;">起止日期</span>
          <el-date-picker style="width:310px;margin-left: 10px;" v-model="value2" type="daterange" align="right"
            size="large" unlink-panels range-separator="至" start-placeholder="预计日期" end-placeholder="最迟日期"
            :picker-options="pickerOptions" @change="setTime" value-format="yyyy-MM-dd HH:mm:ss">
          </el-date-picker>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10">
          <el-form-item label="退货原因" prop="returnReason" v-if="ruleForm.type == 2">
            <el-input v-model="ruleForm.returnReason" clearable placeholder="退货原因" type="textarea"></el-input>
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
        isDeleted:"",
        returnReason: ""
      },
      statusOptions: [
        { label: "在单", value: 0 },
        { label: "生产", value: 1 },
        { label: "在途", value: 2 },
        { label: "入库", value: 3 },
        { label: "占用", value: 4 },
        { label: "出库", value: 5 }],
      shopOptions: [],
      goodsOptions: [],
      positionOptions: [],
      supplierOptions: [],
      inventoryOptions: [],
      userOptions: [],
      userOptions1: [],
      vehicleOptions: [],
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
            picker.$emit('pick', [start, end]);
          }
        }]
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
    this.getSupplierlist()
    this.getUserList()
    this.getvehicleList()
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
      this.ruleForm.isDeleted=this.rowData.isDeleted
      this.ruleForm.createTime = this.rowData.createTime
      this.ruleForm.deadlineTime = this.rowData.deadlineTime
      this.ruleForm.vehicleCode = this.rowData.vehicleCode
      this.ruleForm.status = this.rowData.status
      this.ruleForm.type = this.rowData.type
      this.ruleForm.shopPeopleCode = this.rowData.shopPeopleCode
      this.ruleForm.inventoryPeopleCode = this.rowData.inventoryPeopleCode
      this.ruleForm.returnReason = this.rowData.returnReason
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
            isDeleted:this.ruleForm.isDeleted,
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
            inventoryCode: this.ruleForm.inventoryCode,
            positionCode: this.ruleForm.positionCode,
            createTime: this.ruleForm.createTime,
            deadlineTime: this.ruleForm.deadlineTime,
            vehicleCode: this.ruleForm.vehicleCode,
            status: this.ruleForm.status,
            isDeleted:this.ruleForm.isDeleted,
            type: this.ruleForm.type,
            shopPeopleCode: this.ruleForm.shopPeopleCode,
            inventoryPeopleCode: this.ruleForm.inventoryPeopleCode,
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