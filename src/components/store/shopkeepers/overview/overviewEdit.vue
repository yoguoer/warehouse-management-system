<template>
  <el-dialog size="30%" :title="ifCreate ? '新增商品库存' : '编辑商品库存'" :visible.sync="drawer" :direction="direction"
    :close-on-click-modal="false" :close-on-press-escape="false" :show-close="false" :wrapperClosable="false"
    :append-to-body='true' width="1200px">

    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="130px" class="demo-ruleForm">
      <el-row>
        <el-col :span="10">
          <el-form-item label="门店" prop="shopCode">
            <el-select size="middle" v-model="ruleForm.shopCode" placeholder="所属门店" style="width:100%;" clearable>
              <el-option v-for="item in shopOptions" :key="item.shopKey" :label="item.shopName" :value="item.shopCode"
                @click.native="getInventoryList(item)">
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
          <el-form-item label="仓库" prop="inventoryCode">
            <el-select size="middle" v-model="ruleForm.inventoryCode" placeholder="仓库" style="width:100%;" clearable
              ref="inventorySelect">
              <el-option @click.native="setPosition" v-for="item in inventoryOptions" :key="item.inventoryKey"
                :label="item.inventoryName" :value="item.inventoryCode" :disabled="(item.status == 1) ? false : true">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="库位" prop="positionCode">
            <el-select size="middle" v-model="ruleForm.positionCode" placeholder="库位" style="width:100%;" clearable>
              <el-option v-for="item in positionOptions" :key="item.positionKey" :label="item.positionCode"
                :value="item.positionCode" clearable placeholder="库位">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10">
          <el-form-item label="库存上限" prop="maxNum">
            <el-input v-model="ruleForm.maxNum" clearable placeholder="库存上限" type="Number" :min="0"
              @blur="onInputNumChange('maxNum')"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="库存下限" prop="minNum">
            <el-input v-model="ruleForm.minNum" clearable placeholder="库存下限" type="Number" :min="0"
              @blur="onInputNumChange('minNum')"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10">
          <el-form-item label="现存量" prop="accountNum">
            <el-input v-model="ruleForm.accountNum" clearable placeholder="现存量" type="Number" :min="0"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="占用数" prop="occupyNum">
            <el-input v-model="ruleForm.occupyNum" clearable placeholder="占用数" type="Number" :min="0"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10">
          <el-form-item label="在途数" prop="onwayNum">
            <el-input v-model="ruleForm.onwayNum" clearable placeholder="在途数" type="Number" :min="0"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="残品数" prop="rejectsNum">
            <el-input v-model="ruleForm.rejectsNum" clearable placeholder="残品数" type="Number" :min="0"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10">
          <el-form-item label="最长未动销天数" prop="longestDay">
            <el-input v-model="ruleForm.longestDay" clearable placeholder="最长未动销天数" type="Number" :min="0"></el-input>
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
        longestDay: "",
        occupyNum: "",
        availableNum: "",
        countNum: "",
        rejectsNum: "",
        description: "",
        inventoryCode: "",
        shopkeeperWarehouseKey: "",
        onwayNum: "",
        operateTime: ""
      },
      shopOptions: [],
      goodsOptions: [],
      allGoods: [],
      inventoryOptions: [],
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
        longestDay: [
          { required: true, message: '请设置最长未动销天数', trigger: 'blur' },
        ],
        inventoryCode: [
          { required: true, message: '请选择仓库', trigger: 'blur' },
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
    this.getShopList()
    this.getgoodsList()
    if (this.rowData.shopkeeperWarehouseKey) {
      this.ruleForm.shopCode = this.rowData.shopCode
      this.ruleForm.goodsCode = this.rowData.goodsCode
      this.ruleForm.positionCode = this.rowData.positionCode
      this.ruleForm.maxNum = this.rowData.maxNum
      this.ruleForm.minNum = this.rowData.minNum
      this.ruleForm.accountNum = this.rowData.accountNum
      this.ruleForm.longestDay= this.rowData.longestDay
      this.ruleForm.occupyNum = this.rowData.occupyNum || 0
      this.ruleForm.availableNum = this.rowData.availableNum || 0
      this.ruleForm.countNum = this.rowData.countNum
      this.ruleForm.rejectsNum = this.rowData.rejectsNum
      this.ruleForm.description = this.rowData.description
      this.ruleForm.inventoryCode = this.rowData.inventoryCode
      this.ruleForm.shopkeeperWarehouseKey = this.rowData.shopkeeperWarehouseKey
      this.ruleForm.onwayNum = this.rowData.onwayNum || 0
    } else {
      this.ifCreate = true
    }
    if (this.rowData.shopCode) {
      this.getInventoryList(this.rowData)
    } if (this.rowData.inventoryCode) {
      this.getpositionList()
    }
  },
  mounted() {
  },
  methods: {
    getgoodsList() {
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
    unique(arr) {
      const res = new Map();
      return arr.filter((arr) => !res.has(arr.shopKey) && res.set(arr.shopKey, 1));
    },
    getShopList() {
      ShopInventoryList().then(res => {
        if (res.data.code == 200) {
          // this.shopOptions = res.data.data
          this.shopOptions = this.unique(res.data.data)
        } else {
          this.$message.error("获取失败!");
        }
      })
    },
    getgoodsfinal(item) {
      shopkeeperWarehouseByShopCode({ shopCode: item.shopCode, shopName: item.shopName }).then(res => {
        if (res.data.code == 200) {
          if (res.data.data.length!=0) {
            this.goodsOptions = res.data.data
          } else {
            this.goodsOptions = this.allGoods
          }
        } else {
          this.$message.error("获取失败!");
        }
      });
    },
    async getInventoryList(item) {
      this.getgoodsfinal(item)
      getByshopCode({ shopCode: item.shopCode }).then(res => {
        if (res.data.code == 200) {
          this.inventoryOptions = res.data.data
          this.getpositionList()
        } else {
          this.$message.error("获取失败!");
        }
      });
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
    setPosition() {
      let choosenItem = this.inventoryOptions.filter(item => {
        return item.inventoryCode == this.ruleForm.inventoryCode
      });
      this.getpositionList(choosenItem[0].inventoryKey)
    },
    // 门店和仓库一对一的时候
    // getMyPosition(item) {
    //   this.getgoodslist(item)
    //   getByshopCode({ shopCode: this.ruleForm.shopCode }).then(res => {
    //     if (res.data.code == 200) {
    //       // this.inventoryKey = res.data.data[0].inventoryKey
    //       // console.log(res.data.data.inventoryKey)
    //       positionList({ inventoryKey: res.data.data[0].inventoryKey }).then(res => {
    //         if (res.data.code == 200) {
    //           this.positionOptions = res.data.data
    //         } else {
    //           this.$message.error("获取失败!");
    //         }
    //       });
    //     } else {
    //       this.$message.error("获取失败!");
    //     }
    //   });
    // },
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
            longestDay: this.ruleForm.longestDay,
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