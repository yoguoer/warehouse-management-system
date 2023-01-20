<template>
  <div style="background:#fff;padding:10px;">
    <reloadAndsearch ref="search" :config="searchConfig" @search="search" :hidden="hidden" :hidden1="hidden" />
    <div class="list-model">
      <TableList :pageMethod="getTableData" :searchMethod="getTableData" :table-data="tableData"
        :multiCheck="multiCheck" :tableColumn="tableColumn" :query.sync="query" :total="total"
        :loading="loadings.table">
        <template v-slot:column-num="props">
          <span>{{props.row.inOrderNum+props.row.productNum+props.row.onWayNum+props.row.inStoreSum}}</span>
        </template>
        <template v-slot:column-sum="props">
          <span>{{props.row.inOrderSum+props.row.productSum+props.row.onWaySum+props.row.inStoreSum}}</span>
        </template>
        <template v-slot:column-rate="props">
          <span v-if="props.row.returnSum&&props.row.inStoreSum">{{((props.row.returnSum/props.row.inStoreSum)*100).toFixed(2)}}%</span>
        </template>
        <!-- <template v-slot:column-todo="props">
          <el-button type="text" style="visibility:hidden"></el-button>
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
import { Supplierlist } from '@/api/data'

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
      supplierOptions: [],
    };
  },
  computed: {
    tableColumn() {
      return [
        { prop: "supplierCode", label: "供应商编码" },
        { prop: "supplierName", label: "供应商名称" },
        { slots: { name: "column-num" }, label: "总单数(不含退货)" },
        { slots: { name: "column-sum" }, label: "总数量(不含退货)" },
        { prop: "inOrderNum", label: "在单订单" },
        { prop: "inOrderSum", label: "在单数量" },
        { prop: "productNum", label: "生产订单" },
        { prop: "productSum", label: "生产数量" },
        { prop: "onWayNum", label: "在途订单" },
        { prop: "onWaySum", label: "在途数量" },
        { prop: "inStoreNum", label: "入库订单" },
        { prop: "inStoreSum", label: "入库数量" },
        { prop: "returnCount", label: "有退货订单" },
        { prop: "returnSum", label: "退货数量" },
        { slots: { name: "column-rate" }, label: "退货率" },
        // { slots: { name: "column-todo" }, label: "操作", fixed: "right", width: "120px" },
      ];
    },
    searchConfig() {
      return [
        {
          label: '请选择',
          placeholder: '请选择供应商',
          field: 'supplierCode',
          value: '',
          type: "select",
          options: this.supplierOptions
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
    this.getSupplierlist()
  },
  methods: {
    getSupplierlist(){
      Supplierlist().then(res => {
        if (res.data.code == 200) {
          // this.supplierOptions = res.data.data
          this.supplierOptions = []
          res.data.data.forEach(item => {
            this.supplierOptions.push({ label: item.supplierName, value: item.supplierCode })
          });
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
        supplierCode: "",
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
        supplierCode: searchData.supplierCode||"",
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
      console.log("deleteRow", row)
      shopkeeperWarehouseDelete({ belongKey: row.belongKey, inventoryKey: row.inventoryKey }).then(res => {
        if (res.data.code == 200) {
          this.$message.success("删除成功!");
          this.getTableData()
          this.$forceUpdate()
        } else {
          this.$message.error("删除失败!");
        }
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
