<template>
  <el-dialog size="30%" :title="ifCreate ? '新增' : '编辑'" :visible.sync="drawer" :direction="direction"
    :close-on-click-modal="false" :close-on-press-escape="false" :show-close="false" :wrapperClosable="false"
    :append-to-body='true' width="1200px">

    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-row>
        <el-col :span="10">
          <el-form-item label="门店" prop="shopCode">
            <el-select size="middle" v-model="ruleForm.shopCode" placeholder="所属门店" style="width:100%;" clearable>
              <el-option v-for="item in shopOptions" :key="item.shopKey" :label="item.shopName" :value="item.shopCode"
                @click.native="getMyPosition(item)">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="商品" prop="goodsCode">
            <el-select size="middle" v-model="ruleForm.goodsCode" placeholder="商品" style="width:100%;" clearable>
              <el-option v-for="item in goodsOptions" :key="item.goodsCode" :label="item.goodsName"
                :value="item.goodsCode">
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
          <el-form-item label="库存上限" prop="maxNum">
            <el-input v-model="ruleForm.maxNum" clearable placeholder="库存上限" type="Number" :min="0"
              @blur="onInputNumChange('maxNum')"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10">
          <el-form-item label="库存下限" prop="minNum">
            <el-input v-model="ruleForm.minNum" clearable placeholder="库存下限" type="Number" :min="0"
              @blur="onInputNumChange('minNum')"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="现存量" prop="accountNum">
            <el-input v-model="ruleForm.accountNum" clearable placeholder="现存量" type="Number" :min="0"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10">
          <el-form-item label="占用数" prop="occupyNum">
            <el-input v-model="ruleForm.occupyNum" clearable placeholder="占用数" type="Number" :min="0"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="在途数" prop="onwayNum">
            <el-input v-model="ruleForm.onwayNum" clearable placeholder="在途数" type="Number" :min="0"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10">
          <el-form-item label="残品数" prop="rejectsNum">
            <el-input v-model="ruleForm.rejectsNum" clearable placeholder="残品数" type="Number" :min="0"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="备注" prop="description">
            <el-input v-model="ruleForm.description" clearable placeholder="备注" type="textarea"></el-input>
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
import { shopkeeperWarehouseUpdate, shopkeeperWarehouseAdd, getByshopCode, ShopInventoryList, shopkeeperWarehouseByShopCode } from '@/api/warehouse'
import { positionList } from '@/api/data'
import moment from 'moment'
import { goodslist } from '@/api/data'

