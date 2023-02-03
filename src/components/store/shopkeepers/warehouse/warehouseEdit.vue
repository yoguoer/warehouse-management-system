<template>
  <el-dialog size="30%" :title="ifCreate ? '新增' : '编辑'" :visible.sync="drawer" :direction="direction" :close-on-click-modal="false" 
    :close-on-press-escape="false" :show-close="false" :wrapperClosable="false" :append-to-body='true' width="1200px">

    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-row>
        <el-col :span="10">
          <el-form-item label="订单类型" prop="tempType">
            <el-select size="mini" v-model="ruleForm.tempType" placeholder="订单类型" style="width:200px;margin-right:20px;"
              clearable @change="getOptions()">
              <el-option label="采购订单" value="input_warehouse"></el-option>
              <el-option label="销售订单" value="output_warehouse"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10">
          <el-form-item label="变化类型" prop="type">
            <el-select size="middle" v-model="ruleForm.type" placeholder="变化类型" style="width:100%;" clearable>
              <el-option v-for="item in typeOptions" :label="item.label" :value="item.value" :key="item.key" clearable
                placeholder="变化类型">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="交易类型" prop="transType">
            <el-select size="middle" v-model="ruleForm.transType" placeholder="交易类型" style="width:100%;" clearable>
              <el-option v-for="item in transTypeOptions" :key="item.key" :label="item.label" :value="item.value"
                clearable placeholder="交易类型">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10">
          <el-form-item label="订单编号" prop="inputOutputKey">
            <el-select size="middle" v-model="ruleForm.inputOutputKey" placeholder="所属订单编号" style="width:100%;"
              clearable v-if="isShow">
              <el-option v-for="item in inputOptions" :key="item.inputWarehouseKey" :label="item.inputWarehouseKey"
                :value="item.inputWarehouseKey">
              </el-option>
            </el-select>
            <el-select size="middle" v-model="ruleForm.inputOutputKey" placeholder="所属订单编号" style="width:100%;"
              clearable v-else>
              <el-option v-for="item in outputOptions" :key="item.outputWarehouseKey" :label="item.outputWarehouseKey"
                :value="item.outputWarehouseKey">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="关联库存" prop="shopkeeperWarehouseKey">
            <el-select size="middle" v-model="ruleForm.shopkeeperWarehouseKey" placeholder="关联库存" style="width:100%;"
              clearable>
              <el-option v-for="item in shopkeeperWarehouseOptions" :key="item.shopkeeperWarehouseKey"
                :label="item.shopkeeperWarehouseKey" :value="item.shopkeeperWarehouseKey">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10">
          <el-form-item label="起始库存" prop="startNum">
            <el-input v-model="ruleForm.startNum" clearable placeholder="起始库存" :min="0" type="Number"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="变化数量" prop="quantity">
            <el-input v-model="ruleForm.quantity" clearable placeholder="变化数量" :min="0" type="Number"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10">
          <el-form-item label="结余库存" prop="finalNum">
            <el-input v-model="ruleForm.finalNum" clearable placeholder="结余库存" :min="0" type="Number"></el-input>
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
import { detailWarehouseUpdate, detailWarehouseAdd, shopkeeperWarehouseList, } from '@/api/warehouse'
import { inputWarehouseList } from '@/api/purchasing'
import { outputWarehouseList } from '@/api/marketing'


import moment from 'moment'

