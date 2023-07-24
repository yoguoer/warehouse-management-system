<template>
  <div style="background:#fff;padding:10px;">
    <reloadAndsearch ref="search" :config="searchConfig" @search="search" :hidden="hidden" :hidden1="hidden" />
    <div class="list-model">
      <TableList :pageMethod="getTableData" :searchMethod="getTableData" :table-data="tableData" :multiCheck="multiCheck"
        :tableColumn="tableColumn" :query.sync="query" :total="total" :loading="loadings.table">
        <template v-slot:column-how="props">
          <span v-if="props.row.checkType == 0">盘盈</span>
          <span v-else-if="props.row.checkType == 1">盘亏</span>
          <span v-else>-</span>
        </template>
        <template v-slot:column-happenTime="props">
          <span v-if="props.row.happenTime">{{ props.row.happenTime | datefmt('YYYY-MM-DD HH:mm:ss') }}</span>
        </template>
        <template v-slot:column-checkTime="props">
          <span v-if="props.row.checkTime">{{ props.row.checkTime | datefmt('YYYY-MM-DD HH:mm:ss') }}</span>
        </template>
        <template v-slot:column-much="props">
          <span v-if="props.row.checkType == 0">+{{ props.row.checkNum }}</span>
          <span v-else-if="props.row.checkType == 1">-{{ props.row.checkNum }}</span>
          <span v-else>-</span>
        </template>
        <template v-slot:column-status="props">
          <span>{{
            props.row.checkStatus == '0' ? '未审批'
            : (props.row.checkStatus == '1' ? '同意'
              : (props.row.checkStatus == '2' ? '驳回' : "-"))
          }}</span>
        </template>
        <template v-slot:column-todo="props">
          <el-button type="text" style="visibility:hidden">空</el-button>
          <el-button v-if="props.row.checkStatus != 1" @click="editRow(props.row)" type="text"
            icon="el-icon-s-check">审批</el-button>
          <!-- <el-button class="prohibitclick" @click="deleteRow(props.row)" type="text" size="small"
            icon="el-icon-document">删除</el-button> -->
        </template>
      </TableList>
    </div>
    <countCheckEdit v-if="drawer" :drawer="drawer" :rowData="rowData" @close="drawer = false" @success="success()" />
  </div>
</template>

<script>
import TableList from "@/components/public/tableList";
import reloadAndsearch from "@/components/public/reloadAndsearch/reloadAndsearch.vue";
import countCheckEdit from "./countCheckEdit";
import { countCheckListPage } from '@/api/dataIntegrate'
import { UserList } from '@/api/api'
import { shoplist, goodslist } from '@/api/data'

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
      checkTypeOptions: [
        { label: "盘盈", value: 0 },
        { label: "盘亏", value: 1 }
      ],
      checkPeopleOptions: [

      ],
      checkStatusOptions: [
        { label: "未审批", value: 0 },
        { label: "同意", value: 1 },
        { label: "驳回", value: 2 }
      ],
      shopOptions: [],
      goodsOptions: []
    };
  },
  computed: {
    tableColumn() {
      return [
        // { prop: "countCheckKey", label: "主键" },
        // { prop: "shopkeeperWarehouseKey", label: "关联库存" },
        { prop: "shopCode", label: "门店编码" },
        { prop: "shopName", label: "门店名称", sortable: true },
        { prop: "goodsCode", label: "商品编码" },
        { prop: "goodsName", label: "商品名称", sortable: true },
        // { prop: "inventoryCode", label: "仓库编码" },
        // { prop: "positionCode", label: "货位编码" },
        { slots: { name: "column-how" }, label: "审批类型" },
        { slots: { name: "column-much" }, label: "审批数量", sortable: true },
        { slots: { name: "column-happenTime" }, label: "申请时间", sortable: true },
        { slots: { name: "column-status" }, label: "审批状态" },
        { prop: "description", label: "审批意见" },
        { slots: { name: "column-checkTime" }, label: "审批时间", sortable: true },
        { slots: { name: "column-todo" }, label: "操作", fixed: "right", width: "120px" },
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
          placeholder: '请选择审批类型',
          field: 'checkType',
          value: '',
          type: "select",
          options: this.checkTypeOptions
        },
        {
          label: '请选择',
          placeholder: '请选择审批状态',
          field: 'checkStatus',
          value: '',
          type: "select",
          options: this.checkStatusOptions
        },
        {
          label: '请选择',
          placeholder: '请选择审批人',
          field: 'checkPeople',
          value: '',
          type: "select",
          options: this.checkPeopleOptions
        },
      ];
    }
  },
  props: {
  },
  components: {
    TableList,
    countCheckEdit,
    reloadAndsearch
  },
  created() {
    this.getUserList()
    this.getshoplist()
    this.getgoodslist()
  },
  mounted(){
    setTimeout(
      ()=>{
        if (this.shopOptions) {
            this.shopOptions.forEach(item => {
              this.tableData.forEach(t => {
                if (item.shopCode == t.shopCode) {
                  t.shopName = item.shopName
                }
              })
            })
          }
          this.$forceUpdate()
          console.log(this.tableData)
      },50
    )
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
    getUserList() {
      UserList({ userType: 1 }).then(res => {
        // this.checkPeopleOptions = res.data.data
        this.checkPeopleOptions = []
        res.data.data.forEach(item => {
          this.checkPeopleOptions.push({ label: item.userName, value: item.userCode })
        })
      }).catch(err => {
        console.log(err)
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
        checkStatus: "",
        checkType: "",
        checkPeople: "",
        shopCode: "",
        goodsCode: "",
      };
      countCheckListPage(params).then((res) => {
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
      countCheckListPage({
        checkStatus: searchData.checkStatus,
        checkType: searchData.checkType,
        checkPeople: searchData.checkPeople,
        shopCode: searchData.shopCode,
        goodsCode: searchData.goodsCode,
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
    success() {
      this.drawer = false;
      this.rowData = {}
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
