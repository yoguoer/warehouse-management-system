<template>
  <div style="background:#fff;padding:10px;">
    <reloadAndsearch ref="search" :config="searchConfig" @search="search" />
    <div class="list-model">
      <TableList :pageMethod="getTableData" :searchMethod="getTableData" :table-data="tableData"
        :tableColumn="tableColumn" :query.sync="query" :total="total" :loading="loadings.table">
        <template v-slot:column-inv="props">
          <span v-if="props.row.positionCode == '00000000000000000'">-</span>
          <span v-else>{{ props.row.inventoryCode }}{{ props.row.inventoryName }}</span>
        </template>
        <template v-slot:column-pop="props">
          <span v-if="props.row.positionCode == '00000000000000000'">-</span>
          <span v-else>{{ props.row.positionCode }}</span>
        </template>
        <template v-slot:column-time="props">
          <span>{{ props.row.operateTime }}</span>
        </template>
        <template v-slot:column-ok="props">
          <span v-if="props.row.rejectsNum">{{
            ((props.row.accountNum - props.row.rejectsNum) / props.row.accountNum).toFixed(2) * 100 }}%</span>
        </template>
        <template v-slot:column-todo="props" v-if="userType<2">
          <el-button @click="editRow(props.row)" type="text" icon="el-icon-edit">编辑</el-button>
          <el-button class="prohibitclick" @click="deleteRow(props.row)" type="text" size="small"
            icon="el-icon-document">删除</el-button>
        </template>
      </TableList>
    </div>
    <overviewEdit v-if="drawer" :drawer="drawer" :rowData="rowData" @close="drawer = false" @success="success()" />
  </div>
</template>

<script>
import { shopkeeperWarehouseListPage, shopkeeperWarehouseDelete, shopkeeperWarehouseDeleteList } from "@/api/warehouse";
import TableList from "@/components/public/tableList";
import reloadAndsearch from "@/components/public/reloadAndsearch/reloadAndsearch.vue";
import overviewEdit from "./overviewEdit";
import { shoplist, goodslist } from '@/api/data'

export default {
  name: "slist",
  data() {
    return {
      pageSize: 10,
      pageNo: 1,
      total: null,
      drawer: false,
      rowData: {},
      tableData: [],
      userType:"",
      multipleSelection: [],
      loadings: {
        table: true,
      },
      query: {
        pageNo: 1,
        pageSize: 10,
      },
      shopOptions: [],
      goodsOptions: []
    };
  },
  computed: {
    tableColumn() {
      return [
        { prop: "shopCode", label: "门店编码",width:"100px" },
        { prop: "shopName", label: "门店名称",width:"200px", sortable: true },
        { prop: "goodsCode", label: "商品编码",width:"100px" },
        { prop: "goodsName", label: "商品名称",width:"200px", sortable: true },
        { prop: "maxNum", label: "库存上限", sortable: true,width:"120px" },
        { prop: "minNum", label: "库存下限", sortable: true,width:"120px" },
        { prop: "accountNum", label: "现存量", sortable: true,width:"100px" },
        { prop: "onwayNum", label: "在途数", sortable: true,width:"100px" },
        { prop: "occupyNum", label: "占用数", sortable: true,width:"100px" },
        { prop: "availableNum", label: "可用数", sortable: true,width:"100px" },
        { prop: "modelCode", label: "型号" },
        { prop: "priceLatestPurchase", label: "采购价", sortable: true,width:"100px" },
        // { prop: "inventoryCode", label: "仓库编码" },
        { slots: { name: "column-inv" }, label: "仓库" },
        { slots: { name: "column-pop" }, label: "货位" },
        // { prop: "positionCode", label: "货位" },
        { prop: "rejectsNum", label: "残品数", sortable: true,width:"100px" },
        { slots: { name: "column-ok" }, label: "合格率", sortable: true,width:"100px" },
        { prop: "longestDay", label: "最长未动销天数", sortable: true,width:"100px" },
        { slots: { name: "column-time" }, label: "最后操作时间",width:"150px", sortable: true },
        { prop: "description", label: "备注" },
        { slots: { name: "column-todo" }, label: "操作", fixed: "right", width: "150px" },
      ];
    },
    searchConfig() {
      return [
        {
          label: '请选择',
          placeholder: '请选择门店',
          field: 'shopCode',
          value: '',
          type: "select",
          options: this.shopOptions
        },
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
    overviewEdit,
    reloadAndsearch
  },
  created() {
    this.userType = this.$store.state.user.userType
    this.getshoplist()
    this.getgoodslist()
  },
  methods: {
    getshoplist() {
      shoplist().then(res => {
        if (res.data.code == 200) {
          // this.shopOptions = res.data.data
          res.data.data.forEach(item => {
            this.shopOptions.push({ label: item.shopName, value: item.shopCode })
          })
        } else {
          this.$message.error("获取失败!");
        }
      });
    },
    getgoodslist() {
      goodslist().then(res => {
        if (res.data.code == 200) {
          // this.goodsOptions = res.data.data
          res.data.data.forEach(item => {
            this.goodsOptions.push({ label: item.goodsName, value: item.goodsCode })
          })
        } else {
          this.$message.error("获取失败!");
        }
      });
    },
    getTableData(pageNo = 1, pageSize) {
      // this.query.pageNo = pageNo;
      if (pageSize) {
        this.query.pageSize = pageSize;
      }
      this.loadings.table = true;

      let params = {
        // ...this.query,
        page: this.query.pageNo || pageNo,
        size: this.query.pageSize,
        shopCode: "",
        goodsCode: "",
      };
      shopkeeperWarehouseListPage(params).then((res) => {
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
      shopkeeperWarehouseListPage({
        ...searchData,
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
    editRow(row) {
      this.rowData = row;
      this.drawer = true;
    },
    deleteRow(row) {
      // console.log("deleteRow", row)
      this.$confirm('删除操作, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        shopkeeperWarehouseDelete({ shopkeeperWarehouseKey: row.shopkeeperWarehouseKey }).then(res => {
          if (res.data.code == 200) {
            this.$message.success("删除成功!");
            this.getTableData(this.query.pageNo)
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
    success() {
      this.drawer = false;
      this.rowData = {};
      this.getTableData(this.query.pageNo)
    },
    reload() {
      this.getTableData()
    },
    add() {
      this.rowData = {}
      this.drawer = true
    },
    //批量删除选择
    handleSelectionDelete(val) {
      this.multipleSelection = val
    },
    handleDeleteList() {
      if (this.multipleSelection.length > 0) {
        let shopkeeperWarehouseKeys = [];
        this.multipleSelection.forEach(item => {
          shopkeeperWarehouseKeys.push({ shopkeeperWarehouseKey: item.shopkeeperWarehouseKey })
        })
        console.log(shopkeeperWarehouseKeys);
        this.$confirm('删除操作, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          shopkeeperWarehouseDeleteList(shopkeeperWarehouseKeys).then(() => {
            this.getTableData();
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
          }).catch(error => {
            console.log(error);
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
          this.multipleSelection = []
        });
      } else {
        this.$message({
          type: 'error',
          message: '至少选择一项'
        });
      }
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
  