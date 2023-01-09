<template>
  <div style="background:#fff;padding:10px;">
    <reloadAndsearch ref="search" :config="searchConfig" @search="search" :hidden="hidden" :hidden1="hidden"/>
    <div class="list-model">
      <TableList :pageMethod="getTableData" :searchMethod="getTableData" :table-data="tableData" :multiCheck="multiCheck"
        :tableColumn="tableColumn" :query.sync="query" :total="total" :loading="loadings.table">
        <template v-slot:column-status="props">
          <span>{{
            props.row.checkStatus == '0' ? '未审批'
              : (props.row.checkStatus == '1' ? '同意'
                : (props.row.checkStatus == '2' ? '驳回':"-"))
          }}</span>
        </template>
        <template v-slot:column-type="props">
          <span v-if="props.row.type == 0">采购入库</span>
          <span v-if="props.row.type == 1">采购退货</span>
        </template>
        <template v-slot:column-createTime="props">
          <span>{{ props.row.createTime | datefmt('YYYY-MM-DD HH:mm:ss') }}</span>
        </template>
        <template v-slot:column-deadlineTime="props">
          <span>{{ props.row.deadlineTime | datefmt('YYYY-MM-DD HH:mm:ss') }}</span>
        </template>
        <template v-slot:column-todo="props">
          <el-button type="text" style="visibility:hidden"></el-button>
          <el-button v-if="props.row.checkStatus!=1" @click="editRow(props.row)" type="text" icon="el-icon-edit">审批</el-button>
          <el-button class="prohibitclick" @click="deleteRow(props.row)" type="text" size="small"
            icon="el-icon-document">删除</el-button>
        </template>
      </TableList>
    </div>
    <sReturnEdit v-if="drawer" :drawer="drawer" :rowData="rowData" @close="drawer = false" @success="success()" />
  </div>
</template>

<script>
import { returnCheckListPage, returnCheckDelete, returnCheckDeleteList } from "@/api/purchasing";
import TableList from "@/components/public/tableList";
import reloadAndsearch from "@/components/public/reloadAndsearch/reloadAndsearch.vue";
import sReturnEdit from "./sReturnEdit";
import { shoplist, goodslist, Supplierlist } from '@/api/data'

export default {
  name: "slist",
  data() {
    return {
      total: null,
      drawer: false,
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
      hidden:true,
      multiCheck:false,
      shopOptions: [],
      goodsOptions: [],
      supplierOptions: [],
      statusOptions: [
        { label: "未审批", value: 0 },
        { label: "同意", value: 1 },
        { label: "驳回", value: 2 },],
      //   typeOptions:[
      //     {label:"采购入库",value:0},
      //     {label:"调货入库",value:1}]
    };
  },
  computed: {
    tableColumn() {
      return [
        { prop: "shopCode", label: "门店编码" },
        { prop: "shopName", label: "门店名称" },
        { prop: "goodsCode", label: "商品编码" },
        { prop: "goodsName", label: "商品名称" },
        { prop: "supplierCode", label: "供应商编码" },
        { prop: "supplierName", label: "供应商名称" },
        { prop: "inputPlan", label: "计划数" },
        { prop: "inputPrice", label: "入库价格" },
        { prop: "inputActual", label: "实际数" },
        // { prop: "inventoryCode", label: "仓库编码" },
        // { prop: "positionCode", label: "货位编码" },
        { prop: "vehicleCode", label: "车辆编码" },
        { prop: "checkNum", label: "申请退货数" },
        { prop: "returnNum", label: "实际退货数" },
        { prop: "returnReason", label: "退货原因" },
        { slots: { name: "column-status" }, label: "审批状态" },
        { prop: "description", label: "审批意见" },
        // { slots: { name: "column-createTime" }, label: "预计日期" },
        // { slots: { name: "column-deadlineTime" }, label: "最迟日期" },
        // { prop: "shopPeopleCode", label: "门店操作员" },
        // { prop: "inventoryPeopleCode", label: "仓库操作员" },
        { slots: { name: "column-todo" }, label: "操作", fixed: "right", width: 150 },
      ];
    },
    searchConfig() {
      return [
        // {
        //   label: '请选择',
        //   placeholder: '请选择门店',
        //   field: 'shopCode',
        //   value: '',
        //   type: "select",
        //   options: this.shopOptions
        // },
        // {
        //   label: '请选择',
        //   placeholder: '请选择商品',
        //   field: 'goodsCode',
        //   value: '',
        //   type: "select",
        //   options: this.goodsOptions
        // },
        // {
        //   label: '请选择',
        //   placeholder: '请选择供应商',
        //   field: 'supplierCode',
        //   value: '',
        //   type: "select",
        //   options: this.supplierOptions
        // },
        {
          label: '请选择',
          placeholder: '请选择审批状态',
          field: 'checkStatus',
          value: '',
          type: "select",
          options: this.statusOptions
        },
        // {
        //   label: '请选择',
        //   placeholder: '请选择类型',
        //   field: 'type',
        //   value: '',
        //   type: "select",
        //   options:this.typeOptions
        // },
      ];
    }
  },
  props: {
  },
  components: {
    TableList,
    sReturnEdit,
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
        // shopCode: "",
        // goodsCode: "",
        // supplierCode: "",
        // inventoryCode: "",
        checkStatus: "",
        checkType: 0,
      };
      returnCheckListPage(params).then((res) => {
        if (res.data.code === 200) {
          this.total = res.data.data.total;
          this.tableData = res.data.data.records;
          console.log(this.total, this.tableData);
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
      returnCheckListPage({
        page: this.query.pageNo,
        size: this.query.pageSize,
        // shopCode: searchData.shopCode,
        // goodsCode: searchData.goodsCode,
        // supplierCode: searchData.supplierCode,
        // inventoryCode: searchData.inventoryCode,
        checkStatus:  searchData.checkStatus,
        // isDeleted: 0,
        checkType: 0,
      }).then((res) => {
        if (res.data.code === 200) {
          this.total = res.data.data.total;
          this.tableData = res.data.data.records;
          console.log(this.total, this.tableData);
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
      console.log("deleteRow", row)
      returnCheckDelete({ returnCheckKey: row.returnCheckKey }).then(res => {
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
        let returnCheckKeys = [];
        this.multipleSelection.forEach(item => {
          returnCheckKeys.push({ returnCheckKey: item.returnCheckKey })
        })
        console.log(returnCheckKeys);
        this.$confirm('删除操作, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          returnCheckDeleteList(returnCheckKeys).then(() => {
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
