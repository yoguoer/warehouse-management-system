<template>
  <el-card class="box-card">
    <div slot="header" class="clearfix">
      <span style="margin-right:150px;">{{ title }}</span>
      <el-button style="padding: 3px 0;color:grey" type="text" @click="add()"><i class="el-icon-plus"></i></el-button>
    </div>
    <div class="block">
      <el-tree :data="data" ref="tree" node-key="id" :check-strictly="true" :props="defaultProps"
        :highlight-current="true" :default-expand-all="false" @node-click="handleNodeClick"
        :expand-on-click-node="false">
        <span class="custom-tree-node" slot-scope="{ node, data }">
          <span :class="isActive == node.label ? 'custom-tree-node-active' : 'custom-tree-node'">{{ node.label }}</span>
          <span v-if="node.label !== '全部'">
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
    <vCategoryDialog ref="dialog" v-if="dialogVisible" :dialogVisible="dialogVisible" :rowData="rowData"
      :categoryType="categoryType" @close="dialogVisible = false" />
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
      dialogVisible: false,
      rowData: {},
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
    },

    //删除一个
    remove(data) {
      this.$confirm('确认删除？')
        .then(_ => {
          let params = { categroyKeys: data.categoryKey }
          console.log(this.categoryKeyList)
          if (data.children) {
            this.$message({
              message: '含有子节点，不允许删除！',
              type: 'warning'
            });
          } else {
            deleteCategory(params).then(res => {
              if (res.data.code === 200) {
                this.getTree()
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
      if (this.dialogVisible == false) {
        this.$parent.inputCategory = data.categoryKey
        this.isActive = data.categoryName
        this.$parent.search()
      }
    },

    //添加
    add() {
      this.dialogVisible = true
      this.rowData = {}
    },

    //编辑
    edit(data) {
      this.dialogVisible = true
      this.rowData = data
    },

    // //选中
    handleCheckChange() {

    }

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
  overflow: auto !important;
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
