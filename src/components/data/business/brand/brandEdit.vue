<template>
  <el-drawer
    size="30%"
    :title="ifCreate ? '新增品牌' : '品牌编辑'"
    :visible.sync="drawer"
    :direction="direction"
    :close-on-press-escape="false"
    :show-close="false"
    :wrapperClosable="false">

    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">

      <el-row>
        <el-col :span="5">
          <el-form-item label="品牌编号" prop="brandCode">
            <el-input v-model="ruleForm.brandCode"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item label="品牌名称" prop="brandName">
            <el-input v-model="ruleForm.brandName"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    
      <el-form-item style="display:inline-block;float:right;margin:10px 40%">
        <el-button type="primary" @click="save('ruleForm')" v-if="ifCreate==false">保存</el-button>
        <el-button type="primary" @click="create('ruleForm')" v-else>立即创建</el-button>
        <!-- <el-button @click="resetForm('ruleForm')">重置</el-button> -->
        <el-button @click="close()">关闭</el-button>
      </el-form-item>

    </el-form>

  </el-drawer>
</template>

<script>
import { brandUpdate,brandAdd } from '@/api/data'

export default {
    name: 'guestEdit',
    data(){
      return{
        direction: 'btt',
        ifCreate:false,
        ruleForm:{
            brandName:"",
            brandCode:""
          },
        rules: {
          brandName: [
            { required: true, message: '请输入品牌名称', trigger: 'blur' },
          ],
          brandCode: [
            { required: true, message: '请输入品牌编号', trigger: 'blur' },
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
    watch:{

    },
    created(){
      if(this.rowData.brandName){
        this.ruleForm.brandName=this.rowData.brandName
        this.ruleForm.brandCode=this.rowData.brandCode
        this.ruleForm.brandKey=this.rowData.brandKey
      }else{
        this.ifCreate=true
      }
    },
    methods:{
      close() {
        this.$parent.drawer=false
        this.reset()
      },
      save(){
        brandUpdate(this.ruleForm).then(res=> {
          if(res.data.code==200){
            this.$message.success("编辑成功!");
            this.$parent.success()
            this.$forceUpdate()
          }else{
            this.$message.error("编辑失败!");
          }
        });
      },
      create(){
        brandAdd(this.ruleForm).then(res=> {
          if(res.data.code==200){
            this.$message.success("新增成功!");
            this.$parent.success()
            this.$forceUpdate()
          }else{
            this.$message.error("新增失败!");
          }
        });  
      },
      reset(){
        this.ruleForm={
          brandName:"",
          brandCode:""
        }
      }
    },
}
</script>
<style lang="scss">
.el-select{
  width:100%;
}
.demo-ruleForm{
  padding:0 80px 50px 80px;
}
</style>