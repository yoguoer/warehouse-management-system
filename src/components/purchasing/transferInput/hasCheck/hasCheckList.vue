<template>
  <div style="background:#fff;padding:10px;">
    <reloadAndsearch ref="search" :config="searchConfig" @search="search" />
    <div class="list-model">
      <TableList :pageMethod="getTableData" :searchMethod="getTableData" :table-data="tableData" :height="height"
        :tableColumn="tableColumn" :query.sync="query" :total="total" :loading="loadings.table">
        <template v-slot:column-status1="props">
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
          <span v-if="props.row.type == 0">采购入库</span>
          <span v-if="props.row.type == 1">调货入库</span>
        </template>
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
          <el-button v-if="props.row.status == 2" @click="editRow1(props.row)" type="text"
            icon="el-icon-s-home">确认入库</el-button>
          <el-button v-if="props.row.checkStatus != 1" @click="editRow(props.row)" type="text"
            icon="el-icon-edit">编辑</el-button>
          <el-button v-if="props.row.checkStatus != 2 && props.row.status != 2" class="prohibitclick"
            @click="deleteRow(props.row)" type="text" size="small" icon="el-icon-document">删除</el-button>
        </template>
      </TableList>
    </div>
    <hasCheckEdit v-if="drawer" :drawer="drawer" :rowData="rowData" @close="drawer = false" @success="success()" />
    <returnCargoEdit v-if="drawer1" :drawer="drawer1" :rowData="rowData1" @close="drawer1 = false" @success="success()" />
  </div>
</template>

<script>
import { transferCheckListPage, transferCheckDelete, transferCheckDeleteList } from "@/api/check";
import TableList from "@/components/public/tableList";
import returnCargoEdit from "./returnCargoEdit.vue";
import reloadAndsearch from "@/components/public/reloadAndsearch/reloadAndsearch.vue";
import hasCheckEdit from "./hasCheckEdit";
import { goodslist, Supplierlist } from '@/api/data'
import { ShopInventoryList } from '@/api/warehouse'
import { inputWarehouseDelete, inputWarehouseDeleteList } from "@/api/purchasing";

export default {
  name: "slist",
  data() {
    return {
      total: null,
      drawer: false,
      rowData: {},
      height: "600px",
      drawer1: false,
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
        { label: "在途", value: 2 },
        { label: "入库", value: 3 },
        { label: "占用", value: 4 },
        { label: "出库", value: 5 }],
    };
  },
  computed: {
    tableColumn() {
      return [
        { prop: "shopCode", label: "门店编码", width: "120px", sortable: true },
        { prop: "shopName", label: "门店名称", width: "200px", sortable: true },
        { prop: "goodsCode", label: "商品编码", width: "120px", sortable: true },
        { prop: "goodsName", label: "商品名称", width: "200px", sortable: true },
        { prop: "inputShopCode", label: "调货门店编码", width: "120px" },
        { prop: "inputShopName", label: "调货门店名称", width: "150px", sortable: true },
        { prop: "inputPlan", label: "计划数", width: "100px", sortable: true },
        { prop: "inputPrice", label: "入库价格", width: "120px", sortable: true },
        { prop: "inputActual", label: "实际数", width: "100px", sortable: true },
        { prop: "inventoryCode", label: "仓库编码" },
        { prop: "positionCode", label: "货位编码" },
        { prop: "vehicleCode", label: "车辆编码" },
        { slots: { name: "column-status1" }, label: "货物状态" },
        { prop: "shopPeopleCode", label: "门店操作员", width: "100px" },
        { prop: "inventoryPeopleCode", label: "仓库操作员", width: "100px" },
        { slots: { name: "column-type" }, label: "入库类型" },
        { slots: { name: "column-status" }, label: "审批状态" },
        { prop: "description", label: "审批意见" },
        { slots: { name: "column-happenTime" }, label: "申请日期", width: "150px", sortable: true },
        { slots: { name: "column-checkTime" }, label: "审批日期", width: "150px", sortable: true },
        // { slots: { name: "column-createTime" }, label: "预计日期" },
        // { slots: { name: "column-deadlineTime" }, label: "最迟日期" },
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
      ];
    }
  },
  props: {
  },
  components: {
    TableList,
    hasCheckEdit,
    returnCargoEdit,
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
        checkStatus: 1
      };
      transferCheckListPage(params).then((res) => {
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
      transferCheckListPage({
        page: this.query.pageNo,
        size: this.query.pageSize,
        inputShopCode: searchData.inputShopCode,
        goodsCode: searchData.goodsCode,
        outputShopCode: searchData.outputShopCode,
        checkType: 0,
        checkStatus: 1,
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
    editRow1(row) {
      this.rowData1 = row;
      this.drawer1 = true;
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
            inputWarehouseDelete({ isDeleted: 0, inputWarehouseKey: row.inputWarehouseKey }).then(res => {
              if (res.data.code == 200) {
                this.$message.success("删除成功!");
                this.getTableData()
                this.$forceUpdate()
              } else {
                this.$message.error("删除失败!");
              }
            });
            // this.$message.success("删除成功!");
            // this.getTableData()
            // this.$forceUpdate()
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
        let inputWarehouseKeys = [];
        this.multipleSelection.forEach(item => {
          transferCheckKeys.push({ transferCheckKey: item.transferCheckKey })
          inputWarehouseKeys.push({ isDeleted: 0, inputWarehouseKey: item.inputWarehouseKey })

        })
        // console.log(inputWarehouseKeys);
        // console.log(transferCheckKeys);
        this.$confirm('删除操作, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          transferCheckDeleteList(transferCheckKeys).then(() => {
            inputWarehouseDeleteList(inputWarehouseKeys).then(() => {
              this.getTableData();
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
            }).catch(error => {
              console.log(error);
            });
            //   this.getTableData();
            //   this.$message({
            //     type: 'success',
            //     message: '删除成功!'
            //   });
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
