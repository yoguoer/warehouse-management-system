<template>
  <div style="background:#fff;padding:20px;">
    <div>
      <el-input placeholder="预警对象" v-model="shopSupplierCode" type="text" size="small" :clearable="true">
        <template slot="prepend">预警对象</template>
      </el-input>
      <el-input placeholder="预警商品" v-model="goodsCode" type="text" size="small" :clearable="true">
        <template slot="prepend">预警商品</template>
      </el-input>
      <el-select size="small" v-model="status" placeholder="状态" clearable style="width:200px;margin-right:20px;">
        <el-option label="未处理" :value="0"></el-option>
        <el-option label="已处理" :value="1"></el-option>
      </el-select>
      <el-button type="primary" size="small" @click="search()" icon="el-icon-search">查询</el-button>
      <el-button size="small" @click="clean()" icon="el-icon-refresh" type="warning">重置</el-button>
      <el-button type="success" size="small" icon="el-icon-plus" @click="add()">新增</el-button>
      <el-button class="el-icon-delete" type="danger" size="small" @click="handleDeleteList()">删除</el-button>
      <el-divider />
    </div>
    <div class="list-model">
      <el-table height="600px" :cell-style="{ padding: '5px' }" border :data="alertList" tooltip-effect="dark"
        @selection-change="handleSelectionDelete" style="width: auto;margin-top: 20px;"
        :header-cell-style="{ background: '#F2F6FC', color: '#606266' }">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="shopSupplierCode" label="预警对象" width="180" :show-overflow-tooltip="true">
        </el-table-column>
        <el-table-column prop="goodsCode" label="预警商品" width="180" :show-overflow-tooltip="true">
        </el-table-column>
        <el-table-column prop="status" label="状态" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span style="color:#2d8bf0">{{ scope.row.status == 1 ? '已处理' : '未处理' }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="类型" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>{{ scope.row.type == 0 ? '连续未动销预警' : (scope.row.type == 1 ? '满仓预警' : '缺货预警') }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="alertTime" label="预警时间" :show-overflow-tooltip="true">
        </el-table-column>
        <el-table-column fixed="right" width="250px" label="操作">
          <template slot-scope="scope">
            <el-button @click="editRow(scope.row)" type="text" icon="el-icon-edit">去处理</el-button>
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
      <lackEdit v-if="drawer" :drawer="drawer" :rowData="rowData" @close="drawer = false" @success="success()" />
    </div>
  </div>
</template>

<script>
import lackEdit from "./lackEdit";
import { alertInfolistPage, alertInfoDelete, alertInfoDeleteList } from "@/api/welcome";

export default {
  name: "slist",
  data() {
    return {
      inputCategory: "",
      goodsCode: "",
      shopSupplierCode: "",
      status: "",
      pageSize: 10,
      pageNo: 1,
      total: null,
      drawer: false,
      rowData: {},
      alertList: [],
      multipleSelection: [],
    };
  },
  created() {
    this.getalertInfolistPage()
  },
  methods: {
    search() {
      let params = {
        type: 2,//预警类型（0：连续未动销预警，1：满仓预警，2：缺货预警）
        status: this.status,//状态（0：未处理，1：已处理）
        shopSupplierCode: this.shopSupplierCode,
        goodsCode: this.goodsCode,
        page: 1,
        size: 20,
      }
      alertInfolistPage(params).then(res => {
        if (res.data.code == 200) {
          this.alertList = res.data.data.records
          this.total=res.data.data.total
        } else {
          this.$message.error("获取失败!");
        }
      });
      this.$forceUpdate();
    },
    add() {
      this.editRow({})
    },
    clean() {
      this.status = ''
      this.shopSupplierCode = ''
      this.goodsCode = ''
      this.getalertInfolistPage()
    },
    _pageSize(val) {
      this.pageSize = val;
      this.getalertlistPage()
      this.$forceUpdate();
    },
    _page(val) {
      this.pageNo = val;
      this.getalertlistPage()
      this.$forceUpdate();
    },
    deleteRow(row) {
      console.log("deleteRow", row)
      alertInfoDelete({ alertInfoKey: row.alertInfoKey }).then(res => {
        if (res.data.code == 200) {
          this.$message.success("删除成功!");
          this.getalertInfolistPage()
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
    getalertInfolistPage() {
      let params = {
        type: 2,//预警类型（0：连续未动销预警，1：满仓预警，2：缺货预警）
        status: "",//状态（0：未处理，1：已处理）
        shopSupplierCode: "",
        goodsCode: "",
        page: 1,
        size: 20,
      }
      alertInfolistPage(params).then(res => {
        if (res.data.code == 200) {
          this.alertList = res.data.data.records
          this.total=res.data.data.total
        } else {
          this.$message.error("获取失败!");
        }
      });
    },
    success() {
      this.drawer = false;
      this.rowData = {};
      this.getalertInfolistPage();
    },
    //批量删除选择
    handleSelectionDelete(val) {
      this.multipleSelection = val
    },
    handleDeleteList() {
      if (this.multipleSelection.length > 0) {
        let alertInfoKeys = [];
        this.multipleSelection.forEach(item => {
          alertInfoKeys.push({ alertInfoKey: item.alertInfoKey })
        })
        console.log(alertInfoKeys);
        this.$confirm('删除操作, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          alertInfoDeleteList(alertInfoKeys).then(response => {
            this.getalertInfolistPage();
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
    lackEdit,
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