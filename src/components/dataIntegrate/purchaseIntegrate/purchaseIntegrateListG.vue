<template>
  <div style="background:#fff;padding:10px;">
    <reloadAndsearch ref="search" :config="searchConfig" @search="search" :hidden="hidden" :hidden1="hidden" />
    <div class="list-model">
      <TableList :pageMethod="getTableData" :searchMethod="getTableData" :table-data="tableData" :multiCheck="multiCheck"
        :tableColumn="tableColumn" :query.sync="query" :total="total" :loading="loadings.table">
        <template v-slot:column-num="props">
          <span>{{ props.row.inOrderNum + props.row.productNum + props.row.onWayNum + props.row.inStoreSum }}</span>
        </template>
        <template v-slot:column-sum="props">
          <span>{{ props.row.inOrderSum + props.row.productSum + props.row.onWaySum + props.row.inStoreSum }}</span>
        </template>
        <template v-slot:column-rate="props">
          <span v-if="props.row.returnSum && props.row.inStoreSum">{{ ((props.row.returnSum / props.row.inStoreSum) *
            100).toFixed(2) }}%</span>
        </template>
        <!-- <template v-slot:column-todo="props">
          <el-button type="text" style="visibility:hidden">空</el-button>
          <el-button v-if="props.row.checkStatus != 1" @click="editRow(props.row)" type="text" icon="el-icon-s-check">审批</el-button>
          <el-button class="prohibitclick" @click="deleteRow(props.row)" type="text" size="small"
            icon="el-icon-document">删除</el-button>
        </template> -->
      </TableList>
    </div>
  </div>
</template>

<script>
import TableList from "@/components/public/tableList";
import reloadAndsearch from "@/components/public/reloadAndsearch/reloadAndsearch.vue";
import { purchaseIntegrateListPage } from '@/api/dataIntegrate'
import { goodslist } from '@/api/data'

export default {
  name: "slist",
  data() {
    return {
      pageSize: 10,
      pageNo: 1,
      total: null,
      drawer: false,
      multiCheck: false,
      hidden: true,
      rowData: {},
      tableData: [],
      multipleSelection: [],
      loadings: {
        table: true,
      },
      query: {
        pageNo: 1,
        pageSize: 10,
      },
      goodsOptions: [],
    };
  },
  computed: {
    tableColumn() {
      return [
        { prop: "goodsCode", label: "商品编码" },
        { prop: "goodsName", label: "商品名称", width: "300px", sortable: true },
        { slots: { name: "column-num" }, label: "总单数(不含退货)", width: "160px", sortable: true },
        { slots: { name: "column-sum" }, label: "总数量(不含退货)", width: "160px", sortable: true },
        { prop: "inputPrice", label: "入库价格", width: "120px", sortable: true },
        { prop: "inOrderNum", label: "在单订单", width: "120px", sortable: true },
        { prop: "inOrderSum", label: "在单数量", width: "120px", sortable: true },
        { prop: "productNum", label: "生产订单", width: "120px", sortable: true },
        { prop: "productSum", label: "生产数量", width: "120px", sortable: true },
        { prop: "onWayNum", label: "在途订单", width: "120px", sortable: true },
        { prop: "onWaySum", label: "在途数量", width: "120px", sortable: true },
        { prop: "inStoreNum", label: "入库订单", width: "120px", sortable: true },
        { prop: "inStoreSum", label: "入库数量", width: "120px", sortable: true },
        { prop: "returnCount", label: "有退货订单", width: "120px", sortable: true },
        { prop: "returnSum", label: "退货数量",width: "120px", sortable: true },
        { slots: { name: "column-rate" }, label: "退货率",width: "120px", sortable: true },
        // { slots: { name: "column-todo" }, label: "操作", fixed: "right", width: "120px" },
      ];
    },
    searchConfig() {
      return [
        {
          label: '请选择',
          placeholder: '请选择商品',
          field: 'goodsCode',
          value: '',
          type: "select",
          options: this.goodsOptions
        },
      ];
    }
  },
  props: {
  },
  components: {
    TableList,
    reloadAndsearch
  },
  created() {
    this.getgoodslist()
  },
  methods: {
    getgoodslist() {
      goodslist().then(res => {
        if (res.data.code == 200) {
          this.goodsOptions = []
          res.data.data.forEach(item => {
            this.goodsOptions.push({ label: item.goodsName, value: item.goodsCode })
          })
        } else {
          this.$message.error("获取失败!");
        }
      });
    },
    getTableData(pageNo = 1, pageSize) {
      this.query.pageNo = pageNo;
      if (pageSize) {
        this.query.pageSize = pageSize;
      }
      this.loadings.table = true;

      let params = {
        // ...this.query,
        page: this.query.pageNo,
        size: this.query.pageSize,
        goodsCode: "",
      };
      purchaseIntegrateListPage(params).then((res) => {
        if (res.data.code === 200) {
          this.total = res.data.data.total;
          this.tableData = res.data.data.records;
          // console.log(this.total, this.tableData);
        } else {
          console.log("error");
        }
      })
        .finally(() => {
          this.loadings.table = false;
        });
    },
    search(pageNo = 1, pageSize) {
      this.query.pageNo = pageNo;
      if (pageSize) {
        this.query.pageSize = pageSize;
      }
      const searchData = this.$refs.search.search
      purchaseIntegrateListPage({
        goodsCode: searchData.goodsCode || "",
        page: this.query.pageNo,
        size: this.query.pageSize,
      }).then((res) => {
        if (res.data.code === 200) {
          this.total = res.data.data.total;
          this.tableData = res.data.data.records;
          // console.log(this.total, this.tableData);
        } else {
          console.log("error");
        }
      })
        .finally(() => {
          this.loadings.table = false;
        });
    },
    deleteRow(row) {
      // console.log("deleteRow", row)
      this.$confirm('删除操作, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        shopkeeperWarehouseDelete({ belongKey: row.belongKey, inventoryKey: row.inventoryKey }).then(res => {
          if (res.data.code == 200) {
            this.$message.success("删除成功!");
            this.getTableData()
            this.$forceUpdate()
          } else {
            this.$message.error("删除失败!");
          }
        });

      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    reload() {
      this.getTableData()
    },
  },
};
</script>
<style lang="scss" scoped>
.page-box {
  text-align: right;
  margin: 20px;
}

.el-table {
  overflow-y: auto !important;
  overflow-x: auto !important;
}

.el-input-group {
  width: 300px;
  margin-right: 20px;
}

.list-model {
  margin-top: 20px;
}
</style>
