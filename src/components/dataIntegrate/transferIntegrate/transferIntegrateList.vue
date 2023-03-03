<template>
  <div style="background:#fff;padding:10px;">
    <reloadAndsearch ref="search" :config="searchConfig" @search="search" :hidden="hidden" :hidden1="hidden" />
    <div class="list-model">
      <TableList :pageMethod="getTableData" :searchMethod="getTableData" :table-data="tableData" :multiCheck="multiCheck"
        :tableColumn="tableColumn" :query.sync="query" :total="total" :loading="loadings.table">

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
import { transferIntegrateListPage } from '@/api/dataIntegrate'
import { goodslist, shoplist } from '@/api/data'

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
      InShopOptions: [],
      OutShopOptions: [],
    };
  },
  computed: {
    tableColumn() {
      return [
        { prop: "inShopCode", label: "调入门店编码" },
        { prop: "inShopName", label: "调入门店名称", sortable: true },
        { prop: "goodsCode", label: "商品编码" },
        { prop: "goodsName", label: "商品名称", sortable: true },
        { prop: "outShopCode", label: "调出门店编码", },
        { prop: "outShopName", label: "调出门店名称", sortable: true },
        { prop: "transferNum", label: "调货单数", sortable: true },
        { prop: "transferSum", label: "调货商品数", sortable: true },
        // { slots: { name: "column-todo" }, label: "操作", fixed: "right", width: "120px" },
      ];
    },
    searchConfig() {
      return [
        {
          label: '请选择',
          placeholder: '请选择调货入库门店',
          field: 'InShopCode',
          value: '',
          type: "select",
          options: this.InShopOptions
        },
        {
          label: '请选择',
          placeholder: '请选择商品',
          field: 'goodsCode',
          value: '',
          type: "select",
          options: this.goodsOptions
        },
        {
          label: '请选择',
          placeholder: '请选择调货出库门店',
          field: 'OutShopCode',
          value: '',
          type: "select",
          options: this.OutShopOptions
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
    this.getshoplist()
  },
  methods: {
    getshoplist() {
      shoplist().then(res => {
        this.InShopOptions = []
        this.OutShopOptions = []
        if (res.data.code == 200) {
          res.data.data.forEach(item => {
            this.InShopOptions.push({ label: item.shopName, value: item.shopCode })
            this.OutShopOptions.push({ label: item.shopName, value: item.shopCode })
          });
        } else {
          this.$message.error("获取失败!");
        }
      });
    },
    getgoodslist() {
      goodslist().then(res => {
        this.goodsOptions = []
        if (res.data.code == 200) {
          res.data.data.forEach(item => {
            this.goodsOptions.push({ label: item.goodsName, value: item.goodsCode })
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
        goodsCode: "",
        InShopCode: "",
        OutShopCode: ""
      };
      transferIntegrateListPage(params).then((res) => {
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
      transferIntegrateListPage({
        goodsCode: searchData.goodsCode,
        InShopCode: searchData.InShopCode,
        OutShopCode: searchData.OutShopCode,
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
