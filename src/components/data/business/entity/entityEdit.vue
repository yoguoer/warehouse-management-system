<template>
  <el-drawer size="30%" :title="ifCreate ? '新增业务主体' : '编辑业务主体'" :visible.sync="drawer" :direction="direction"
    :close-on-press-escape="false" :show-close="false" :wrapperClosable="false">

    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="150px" class="demo-ruleForm">

      <el-row>
        <el-col :span="5">
          <el-form-item label="业务主体编号" prop="entityCode">
            <el-input v-model="ruleForm.entityCode" class="readonly-copy" placeholder="业务主体编号" readonly v-if="ifCreate==false"></el-input>
            <el-input v-model="ruleForm.entityCode" v-else></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item label="业务主体名称" prop="entityName">
            <el-input v-model="ruleForm.entityName"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-form-item style="display:inline-block;float:right;margin:10px 40%">
        <el-button type="primary" @click="save('ruleForm')" v-if="ifCreate == false">保存</el-button>
        <el-button type="primary" @click="create('ruleForm')" v-else>立即创建</el-button>
        <!-- <el-button @click="resetForm('ruleForm')">重置</el-button> -->
        <el-button @click="close()">关闭</el-button>
      </el-form-item>

    </el-form>

  </el-drawer>
</template>

<script>
import { entityUpdate, entityAdd } from '@/api/data'

export default {
  name: 'guestEdit',
  data() {
    return {
      direction: 'btt',
      ifCreate: false,
      ruleForm: {
        entityName: "",
        entityCode: ""
      },
      rules: {
        entityName: [
          { required: true, message: '请输入业务主体名称', trigger: 'blur' },
        ],
        entityCode: [
          { required: true, message: '请输入业务主体编号', trigger: 'blur' },
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
    if (this.rowData.entityCode) {
      this.ruleForm.entityName = this.rowData.entityName
      this.ruleForm.entityCode = this.rowData.entityCode
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
          entityUpdate(this.ruleForm).then(res => {
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
          entityAdd(this.ruleForm).then(res => {
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