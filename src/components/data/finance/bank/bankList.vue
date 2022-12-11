<template>
  <div>
    <!-- <el-button type="primary" plain size="small" icon="el-icon-refresh" @click="reload()">刷新</el-button> -->
    <!-- <el-button type="primary" plain size="small" icon="el-icon-plus" @click="add()">新增</el-button> -->
    <!-- <el-divider/> -->
    <div>
      <el-input placeholder="户主名" v-model="accountName" type="text" size="small" :clearable="true">
        <template slot="prepend">户主名</template>
      </el-input>
      <el-input placeholder="银行名" v-model="accountNumber" type="text" size="small" :clearable="true">
        <template slot="prepend">银行名</template>
      </el-input>
      <el-button type="primary" size="small" @click="search()" icon="el-icon-search">查询</el-button>
      <el-button size="small" @click="clean()" icon="el-icon-refresh" type="warning">重置</el-button>
      <el-button type="success" size="small" icon="el-icon-plus" @click="add()">新增</el-button>
      <el-button class="el-icon-delete" type="danger" size="small" @click="handleDeleteList()">删除</el-button>
      <!-- <el-button type="danger"  size="small" icon="el-icon-refresh" @click="reload()">刷新</el-button> -->
      <el-divider/>
    </div>
    <div class="list-model">
      <el-table height="600px" :cell-style="{ padding: '5px' }" border :data="bankList" tooltip-effect="dark" @selection-change="handleSelectionDelete"
        style="width: auto;margin-top: 20px;" :header-cell-style="{background:'#F2F6FC',color:'#606266'}">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="bankName" label="银行全称">
        </el-table-column>
        <el-table-column prop="accountName" label="户主名">
        </el-table-column>
        <el-table-column prop="accountNumber" label="银行账号">
        </el-table-column>
        <el-table-column prop="taxNumber" label="税号">
        </el-table-column>
        <el-table-column prop="accountTel" label="联系电话">
        </el-table-column>
        <!-- <el-table-column prop="supplierBillingKey" label="所属供应商">
        </el-table-column> -->
        <el-table-column align="center" fixed="right" width="200px" label="操作">
          <template slot-scope="scope">
            <el-button @click="editRow(scope.row)" type="text" icon="el-icon-edit">编辑</el-button>
            <el-button @click.native.prevent="deleteRow(scope.row)" type="text" size="small" icon="el-icon-delete">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="page-box">
        <el-pagination layout="total, sizes, prev, pager, next, jumper" :page-sizes="[10, 20, 50, 100]"
          :current="parseInt(pageNo)" :total="total" :page-size.sync="pageSize" :page-no.sync="pageNo"
          @current-change="_page" @size-change="_pageSize"></el-pagination>
      </div>
      <bankEdit v-if="drawer" :drawer="drawer" :rowData="rowData" @close="drawer = false" @success="success()" />
    </div>
  </div>
</template>

<script>
import bankEdit from "./bankEdit";
import { banklistPage,bankDelete,bankDeleteList } from "@/api/data";

export default {
  name: "slist",
  data() {
    return {
      accountNumber: "",
      accountName: "",
      pageSize: 10,
      pageNo: 1,
      total: null,
      drawer: false,
      rowData: {},
      bankList: [],
    };
  },
  created() {
    this.getBanklistPage();
  },
  methods: {
    clean(){
      this.accountNumber = ''
      this.accountName = ''
      this.reload()
    },
    search() {
      banklistPage({ accountName: this.accountName, bankName: this.accountNumber,page: 1, size: 20 }).then((res) => {
        this.bankList = res.data.data.records;
        console.log("bankList:", this.bankList);
      });
      this.$forceUpdate();
    },
    reload(){
      this.getBanklistPage()
    },
    add(){
       this.editRow({})
    },
    _pageSize(val) {
      this.pageSize = val;
      this.getBanklistPage()
      this.$forceUpdate();
    },
    _page(val) {
      this.pageNo = val;
      this.getBanklistPage()
      this.$forceUpdate();
    },
    deleteRow(row) {
      console.log("deleteRow", row)
      bankDelete({ billingKey: row.billingKey }).then(res => {
        if (res.data.code == 200) {
          this.$message.success("删除成功!");
          this.getBanklistPage()
          this.$forceUpdate()
        } else {
          this.$message.error("删除失败!");
        }
      });
    },
    editRow(row) {
      console.log("edit")
      this.drawer = true
      this.rowData = row
    },
    getBanklistPage() {
      banklistPage({ page: this.pageNo, size: this.pageSize }).then((res) => {
        this.bankList = res.data.data.records;
        // console.log("bankList:", this.bankList);
      });
      this.$forceUpdate();
    },
    success() {
      this.drawer = false;
      this.rowData = {};
      this.getBanklistPage();
    },
    //批量删除选择
    handleSelectionDelete(val) {
      this.multipleSelection = val
    },
    //根据 userId 批量删除用户
    handleDeleteList() {
      let billingKeys = [];
      this.multipleSelection.forEach(item => {
        billingKeys.push({billingKey:item.billingKey})
      })
      console.log(billingKeys);
      this.$confirm('删除操作, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
      }).then(() => {
        bankDeleteList(billingKeys).then(response => {
            this.getBanklistPage();
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
      });
    },
  },
  props: {
  },
  components: {
    bankEdit,
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
</style>
