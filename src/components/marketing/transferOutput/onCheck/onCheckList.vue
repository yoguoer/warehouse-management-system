<template>
  <div style="background:#fff;padding:10px;">
    <reloadAndsearch ref="search" :config="searchConfig" @search="search" :hidden="hidden" :hidden1="hidden" />
    <div class="list-model">
      <TableList :pageMethod="getTableData" :searchMethod="getTableData" :table-data="tableData" :height="height"
        :multiCheck="multiCheck" :tableColumn="tableColumn" :query.sync="query" :total="total" :loading="loadings.table">
        <template v-slot:column-status="props">
          <span>{{
            props.row.checkStatus == '0' ? '未审批'
            : (props.row.checkStatus == '1' ? '同意'
              : (props.row.checkStatus == '2' ? '驳回' : "-"))
          }}</span>
        </template>
        <template v-slot:column-happenTime="props">
          <span v-if="props.row.happenTime">{{ props.row.happenTime | datefmt('YYYY-MM-DD HH:mm:ss') }}</span>
        </template>
        <template v-slot:column-checkTime="props">
          <span v-if="props.row.checkTime">{{ props.row.checkTime | datefmt('YYYY-MM-DD HH:mm:ss') }}</span>
        </template>
        <template v-slot:column-todo="props">
          <el-button v-if="props.row.checkStatus != 1" @click="editRow(props.row)" type="text"
            icon="el-icon-s-check">审批</el-button>
          <el-button v-if="props.row.checkStatus == 1" class="prohibitclick" @click="deleteRow(props.row)" type="text"
            size="small" icon="el-icon-document">删除</el-button>
        </template>
      </TableList>
    </div>
    <onCheckEdit v-if="drawer" :drawer="drawer" :rowData="rowData" @close="drawer = false" @success="success()" />
  </div>
</template>

<script>
import { transferCheckListPage, transferCheckDelete, transferCheckDeleteList } from "@/api/check";
import TableList from "@/components/public/tableList";
import reloadAndsearch from "@/components/public/reloadAndsearch/reloadAndsearch.vue";
import onCheckEdit from "./onCheckEdit";
import { goodslist, Supplierlist } from '@/api/data'
import { ShopInventoryList } from '@/api/warehouse'

export default {
  name: "slist",
  data() {
    return {
      total: null,
      drawer: false,
      hidden: true,
      multiCheck: false,
      height: "600px",
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
      supplierOptions: [],
      statusOptions: [
        { label: "在单", value: 0 },
        { label: "生产", value: 1 },
        { label: "在途", value: 2 },
        { label: "入库", value: 3 },
        { label: "占用", value: 4 },
        { label: "出库", value: 5 }],
      checkStatusOptions: [
        { label: "未审核", value: 0 },
        { label: "驳回", value: 2 }]
    };
  },
  computed: {
    tableColumn() {
      return [
        { prop: "shopCode", label: "门店编码" },
        { prop: "shopName", label: "门店名称", width: "200px", sortable: true },
        { prop: "goodsCode", label: "商品编码" },
        { prop: "goodsName", label: "商品名称", width: "200px", sortable: true },
        { prop: "inputShopCode", label: "调出门店编码" },
        { prop: "inputShopName", label: "调出门店名称", width: "200px", sortable: true },
        { prop: "inputPlan", label: "申请数", width: "100px", sortable: true },
        { prop: "inputPrice", label: "入库价格", width: "120px", sortable: true },
        // { prop: "shopPeopleCode", label: "门店操作员" },
        // { prop: "inputActual", label: "实际数" },
        // { prop: "inventoryCode", label: "仓库编码" },
        // { prop: "positionCode", label: "货位编码" },
        // { prop: "vehicleCode", label: "车辆编码" },
        // { slots: { name: "column-status" }, label: "状态" },
        // { slots: { name: "column-type" }, label: "入库类型" },
        { slots: { name: "column-status" }, label: "审批状态" },
        { prop: "description", label: "审批意见" },
        { slots: { name: "column-happenTime" }, label: "申请日期", width: "150px", sortable: true },
        { slots: { name: "column-checkTime" }, label: "审批日期", width: "150px", sortable: true },
        // { slots: { name: "column-createTime" }, label: "预计日期" },
        // { slots: { name: "column-deadlineTime" }, label: "最迟日期" },
        // { prop: "inventoryPeopleCode", label: "仓库操作员" },
        // { prop: "returnReason", label: "退货原因" },
        { slots: { name: "column-todo" }, label: "操作", fixed: "right", width: 150 },
      ];
    },
    searchConfig() {
      return [
        {
          label: '请选择',
          placeholder: '请选择调货入库门店',
          field: 'inputShopCode',
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
          placeholder: '请选择调货出库门店',
          field: 'outputShopCode',
          value: '',
          type: "select",
          options: this.shopOptions
        },
        {
          label: '请选择',
          placeholder: '请选择审批状态',
          field: 'checkStatus',
          value: '',
          type: "select",
          options: this.checkStatusOptions
        },
      ];
    }
  },
  props: {
  },
  components: {
    TableList,
    onCheckEdit,
    reloadAndsearch
  },
  created() {
    this.getshoplist()
    this.getgoodslist()
    this.getSupplierlist()
    let user = JSON.parse(localStorage.getItem("userInfo"))
    this.userType = user.userType
  },
  methods: {
    getSupplierlist() {
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
    unique(arr) {
      const res = new Map();
      return arr.filter((arr) => !res.has(arr.shopKey) && res.set(arr.shopKey, 1));
    },
    getshoplist() {
      ShopInventoryList().then(res => {
        if (res.data.code == 200) {
          this.shopOptions = []
          // this.shopOptions = res.data.data
          let temp = this.unique(res.data.data)
          temp.forEach(item => {
            this.shopOptions.push({ label: item.shopName, value: item.shopCode })
          });
        } else {
          this.$message.error("获取失败!");
        }
      })
    },
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
        page: this.query.pageNo,
        size: this.query.pageSize,
        inputShopCode: "",
        goodsCode: "",
        outputShopCode: "",
        checkType: 0,
        checkStatus: 13,
      };
      transferCheckListPage(params).then((res) => {
        if (res.data.code === 200) {
          this.total = res.data.data.total;
          this.tableData = res.data.data.records;
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
      transferCheckListPage({
        page: this.query.pageNo,
        size: this.query.pageSize,
        inputShopCode: searchData.inputShopCode,
        goodsCode: searchData.goodsCode,
        outputShopCode: searchData.outputShopCode,
        checkType: 0,
        checkStatus: searchData.checkStatus || 13,
      }).then((res) => {
        if (res.data.code === 200) {
          this.total = res.data.data.total;
          // this.tableData = res.data.data.records;
          this.tableData = []
          res.data.data.records.forEach(item => {
            if (item.checkStatus == 0 || item.checkStatus == 2) {
              this.tableData.push(item)
            }
          })
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
        transferCheckDelete({ transferCheckKey: row.transferCheckKey }).then(res => {
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
        let transferCheckKeys = [];
        this.multipleSelection.forEach(item => {
          transferCheckKeys.push({ transferCheckKey: item.transferCheckKey })
        })
        console.log(transferCheckKeys);
        this.$confirm('删除操作, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          transferCheckDeleteList(transferCheckKeys).then(() => {
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
