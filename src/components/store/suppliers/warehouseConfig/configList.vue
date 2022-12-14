<template>
    <div>
      <reloadAndsearch ref="search" :config="searchConfig" @search="search" />
      <div class="list-model">
        <TableList :pageMethod="getTableData" :searchMethod="getTableData" :table-data="tableData"
          :tableColumn="tableColumn" :query.sync="query" :total="total" :loading="loadings.table">
          <template v-slot:column-status="props">
            <el-tag type="success" size="medium" v-if=" props.row.supplierInventoryStatus == 1">正常</el-tag>
            <el-tag type="danger" size="medium" v-if="props.row.supplierInventoryStatus == 2">关仓</el-tag>
          </template>
          <template v-slot:column-todo="props">
            <el-button class="prohibitclick" @click="editRow(props.row)" type="text" size="small">编辑</el-button>
            <el-button class="prohibitclick" @click="deleteRow(props.row)" type="text" size="small">删除</el-button>
          </template>
        </TableList>
      </div>
      <configEdit v-if="drawer" :drawer="drawer" :rowData="rowData" @close="drawer = false" @success="success()" />
    </div>
</template>

<script>
import { supplierInventoryListPage, supplierInventoryDelete, supplierInventoryDeleteList } from "@/api/warehouse";
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
        supplierInventoryList: [],
        tableData: [],
        multipleSelection: [],
        loadings: {
          table: true,
        },
        query: {
          pageNo: 1,
          pageSize: 10,
        },
      };
    },
    created() {
      // this.getTableData()
    },
    computed: {
      tableColumn() {
        return [
          { prop: "supplierCode", label: "供应商编码" },
          { prop: "supplierName", label: "供应商名称" },
          { prop: "supplierInventoryCode", label: "仓库编码" },
          { prop: "supplierInventoryName", label: "仓库名称" },
          { slots: { name: "column-status" }, label: "状态" },
          { prop: "description", label: "备注" },
          { slots: { name: "column-todo" }, label: "操作", fixed: "right" },
        ];
      },
      searchConfig() {
        return [
          {
            label: '供应商编码',
            placeholder: '请输入供应商编码',
            field: 'supplierCode',
            value: '',
            type: 'input'
          },
          {
            label: '业务仓库编码',
            placeholder: '请输入业务仓库编码',
            field: 'supplierInventoryCode',
            value: '',
            type: 'input'
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
          supplierInventoryName: "",
          supplierInventoryCode: "",
          supplierInventoryStatus:""
        };
        supplierInventoryListPage(params).then((res) => {
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
        // supplierInventoryListPage({
        //   ...searchData,
        //   page: this.query.pageNo,
        //   size: this.query.pageSize,
        // }).then((res) => {
        //   if (res.data.code === 200) {
        //     this.total = res.data.data.total;
        //     this.tableData = res.data.data.records;
        //     console.log(this.total, this.tableData);
        //   } else {
        //     console.log("error");
        //   }
        // })
        //   .finally(() => {
        //     this.loadings.table = false;
        //   });
      },
      deleteRow(row) {
        console.log("deleteRow", row)
        supplierInventoryDelete({ supplierInventoryKey: row.supplierInventoryKey }).then(res => {
          if (res.data.code == 200) {
            this.$message.success("删除成功!");
            this.getTableData()
            this.$forceUpdate()
          } else {
            this.$message.error("删除失败!");
          }
        });
      },
      editRow(row) {
        console.log("edit")
        this.rowData = row
        this.drawer = true
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
        this.editRow({})
      },
      //批量删除选择
      handleSelectionDelete(val) {
        this.multipleSelection = val
      },
      //根据 userId 批量删除用户
      handleDeleteList() {
        if (this.multipleSelection.length > 0) {
          let supplierInventoryKeys = [];
          this.multipleSelection.forEach(item => {
            supplierInventoryKeys.push({ supplierInventoryKey: item.supplierInventoryKey })
          })
          console.log(supplierInventoryKeys);
          this.$confirm('删除操作, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            supplierInventoryDeleteList(supplierInventoryKeys).then(() => {
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
    text-align: center;
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
  