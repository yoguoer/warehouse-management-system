<template>
  <div>
    <div>
      <el-input placeholder="客户名称" v-model="inputName" type="text" size="small" :clearable="true">
        <template slot="prepend">客户名称</template>
      </el-input>
      <el-button type="primary" size="small" @click="search()" icon="el-icon-search">查询</el-button>
      <el-button size="small" @click="clean()" icon="el-icon-refresh" type="warning">重置</el-button>
      <el-button type="success" size="small" icon="el-icon-plus" @click="add()">新增</el-button>
      <el-button class="el-icon-delete" type="danger" size="small" @click="handleDeleteList()">删除</el-button>
      <el-divider />
    </div>
    <leftCard :categoryType="categoryType" :title="title" style="height:600px;" ref="leftcard" />
    <div class="list-model">
      <el-table height="600px" :cell-style="{ padding: '5px' }" border :data="guestList" tooltip-effect="dark"
        @selection-change="handleSelectionDelete" style="width: auto;margin-top: 20px;"
        :header-cell-style="{ background: '#F2F6FC', color: '#606266' }">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="customerCode" sortable label="客户编号" :show-overflow-tooltip="true" width="120px">
        </el-table-column>
        <el-table-column prop="customerName" label="客户名称" :show-overflow-tooltip="true">
        </el-table-column>
        <el-table-column label="客户地址" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>
              {{ scope.row.province }},{{ scope.row.city }},{{ scope.row.district }},{{ scope.row.detail }}
            </span>
          </template>
        </el-table-column>
        <el-table-column label="联系人列表" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span v-for="item in scope.row.customerContactList" :key="item.contactKey" :label="item">
              <span>
                {{ item.contactName }};
              </span>
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="categoryName" label="所属分类" :show-overflow-tooltip="true">
        </el-table-column>
        <el-table-column width="200px" fixed="right" label="操作" v-if="userType<2">
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
          :current-page="parseInt(pageNo)" :total="total" :page-size="pageSize" :page-no="pageNo" @current-change="_page"
          @size-change="_pageSize"></el-pagination>
      </div>
      <guestsEdit v-if="drawer" :drawer="drawer" :rowData="rowData" @close="drawer = false" @success="success()" />
      <!-- <customerDetail v-if="isShow" :drawer="isShow" :rowData="rowData" @close="isShow = false" @success="success()"
        ref="detail" /> -->
    </div>
  </div>
</template>

<script>
import guestsEdit from './customerEdit'
// import customerDetail from "./customerDetail";
import leftCard from '@/components/public/leftCard.vue'
import { CustomerListpage, Customerdelete, CustomerdeleteList } from "@/api/data";

export default {
  name: 'guestsList',
  data() {
    return {
      pageSize: 10,
      pageNo: 1,
      total: null,
      drawer: false,
      // isShow:false,
      rowData: {},
      categoryType: 'CUSTOMER',
      title: "客户分类",
      guestList: [],
      multipleSelection: [],
      inputName: '',
      userType:"",
      inputCategory: ''
    }
  },
  created() {
    this.userType = this.$store.state.user.userType
    this.getCustomer()
  },
  methods: {
    //客户
    getCustomer() {
      CustomerListpage({
        categoryKey: "",
        customerName: "",
        page: this.pageNo,
        size: this.pageSize,
      }).then((res) => {
        if (res.data.code === 200) {
          this.total = res.data.data.total;
          this.guestList = res.data.data.records;
          // console.log(this.total, this.guestList);
        } else {
          console.log("error");
        }
      });
      this.$forceUpdate()
    },
    clean() {
      this.inputName = ''
      this.inputCategory = ''
      this.$refs.leftcard.isActive = ''
      this.reload()
    },
    search() {
      //客户
      CustomerListpage({
        categoryKey: this.inputCategory,
        customerName: this.inputName,
        page: 1,
        size: this.pageSize,
      }).then((res) => {
        if (res.data.code === 200) {
          this.total = res.data.data.total;
          this.guestList = res.data.data.records;
          // console.log(this.total, this.guestList);
        } else {
          console.log("error");
        }
      });
      this.$forceUpdate();
    },
    _pageSize(val) {
      this.pageSize = val
      this.getCustomer()
      this.$forceUpdate()
    },
    _page(val) {
      this.pageNo = val
      this.getCustomer()
      this.$forceUpdate()
    },
    deleteRow(row) {
      this.$confirm('删除操作, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        Customerdelete({ customerKey: row.customerKey }).then((res) => {
          if (res.data.code === 200) {
            this.$message.success("删除成功!");
            this.getCustomer();
            this.$forceUpdate();
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
      // this.rowData = row;
      // this.isShow = true//详情
      localStorage.setItem('customerDetail', JSON.stringify(row))
      this.$router.push({
        name: "customer-contact",
        params: {
          customerKey: row.customerKey
        }
      })
    },
    editRow(row) {
      this.rowData = row;
      this.drawer = true;
    },
    reload() {
      this.inputName = ''
      this.inputCategory = ''
      this.pageNo = 1
      this.$refs.leftcard.getTree()
      this.getCustomer()
    },
    add() {
      this.editRow({})
    },
    success() {
      this.getCustomer();
      this.$forceUpdate();
      this.drawer = false;
      this.rowData = {};
    },
    //批量删除选择
    handleSelectionDelete(val) {
      this.multipleSelection = val
    },
    handleDeleteList() {
      if (this.multipleSelection.length > 0) {
        let customerKeys = [];
        this.multipleSelection.forEach(item => {
          customerKeys.push({ customerKey: item.customerKey })
        })
        console.log(customerKeys);
        this.$confirm('删除操作, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          CustomerdeleteList(customerKeys).then(response => {
            this.getCustomer();
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
  props: {
  },
  components: {
    guestsEdit,
    // customerDetail,
    leftCard
  }
}
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

.top-title {
  color: #0066ff;
  font-size: 18px;
  line-height: 50px;
}

.el-divider--horizontal {
  margin: 20px 0;
}

.el-input-group {
  width: 300px;
  margin-right: 20px;
}

.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}

.el-drawer__open .el-drawer.btt {
  height: 60% !important;
}

.el-dropdown-link {
  cursor: pointer;
  color: #409EFF;
}

.el-icon-arrow-down {
  font-size: 12px;
}
</style>