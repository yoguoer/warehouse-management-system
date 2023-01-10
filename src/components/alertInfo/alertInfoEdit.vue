<template>
  <el-dialog size="30%" :title="ifCreate ? '新增预警' : '新增预警'" :visible.sync="drawer" :direction="direction"
    :close-on-press-escape="false" :show-close="false" :wrapperClosable="false" :closeOnClickModal="false"
    width="1200px" :before-close="close">

    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-row>
        <el-col :span="10">
          <el-form-item label="预警对象" prop="shopSupplierCode">
            <el-select size="middle" v-model="ruleForm.shopSupplierCode" placeholder="预警对象" style="width:100%;" clearable>
              <el-option v-for="item in shopOptions" :key="item.shopKey" :label="item.shopName" :value="item.shopCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="预警商品" prop="goodsCode">
            <el-select size="middle" v-model="ruleForm.goodsCode" placeholder="预警商品" style="width:100%;" clearable>
              <el-option v-for="item in goodsOptions" :key="item.goodsKey" :label="item.goodsName" :value="item.goodsCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10">
          <el-form-item label="状态" prop="status">
            <el-select size="mini" v-model="ruleForm.status" placeholder="请选择" style="width:100%;" clearable>
                  <el-option label="未处理" :value="0"></el-option>
                  <el-option label="已处理" :value="1"></el-option>
                </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="类型" prop="type">
            <!-- 预警类型（0：连续未动销预警，1：满仓预警，2：缺货预警） -->
            <el-select size="mini" v-model="ruleForm.type" placeholder="请选择" style="width:100%;" clearable>
                  <el-option label="连续未动销预警" :value="0"></el-option>
                  <el-option label="满仓预警" :value="1"></el-option>
                  <el-option label="缺货预警" :value="2"></el-option>
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
import { alertInfoUpdate, alertInfoAdd } from '@/api/welcome'
import moment from 'moment'
import { shoplist, goodslist } from '@/api/data'

export default {
  name: 'guestEdit',
  data() {
    return {
      direction: 'btt',
      ifCreate: false,
      ruleForm: {
        goodsCode: "",
        shopSupplierCode: "",
        status: "",
        type: "",
        alertTime: "",
        alertInfoKey: "",
      },
      goodsOptions: "",
      shopOptions: "",
      rules: {
        goodsCode: [
          { required: true, message: '请选择预警商品', trigger: 'blur' },
        ],
        shopSupplierCode: [
          { required: true, message: '请选择预警对象', trigger: 'blur' },
        ],
        status: [
          { required: true, message: '请选择状态', trigger: 'blur' },
        ],
        type: [
          { required: true, message: '请选择类型', trigger: 'blur' },
        ]
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
    this.getgoodslist()
    this.getshoplist()
    this.ruleForm.type = this.rowData.type||this.$route.params.type
    if (this.rowData.alertInfoKey) {
      this.ruleForm.goodsCode = this.rowData.goodsCode
      this.ruleForm.shopSupplierCode = this.rowData.shopSupplierCode
      this.ruleForm.alertInfoKey = this.rowData.alertInfoKey
      this.ruleForm.status = this.rowData.status
      // this.ruleForm.type = this.rowData.type
      this.ruleForm.alertTime = this.rowData.alertTime
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
        } else {
          this.$message.error("获取失败!");
        }
      });
    },
    close() {
      this.$parent.drawer = false
      this.reset()
    },
    save(formName) {
      this.ruleForm.alertTime = moment().format("YYYY-MM-DD HH:mm:ss");
      this.$refs[formName].validate((valid) => {
        if (valid) {
          alertInfoUpdate(this.ruleForm).then(res => {
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
            goodsCode: this.ruleForm.goodsCode,
            shopSupplierCode: this.ruleForm.shopSupplierCode,
            status: this.ruleForm.status,
            type: this.ruleForm.type,
            alertTime: moment().format("YYYY-MM-DD HH:mm:ss")
          }
          alertInfoAdd(data).then(res => {
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
    reset() {
      this.ruleForm = {
        goodsCode: "",
        shopSupplierCode: "",
        status: "",
        type: "",
        alertTime: "",
        alertInfoKey: "",
      }
    }
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