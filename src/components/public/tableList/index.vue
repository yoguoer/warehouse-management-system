<template>
  <div class="list-model">
    <el-table
      :data="tableData"
      :cell-style="{ padding: '5px' }"
      :header-cell-style="{background:'#F2F6FC',color:'#606266'}"
      v-loading="loading"
      tooltip-effect="dark"
      :height="height"
      style=" width: auto;margin-top: 20px"
      @selection-change="handleSelectionChange"
      border
    >
      <template><el-table-column type="selection" width="55" v-if="multiCheck"></el-table-column></template>
      <template v-for="(rowItem, rowIdx) in tableColumn">
        <template v-if="rowItem.children">
          <el-table-column :label="rowItem.label" :key="(rowItem.label + rowIdx)" :align="rowItem.align ? rowItem.align : 'center'">
            <template v-for="(rowItem1, rowIdx1) in rowItem.children">
              <el-table-column
                v-if="rowItem1.slots"
                :key="rowItem1.slots.name"
                :label="rowItem1.label"
                :width="rowItem1.width"
                :align="rowItem1.align"
                show-overflow-tooltip
                :fixed="rowItem.fixed"
              >
                <template slot-scope="scope">
                  <slot :name="rowItem1.slots.name" v-bind="scope"></slot>
                </template>
              </el-table-column>
              <el-table-column
                v-else
                :key="(rowItem1.label + rowIdx1)"
                :prop="rowItem1.prop"
                :label="rowItem1.label"
                :width="rowItem1.width"
                :align="rowItem1.align"
                :type="rowItem1.type"
                show-overflow-tooltip
                :fixed="rowItem.fixed"
              ></el-table-column>
            </template>
          </el-table-column>
        </template>
        <template v-else>
          <el-table-column
            v-if="rowItem.slots"
            :key="rowItem.slots.name"
            :label="rowItem.label"
            :width="rowItem.width"
            :align="rowItem.align"
            show-overflow-tooltip
            :fixed="rowItem.fixed"
          >
            <template slot-scope="scope">
              <slot :name="rowItem.slots.name" v-bind="scope"></slot>
            </template>
          </el-table-column>
          <el-table-column
            v-else
            :key="rowItem.label + rowIdx"
            :prop="rowItem.prop"
            :label="rowItem.label"
            :width="rowItem.width"
            :align="rowItem.align"
            :type="rowItem.type"
            show-overflow-tooltip
            :fixed="rowItem.fixed"
          ></el-table-column>
        </template>
      </template>
    </el-table>

    <el-pagination
      v-if="query && query.pageNo"
      @current-change="handleCurrentChange"
      @size-change="handleSizeChange"
      :current-page="query.pageNo"
      :page-size="query.pageSize"
      :page-sizes="[10, 20, 30]"
      layout="total,sizes, prev, pager, next, jumper"
      :total="total"
    >
    </el-pagination>
  </div>
</template>

<script>
export default {
  props: {
    // 列表数据
    tableData: { type: Array, default: () => [] },
    /** 示例
      tableColumn: [
        { prop: 'applyName', label: '角色名称', width: 180 },
        { prop: 'createTime', label: '更新时间', minWidth: 100 },
        { slots: { name: 'column-todo' }, label: '操作', width: 200 },
      ]
     */
    tableColumn: { type: Array, default: () => [] },
    total: Number, // 表格数据总条目数
    query: Object, // 表单对象 含分页数据
    pageMethod: Function, // 表格翻页方法
    searchMethod: Function, // 表格查询方法
    resetMethod: Function, // 表单重置方法
    loading: Boolean, // 查询loading
    multiCheck:{type:Boolean,default:true},//是否显示多选框
    height: { type: String, default: () => "600px" },
  },
  data() {
    return {
    };
  },
  created() {},
  methods: {
    // 设置每页数据大小
    handleSizeChange(size) {
      this.query.pageNo = 1;
      this.query.pageSize = size;
      console.log(this.query,'this.query');
      console.log(size);
      this.searchMethod && this.searchMethod();
    },
    // 点击切页
    handleCurrentChange(val) {
      console.log(val);
      this.pageMethod && this.pageMethod(val);
    },
    // 表格选择
    handleSelectionChange(val) {
      this.multipleSelection = val
      console.log(this.multipleSelection)
      this.$parent.multipleSelection=this.multipleSelection
    }
  },
};
</script>

<style lang="scss" scoped>
// 分页样式
/deep/.el-pagination {
  margin: 16px 0;
  text-align: right;
}

</style>
