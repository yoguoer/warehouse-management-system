<template>
  <div>
    <div>
      <el-input placeholder="联系人名称" v-model="inputName" type="text" size="small" :clearable="true">
        <template slot="prepend">联系人名称</template>
      </el-input>
      <el-select size="middle" v-model="supplierKey" placeholder="所属供应商" style="width:200px;margin-right:20px" clearable>
        <el-option v-for="item in supplyOptions" :key="item.supplierKey" :label="item.supplierName"
          :value="item.supplierKey" placeholder="所属供应商">
        </el-option>
      </el-select>

      <el-button type="primary" size="small" @click="search()" icon="el-icon-search">查询</el-button>
      <el-button size="small" @click="clean()" icon="el-icon-refresh" type="warning">重置</el-button>
      <el-button type="success" size="small" icon="el-icon-plus" @click="add()">新增</el-button>
      <el-button class="el-icon-delete" type="danger" size="small" @click="handleDeleteList()">删除</el-button>
      <el-divider />
    </div>
    <div class="list-model">
      <el-table height="600px" :cell-style="{ padding: '5px' }" border :data="allList" tooltip-effect="dark"
        @selection-change="handleSelectionDelete" style="width: auto;margin-top: 20px;"
        :header-cell-style="{ background: '#F2F6FC', color: '#606266' }">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column label="所属供应商" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>{{ scope.row.supplierCode }}{{ scope.row.supplierName }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="contactCode" sortable label="联系人编号" :show-overflow-tooltip="true" width="120px">
        </el-table-column>
        <el-table-column prop="contactName" label="联系人名称" :show-overflow-tooltip="true" width="120px">
        </el-table-column>
        <el-table-column prop="contactTel" label="联系电话" :show-overflow-tooltip="true" width="150px">
        </el-table-column>
        <el-table-column prop="contactEmail" label="联系邮箱" :show-overflow-tooltip="true">
        </el-table-column>
        <el-table-column label="省" sortable prop="province" width="100px" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column label="市" prop="city" width="100px" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column label="区" prop="district" width="100px" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column label="详细地址" prop="detail" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column width="200px" fixed="right" label="操作"  v-if="userType<2&&userBelong!=1">
          <template slot-scope="scope">
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
      <contactEdit v-if="drawer" :drawer="drawer" :rowData="rowData" @close="drawer = false" @success="_success()" />
    </div>
  </div>
</template>

<script>
import contactEdit from './contactEdit.vue'
import { contactListpage, Supplierlist, Contactdelete, ContactdeleteList } from '@/api/data'

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
      supplierKey: '',
      inputContactType: '',
      userType:"",
      userBelong:"",
      supplyOptions: []
    }
  },
  created() {
    this.userType = this.$store.state.user.userType
    this.userBelong = this.$store.state.user.userBelong
    this.getContact()
    this.getSupplierlist()
    if (this.$route.params.supplierKey) {
      this.supplierKey = this.$route.params.supplierKey
    }
  },
  methods: {
    getSupplierlist() {
      Supplierlist().then(res => {
        if (res.data.code == 200) {
          this.supplyOptions = res.data.data
        } else {
          this.$message.error("获取失败!");
        }
      });
    },
    //联系人
    getContact() {
      this.allList = []
      contactListpage({ contactSupplierKey: this.$route.params.supplierKey || '', contactName: '', page: this.pageNo, size: this.pageSize }).then(res => {
        if (res.data.code === 200) {
          this.total = res.data.data.total
          this.allList = res.data.data.records
        } else {
          console.log('error');
        }
      })
    },
    clean() {
      this.inputName = ''
      this.supplierKey = ''
      this.inputContactType = ''
      this.reload()
    },
    search() {
      // if (this.supplyDetail.supplierKey) {
      //   params.supplierKey = this.supplyDetail.supplierKey
      // }
      //联系人
      contactListpage({ contactName: this.inputName, contactSupplierKey: this.supplierKey, page: 1, size: this.pageSize }).then(res => {
        if (res.data.code === 200) {
          this.total = res.data.data.total
          this.allList = res.data.data.records
          // console.log(this.total, this.allList);
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
      this.$confirm('删除操作, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        Contactdelete({ contactKey: row.contactKey })
          .then((res) => {
            if (res.data.code === 200) {
              this.$message.success('删除成功!')
              this.getContact()
              this.$forceUpdate()
            }
          })

      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
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