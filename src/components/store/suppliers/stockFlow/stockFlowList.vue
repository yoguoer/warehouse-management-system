<template>
  <div style="background:#fff;padding:10px;">
    <reloadAndsearch ref="search" :config="searchConfig" @search="search" :hidden="hidden" :hidden1="hidden" />
    <div class="list-model">
      <TableList :pageMethod="getTableData" :searchMethod="getTableData" :table-data="tableData" :multiCheck="multiCheck"
        :tableColumn="tableColumn" :query.sync="query" :total="total" :loading="loadings.table">
        <template v-slot:column-status="props">
          <span>{{
            props.row.status == '0' ? '在单'
            : (props.row.status == '1' ? '生产'
              : (props.row.status == '2' ? '在途' : '已接收'))
          }}</span>
        </template>
        <template v-slot:column-createTime="props">
          <span v-if="props.row.createTime">{{ props.row.createTime | datefmt('YYYY-MM-DD HH:mm:ss') }}</span>
        </template>
        <template v-slot:column-deadlineTime="props">
          <span v-if="props.row.deadlineTime">{{ props.row.deadlineTime | datefmt('YYYY-MM-DD HH:mm:ss') }}</span>
        </template>
        <template v-slot:column-todo="props">
          <el-button type="text" style="visibility:hidden">空</el-button>
          <!-- <el-button v-if="props.row.returnReason&&!props.row.returnNum" @click="editRow(props.row)" type="text"
            icon="el-icon-s-check">审批</el-button> -->
          <el-button v-if="props.row.status < 1" @click="editRow1(props.row)" type="text"
            icon="el-icon-s-ticket">接收订单</el-button>
          <el-button v-if="props.row.status < 2" @click="editRow1(props.row)" type="text"
            icon="el-icon-s-promotion">发出货物</el-button>
        </template>
      </TableList>
    </div>
    <sReturnEdit v-if="drawer" :drawer="drawer" :rowData="rowData" @close="drawer = false" @success="success()" />
    <sInOrderEdit v-if="drawer1" :drawer="drawer1" :rowData="rowData1" @close="drawer1 = false" @success="success()" />
  </div>
</template>

<script>
import { inputWarehouseListPage, inputWarehouseDelete, inputWarehouseDeleteList, } from "@/api/purchasing";
import { returnCheckByKey } from "@/api/check";
import TableList from "@/components/public/tableList";
import reloadAndsearch from "@/components/public/reloadAndsearch/reloadAndsearch.vue";
import sReturnEdit from "@/components/store/suppliers/stockManagement/sReturn/sReturnEdit.vue";
import sInOrderEdit from "@/components/store/suppliers/stockManagement/sInOrder/sInOrderEdit.vue";
import { shoplist, goodslist, inventorylist, Supplierlist } from '@/api/data'

