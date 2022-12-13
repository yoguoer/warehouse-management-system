<template>
  <div>
    <!-- <el-button type="primary" plain size="small" icon="el-icon-refresh" @click="reload()">刷新</el-button> -->
    <!-- <el-button type="primary" plain size="small" icon="el-icon-plus" @click="add()">新增</el-button> -->
    <!-- <el-divider/> -->
    <div>
      <el-select v-model="inputCustomer" placeholder="客户" style="width:200px;margin-right:20px;" :clearable="true"
        size="small">
        <el-option v-for="(item, index) in options" :key="index" :label="item.customerName"
          :value="item.customerKey"></el-option>
      </el-select>
      <el-input placeholder="联系人名称" v-model="inputName" type="text" size="small" :clearable="true">
        <template slot="prepend">联系人名称</template>
      </el-input>
      <el-button type="primary" size="small" @click="search()" icon="el-icon-search">查询</el-button>
      <el-button size="small" @click="clean()" icon="el-icon-refresh" type="warning">重置</el-button>
      <el-button type="success" size="small" icon="el-icon-plus" @click="add()">新增</el-button>
      <el-button class="el-icon-delete" type="danger" size="small" @click="handleDeleteList()">删除</el-button>
      <!-- <el-button type="danger"  size="small" icon="el-icon-refresh" @click="reload()">刷新</el-button> -->
      <el-divider />
    </div>
    <div class="list-model">
      <el-table height="600px" :cell-style="{ padding: '5px' }" border :data="allList" tooltip-effect="dark"
        @selection-change="handleSelectionDelete" style="width: auto;margin-top: 20px;"
        :header-cell-style="{ background: '#F2F6FC', color: '#606266' }">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="contactName" label="联系人名称">
        </el-table-column>
        <el-table-column prop="contactNumber" sortable label="联系人编号">
        </el-table-column>
        <el-table-column prop="contactTel" label="联系电话">
        </el-table-column>
        <el-table-column prop="contactEmail" label="联系邮箱">
        </el-table-column>
        <el-table-column label="省" prop="province"></el-table-column>
        <el-table-column label="市" prop="city"></el-table-column>
        <el-table-column label="区" prop="district"></el-table-column>
        <el-table-column label="详细地址" prop="detail"></el-table-column>
        <el-table-column width="200px" fixed="right" label="操作">
          <template slot-scope="scope">
            <el-button @click="editRow(scope.row)" type="text" icon="el-icon-edit">编辑</el-button>
            <el-button @click.native.prevent="deleteRow(scope.row)" type="text" size="small"
              icon="el-icon-delete">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="page-box">
        <el-pagination layout="total, sizes, prev, pager, next, jumper" :page-sizes="[10, 20, 50, 100]"
          :current="parseInt(pageNo)" :total="total" :page-size="pageSize" :page-no="pageNo" @current-change="_page"
          @size-change="_pageSize"></el-pagination>
      </div>
      <contactEdit v-if="drawer" :drawer="drawer" :rowData="rowData" @close="drawer = false" @success="_success()" />
    </div>
  </div>
</template>

<script>
import contactEdit from './contactEdit.vue'
import { contactListpage, CustomerList, Contactdelete, ContactdeleteList } from '@/api/data'

export default {
  name: 'guestsList',
  data() {
    return {
      multipleSelection: [],
      pageSize: 10,
      pageNo: 1,
      total: null,
      drawer: false,
      rowData: {},
      allList: [],
      inputName: '',
      inputCustomer: '',
      inputContactType: '',
      options: []
    }
  },
  created() {
    this.getContact()
    this.getCustomer()
  },
  methods: {
    getCustomer() {
      CustomerList({}).then(res => {
        if (res.data.code === 200) {
          this.options = res.data.data
        } else {
          console.log('error');
        }
      })
    },
    //联系人
    getContact() {
      this.allList = []
      contactListpage({ contactCustomerKey: '', contactName: '', page: this.pageNo, size: this.pageSize }).then(res => {
        if (res.data.code === 200) {
          this.total = res.data.data.total
          this.allList = res.data.data.records
          console.log(this.total, this.allList);
        } else {
          console.log('error');
        }
      })
    },
    clean() {
      this.inputName = ''
      this.inputCustomer = ''
      this.inputContactType = ''
      this.reload()
    },
    search() {
      //联系人
      contactListpage({ contactName: this.inputName, contactCustomerKey: this.inputCustomer, contactType: this.inputContactType, page: this.pageNo, size: this.pageSize }).then(res => {
        if (res.data.code === 200) {
          this.total = res.total
          this.allList = res.data.data.records
          console.log(this.total, this.allList);
        } else {
          console.log('error');
        }
      })
      this.$forceUpdate()
    },
    _pageSize(val) {
      this.pageSize = val
      this.getContact()
      this.$forceUpdate()
    },
    _page(val) {
      this.pageNo = val
      this.getContact()
      this.$forceUpdate()
    },
    deleteRow(row) {
      Contactdelete({ contactKey: row.contactKey })
        .then((res) => {
          if (res.data.code === 200) {
            this.$message.success('删除成功!')
            this.getContact()
            this.$forceUpdate()
          }
        })
    },
    editRow(row) {
      this.rowData = row
      this.drawer = true
    },
    reload() {
      this.getContact()
      this.getCustomer()
    },
    add() {
      this.editRow({})
    },
    success() {
      this.getContact()
      this.$forceUpdate()
      this.drawer = false
      this.rowData = {}
    },
    //批量删除选择
    handleSelectionDelete(val) {
      this.multipleSelection = val
    },
    //根据 userId 批量删除用户
    handleDeleteList() {
      if (this.multipleSelection.length > 0) {
        let contactKeys = [];
        this.multipleSelection.forEach(item => {
          contactKeys.push({ contactKey: item.contactKey })
        })
        console.log(contactKeys);
        this.$confirm('删除操作, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          ContactdeleteList(contactKeys).then(response => {
            this.getContact();
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
    contactEdit
  }
}
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