<template>
  <el-drawer size="30%" :title="ifCreate ? '新增门店仓库配置' : '编辑门店仓库配置'" :visible.sync="drawer" :direction="direction"
    :close-on-press-escape="false" :show-close="false" :wrapperClosable="false" :append-to-body='true'
    :close-on-click-modal="false">

    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="150px" class="demo-ruleForm">
      <el-row>
        <el-row>
          <el-col :span="5">
            <el-form-item label="所属门店" prop="belongKey">
              <el-select size="middle" v-model="ruleForm.belongKey" placeholder="所属门店" style="width:100%;">
                <el-option v-for="item in shopOptions" :key="item.shopKey" :label="item.shopName" :value="item.shopKey"
                  clearable placeholder="所属门店">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="默认仓库" prop="inventoryKey">
              <el-select size="middle" v-model="ruleForm.inventoryKey" placeholder="默认仓库" style="width:100%;">
                <el-option v-for="item in options" :key="item.inventoryKey" :label="item.inventoryName" clearable
                  :value="item.inventoryKey" @click="setStauts(item)">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-row>
      <el-row>
        <el-form-item style="display:inline-block;float:right;margin:10px 40%">
          <el-button type="primary" @click="save('ruleForm')" v-if="ifCreate == false">保存</el-button>
          <el-button type="primary" @click="create('ruleForm')" v-else>立即创建</el-button>
          <!-- <el-button @click="resetForm('ruleForm')">重置</el-button> -->
          <el-button @click="close()">关闭</el-button>
        </el-form-item>
      </el-row>
    </el-form>

  </el-drawer>
</template>

<script>
import { ShopInventoryUpdate, ShopInventoryAdd } from '@/api/warehouse'
import { inventorylist, shoplist } from '@/api/data'

export default {
  name: 'guestEdit',
  data() {
    return {
      direction: 'btt',
      ifCreate: false,
      ruleForm: {
        belongKey: "",
        inventoryKey: "",
        status: 1,
      },
      shopOptions: [],
      options: [],
      rules: {
        belongKey: [
          { required: true, message: '请选择门店', trigger: 'blur' },
        ],
        inventoryKey: [
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
    ShopInventoryList: []
  },
  watch: {

  },
  created() {
    this.getshoplist()
    this.getinventorylist();
    if (this.rowData.belongKey) {
      this.ruleForm.belongKey = this.rowData.belongKey
      this.ruleForm.inventoryKey = this.rowData.inventoryKey
    } else {
      this.ifCreate = true
    }
    // console.log("ShopInventoryList",this.ShopInventoryList)
  },
  methods: {
    setStatus(item) {
      this.options.forEach(t => {
        if (item.inventoryKey == t.inventoryKey && item.belongKey != null || item.belongKey != "") {
          this.status = 2
        }
      })
    },
    getshoplist() {
      shoplist().then(res => {
        if (res.data.code == 200) {
          this.shopOptions = res.data.data
          // let temp=res.data.data
          // this.ShopInventoryList.forEach(t=>{
          //   temp.forEach(item=>{// 去掉已有仓库的
          //   if(item.shopKey==t.shopKey){
          //       let index=temp.indexOf(item)
          //       temp.splice(index,1)
          //     }
          //   })
          // })
          // this.shopOptions = temp
        } else {
          this.$message.error("获取失败!");
        }
      });
    },
    getinventorylist() {
      inventorylist()
        .then((res) => {
          if (res.data.code === 200) {
            // this.options = res.data.data
            this.options = []
            res.data.data.forEach(item => {
              if (item.inventoryType == '2' && item.belongKey == null || item.belongKey == "") {
                this.options.push(item)
              }
            });
          } else {
            this.$message.error(res.msg);
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },
    close() {
      this.$parent.drawer = false
    },
    save(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          ShopInventoryUpdate(this.ruleForm).then(res => {
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
          ShopInventoryAdd(this.ruleForm).then(res => {
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