<template>
    <el-dialog :title="ifCreate ? '新增供应商' : '编辑供应商'" :visible.sync="drawer" :direction="direction"
        :close-on-press-escape="false" :show-close="false" :wrapperClosable="false" :append-to-body="true"
        width="600px">
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px">

            <el-form-item label="供应商编号" prop="superlierNumber">
                <el-select v-model="ruleForm.superlierNumber" placeholder="请输入供应商编号" clearable>
                    <el-option v-for="item in supplierEntityList" :key="item.entityCode" :label="item.entityCode"
                        :value="item.entityCode" @click.native="supplierChange(item)">
                        <div>[{{ item.entityCode }}] {{ item.entityName }}</div>
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="供应商名称" prop="supplierName">
                <el-select v-model="ruleForm.superlierNumber" placeholder="请输入供应商编号" clearable>
                    <el-option v-for="item in supplierEntityList" :key="item.entityCode" :label="item.entityName"
                        :value="item.entityName" @click.native="supplierChange(item)">
                        <div>[{{ item.entityCode }}] {{ item.entityName }}</div>
                    </el-option>
                </el-select>
            </el-form-item>

            <el-form-item label="所属分类" v-model="ruleForm.categoryKey" prop="categoryKey">
                <listBoxF style="width:335px">
                    <template slot="content">
                        <treeselect class="treeSelect-option" v-model="value" :normalizer="normalizer" :options="list"
                            clearable style="width:335px" placeholder="请选择" @select="selectNode" />
                    </template>
                </listBoxF>
            </el-form-item>
            <el-form-item label="备注" prop="superlierDescription">
                <el-input v-model="ruleForm.superlierDescription" placeholder="备注" clearable type="textarea"></el-input>
            </el-form-item>
            <el-row class="dialog_footer">
                <el-button type="primary" size="small" @click="save('ruleForm')" v-if="ifCreate == false">提交</el-button>
                <el-button type="primary" size="small" @click="create('ruleForm')" v-else>立即创建</el-button>
                <el-button @click="close()" size="small">关闭</el-button>
            </el-row>
        </el-form>
    </el-dialog>
</template>

<script>
import listBoxF from '@/components/public/listBoxF/listBoxF.vue'
/**
* 树形组件 用于选择框
*/
// import the component
import Treeselect from '@riophae/vue-treeselect'
// import the styles
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import { Supplierupdate, Supplieradd, getCategoryTree, entityList } from "@/api/data";
export default {
  components: {
      listBoxF,
      Treeselect
  },
  data() {
      return {
          // 实体下拉列表
          supplierEntityList: [],

          direction: 'btt',
          ifCreate: false,
          ruleForm: {
              superlierNumber: "",
              supplierName: "",
              superlierDescription: "",
              categoryKey: ""
          },
          rules: {
              superlierNumber: [
                  { required: true, message: '请输入供应商编号', trigger: 'blur' },
              ],
              supplierName: [
                  { required: true, message: '请输入供应商名称', trigger: 'blur' },
              ],
              categoryKey: [
                  { required: true, message: '请选择所属分类', trigger: 'blur' },
              ]
          },
          // 所属事业群编码
          options: [],
          value: "",
          list: []
      }
  },
  props: {
      drawer: {
          default: true,
      },
      rowData: {},
      multiple: {
          type: Boolean,
          default: false
      },
  },
  methods: {
      close() {
          this.$parent.drawer = false
      },
      save(formName) {
          this.ruleForm.businessGroupCode = this.value || ""
          this.$refs[formName].validate((valid) => {
              if (valid) {
                  Supplierupdate(this.ruleForm).then(res => {
                      if (res.code == 200) {
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
          this.ruleForm.businessGroupCode = this.value || ""
          this.$refs[formName].validate((valid) => {
              if (valid) {
                  Supplieradd(this.ruleForm).then(res => {
                      if (res.code == 200) {
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
      // 自定义参数键值名称
      normalizer(node) {
        //去掉children=[]的children属性
        if (node.children && !node.children.length) {
          delete node.children;
        }
        return {
          id: node.categoryKey,
          label: node.categoryName,
          children: node.children,
          level: node.level
        }
      },
      selectNode(node) {
        this.ruleForm.categoryKey = node.categoryKey || ''
        this.value = node.categoryKey
        // console.log("selectNode(node)：",this.ruleForm)
      },
      getTree() {
        let params = {
          categoryType: "SUPPLIER",
        }
        getCategoryTree(params).then(res => {
          if (res.code === 200) {
            res.data.forEach(item => {
              if (item.categoryName == '全部') {
                let index = res.data.indexOf(item)
                res.data.splice(index, 1)
              }
            });
            this.list = res.data
            // console.log(this.list);
          } else {
            console.log('error');
          }
        })
      },
      getSupplierEntityList() {
        entityList().then((res) => {
            if (res.code == 200) {
                this.supplierEntityList = res.data
            }
        })
      },
      supplierChange(e) {
        this.ruleForm.superlierNumber = e.entityCode
        this.ruleForm.supplierName = e.entityName
      }
  },
  watch: {
      // 监听选中值的变化
      value: {
          deep: true,
          handler(val) {
              this.$emit('getSelectVal', val)
          }
      },
  },
  created() {
      this.getTree()
      this.getSupplierEntityList()

      if (this.rowData.supplierKey) {
          this.ruleForm = {
            supplierKey: this.rowData.supplierKey,
            superlierNumber: this.rowData.superlierNumber,
            supplierName: this.rowData.supplierName,
            superlierDescription: this.rowData.superlierDescription,
            categoryKey: this.rowData.categoryKey
          }
      } else {
          this.ifCreate = true
      }

      if (this.ruleForm.categoryKey) {
          this.value = this.ruleForm.categoryKey
      } else {
          this.value = null
      }
  }
}
</script>

<style lang="scss" scoped>
/deep/ .input-number {
  .el-input-number__decrease, .el-input-number__increase {
      height: 34px;
      display: flex;
      align-items: center;
      justify-content: center;
  }
}
</style>