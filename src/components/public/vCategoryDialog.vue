<template>
  <el-dialog
    :title="ifCreate ? '创建分类' : '编辑'"
    :visible.sync="dialogVisible"
    width="30%"
    :closeOnClickModal="false"
    :before-close="handleClose">

    <el-form label-position="left" label-width="80px" :model="formData" ref="myform">
      <el-form-item label="分类名称"
      prop="categoryName"
      :rules="[{ required: true, message: '请输入分类名称', trigger: 'blur' } ]">
        <el-input v-model="formData.categoryName"></el-input>
      </el-form-item>
      <el-form-item label="父节点" v-model="formData.parentCategoryKey">
        <listBoxF>
          <template slot="content">
            <treeselect v-model="value" :multiple="multiple" :normalizer="normalizer" :options="list"
            placeholder="请选择" @select="selectNode"  style="width:455px;"/>
          </template>
        </listBoxF>
      </el-form-item>
      <el-form-item label="排序">
        <el-input v-model="formData.sort"></el-input>
      </el-form-item>
    </el-form>

    <span slot="footer" class="dialog-footer">
      <el-button @click="close()">取 消</el-button>
      <el-button type="primary"  @click="submit()" v-if="ifCreate">确 定</el-button>
      <el-button type="primary"  @click="save()" v-else>确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import listBoxF from './listBoxF/listBoxF.vue'
// import treeselect from './treeSelect'
import {getCategoryTree, addCategory, updateCategory} from '@/api/data'
 /**
   * 树形组件 用于选择框
   */
  // import the component
  import Treeselect from '@riophae/vue-treeselect'
  // import the styles
  import '@riophae/vue-treeselect/dist/vue-treeselect.css'

  export default {
    name:"vCategoryDialog",
    data() {
      return {
        list:[],
        // dialogVisible: false,
        ifCreate:true,
        formData:{
          categoryName:'',
          parentCategoryKey:'',
          categoryType:this.categoryType,
          sort:1,
          level: 1,
          categoryKey:''
        },
      }
    },
    props:{
      categoryType:String,
      dialogVisible:{
        type:Boolean,
        default:false
      },
      // list: { type: Array, default: () => [] }
       multiple:{
        type:Boolean,
        default:false
      },
      rowData:{}
    },
    watch:{
      // 监听选中值的变化
      value:{
        deep:true,
        handler(val){
          this.$emit('getSelectVal',val)
        }
      }
    },
    methods: {
      // 自定义参数键值名称
      normalizer(node){
        //去掉children=[]的children属性
        if(node.children && !node.children.length){
            delete node.children;
        }
        return {
          id: node.categoryKey,
          label: node.categoryName,
          children: node.children,
          level: node.level,
          isDisabled: node.categoryKey == this.formData.categoryKey ? true : false
        }
      },
      selectNode(node){
        this.formData.level=node.level+1
        this.formData.parentCategoryKey=node.categoryKey || ''
        this.value=node.categoryKey
        console.log("selectNode(node)：",this.formData)
      },
      handleClose(done) {
        this.$emit("close");
        this.reset()
      },
      reset(){
        this.formData={
          categoryName:'',
          parentCategoryKey:'',
          categoryType:this.categoryType,
          sort:1,
          level: 1,
          categoryKey:''
        }
        this.value=null
      },

      getTree(){
        let params = {
          categoryType: this.categoryType,
        }
        getCategoryTree(params).then(res=>{
          if (res.data.code === 200) {
          this.list = []
          res.data.data.forEach(item => {
            if(item.categoryName !='暂无分类'&&item.categoryName!='全部'){
                this.list.push(item)
              }
          });
          // this.list = res.data.data
          // console.log(this.list);
          }else{
            console.log('error');
          }
        })
        this.$forceUpdate()
      },

      submit(){
        if(!this.formData.categoryName){
            this.$message({
              type: 'warning',
              message: '请填写分类名称！ '
            })
        }else{
          this.createCategory()
          this.$parent.dialogVisible = false
        }
        // console.log(this.formData)
      },

      save(){
        this.updateCategory()
        this.$parent.dialogVisible = false
        // console.log(this.formData)
      },

      close(){
        this.$emit("close");
        this.reset()
      },

      //新增分类
      createCategory(){
        console.log("this.form-=====",this.formData)
        addCategory(this.formData).then(res=>{
          this.reset()
          if (res.data.code === 200) {
            this.$parent.$parent.getTree()
            // this.getTree()
            this.$emit("close");
            this.reset()
            this.$message({
              type: 'success',
              message: '添加成功！ '
            });
          }else{
            console.log('error');
            this.$message({
              type: 'warning',
              message: '添加失败！ '
            });
          }
        })
      },

      //编辑分类
     updateCategory(){
        console.log("this.form-=====",this.formData)
        updateCategory(this.formData).then(res=>{
          if (res.data.code === 200) {
            this.$parent.$parent.getTree()
            // this.getTree()
            this.$emit("close");
            this.reset()
            this.$message({
              type: 'success',
              message: '编辑成功！ '
            });
          }else{
            console.log('error');
            this.$message({
              type: 'warning',
              message: '编辑失败！ '
            });
          }
        })
      }
    },

    components:{
      listBoxF,
      Treeselect
    },
    created(){
      // console.log(this.value,this.formData)
      this.getTree()
      this.reset()
      if(this.rowData.categoryKey){
        this.ifCreate=false
        this.formData.categoryKey=this.rowData.categoryKey
        this.formData.categoryName=this.rowData.categoryName
        this.formData.level=this.rowData.level
        this.formData.parentCategoryKey=this.rowData.parentCategoryKey
        this.formData.sort=this.rowData.sort
      }else{
        this.reset()
      }

      if(this.formData.parentCategoryKey){
        this.value=this.formData.parentCategoryKey
      }else{
        this.value=null
      }
    }

  };
</script>