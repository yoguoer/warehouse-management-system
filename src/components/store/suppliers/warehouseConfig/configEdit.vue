<template>
  <el-drawer size="30%" :title="ifCreate ? '新增供应商仓库配置' : '编辑供应商仓库配置'" :visible.sync="drawer" :direction="direction"
    :close-on-press-escape="false" :show-close="false" :wrapperClosable="false">

    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="150px" class="demo-ruleForm">
      <el-row>
        <el-row>
          <el-col :span="5">
            <el-form-item label="供应商编号" prop="supplierCode">
              <el-input v-model="ruleForm.supplierCode" class="readonly-copy" placeholder="供应商编号" readonly
                v-if="ifCreate == false"></el-input>
              <el-input v-model="ruleForm.supplierCode" v-else clearable placeholder="供应商编号"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="供应商名称" prop="supplierName">
              <el-input v-model="ruleForm.supplierName" clearable placeholder="供应商名称"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="5">
            <el-form-item label="仓库编号" prop="supplierInventoryCode">
              <el-input v-model="ruleForm.supplierInventoryCode" class="readonly-copy" placeholder="仓库编号" readonly
                v-if="ifCreate == false"></el-input>
              <el-input v-model="ruleForm.supplierInventoryCode" v-else clearable placeholder="仓库编号"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="仓库名称" prop="supplierInventoryName">
              <el-input v-model="ruleForm.supplierInventoryName" clearable placeholder="仓库名称"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="5">
            <el-form-item label="状态" prop="supplierInventoryStatus">
              <el-input v-model="ruleForm.supplierInventoryStatus" class="readonly-copy" placeholder="状态"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="备注" prop="description">
              <el-input v-model="ruleForm.description" clearable placeholder="备注"></el-input>
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
// import { supplierInventoryUpdate, supplierInventoryAdd } from '@/api/data'

export default {
  name: 'guestEdit',
  data() {
    return {
      direction: 'btt',
      ifCreate: false,
      ruleForm: {
        supplierCode: "",
        supplierCode: "",
        supplierInventoryCode:"",
        supplierInventoryName:"",

      },
      rules: {
        supplierName: [
          { required: true, message: '请输入供应商名称', trigger: 'blur' },
        ],
        supplierCode: [
          { required: true, message: '请输入供应商编号', trigger: 'blur' },
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
    if (this.rowData.supplierInventoryCode) {
      this.ruleForm.supplierInventoryName = this.rowData.supplierInventoryName
      this.ruleForm.supplierInventoryCode = this.rowData.supplierInventoryCode
    } else {
      this.ifCreate = true
    }
  },
  methods: {
    close() {
      this.$parent.drawer = false
    },
    save(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          supplierInventoryUpdate(this.ruleForm).then(res => {
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
          supplierInventoryAdd(this.ruleForm).then(res => {
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