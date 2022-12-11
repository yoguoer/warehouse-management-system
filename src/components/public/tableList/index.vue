<template>
  <div>
    <el-table
      :data="tableData"
      :header-cell-style="{ background: '#fafafa' }"
      v-loading="loading"
      tooltip-effect="dark"
      size="small"
      style="width: 100%"
      border
    >
      <template v-for="(rowItem, rowIdx) of tableColumn">
        <el-table-column
          v-if="rowItem.slots"
          :key="rowIdx"
          :label="rowItem.label"
          :width="rowItem.width"
          :align="rowItem.align"
          show-overflow-tooltip
        >
          <template slot-scope="scope">
            <slot :name="rowItem.slots.name" v-bind="scope"></slot>
          </template>
        </el-table-column>
        <el-table-column
          v-else
          :key="rowIdx"
          :prop="rowItem.prop"
          :label="rowItem.label"
          :width="rowItem.width"
          :align="rowItem.align"
          show-overflow-tooltip
        ></el-table-column>
      </template>
    </el-table>

    <el-pagination
      v-if="query && query.pageNo"
      @current-change="handleCurrentChange"
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
      this.searchMethod && this.searchMethod();
    },
    // 点击切页
    handleCurrentChange(val) {
      console.log(val);
      this.pageMethod && this.pageMethod(val);
    },
  },
};
</script>

<style lang="scss" scoped>
.tree-menu {
  height: calc(100% - 50px);
}
/deep/.ans-table .table-header-row {
  background: none;
}
.menu /deep/.el-table .el-table__cell {
  padding: 4px 0;
}
.menu /deep/.el-table th.el-table__cell > .cell {
  font-size: 12px;
  color: #555;
  border-top: 1px solid #ebeef5;
  padding-top: 10px;
}
.menu /deep/.el-table td.el-table__cell > .cell {
  font-size: 12px;
  color: #666;
}
// 分页样式
/deep/.el-pagination {
  margin: 16px 0;
  text-align: right;
}
</style>