export default {
  name: "slist",
  data() {
    return {
      total: null,
      drawer: false,
      drawer1: false,
      hidden: true,
      multiCheck: false,
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
      userType: "",
      shopOptions: [],
      goodsOptions: [],
      inventoryOptions: [],
      supplierOptions: [],
      statusOptions: [
        { label: "在单", value: 0 },
        { label: "生产", value: 1 },
        { label: "在途", value: 2 }],
      //   typeOptions:[
      //     {label:"采购入库",value:0},
      //     {label:"调货入库",value:1}]
    };
  },
  computed: {
    tableColumn() {
      return [
      { prop: "shopCode", label: "门店编码", width: "100px" },
        { prop: "shopName", label: "门店名称", width: "200px", sortable: true },
        { prop: "goodsCode", label: "商品编码", width: "100px" },
        { prop: "goodsName", label: "商品名称", width: "200px", sortable: true },
        { prop: "supplierCode", label: "供应商编码", width: "100px" },
        { prop: "supplierName", label: "供应商名称", width: "200px", sortable: true },
        { prop: "inputPlan", label: "计划数" },
        { prop: "inputPrice", label: "订购价格" },
        { prop: "inputActual", label: "实际数" },
        // { prop: "inventoryCode", label: "仓库编码" },
        // { prop: "positionCode", label: "货位编码" },
        { slots: { name: "column-status" }, label: "状态" },
        { prop: "vehicleCode", label: "车辆编码" },
        { slots: { name: "column-createTime" }, label: "预计日期", sortable: true , width: "150px"},
        { slots: { name: "column-deadlineTime" }, label: "最迟日期", sortable: true, width: "150px" },
        { prop: "returnNum", label: "退货数" },
        // { prop: "shopPeopleCode", label: "门店操作员" },
        // { prop: "inventoryPeopleCode", label: "仓库操作员" },
        { prop: "returnReason", label: "退货原因" },
        { slots: { name: "column-todo" }, label: "操作", fixed: "right", width: 250 },
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
        {
          label: '请选择',
          placeholder: '请选择供应商',
          field: 'supplierCode',
          value: '',
          type: "select",
          options: this.supplierOptions
        },
        {
          label: '请选择',
          placeholder: '请选择状态',
          field: 'status',
          value: '',
          type: "select",
          options: this.statusOptions
        },
      ];
    }
  },
  props: {
  },
  components: {
    TableList,
    sReturnEdit,
    sInOrderEdit,
    reloadAndsearch
  },
  created() {
    this.getshoplist()
    this.getgoodslist()
    this.getSupplierlist()
    this.getinventorylist();
    let user = JSON.parse(localStorage.getItem("userInfo"))
    this.userType = user.userType
  },
  methods: {
    getSupplierlist() {
      Supplierlist().then(res => {
        if (res.data.code == 200) {
          this.supplierOptions = []
          res.data.data.forEach(item => {
            this.supplierOptions.push({ label: item.supplierName, value: item.supplierCode })
          });
        } else {
          this.$message.error("获取失败!");
        }
      });
    },
    getinventorylist() {
      inventorylist()
        .then((res) => {
          if (res.data.code === 200) {
            this.inventoryOptions = []
            res.data.data.forEach(item => {
              if (item.inventoryType == '2' && item.belongKey != null || item.belongKey != "") {
                this.inventoryOptions.push({ label: item.inventoryName, value: item.inventoryCode })
              }
            });
          } else {
            this.$message.error(res.msg);
          }
        })
        .catch((e) => {
          console.log(e);
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
        page: this.query.pageNo,
        size: this.query.pageSize,
        shopCode: "",
        goodsCode: "",
        supplierCode: "",
        inventoryCode: "",
        status: "",
        isDeleted: 0,
        type: ""
      };
      inputWarehouseListPage(params).then((res) => {
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
      inputWarehouseListPage({
        page: this.query.pageNo,
        size: this.query.pageSize,
        shopCode: searchData.shopCode,
        goodsCode: searchData.goodsCode,
        supplierCode: searchData.supplierCode,
        inventoryCode: "",
        status: searchData.status,
        isDeleted: 0,
        type: ""
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
    editRow1(row) {
      this.rowData1 = row;
      this.drawer1 = true;
    },
    editRow(row) {
      returnCheckByKey({ checkType: 0, inputOutputKey: row.inputWarehouseKey }).then(res => {
        if (res.data.code == 200) {
          this.rowData = res.data.data
          this.drawer = true;
        } else {
          this.$message.error("获取失败!");
        }
      })
    },
    deleteRow(row) {
      // console.log("deleteRow", row)
      this.$confirm('删除操作, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        inputWarehouseDelete({ inputWarehouseKey: row.inputWarehouseKey, isDeleted: 1 }).then(res => {
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
      this.drawer1 = false;
      this.rowData1 = {};
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
        let inputWarehouseKeys = [];
        this.multipleSelection.forEach(item => {
          inputWarehouseKeys.push({ inputWarehouseKey: item.inputWarehouseKey })
        })
        console.log(inputWarehouseKeys);
        this.$confirm('删除操作, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          inputWarehouseDeleteList(inputWarehouseKeys).then(() => {
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
