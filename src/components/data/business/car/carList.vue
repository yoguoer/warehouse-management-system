<template>
  <div>
    <div>
      <el-input placeholder="车型" v-model="inputvehicleType" type="text" size="small" :clearable="true">
        <template slot="prepend">车型</template>
      </el-input>
      <el-input placeholder="车辆编码" v-model="inputvehicleCode" type="text" size="small" :clearable="true">
        <template slot="prepend">车型</template>
      </el-input>
      <el-button type="primary" size="small" @click="search()" icon="el-icon-search">查询</el-button>
      <el-button size="small" @click="clean()" icon="el-icon-refresh" type="warning">重置</el-button>
      <el-button type="success" size="small" icon="el-icon-plus" @click="add()">新增</el-button>
      <el-button class="el-icon-delete" type="danger" size="small" @click="handleDeleteList()">删除</el-button>
      <!-- <el-button type="danger"  size="small" icon="el-icon-refresh" @click="reload()">刷新</el-button> -->
      <el-divider />
    </div>
    <div class="list-model">
      <el-table height="600px" :cell-style="{ padding: '5px' }" border :data="carList" tooltip-effect="dark"
        @selection-change="handleSelectionDelete" style="width: auto;margin-top: 20px;"
        :header-cell-style="{ background: '#F2F6FC', color: '#606266' }">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="vehicleCode" label="车辆编码">
        </el-table-column>
        <el-table-column prop="vehicleType" label="车型">
        </el-table-column>
        <el-table-column prop="detail" label="详细说明">
        </el-table-column>
        <el-table-column prop="maxCapacity" sortable label="装货体积">
        </el-table-column>
        <el-table-column prop="maxWeight" sortable label="载重">
        </el-table-column>
        <el-table-column prop="description" label="备注">
        </el-table-column>
        <el-table-column fixed="right" width="200px" label="操作">
          <template slot-scope="scope">
            <el-button @click="editRow(scope.row)" type="text">编辑</el-button>
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
      <carEdit v-if="drawer" :drawer="drawer" :rowData="rowData" @close="drawer = false" @success="success()" />
    </div>
  </div>
</template>

<script>
import carEdit from "./carEdit";
import { vehiclelistPage, vehicleDelete, vehicleDeleteList } from "@/api/data";

export default {
  name: "slist",
  data() {
    return {
      inputvehicleCode: "",
      inputvehicleType: "",
      pageSize: 10,
      pageNo: 1,
      total: null,
      drawer: false,
      rowData: {},
      carList: [],
      multipleSelection: [],
    };
  },
  created() {
    this.getCarlistPage();
  },
  methods: {
    search() {
      vehiclelistPage({ page: this.pageNo, size: this.pageSize, vehicleType: this.inputvehicleType, vehicleCode: this.inputvehicleCode }).then(res => {
        this.carList = res.data.data.records;
        this.total = res.data.data.total
        console.log("carList:", this.carList);
        this.$forceUpdate();
      });
    },
    clean() {
      this.inputvehicleType = ''
      this.inputvehicleCode = ''
      this.reload()
    },
    _pageSize(val) {
      this.pageSize = val;
      this.getCarlistPage()
      this.$forceUpdate();
    },
    _page(val) {
      this.pageNo = val;
      this.getCarlistPage()
      this.$forceUpdate();
    },
    deleteRow(row) {
      console.log("deleteRow", row)
      vehicleDelete({ vehicleKey: row.vehicleKey }).then(res => {
        if (res.data.code == 200) {
          this.$message.success("删除成功!");
          this.getCarlistPage()
          this.$forceUpdate()
        } else {
          this.$message.error("删除失败!");
        }
      });
    },
    reload() {
      this.getCarlistPage()
    },
    add() {
      this.editRow({})
    },
    editRow(row) {
      console.log("edit")
      this.rowData = row
      this.drawer = true
    },
    getCarlistPage() {
      vehiclelistPage({ vehicleType: "", vehicleCode: "", page: this.pageNo, size: this.pageSize }).then((res) => {
        this.carList = res.data.data.records;
        this.total = res.data.data.total
        console.log("carList:", this.carList);
      });
      this.$forceUpdate();
    },
    success() {
      this.drawer = false;
      this.rowData = {};
      this.getCarlistPage();
    },
    //批量删除选择
    handleSelectionDelete(val) {
      this.multipleSelection = val
    },
    handleDeleteList() {
      if (this.multipleSelection.length > 0) {
        let vehicleKeys = [];
        this.multipleSelection.forEach(item => {
          vehicleKeys.push({ vehicleKey: item.vehicleKey })
        })
        console.log(vehicleKeys);
        this.$confirm('删除操作, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          vehicleDeleteList(vehicleKeys).then(response => {
            this.getCarlistPage();
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
    carEdit,
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