export default {
  name: 'guestEdit',
  data() {
    return {
      direction: 'btt',
      ifCreate: false,
      isShow: true,
      ruleForm: {
        tempType: "",
        inputOutputKey: "",
        shopkeeperWarehouseKey: "",
        type: "",
        transType: "",
        quantity: "",
        startNum: "",
        finalNum: "",
        atTime: "",
        detailWarehouseKey: "",
      },
      inputOptions: [],
      outputOptions: [],
      shopkeeperWarehouseOptions: [],
      // 交易类型(0采购入库、1采购退货出库、2零售出库、3零售退货入库、4客户订购出库、5客户订购退货入库、6调货入库、7调货出库)
      transTypeOptions: [
        { label: "采购入库", value: 0, key: 'transType0' },
        { label: "采购退货出库", value: 1, key: 'transType1' },
        { label: "零售出库", value: 2, key: 'transType2' },
        { label: "零售退货入库", value: 3, key: 'transType3' },
        { label: "客户订购出库", value: 4, key: 'transType4' },
        { label: "客户订购退货入库", value: 5, key: 'transType5' },
        { label: "调货入库", value: 6, key: 'transType6' },
        { label: "调货出库", value: 7, key: 'transType7' },
      ],
      // 变化类型(0：入库/总量增加，1：出库/总量减少)
      typeOptions: [
        { label: "入库/总量增加", value: 0, key: 'type0' },
        { label: "出库/总量减少", value: 1, key: 'type1' },
      ],
      rules: {
        tempType: [
          { required: true, message: '请选择订单类型', trigger: 'blur' },
        ],
        inputOutputKey: [
          { required: true, message: '请选择订单编号', trigger: 'blur' },
        ],
        shopkeeperWarehouseKey: [
          { required: true, message: '请选择关联库存', trigger: 'blur' },
        ],
        transType: [
          { required: true, message: '请选择交易类型', trigger: 'blur' },
        ],
        type: [
          { required: true, message: '请选择变化类型', trigger: 'blur' },
        ],
        quantity: [
          { required: true, message: '请设置变化数量', trigger: 'blur' },
        ],
        startNum: [
          { required: true, message: '请设置起始库存', trigger: 'blur' },
        ],
        finalNum: [
          { required: true, message: '请设置结余库存', trigger: 'blur' },
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
    this.getshopkeeperWarehouseList()
    this.getinputWarehouseList()
    this.getoutputWarehouseList()
    if (this.rowData.detailWarehouseKey) {
      this.ruleForm.inputOutputKey = this.rowData.inputOutputKey
      this.ruleForm.shopkeeperWarehouseKey = this.rowData.shopkeeperWarehouseKey
      this.ruleForm.type = this.rowData.type
      this.ruleForm.quantity = this.rowData.quantity
      this.ruleForm.startNum = this.rowData.startNum
      this.ruleForm.finalNum = this.rowData.finalNum
      this.ruleForm.detailWarehouseKey = this.rowData.detailWarehouseKey
      this.ruleForm.transType = this.rowData.transType
      this.ruleForm.atTime = this.rowData.atTime
      // (0采购入库、1采购退货出库、2零售出库、3零售退货入库、4客户订购出库、5客户订购退货入库、6调货入库、7调货出库)
      if (this.ruleForm.transType == 0 || this.ruleForm.transType == 1 || this.ruleForm.transType == 6) {
        this.ruleForm.tempType = 'input_warehouse'
      } else {
        this.ruleForm.tempType = 'output_warehouse'
      }
      this.getOptions()
    } else {
      this.ifCreate = true
    }
  },
  methods: {
    getshopkeeperWarehouseList() {
      shopkeeperWarehouseList().then(res => {
        if (res.data.code == 200) {
          this.shopkeeperWarehouseOptions = res.data.data
        } else {
          this.$message.error(res.data.msg);
        }
      });
    },
    getinputWarehouseList() {
      inputWarehouseList().then(res => {
        if (res.data.code == 200) {
          this.inputOptions = res.data.data
        } else {
          this.$message.error(res.data.msg);
        }
      });
    },
    getoutputWarehouseList() {
      outputWarehouseList().then(res => {
        if (res.data.code == 200) {
          this.outputOptions = res.data.data
        } else {
          this.$message.error(res.data.msg);
        }
      });
    },
    close() {
      this.$parent.drawer = false
    },
    getOptions() {
      if (this.tempType == 'input_warehouse') {
        this.isShow = true
      } else if (this.tempType == 'output_warehouse') {
        this.isShow = false
      }
    },
    save(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let data = {
            inputOutputKey: this.ruleForm.inputOutputKey,
            shopkeeperWarehouseKey: this.ruleForm.shopkeeperWarehouseKey,
            type: this.ruleForm.type,
            transType: this.ruleForm.transType,
            quantity: this.ruleForm.quantity,
            startNum: this.ruleForm.startNum,
            finalNum: this.ruleForm.finalNum,
            atTime: this.ruleForm.atTime,
            status:"",
            detailWarehouseKey: this.ruleForm.detailWarehouseKey
          }
          detailWarehouseUpdate(data).then(res => {
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
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let data = {
            inputOutputKey: this.ruleForm.inputOutputKey,
            shopkeeperWarehouseKey: this.ruleForm.shopkeeperWarehouseKey,
            type: this.ruleForm.type,
            transType: this.ruleForm.transType,
            quantity: this.ruleForm.quantity,
            startNum: this.ruleForm.startNum,
            finalNum: this.ruleForm.finalNum,
            atTime: moment().format("YYYY-MM-DD HH:mm:ss"),
            status:"",
            detailWarehouseKey: this.ruleForm.detailWarehouseKey
          }
          detailWarehouseAdd(data).then(res => {
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