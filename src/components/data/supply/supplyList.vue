<template>
  <div>
    <div>
      <el-input placeholder="供应商编号" v-model="supplyId" type="text" size="small" :clearable="true">
        <template slot="prepend">供应商编号</template>
      </el-input>
      <el-input placeholder="供应商名称" v-model="supplyName" type="text" size="small" :clearable="true">
        <template slot="prepend">供应商名称</template>
      </el-input>
      <el-button type="primary" size="small" @click="search()" icon="el-icon-search">查询</el-button>
      <el-button size="small" @click="clean()" icon="el-icon-refresh" type="warning">重置</el-button>
      <el-button type="success" size="small" icon="el-icon-plus" @click="add()">新增</el-button>
      <el-button class="el-icon-delete" type="danger" size="small" @click="handleDeleteList()">删除</el-button>
      <!-- <el-button type="danger"  size="small" icon="el-icon-refresh" @click="reload()">刷新</el-button> -->
      <el-divider />
    </div>
    <leftCard :categoryType="categoryType" :title="title" style="height:600px;" ref="leftcard" />
    <div class="list-model">
      <el-table height="600px" :cell-style="{ padding: '5px' }" border :data="supplyList" tooltip-effect="dark"
        @selection-change="handleSelectionDelete" style="width: auto;margin-top: 20px;"
        :header-cell-style="{ background: '#F2F6FC', color: '#606266' }">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="supplierCode" sortable label="供应商编号" width="120px">
        </el-table-column>
        <el-table-column prop="supplierName" label="供应商名称">
        </el-table-column>
        <el-table-column label="联系人列表">
          <template slot-scope="scope">
            <span v-for="(item, index) in scope.row.supplierContactList" :key="index" :label="item">
              <span>
                {{ item.contactName }};
              </span>
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="categoryName" label="所属分类">
        </el-table-column>
        <el-table-column fixed="right" width="200px" label="操作">
          <template slot-scope="scope">
            <el-button @click="openDetail(scope.row)" type="text" icon="el-icon-document">详情</el-button>
            <el-button @click="editRow(scope.row)" type="text" icon="el-icon-edit">编辑</el-button>
            <el-button @click.native.prevent="deleteRow(scope.row)" type="text" size="small"
              icon="el-icon-delete">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="page-box">
        <el-pagination layout="total, sizes, prev, pager, next, jumper" :page-sizes="[10, 20, 50, 100]"
          :current="parseInt(pageNo)" :total="total" :page-size.sync="pageSize" :page-no.sync="pageNo"
          @current-change="_page" @size-change="_pageSize"></el-pagination>
      </div>
      <supplyEdit v-if="drawer" :drawer="drawer" :rowData="rowData" @close="drawer = false" @success="success()"
        ref="edit" />
    </div>
  </div>
</template>

<script>
import supplyEdit from "./supplyEdit";
import { SupplierlistPage, SupplierDelete, SupplierDeleteList, Supplierlist } from "@/api/data";
import leftCard from "@/components/public/leftCard.vue";

export default {
  name: "slist",
  data() {
    return {
      categoryType: "SUPPLIER",
      inputCategory: "",
      title: "供应商分类",
      supplyName: "",
      supplyId: "",
      pageSize: 10,
      pageNo: 1,
      total: null,
      drawer: false,
      rowData: {},
      supplyList: [],
      multipleSelection: []
    };
  },
  props: {
  },
  components: {
    supplyEdit,
    leftCard,
  },
  created() {
    this.getSupplierlistPage();
  },
  methods: {
    search() {
      SupplierlistPage({ supplierCode: this.supplyId, supplierName: this.supplyName, categoryKey: this.inputCategory, page: 1, size: this.pageSize }).then(res => {
        this.supplyList = res.data.data.records;
        this.total = res.data.data.total
      });
      this.$forceUpdate();
    },
    _pageSize(val) {
      this.pageSize = val;
      this.getSupplierlistPage()
      this.$forceUpdate();
    },
    _page(val) {
      this.pageNo = val;
      this.getSupplierlistPage()
      this.$forceUpdate();
    },
    deleteRow(row) {
      // console.log("deleteRow", row)
      this.$confirm('删除操作, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        SupplierDelete({ supplierKey: row.supplierKey }).then(res => {
          if (res.data.code == 200) {
            this.$message.success("删除成功!");
            this.getSupplierlistPage()
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
    //编辑
    editRow(row) {
      this.rowData = row
      this.drawer = true
    },
    getSupplierlistPage() {
      SupplierlistPage({ supplierCode: this.supplyId, supplierName: this.supplyName, categoryKey: this.inputCategory, page: this.pageNo, size: this.pageSize }).then((res) => {
        this.supplyList = res.data.data.records;
        this.total = res.data.data.total
        // console.log(this.total, this.supplyList);
      });
      this.$forceUpdate();
    },
    success() {
      this.drawer = false;
      this.rowData = {};
      this.getSupplierlistPage();
    },
    reload() {
      this.$refs.leftcard.getTree()
      this.getSupplierlistPage()
    },
    add() {
      this.editRow({})
    },
    clean() {
      this.supplyName = ''
      this.supplyId = ''
      this.inputCategory = ''
      this.$refs.leftcard.isActive = ''
      this.reload()
    },
    //批量删除选择
    handleSelectionDelete(val) {
      this.multipleSelection = val
    },
    handleDeleteList() {
      if (this.multipleSelection.length > 0) {
        let supplierkeys = [];
        this.multipleSelection.forEach(item => {
          supplierkeys.push({ supplierKey: item.supplierKey })
        })
        console.log(supplierkeys);
        this.$confirm('删除操作, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          SupplierDeleteList(supplierkeys).then(response => {
            this.getSupplierlistPage();
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
</style>
