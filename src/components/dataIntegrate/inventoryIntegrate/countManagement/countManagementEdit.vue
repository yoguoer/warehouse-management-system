<template>
  <el-dialog size="30%" :title="ifCreate ? '新增盘点数' : '编辑盘点数'" :visible.sync="drawer" :direction="direction"
    :close-on-press-escape="false" :show-close="false" :wrapperClosable="false" :append-to-body='true' width="1200px">

    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-row>
        <el-col :span="10">
          <el-form-item label="门店" prop="shopCode">
            <el-select size="middle" v-model="ruleForm.shopCode" placeholder="所属门店" style="width:100%;"  @change="getMyPosition()" clearable disabled>
              <el-option v-for="item in shopOptions" :key="item.shopKey" :label="item.shopName" :value="item.shopCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="商品" prop="goodsCode">
            <el-select size="middle" v-model="ruleForm.goodsCode" placeholder="商品" style="width:100%;" clearable disabled>
              <el-option v-for="item in goodsOptions" :key="item.goodsCode" :label="item.goodsName" :value="item.goodsCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10">
          <el-form-item label="账面库存" prop="accountNum">
            <el-input v-model="ruleForm.accountNum" clearable placeholder="账面库存" type="Number"  :min="0" disabled></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="盘点数" prop="countNum">
            <el-input v-model="ruleForm.countNum" clearable placeholder="盘点数" type="Number" :min="0"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
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
import { shopkeeperWarehouseUpdate, shopkeeperWarehouseAdd,getByshopCode } from '@/api/warehouse'
import { shoplist, goodslist, positionList } from '@/api/data'
import moment from 'moment'

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
        countNum:"",
        rejectsNum:"",
        description: "",
        shopkeeperWarehouseKey: "",
        onwayNum:"",
        operateTime:""
      },
      shopOptions: [],
      goodsOptions: [],
      positionOptions: [],
      rules: {
        shopCode: [
          { required: true, message: '请选择门店', trigger: 'blur' },
        ],
        goodsCode: [
          { required: true, message: '请选择商品', trigger: 'blur' },
        ],
        accountNum: [
          { required: true, message: '请设置账面库存', trigger: 'blur' },
        ],
        countNum: [
          { required: true, message: '请设置盘点数', trigger: 'blur' },
        ],
      }
    }
  },
  props: {
    drawer: {
      default: true,
    },
    rowData: {},
    shopGoodsList:[]
  },
  watch: {

  },
  created() {
    this.getshoplist()
    // this.getgoodslist()
    if (this.rowData.shopkeeperWarehouseKey) {
      this.ruleForm.shopCode = this.rowData.shopCode
      this.ruleForm.goodsCode = this.rowData.goodsCode
      this.ruleForm.positionCode = this.rowData.positionCode
      this.ruleForm.maxNum = this.rowData.maxNum
      this.ruleForm.minNum = this.rowData.minNum
      this.ruleForm.accountNum = this.rowData.accountNum
      this.ruleForm.occupyNum = this.rowData.occupyNum
      this.ruleForm.availableNum = this.rowData.availableNum
      this.ruleForm.countNum=this.rowData.countNum
      this.ruleForm.rejectsNum=this.rowData.rejectsNum
      this.ruleForm.description = this.rowData.description
      this.ruleForm.shopkeeperWarehouseKey = this.rowData.shopkeeperWarehouseKey
      this.ruleForm.onwayNum=this.rowData.onwayNum
    } else {
      this.ifCreate = true
    }
  },
  methods: {
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
          this.shopGoodsList.forEach(t=>{
            this.goodsOptions.forEach(item=>{
              if(item.goodsCode==t.goodsCode && t.shopCode==this.ruleForm.shopCode){
                  let index=this.goodsOptions.indexOf(t)
                  this.goodsOptions.splice(index,1)
                }
              })
          })
        } else {
          this.$message.error("获取失败!");
        }
      });
    },
    getMyPosition(){
      this.getgoodslist()
      getByshopCode({shopCode:this.ruleForm.shopCode}).then(res => {
        if (res.data.code == 200) {
          this.inventoryKey=res.data.data.inventoryKey
          positionList({ inventoryKey: res.data.data.inventoryKey }).then(res => {
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
          this.ruleForm.operateTime=moment().format("YYYY-MM-DD HH:mm:ss");
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
          this.ruleForm.operateTime=moment().format("YYYY-MM-DD HH:mm:ss");
          let data = {
            shopCode: this.ruleForm.shopCode,
            goodsCode: this.ruleForm.goodsCode,
            positionCode: this.ruleForm.positionCode,
            maxNum: this.ruleForm.maxNum,
            minNum: this.ruleForm.minNum,
            accountNum: this.ruleForm.accountNum,
            occupyNum: this.ruleForm.occupyNum,
            availableNum: this.ruleForm.availableNum,
            rejectsNum:this.ruleForm.rejectsNum,
            countNum:this.ruleForm.countNum,
            description: this.ruleForm.description,
            onwayNum:this.ruleForm.onwayNum
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