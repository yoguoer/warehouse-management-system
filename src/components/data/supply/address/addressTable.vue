<template>
  <div>
    <reloadAndsearch ref="search" :config="searchConfig" @search="search" />

    <div class="list-model">
      <TableList :pageMethod="getTableData" :searchMethod="getTableData" :table-data="tableData"
        :tableColumn="tableColumn" :query.sync="query" :total="total" :loading="loadings.table"
        :multiCheck="multiCheck">

        <template v-slot:column-address="props">
          <div>{{ props.row.province }}{{props.row.city}}{{props.row.district}}{{props.row.detail }}</div>
        </template>

        <template v-slot:column-todo="props">
          <el-button class="prohibitclick" @click="editRow(props.row)" type="text" size="small">编辑</el-button>
          <el-button class="prohibitclick" @click="deleteRow(props.row)" type="text" size="small">删除</el-button>
        </template>
      </TableList>
      <addressEdit v-if="drawer" :drawer="drawer" :rowData="rowData" @close="drawer = false" @success="_success()" />
    </div>
  </div>
</template>

<script>
import reloadAndsearch from "@/components/public/reloadAndsearch/reloadAndsearch.vue";
import TableList from "@/components/public/tableList";
import { supplierAddressPage, supplierAddressDelete, Supplierlist } from "@/api/data";
import addressEdit from "./addressEdit";
export default {
  components: {
    reloadAndsearch,
    TableList,
    addressEdit
  },
  data() {
    return {
      supplyDetail: {},
      loadings: {
        table: true,
      },
      query: {
        pageNo: 1,
        pageSize: 10,
      },
      rowData: {},
      drawer: false,
      multiCheck: true,
      total: null,
      tableData: [],
      supplierList: []
    }
  },
  methods: {
    getSupplierlist() {
      Supplierlist().then(res => {
        if (res.data.code == 200) {
          // this.supplierList = res.data.data
          this.supplierList = []
          res.data.data.forEach(item => {
            this.supplierList.push({ label: item.supplierName, value: item.supplierKey })
          });
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
      const searchData = this.$refs.search.search
      let params = {
        ...searchData,
      }
      if (this.supplyDetail.supplierKey) {
        params.supplierKey = this.supplyDetail.supplierKey
      }
      supplierAddressPage(params).then((res) => {
        this.tableData = res.data.data.records;
        this.total = res.data.data.total;
      })
        .finally(() => {
          this.loadings.table = false
        })
    },
    search(pageNo = 1, pageSize) {
      this.query.pageNo = pageNo;
      if (pageSize) {
        this.query.pageSize = pageSize;
      }
      this.loadings.table = true;
      const searchData = this.$refs.search.search
      let params = {
        supplierAddressKey:searchData.supplierAddressKey,
        page: this.query.pageNo,
        size: this.query.pageSize,
      }
      supplierAddressPage(params).then((res) => {
        this.tableData = res.data.data.records;
        this.total = res.data.data.total;
        console.log("skuList:", this.tableData);
      })
        .finally(() => {
          this.loadings.table = false
        })
    },
    add() {
      this.editRow({})
    },
    editRow(row) {
      this.rowData = row;
      this.drawer = true;
    },
    deleteRow(row) {
      console.log("deleteRow", row);
      supplierAddressDelete({ supplierAddressKeys: row.supplierAddressKey }).then((res) => {
        if (res.data.code == 200) {
          this.$message.success("删除成功!");
          this.search();
          this.$forceUpdate();
        } else {
          this.$message.error(res.data.msg);
        }
      });
    },
    success() {
      this.drawer = false;
      this.rowData = {};
      this.search()
    },
  },
  computed: {
    tableColumn() {
      return [
        { prop: "supplierAddressKey", label: "供应商", width: 250 },
        { prop: "addressType", label: "地址类型", width: 180 },
        { slots: { name: "column-address" }, label: "地址" },

        { slots: { name: "column-todo" }, label: "操作", fixed: "right", width: '120px' },
      ];
    },
    searchConfig() {
      return[
      {
          label: '请选择',
          placeholder: '请选择供应商',
          field: 'supplierAddressKey',
          value: '',
          type: "select",
          options: this.supplierList
        },
      ]
     }
  },
  created() {
    if (this.$route.params.supplierKey) {
      let rowData = localStorage.getItem('supplyDetail')
      if (rowData) {
        this.supplyDetail = JSON.parse(rowData)
      }
    }
    this.getSupplierlist()
  }
}
</script>