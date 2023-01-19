<template>
  <div style="background:#fff;padding:10px;">
    <reloadAndsearch ref="search" :config="searchConfig" @search="search" />
    <div class="list-model">
      <TableList :pageMethod="getTableData" :searchMethod="getTableData" :table-data="tableData"
        :tableColumn="tableColumn" :query.sync="query" :total="total" :loading="loadings.table">
        <template v-slot:column-type="props">
          <span>{{ props.row.type == 0 ? '入库/总量增加' : (props.row.type == 1 ? '出库/总量减少' : "-") }}</span>
        </template>
        <template v-slot:column-transType="props">
          <span>{{
            props.row.transType == 0 ? '采购入库'
              : (props.row.transType == 1 ? '采购退货出库'
                : (props.row.transType == 2 ? '零售出库'
                  : (props.row.transType == 3 ? '零售退货入库'
                    : (props.row.transType == 4 ? '客户订购出库'
                      : (props.row.transType == 5 ? '客户订购退货入库' 
                      : (props.row.transType == 6 ? '调货入库'
                      : (props.row.transType == 7 ? '调货出库'
                      : (props.row.transType == 8 ? '盘盈入库'
                      : (props.row.transType == 9 ? '盘亏出库': '-')))))))))
          }}</span>
        </template>
        <template v-slot:column-quantity="props">
          <span v-if="props.row.transType==0||props.row.transType==3||props.row.transType==5||props.row.transType==6||props.row.transType==8">+{{props.row.quantity}}</span>
          <span v-else>-{{props.row.quantity}}</span>
        </template>
        <template v-slot:column-atTime="props">
          <span v-if="props.row.atTime">{{ props.row.atTime | datefmt('YYYY-MM-DD HH:mm:ss') }}</span>
        </template>
        <template v-slot:column-todo="props">
          <el-button @click="editRow(props.row)" type="text" icon="el-icon-edit">编辑</el-button>
          <el-button class="prohibitclick" @click="deleteRow(props.row)" type="text" size="small"
            icon="el-icon-document">删除</el-button>
        </template>
      </TableList>
    </div>
    <warehouseEdit v-if="drawer" :drawer="drawer" :rowData="rowData" @close="drawer = false" @success="success()"
      :shopGoodsList="tableData" />
  </div>
</template>

<script>
import { detailWarehouseListPage, detailWarehouseDelete, detailWarehouseDeleteList } from "@/api/warehouse";
import TableList from "@/components/public/tableList";
import reloadAndsearch from "@/components/public/reloadAndsearch/reloadAndsearch.vue";
import warehouseEdit from "./warehouseEdit";
import { shoplist, goodslist } from '@/api/data'

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
      shopOptions:[],
      goodsOptions:[],
      // 交易类型(0采购入库、1采购退货出库、2零售出库、3零售退货入库、4客户订购出库、5客户订购退货入库、6调货入库、7调货出库)
      transTypeOptions: [
        { label: "采购入库", value: 0 },
        { label: "采购退货出库", value: 1 },
        { label: "零售出库", value: 2 },
        { label: "零售退货入库", value: 3 },
        { label: "客户订购出库", value: 4 },
        { label: "客户订购退货入库", value: 5 },
        { label: "调货入库", value: 6 },
        { label: "调货出库", value: 7 },
        { label: "盘盈入库", value: 8 },
        { label: "盘亏出库", value: 9 },
      ],
      // 变化类型(0：入库/总量增加，1：出库/总量减少)
      typeOptions: [
        { label: "入库/总量增加", value: 0 },
        { label: "出库/总量减少", value: 1 },
      ]
    };
  },
  computed: {
    tableColumn() {
      return [
        // { prop: "inputOutputKey", label: "订单编号" },
        // { prop: "shopkeeperWarehouseKey", label: "关联库存" },
        { prop: "shopCode", label: "门店编码" },
        { prop: "goodsCode", label: "商品编码" },
        { slots: { name: "column-type" }, label: "变化类型" },
        { slots: { name: "column-transType" }, label: "交易类型" },
        { prop: "startNum", label: "起始库存" },
        { slots: { name: "column-quantity" }, label: "变化数量" },
        { prop: "finalNum", label: "结余库存" },
        { slots: { name: "column-atTime" }, label: "发生日期" },
        { slots: { name: "column-todo" }, label: "操作", fixed: "right", width: "150px" },
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
          placeholder: '请选择变化类型',
          field: 'type',
          value: '',
          type: "select",
          options: this.typeOptions
        },
        {
          label: '请选择',
          placeholder: '请选择交易类型',
          field: 'transType',
          value: '',
          type: "select",
          options: this.transTypeOptions
        },
      ];
    }
  },
  props: {
  },
  components: {
    TableList,
    warehouseEdit,
    reloadAndsearch
  },
  created() {
    this.getshoplist()
    this.getgoodslist()
  },
  methods: {
    getshoplist() {
      shoplist().then(res => {
        if (res.data.code == 200) {
          // this.shopOptions = res.data.data
          res.data.data.forEach(item=>{
            this.shopOptions.push({label:item.shopName,value:item.shopCode})
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
          res.data.data.forEach(item=>{
            this.goodsOptions.push({label:item.goodsName,value:item.goodsCode})
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
        // ...this.query,
        page: this.query.pageNo,
        size: this.query.pageSize,
        transType: "",
        type: "",
        goodsCode:"",
        shopCode:""
      };
      detailWarehouseListPage(params).then((res) => {
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
      detailWarehouseListPage({
        transType: searchData.transType,
        type: searchData.type,
        goodsCode:searchData.goodsCode,
        shopCode:searchData.shopCode,
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
    editRow(row) {
      this.rowData = row;
      this.drawer = true;
    },
    deleteRow(row) {
      console.log("deleteRow", row)
      detailWarehouseDelete({ detailWarehouseKey: row.detailWarehouseKey }).then(res => {
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
        let detailWarehouseKeys = [];
        this.multipleSelection.forEach(item => {
          detailWarehouseKeys.push({ detailWarehouseKey: item.detailWarehouseKey })
        })
        console.log(detailWarehouseKeys);
        this.$confirm('删除操作, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          detailWarehouseDeleteList(detailWarehouseKeys).then(() => {
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
