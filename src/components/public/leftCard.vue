<template>
  <el-card class="box-card">
    <div slot="header" class="clearfix">
      <span style="margin-right:150px;">{{ title }}</span>
      <!-- <el-button style=" padding: 3px 0;color:grey" type="text" @click="multiEdit()"><i class="el-icon-edit"></i></el-button> -->
      <el-button style="padding: 3px 0;color:grey" type="text" @click="add()"><i class="el-icon-plus"></i></el-button>
      <!-- <el-button style="padding: 3px 0;color:grey" type="text" @click="multiRemove()"><i class="el-icon-close"></i></el-button> -->
    </div>
    <!-- <el-tree :data="data" :props="defaultProps" @node-click="handleNodeClick"></el-tree> -->
    <div class="block">
      <el-tree :data="data" ref="tree" node-key="id" :check-strictly="true" :props="defaultProps"
        :expand-on-click-node="false" :default-expand-all="true" @node-click="handleNodeClick">
        <span class="custom-tree-node" slot-scope="{ node, data }">
          <span :class="isActive == node.label ? 'custom-tree-node-active' : 'custom-tree-node'">{{ node.label }}</span>
          <span v-if="node.label != '全部'">
            <el-button type="text" size="small" @click="() => edit(data)">
              编辑
            </el-button>
            <el-button type="text" size="small" @click="() => remove(data)">
              删除
            </el-button>
          </span>
        </span>
      </el-tree>
    </div>
    <vCategoryDialog ref="dialog" :ifCreate="ifCreate" :categoryType="categoryType" />
  </el-card>
</template>

<script>
import { getCategoryTree, deleteCategory } from '@/api/data'
import vCategoryDialog from './vCategoryDialog.vue'

export default {
  name: 'businessEntity',
  data() {
    return {
      data: [],
      list: [],
      isActive: "",
      ifCreate: false,
      defaultProps: {
        id: 'categoryKey',
        children: 'children',
        label: 'categoryName',
      },
      categoryKeyList: [],
      CheckedNodesValue: [],
    }
  },
  props: {
    title: String,
    categoryType: String,
  },
  methods: {
    getTree() {
      let params = {
        categoryType: this.categoryType,
      }
      getCategoryTree(params).then(res => {
        if (res.data.code === 200) {
          this.data = res.data.data
        } else {
          console.log('error');
        }
      })
      // console.log("树状data",this.data)
    },

    //删除一个
    remove(data) {
      this.$confirm('确认删除？')
        .then(_ => {
          let params = { categroyKeys: data.categoryKey }
          console.log(this.categoryKeyList)
          if (data.children.length > 0) {
            this.$message({
              message: '含有子节点，不允许删除！',
              type: 'warning'
            });
          } else {
            deleteCategory(params).then(res => {
              if (res.data.code === 200) {
                this.getTree()
                this.$refs.dialog.getTree()
                console.log("删除")
              } else {
                console.log('error');
              }
            })
          }
        })
        .catch(_ => { });
    },

    //点击分类，查询对应信息
    handleNodeClick(data) {
      this.$parent.inputCategory = data.categoryKey
      this.isActive = data.categoryName
      this.$parent.search()
    },

    //添加
    add() {
      this.ifCreate = true
      this.$refs.dialog.dialogVisible = true
      // console.log("新增",this.$refs.dialog.formData)
    },

    //编辑
    edit(data) {
      this.ifCreate = false
      this.$refs.dialog.dialogVisible = true
      this.$refs.dialog.formData.categoryName = data.categoryName
      this.$refs.dialog.formData.parentCategoryKey = data.parentCategoryKey
      this.$refs.dialog.value = data.parentCategoryKey || null
      this.$refs.dialog.formData.categoryKey = data.categoryKey
      this.$refs.dialog.formData.level = data.level
      // console.log("编辑",this.$refs.dialog.formData)
    },

    // //选中
    handleCheckChange() {
      //   let res = this.$refs.tree.getCheckedNodes(true,true); //这里两个true，1. 是否只是叶子节点 2. 是否包含半选节点（就是使得选择的时候不包含父节点）
      //   console.log('选中:',res)
      //   let arrLabel = [];
      //   let arr = [];
      //   //生成一个选中列表
      //   res.forEach(item => {
      //     arrLabel.push(item.categoryKey);//key
      //     arr.push(item);//整个值
      //   });
      //   this.categoryKeyList = String(arrLabel);
      //   this.CheckedNodesValue = JSON.stringify(arr);
      //   console.log('选中结点的完整信息CheckedNodesValue:',this.CheckedNodesValue)
      //   console.log('选中结点的编号categoryKeyList:',this.categoryKeyList)
    }
    // //删除多个
    // multiRemove(){
    //   console.log(this.categoryKeyList)
    //   if(this.categoryKeyList.length==0){
    //     this.$message({
    //       message: '请先选择结点！',
    //       type: 'warning'
    //     });
    //   }else{
    //    let params = { categroyKeys: this.categoryKeyList }
    //     console.log(params)
    //     deleteCategory(params).then(res=>{
    //       if (res.code === 200) {
    //         this.getTree()
    //         this.$refs.dialog.getList()
    //         console.log("删除")
    //       }else{
    //         console.log('error');
    //       }
    //     })
    //   }
    // },
  },
  watch: {},
  created() {
    this.getTree()
  },
  computed: {
  },
  mounted() {
  },
  beforeDestroy() {
  },
  components: {
    vCategoryDialog
  }
}
</script>

<style lang="scss" rel="stylesheet/scss" scoped>
.box-card {
  width: 300px;
  height: 550px;
  margin: 0 20px 20px 0;
  float: left;
}

.custom-tree-node,
.custom-tree-node-active {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}

.custom-tree-node-active {
  background: #f5f7fa;
  padding: 2px;
  width: 100%;
  color: #07a;
}
</style>
