<template>
  <div style="background:#fff;padding:10px;">
    <reloadAndsearch ref="search" :config="searchConfig" @search="search" />
    <div class="list-model">
      <TableList :pageMethod="getTableData" :searchMethod="getTableData" :table-data="tableData"
        :tableColumn="tableColumn" :query.sync="query" :total="total" :loading="loadings.table">
        <template v-slot:column-status="props">
          <el-tag type="success" size="medium" v-if="props.row.status == 1">正常</el-tag>
          <el-tag type="danger" size="medium" v-if="props.row.status == 2">关仓</el-tag>
        </template>
        <template v-slot:column-address="props">
          <span>{{ props.row.province }}{{ props.row.city }}{{ props.row.district }}{{ props.row.detail }}</span>
        </template>
        <template v-slot:column-todo="props">
          <el-button class="prohibitclick" @click="setClose(props.row)" type="text" size="small"
            v-if="props.row.status == 1" icon="el-icon-moon">设为关仓</el-button>
          <el-button class="prohibitclick" @click="setOpen(props.row)" type="text" size="small"
            v-if="props.row.status == 2" icon="el-icon-sunny">设为正常</el-button>
          <el-button class="prohibitclick" @click="deleteRow(props.row)" type="text" size="small"
            icon="el-icon-document">删除</el-button>
        </template>
      </TableList>
    </div>
    <configEdit v-if="drawer" :drawer="drawer" :rowData="rowData" @close="drawer = false" @success="success()"
      :ShopInventoryList="tableData" />
  </div>
</template>

<script>
import { ShopInventoryListPage, ShopInventoryDelete, ShopInventoryDeleteList, ShopInventoryUpdate } from "@/api/warehouse";
import TableList from "@/components/public/tableList";
import reloadAndsearch from "@/components/public/reloadAndsearch/reloadAndsearch.vue";
import configEdit from "./configEdit";

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
      multipleSelection: [],
      loadings: {
        table: true,
      },
      query: {
        pageNo: 1,
        pageSize: 10,
      },
      statusList: [
        { label: "开业", value: "1" },
        { label: "关店", value: "2" },
        { label: "搬迁", value: "3" },
        { label: "筹备", value: "4" },
        { label: "暂停", value: "5" },
        { label: "预关店", value: "6" }
      ],
    };
  },
  created() {
    // this.getTableData()
  },
  computed: {
    tableColumn() {
      return [
        { prop: "shopCode", label: "门店编码" },
        { prop: "shopName", label: "门店名称", sortable: true },
        { prop: "inventoryCode", label: "仓库编码" },
        { prop: "inventoryName", label: "仓库名称" },
        { slots: { name: "column-address" }, label: "仓库地址" },
        { slots: { name: "column-status" }, label: "状态" },
        { prop: "description", label: "仓库备注" },
        { slots: { name: "column-todo" }, label: "操作", fixed: "right" },
      ];
    },
    searchConfig() {
      return [
        {
          label: '门店名称',
          placeholder: '请输入门店名称',
          field: 'shopName',
          value: '',
          type: 'input'
        },
        {
          label: '门店仓库名称',
          placeholder: '请输入门店仓库名称',
          field: 'inventoryName',
          value: '',
          type: 'input'
        },
        {
          label: '请选择',
          placeholder: '请选择状态',
          field: 'status',
          value: '',
          type: "select",
          options: this.statusList
        }
      ];
    }
  },
  props: {
  },
  components: {
    TableList,
    configEdit,
    reloadAndsearch
  },
  methods: {
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
        inventoryName: "",
        inventoryCode: "",
        status: ""
      };
      ShopInventoryListPage(params).then((res) => {
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
      ShopInventoryListPage({
        ...searchData,
        page: this.query.pageNo,
        size: this.query.pageSize,
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
    deleteRow(row) {
      // console.log("deleteRow", row)
      this.$confirm('删除操作, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        ShopInventoryDelete({ belongKey: row.belongKey, inventoryKey: row.inventoryKey }).then(res => {
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
    setClose(row) {
      let ruleForm = {
        belongKey: row.shopKey,
        inventoryKey: row.inventoryKey,
        status: 2,
        shopCode:row.shopCode
      }
      ShopInventoryUpdate(ruleForm).then(res => {
        if (res.data.code == 200) {
          this.$message.success("关仓成功!");
          this.getTableData(this.query.pageNo)
          this.$forceUpdate()
        } else {
          this.$message.error("分区内还有库存，请先处理库存数据为0再关仓!");
        }
      })
    },
    setOpen(row) {
      let ruleForm = {
        belongKey: row.shopKey,
        inventoryKey: row.inventoryKey,
        status: 1
      }
      ShopInventoryUpdate(ruleForm).then(res => {
        if (res.data.code == 200) {
          this.$message.success("编辑成功!");
          this.getTableData(this.query.pageNo)
          this.$forceUpdate()
        } else {
          this.$message.error("编辑失败!");
        }
      })
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
        let ShopInventorys = [];
        this.multipleSelection.forEach(item => {
          ShopInventorys.push({ belongKey: item.belongKey, inventoryKey: item.inventoryKey })
        })
        console.log(ShopInventorys);
        this.$confirm('删除操作, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          ShopInventoryDeleteList(ShopInventorys).then(() => {
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
  