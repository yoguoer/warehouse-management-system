<template>
  <div style="background:#fff;padding:10px;">
    <reloadAndsearch ref="search" :config="searchConfig" @search="search" :hidden="hidden" />
    <div class="list-model">
      <TableList :pageMethod="getTableData" :searchMethod="getTableData" :table-data="tableData"
        :tableColumn="tableColumn" :query.sync="query" :total="total" :loading="loadings.table">
        <template v-slot:column-status="props">
          <span>{{
            props.row.status == '0' ? '在单'
            : (props.row.status == '1' ? '生产'
              : (props.row.status == '2' ? '在途'
                : (props.row.status == '3' ? '入库'
                  : (props.row.status == '4' ? '占用'
                    : (props.row.status == '5' ? '出库' : '-')))))
          }}</span>
        </template>
        <template v-slot:column-type="props">
          <span v-if="props.row.type == 0">零售</span>
          <span v-if="props.row.type == 1">客户订购</span>
          <span v-if="props.row.type == 2">调货出库</span>
        </template>
        <template v-slot:column-createTime="props">
          <span v-if="props.row.createTime">{{ props.row.createTime | datefmt('YYYY-MM-DD HH:mm:ss') }}</span>
        </template>
        <template v-slot:column-deadlineTime="props">
          <span v-if="props.row.deadlineTime">{{ props.row.deadlineTime | datefmt('YYYY-MM-DD HH:mm:ss') }}</span>
        </template>
        <template v-slot:column-isDeleted="props">
          <span>{{ props.row.isDeleted == '0' ? '否' : (props.row.isDeleted == '1' ? '是' : '-') }}</span>
        </template>
        <template v-slot:column-todo="props">
          <!-- <el-button v-if="userType == 0&&props.row.isDeleted==0" @click="editRow(props.row)" type="text"
            icon="el-icon-edit">编辑</el-button> -->
          <el-button v-if="userType == 0" class="prohibitclick" @click="deleteRow(props.row)" type="text" size="small"
            icon="el-icon-document">删除</el-button>
        </template>
      </TableList>
    </div>
    <detailsEdit v-if="drawer" :drawer="drawer" :rowData="rowData" @close="drawer = false" @success="success()" />
  </div>
</template>

<script>
import { outputWarehouseListPage, outputWarehouseDelete, outputWarehouseDeleteList } from "@/api/marketing";
import TableList from "@/components/public/tableList";
import reloadAndsearch from "@/components/public/reloadAndsearch/reloadAndsearch.vue";
import detailsEdit from "./detailsEdit";
import { shoplist, goodslist, inventorylist, CustomerList } from '@/api/data'

export default {
  name: "slist",
  data() {
    return {
      total: null,
      drawer: false,
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
      userType: "",
      shopOptions: [],
      goodsOptions: [],
      inventoryOptions: [],
      customerOptions: [],
      statusOptions: [
        { label: "占用", value: 4 },
        { label: "出库", value: 5 }],
      typeOptions: [
        { label: "零售出库", value: 0 },
        { label: "客户订购出库", value: 1 },
        { label: "调货出库", value: 2 },
      ],
      deletedOptions: [
        { label: "否", value: 0 },
        { label: "是", value: 1 }
      ]
    };
  },
  computed: {
    tableColumn() {
      return [
        { prop: "shopCode", label: "门店编码" },
        { prop: "shopName", label: "门店名称", width: "200px", sortable: true },
        { prop: "goodsCode", label: "商品编码" },
        { prop: "goodsName", label: "商品名称", width: "200px", sortable: true },
        { prop: "customerCode", label: "客户编码" },
        { prop: "customerName", label: "客户名称", width: "200px", sortable: true },
        { prop: "inventoryCode", label: "仓库编码" },
        { prop: "positionCode", label: "货位编码" },
        { prop: "vehicleCode", label: "车辆编码" },
        { slots: { name: "column-status" }, label: "状态" },
        { slots: { name: "column-type" }, label: "出库类型" },
        { prop: "shopPeopleCode", label: "门店操作员", width: "100px" },
        { prop: "inventoryPeopleCode", label: "仓库操作员", width: "100px" },
        { prop: "outputPlan", label: "计划数", width: "100px", sortable: true },
        { prop: "outputPrice", label: "出库价格", width: "120px", sortable: true },
        { prop: "outputActual", label: "实际数", width: "100px", sortable: true },
        { prop: "returnNum", label: "退货数", width: "100px", sortable: true },
        { prop: "returnReason", label: "退货原因" },
        { prop: "outputShopCode", label: "调往门店编码", width: "120px" },
        { prop: "outputShopName", label: "调往门店名称", width: "200px", sortable: true },
        { slots: { name: "column-createTime" }, label: "预计日期", width: "150px", sortable: true },
        { slots: { name: "column-deadlineTime" }, label: "最迟日期", width: "150px", sortable: true },
        { slots: { name: "column-isDeleted" }, label: "是否删除" },
        { slots: { name: "column-todo" }, label: "操作", fixed: "right", width: 150 },
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
          placeholder: '请选择仓库',
          field: 'inventoryCode',
          value: '',
          type: "select",
          options: this.inventoryOptions
        },
        {
          label: '请选择',
          placeholder: '请选择客户',
          field: 'customerCode',
          value: '',
          type: "select",
          options: this.customerOptions
        },
        {
          label: '请选择',
          placeholder: '请选择状态',
          field: 'status',
          value: '',
          type: "select",
          options: this.statusOptions
        },
        {
          label: '请选择',
          placeholder: '请选择类型',
          field: 'type',
          value: '',
          type: "select",
          options: this.typeOptions
        },
        {
          label: '请选择',
          placeholder: '是否删除',
          field: 'isDeleted',
          value: '',
          type: "select",
          options: this.deletedOptions
        },
      ];
    }
  },
  props: {
  },
  components: {
    TableList,
    detailsEdit,
    reloadAndsearch
  },
  created() {
    this.getshoplist()
    this.getgoodslist()
    this.getCustomerList()
    this.getinventorylist();
    let user = JSON.parse(localStorage.getItem("userInfo"))
    this.userType = user.userType
  },
  methods: {
    getCustomerList() {
      CustomerList().then(res => {
        if (res.data.code == 200) {
          this.customerOptions = []
          res.data.data.forEach(item => {
            this.customerOptions.push({ label: item.customerName, value: item.customerCode })
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
        customerCode: "",
        inventoryCode: "",
        status: "",
        isDeleted: "",
        type: ""
      };
      outputWarehouseListPage(params).then((res) => {
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
      outputWarehouseListPage({
        page: this.query.pageNo,
        size: this.query.pageSize,
        shopCode: searchData.shopCode,
        goodsCode: searchData.goodsCode,
        customerCode: searchData.customerCode,
        inventoryCode: searchData.inventoryCode,
        status: searchData.status,
        isDeleted: searchData.isDeleted,
        type: searchData.type
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
        outputWarehouseDelete({ outputWarehouseKey: row.outputWarehouseKey, isDeleted: 1 }).then(res => {
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
    success() {
      this.drawer = false;
      this.rowData = {};
      this.getTableData();
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
        let outputWarehouseKeys = [];
        this.multipleSelection.forEach(item => {
          outputWarehouseKeys.push({ outputWarehouseKey: item.outputWarehouseKey, isDeleted: 1 })
        })
        console.log(outputWarehouseKeys);
        this.$confirm('删除操作, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          outputWarehouseDeleteList(outputWarehouseKeys).then(() => {
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
