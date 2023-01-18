<template>
  <div style="background:#fff;padding:10px;">
    <reloadAndsearch ref="search" :config="searchConfig" @search="search" :hidden="hidden" :hidden1="hidden" />
    <div class="list-model">
      <TableList :pageMethod="getTableData" :searchMethod="getTableData" :table-data="tableData"
        :multiCheck="multiCheck" :tableColumn="tableColumn" :query.sync="query" :total="total"
        :loading="loadings.table">
        <template v-slot:column-how="props">
          <span v-if="!props.row.countNum">-</span>
          <span v-else-if="props.row.accountNum == props.row.countNum">盘平</span>
          <!-- 实际清点数 > 账面库存 -->
          <span v-else-if="props.row.accountNum < props.row.countNum">盘盈</span>
          <!-- 实际清点数 < 账面库存 -->
          <span v-else-if="props.row.accountNum > props.row.countNum">盘亏</span>
        </template>
        <template v-slot:column-much="props">
          <span v-if="!props.row.countNum || props.row.accountNum == props.row.countNum"></span>
          <!-- 实际清点数 > 账面库存 -->
          <span v-else-if="props.row.accountNum < props.row.countNum">+{{
            props.row.countNum - props.row.accountNum
          }}</span>
          <!-- 实际清点数 < 账面库存 -->
          <span v-else-if="props.row.accountNum > props.row.countNum">-{{
            props.row.accountNum - props.row.countNum
          }}</span>
        </template>
        <template v-slot:column-todo="props">
          <el-button v-if="props.row.countCheckKey == '' && props.row.countNum != props.row.accountNum"
            @click="editRow1(props.row)" type="text" icon="el-icon-mouse">提交审核</el-button>
          <el-button @click="editRow(props.row)" type="text" icon="el-icon-notebook-2">设置盘点数</el-button>
          <!-- <el-button class="prohibitclick" @click="deleteRow(props.row)" type="text" size="small"
            icon="el-icon-document">删除</el-button> -->
        </template>
      </TableList>
    </div>
    <inventoryIntegrateEdit v-if="drawer" :drawer="drawer" :rowData="rowData" @close="drawer = false"
      @success="success()" :shopGoodsList="tableData" />
    <countCheckEdit v-if="drawer1" :drawer="drawer1" :rowData="rowData1" @close="drawer1 = false"
      @success="success()" />
  </div>
</template>

<script>
import { shopkeeperWarehouseListPage, shopkeeperWarehouseDelete, shopkeeperWarehouseDeleteList } from "@/api/warehouse";
import TableList from "@/components/public/tableList";
import reloadAndsearch from "@/components/public/reloadAndsearch/reloadAndsearch.vue";
import inventoryIntegrateEdit from "./inventoryIntegrateEdit";
import countCheckEdit from "./countCheckEdit";
import { shoplist, goodslist } from '@/api/data'
import { countCheckList } from '@/api/dataIntegrate'

export default {
  name: "slist",
  data() {
    return {
      pageSize: 10,
      pageNo: 1,
      total: null,
      drawer: false,
      drawer1: false,
      multiCheck: false,
      hidden: true,
      rowData: {},
      rowData1: {},
      tableData: [],
      multipleSelection: [],
      loadings: {
        table: true,
      },
      query: {
        pageNo: 1,
        pageSize: 10,
      },
      shopOptions: [],
      list: [],
      goodsOptions: []
    };
  },
  computed: {
    tableColumn() {
      return [
        { prop: "shopCode", label: "门店编码" },
        { prop: "shopName", label: "门店名称" },
        { prop: "goodsCode", label: "商品编码" },
        { prop: "goodsName", label: "商品名称" },
        { prop: "modelCode", label: "型号" },
        { prop: "inventoryCode", label: "仓库编码" },
        { prop: "positionCode", label: "货位编码" },
        { prop: "accountNum", label: "账面库存" },
        { prop: "countNum", label: "清点数" },
        { slots: { name: "column-how" }, label: "盘点情况" },
        { slots: { name: "column-much" }, label: "盈亏数量" },
        { prop: "description", label: "备注" },
        { slots: { name: "column-todo" }, label: "操作", fixed: "right", width: "250px" },
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
    inventoryIntegrateEdit,
    countCheckEdit,
    reloadAndsearch
  },
  created() {
    this.getcountCheckList()
    this.getshoplist()
    this.getgoodslist()
  },
  methods: {
    getcountCheckList() {
      countCheckList().then(res => {
        if (res.data.code == 200) {
          this.list = res.data.data
        } else {
          this.$message.error("获取失败!");
        }
      });
    },
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
      this.query.pageNo = pageNo;
      if (pageSize) {
        this.query.pageSize = pageSize;
      }
      this.loadings.table = true;

      let params = {
        // ...this.query,
        page: this.query.pageNo,
        size: this.query.pageSize,
        shopCode: "",
        goodsCode: "",
      };
      shopkeeperWarehouseListPage(params).then((res) => {
        if (res.data.code === 200) {
          this.total = res.data.data.total;
          this.tableData = res.data.data.records;
          if (this.list.length == 0) {
            this.tableData.forEach(item => {
              item.countCheckKey = ""
            })
          } else {
            this.tableData.forEach(item => {
              this.list.forEach(temp => {
                if (item.shopkeeperWarehouseKey == temp.shopkeeperWarehouseKey) {
                  item.countCheckKey = temp.countCheckKey
                } else {
                  item.countCheckKey = ""
                }
              })
            })
          }
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
          if (this.list.length == 0) {
            this.tableData.forEach(item => {
              item.countCheckKey = ""
            })
          } else {
            this.tableData.forEach(item => {
              this.list.forEach(temp => {
                if (item.shopkeeperWarehouseKey == temp.shopkeeperWarehouseKey) {
                  item.countCheckKey = temp.countCheckKey
                } else {
                  item.countCheckKey = ""
                }
              })
            })
          }
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
    editRow1(row) {
      //盘盈
      if (row.countNum > row.accountNum) {
        this.rowData1 = {
          shopkeeperWarehouseKey: row.shopkeeperWarehouseKey,
          checkNum: row.countNum - row.accountNum,
          checkType: 0,
        }
      } else if (row.countNum < row.accountNum) {//盘亏
        this.rowData1 = {
          shopkeeperWarehouseKey: row.shopkeeperWarehouseKey,
          checkNum: row.accountNum - row.countNum,
          checkType: 1,
        }
      }
      this.drawer1 = true;
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
    success() {
      this.drawer = false;
      this.rowData = {};
      this.drawer1 = false;
      this.rowData1 = {};
      this.getcountCheckList()
      this.getTableData();
      this.$forceUpdate()
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
