<template>
  <div>
    <reloadAndsearch ref="search" :config="searchConfig" @search="search"/>
    <leftCard ref="leftcard" :categoryType="categoryType" :title="title" style="margin-top:0" />
    <div class="list-model">
      <TableList :pageMethod="getTableData" :searchMethod="getTableData" :table-data="tableData"
        :tableColumn="tableColumn" :query.sync="query" :total="total" :loading="loadings.table"
        :multiCheck="multiCheck">
        <!-- 1A-直营店，1B-加盟店，1C-合作店，1D-代理店，1E-代理商', -->
        <template v-slot:column-cooperation="props">
          <span>{{ props.row.cooperationType == '1' ? '直营店'
              : (props.row.cooperationType == '2' ? '加盟店'
                : (props.row.cooperationType == '3' ? '合作店'
                  : (props.row.cooperationType == '4' ? '代理店'
                    : (props.row.cooperationType == '5' ? '代理商' : '-'))))
          }}</span>
        </template>
        <!-- 3A-开业，3B-关店，3C-搬迁，3D-筹备，3E-暂停，3F-预关店',   -->
        <template v-slot:column-status="props">
          <span>{{ props.row.shopStatus == '1' ? '开业'
              : (props.row.shopStatus == '2' ? '关店'
                : (props.row.shopStatus == '3' ? '搬迁'
                  : (props.row.shopStatus == '4' ? '筹备'
                    : (props.row.shopStatus == '5' ? '暂停'
                      : (props.row.shopStatus == '6' ? '预关店' : '-')))))
          }}</span>
        </template>
        <template v-slot:column-todo="props">
          <el-button class="prohibitclick" @click="editRow(props.row)" type="text" size="small">编辑</el-button>
          <el-button
            class="prohibitclick"
            @click="deleteRow(props.row)"
            type="text"
            size="small"
            >删除</el-button
          >
        </template>
      </TableList>
      <shopEdit ref="shopEdit" v-if="shopEditVisable" :dialogVisible="shopEditVisable" :rowData="rowData"
        @close="shopEditVisable = false" @success="_success()"></shopEdit>
    </div>
  </div>
</template>

<script>
import shopEdit from "./shopEdit.vue";
import leftCard from "@/components/public/leftCard.vue";
import { shoplistPage, shopDelete,shopDeleteList } from "@/api/data";
import TableList from "@/components/public/tableList";
import reloadAndsearch from "@/components/public/reloadAndsearch/reloadAndsearch.vue";

export default {
  name: "shopList",
  data() {
    return {
      total: null,
      rowData: {},
      shopEditVisable: false,
      multiCheck: true,
      title: "门店分类",
      categoryType: "SHOP",
      list: [],
      hidden: true,
      inputCategory: "",
      tableData: [],
      multipleSelection: [],
      strDelete: '',
      loadings: {
        table: true,
      },
      query: {
        pageNo: 1,
        pageSize: 10,
      },
      shopStatusList:[
        {label:"开业",value:"1"},
        {label:"关店",value:"2"},
        {label:"搬迁",value:"3"},
        {label:"筹备",value:"4"},
        {label:"暂停",value:"5"},
        {label:"预关店",value:"6"}
      ],
      cooperationTypeList:[
        {label:"直营店",value:"1"},
        {label:"加盟店",value:"2"},
        {label:"合作店",value:"3"},
        {label:"代理店",value:"4"},
        {label:"代理商",value:"5"}
      ]
    };
  },
  props: {},
  components: {
    shopEdit,
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
        { prop: "shopCode", label: "门店编码" },
        { prop: "shopName", label: "门店名称" },
        { slots: { name: "column-cooperation" }, label: "经营类型" },//1A-直营店，1B-加盟店，1C-合作店，1D-代理店，1E-代理商',
        { slots: { name: "column-status" }, label: "店铺状态" },//3A-开业，3B-关店，3C-搬迁，3D-筹备，3E-暂停，3F-预关店',   
        { prop: "businessPersonCode", label: "业务人员" },
        { prop: "liablePersonCode", label: "责任人员" },
        { prop: "description", label: "备注"},   
        { slots: { name: "column-todo" }, label: "操作", fixed: "right" },
      ]
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
            label: '请选择',
            placeholder: '请选择门店状态',
            field: 'shopStatus',
            value: '',
            type: "select",
            options:this.shopStatusList
          },
          {
            label: '请选择',
            placeholder: '请选择经营类型',
            field: 'cooperationType',
            value: '',
            type: "select",
            options:this.cooperationTypeList
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
      };
      shoplistPage(params).then((res) => {
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
      console.log("deleteRow", row)
      shopDelete({ shopKey: row.shopKey }).then(res => {
        if (res.data.code == 200) {
          this.$message.success("删除成功!");
          this.getTableData()
          this.$forceUpdate()
        } else {
          this.$message.error("删除失败!");
        }
      });
    },
    //批量删除选择
    handleSelectionDelete(val) {
      this.multipleSelection = val
    },
    handleDeleteList() {
      if (this.multipleSelection.length > 0) {
        let shopKeys = [];
        this.multipleSelection.forEach(item => {
          shopKeys.push({ shopKey: item.shopKey })
        })
        console.log(shopKeys);
        this.$confirm('删除操作, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          shopDeleteList(shopKeys).then(() => {
            this.getTableData();
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
            this._handleFresh()
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
    reload() {
      this.getTableData()
      this.$refs.leftcard.getTree()
    },
    add() {
      this.editRow({})
    },
    editRow(row) {
      this.rowData = row;
      this.shopEditVisable = true;
    },
    handleClose(done) {
      done();
    },
    search(pageNo = 1, pageSize) {
      this.query.pageNo = pageNo;
      if (pageSize) {
        this.query.pageSize = pageSize;
      }
      const searchData = this.$refs.search.search
      shoplistPage({
        ...searchData,
        categoryKey:this.inputCategory,
        page: this.query.pageNo,
        size: this.query.pageSize,
      }).then((res) => {
        this.tableData = res.data.data.records;
        this.total = res.data.data.total;
        this.$forceUpdate()
      })
        .finally(() => {
          this.loadings.table = false;
        });
    },
    success() {
      this.rowData = {};
      this.shopEditVisable = false
      this.getTableData()
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

.list-model {
  width: 100%;
}

.el-table {
  overflow-y: auto !important;
  overflow-x: auto !important;
}

.el-table__fixed {
  height: auto !important; // 让固定列的高自适应，且设置!important覆盖ele-ui的默认样式
  bottom: 17px; // 固定列默认设置了定位，    position: absolute;top: 0;left: 0;只需要再设置一下bottom的值，让固定列和父元素的底部出现距离即可
}

.list-model {
  margin-top: 20px;
}
</style>
