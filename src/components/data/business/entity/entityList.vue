<template>
  <div>
    <reloadAndsearch ref="search" :config="searchConfig" @search="search" />
    <div class="list-model">
      <TableList :pageMethod="getTableData" :searchMethod="getTableData" :table-data="tableData"
        :tableColumn="tableColumn" :query.sync="query" :total="total" :loading="loadings.table">
        <template v-slot:column-todo="props">
          <el-button class="prohibitclick" @click="editRow(props.row)" type="text" size="small">编辑</el-button>
          <el-button class="prohibitclick" @click="deleteRow(props.row)" type="text" size="small">删除</el-button>
        </template>
      </TableList>
      <entityEdit v-if="drawer" :drawer="drawer" :rowData="rowData" @close="drawer = false" @success="success()" />
    </div>
  </div>
</template>

<script>
import entityEdit from "./entityEdit";
import { entityListPage, entityDelete, entityDeleteList } from "@/api/data";
import TableList from "@/components/public/tableList";
import reloadAndsearch from "@/components/public/reloadAndsearch/reloadAndsearch.vue";

export default {
  name: "slist",
  data() {
    return {
      pageSize: 10,
      pageNo: 1,
      total: null,
      drawer: false,
      rowData: {},
      entityList: [],
      tableData: [],
      multipleSelection: [],
      loadings: {
        table: true,
      },
      query: {
        pageNo: 1,
        pageSize: 10,
        keyword: "",
      },
    };
  },
  created() {
    // this.getTableData()
  },
  computed: {
    tableColumn() {
      return [
        { prop: "entityCode", label: "业务主体编码" },
        { prop: "entityName", label: "业务主体名称" },
        { slots: { name: "column-todo" }, label: "操作", fixed: "right" },
      ];
    },
    searchConfig() {
      return [
        {
          label: '业务主体编码',
          placeholder: '请输入业务主体编码',
          field: 'entityCode',
          value: '',
          type: 'input'
        },
        {
          label: '业务主体名称',
          placeholder: '请输入业务主体名称',
          field: 'entityName',
          value: '',
          type: 'input'
        }
      ];
    }
  },
  props: {
  },
  components: {
    entityEdit,
    TableList,
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
        entityName: "",
        entityCode: "",
      };
      entityListPage(params).then((res) => {
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
      entityListPage({
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
    deleteRow(row) {
      console.log("deleteRow", row)
      entityDelete({ entityCode: row.entityCode }).then(res => {
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
        let entityCodes = [];
        this.multipleSelection.forEach(item => {
          entityCodes.push({ entityCode: item.entityCode })
        })
        console.log(entityCodes);
        this.$confirm('删除操作, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          entityDeleteList(entityCodes).then(() => {
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
