<template>
  <div>
    <reloadAndsearch ref="search" :config="searchConfig" @search="search" />
    <leftCard :categoryType="categoryType" :title="title" style="margin-top:0" ref="leftcard" />
    <div class="list-model">
      <TableList :pageMethod="getTableData" :searchMethod="getTableData" :table-data="tableData"
        :tableColumn="tableColumn" :query.sync="query" :total="total" :loading="loadings.table"
        :multiCheck="multiCheck">
        <template v-slot:column-todo="props">
          <el-button class="prohibitclick" @click="openDetail(props.row)" type="text" size="small">详情</el-button>
          <el-button class="prohibitclick" @click="editRow(props.row)" type="text" size="small">编辑</el-button>
          <el-button class="prohibitclick" @click="deleteRow(props.row)" type="text" size="small">删除</el-button>
        </template>
      </TableList>
      <supplyEdit v-if="drawer" :drawer="drawer" :rowData="rowData" @close="drawer = false" @success="success()" />
    </div>
  </div>
</template>

<script>
import _ from 'lodash'
import supplyEdit from "./supplyEdit";
import { SupplierlistPage, SupplierDelete } from "@/api/data";
import leftCard from "@/components/public/leftCard.vue";
import TableList from "@/components/public/tableList";
import reloadAndsearch from "@/components/public/reloadAndsearch/reloadAndsearch.vue";

export default {
  name: "slist",
  data() {
    return {
      categoryType: "SUPPLIER",
      inputCategory: "",
      title: "供应商分类",
      total: null,
      drawer: false,
      multiCheck: true,
      rowData: {},
      supplyList: [],
      multipleSelection: [],
      strDelete: '',
      tableData: [],
      loadings: {
        table: true,
      },
      query: {
        pageNo: 1,
        pageSize: 10,
      },
    };
  },
  props: {
  },
  components: {
    supplyEdit,
    leftCard,
    TableList,
    reloadAndsearch
  },
  created() {
    // this.getTableData()
  },
  computed: {
    tableColumn() {
      return [
        { prop: "superlierNumber", label: "供应商编号" },
        { prop: "supplierName", label: "供应商名称" },
        { prop: "superlierDescription", label: "详情" },
        // { slots: { name: "column-contact" }, label: "联系人列表" },   
        { slots: { name: "column-todo" }, label: "操作" },
      ];
    },
    searchConfig() {
      return [
        {
          label: '供应商名称',
          placeholder: '请输入供应商名称',
          field: 'supplierName',
          value: '',
          type: 'input'
        }
      ];
    }
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
        supplierName: "",
        categoryKey: this.inputCategory,
      };
      SupplierlistPage(params).then((res) => {
        if (res.code === 200) {
          this.total = res.data.total;
          this.tableData = res.data.records;
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
      SupplierlistPage({
        supplierName: searchData.supplierName || "",
        page: this.query.pageNo,
        size: this.query.pageSize,
        categoryKey: this.inputCategory == "all" ? "" : this.inputCategory,
      }).then(res => {
        this.tableData = res.data.records;
        this.total = res.data.total;
        console.log("tableData", this.tableData)
      })
        .finally(() => {
          this.loadings.table = false;
        });
    },
    reload() {
      this.$refs.leftcard.getTree()
      this.getTableData()
    },
    add() {
      this.editRow({})
    },
    deleteRow(row) {
      // console.log("deleteRow", row)
      SupplierDelete({ supplierKeys: row.supplierKey }).then(res => {
        if (res.code == 200) {
          this.$message.success("删除成功!");
          this.getTableData()
          this.$forceUpdate()
        } else {
          this.$message.error("删除失败!");
        }
      });
    },
    deleteByList() {
      if (this.multipleSelection.length > 0) {
        let arr = []
        this.multipleSelection.forEach(item => {
          arr.push(item.supplierKey)
          this.strDelete = _.join(arr, ',')
        })
        SupplierDelete({ supplierKeys: this.strDelete }).then(res => {
          if (res.code == 200) {
            this.$message.success("删除成功!");
            this.getTableData()
            this.$forceUpdate()
          } else {
            this.$message.error(res.msg);
          }
        });
      } else {
        this.$message.error("至少选择一项");
      }
    },
    //编辑
    editRow(row) {
      this.rowData = row
      this.drawer = true//编辑
      // 点击编辑，先查询详情，再把rowData传过去
      // SupplierqueryById({ supplierKey: row.supplierKey }).then(res => {
      //   this.rowData = res.data;
      //   this.drawer = true//编辑
      //   console.log("this.rowData", this.rowData)
      // });
    },
    //详情
    openDetail(row) {
      localStorage.setItem('supplyDetail', JSON.stringify(row))
      this.$router.push({
        name: "supplyDetail",
        params: {
          supplierKey: row.supplierKey
        }
      })
    },
    success() {
      this.drawer = false;
      this.rowData = {};
      this.getTableData();
    },
    cleanInput() {
      this.$refs.leftcard.isActive = ""
      this.inputCategory = ''
    }
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