export default {
  name: 'guestEdit',
  data() {
    return {
      direction: 'btt',
      ifCreate: false,
      ruleForm: {
        shopCode: "",
        goodsCode: "",
        positionCode: "",
        maxNum: "",
        minNum: "",
        accountNum: "",
        occupyNum: "",
        availableNum: "",
        countNum: "",
        rejectsNum: "",
        description: "",
        shopkeeperWarehouseKey: "",
        onwayNum: "",
        operateTime: ""
      },
      shopOptions: [],
      goodsOptions: [],
      allGoods: [],
      positionOptions: [],
      rules: {
        shopCode: [
          { required: true, message: '请选择门店', trigger: 'blur' },
        ],
        goodsCode: [
          { required: true, message: '请选择商品', trigger: 'blur' },
        ],
        // onwayNum: [
        //   { required: true, message: '请设置在途数', trigger: 'blur' },
        // ],
        maxNum: [
          { required: true, message: '请设置库存上限', trigger: 'blur' },
        ],
        minNum: [
          { required: true, message: '请设置库存下限', trigger: 'blur' },
        ],
        accountNum: [
          { required: true, message: '请设置现存量', trigger: 'blur' },
        ],
        // occupyNum: [
        //   { required: true, message: '请设置占用数', trigger: 'blur' },
        // ],
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
    this.getShopInventoryList()
    this.getAllgoods()
    if (this.rowData.shopkeeperWarehouseKey) {
      this.ruleForm.shopCode = this.rowData.shopCode
      this.ruleForm.goodsCode = this.rowData.goodsCode
      this.ruleForm.positionCode = this.rowData.positionCode
      this.ruleForm.maxNum = this.rowData.maxNum
      this.ruleForm.minNum = this.rowData.minNum
      this.ruleForm.accountNum = this.rowData.accountNum
      this.ruleForm.occupyNum = this.rowData.occupyNum || 0
      this.ruleForm.availableNum = this.rowData.availableNum || 0
      this.ruleForm.countNum = this.rowData.countNum
      this.ruleForm.rejectsNum = this.rowData.rejectsNum
      this.ruleForm.description = this.rowData.description
      this.ruleForm.shopkeeperWarehouseKey = this.rowData.shopkeeperWarehouseKey
      this.ruleForm.onwayNum = this.rowData.onwayNum || 0
      this.getMyPosition(this.rowData)
    } else {
      this.ifCreate = true
    }
  },
  mounted() {
    if (this.rowData.shopCode) {
      console.log(this.rowData)
      this.getgoodslist(this.rowData)
    }
  },
  methods: {
    getAllgoods() {
      goodslist().then(res => {
        if (res.data.code == 200) {
          this.allGoods = res.data.data
          // console.log(this.allGoods,"allGoods")
        } else {
          this.$message.error("获取失败!");
        }
      });
    },
    onInputNumChange(typeName) {
      if (this.ruleForm.maxNum < this.ruleForm.minNum) {
        if (typeName = 'maxNum') {
          this.ruleForm.maxNum = ''
        } else {
          this.ruleForm.minNum = ''
        }
        this.$message.warning('库存上限应不得小于库存下限')
      }
    },
    getShopInventoryList() {
      // shopkeeperWarehouseList().then(res => {
      //   if (res.data.code == 200) {
      //     this.shopOptions = res.data.data
      //   } else {
      //     this.$message.error(res.data.msg);
      //   }
      // });
      ShopInventoryList().then(res => {
        if (res.data.code == 200) {
          this.shopOptions = res.data.data
        } else {
          this.$message.error("获取失败!");
        }
      })
    },
    getgoodslist(item) {
      shopkeeperWarehouseByShopCode({ shopCode: item.shopCode, shopName: item.shopName }).then(res => {
        if (res.data.code == 200) {
          this.goodsOptions = []
          if (res.data.data.length==0) {
            this.goodsOptions = this.allGoods
          } else {
            this.allGoods.forEach(item => {
              res.data.data.forEach(goods => {
                if (item.goodsCode != goods.goodsCode) {
                  this.goodsOptions.push(item)
                }
              })
            })
          }
          // console.log("goodsOptions",this.goodsOptions)
          // this.goodsOptions = res.data.data
        } else {
          this.$message.error("获取失败!");
        }
      });
    },
    getMyPosition(item) {
      this.getgoodslist(item)
      getByshopCode({ shopCode: this.ruleForm.shopCode }).then(res => {
        if (res.data.code == 200) {
          this.inventoryKey = res.data.data[0].inventoryKey
          console.log(res.data.data.inventoryKey)
          positionList({ inventoryKey: res.data.data[0].inventoryKey }).then(res => {
            if (res.data.code == 200) {
              this.positionOptions = res.data.data
            } else {
              this.$message.error("获取失败!");
            }
          });
        } else {
          this.$message.error("获取失败!");
        }
      });
    },
    close() {
      this.$parent.drawer = false
    },
    save(formName) {
      this.ruleForm.availableNum = parseInt(this.ruleForm.accountNum) + parseInt(this.ruleForm.onwayNum) - parseInt(this.ruleForm.occupyNum)
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.ruleForm.operateTime = moment().format("YYYY-MM-DD HH:mm:ss");
          shopkeeperWarehouseUpdate(this.ruleForm).then(res => {
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
      this.ruleForm.availableNum = parseInt(this.ruleForm.accountNum) + parseInt(this.ruleForm.onwayNum) - parseInt(this.ruleForm.occupyNum)
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.ruleForm.operateTime = moment().format("YYYY-MM-DD HH:mm:ss");
          let data = {
            shopCode: this.ruleForm.shopCode,
            goodsCode: this.ruleForm.goodsCode,
            positionCode: this.ruleForm.positionCode,
            maxNum: this.ruleForm.maxNum,
            minNum: this.ruleForm.minNum,
            accountNum: this.ruleForm.accountNum,
            occupyNum: this.ruleForm.occupyNum || 0,
            availableNum: this.ruleForm.availableNum || 0,
            rejectsNum: this.ruleForm.rejectsNum,
            countNum: this.ruleForm.countNum,
            description: this.ruleForm.description,
            onwayNum: this.ruleForm.onwayNum || 0,
            operateTime: moment().format("YYYY-MM-DD HH:mm:ss")
          }
          shopkeeperWarehouseAdd(data).then(res => {
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