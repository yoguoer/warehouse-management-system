<template>
  <div>
    <div>
      <el-input placeholder="品牌编号" v-model="brandId" type="text" size="small" :clearable="true">
        <template slot="prepend">品牌编号</template>
      </el-input>
      <el-input placeholder="品牌名称" v-model="brandName" type="text" size="small" :clearable="true">
        <template slot="prepend">品牌名称</template>
      </el-input>

      <el-button type="primary" size="small" @click="search()" icon="el-icon-search">查询</el-button>
      <el-button size="small" @click="clean()" icon="el-icon-refresh" type="warning">重置</el-button>
      <el-button type="success" size="small" icon="el-icon-plus" @click="add()">新增</el-button>
      <el-button class="el-icon-delete" type="danger" size="small" @click="handleDeleteList()">删除</el-button>
      <el-divider />
    </div>
    <div class="list-model">
      <el-table height="600px" :cell-style="{ padding: '5px' }" border :data="brandList" tooltip-effect="dark"
        @selection-change="handleSelectionDelete" style="width: auto;margin-top: 20px;"
        :header-cell-style="{ background: '#F2F6FC', color: '#606266' }">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="brandCode" sortable label="品牌编号">
        </el-table-column>
        <el-table-column prop="brandName" label="品牌名称">
        </el-table-column>
        <el-table-column fixed="right" width="200px" label="操作">
          <template slot-scope="scope">
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
      <brandEdit v-if="drawer" :drawer="drawer" :rowData="rowData" @close="drawer = false" @success="success()" />
    </div>
  </div>
</template>

<script>
import brandEdit from "./brandEdit";
import { brandlistPage, brandDelete, brandDeleteList } from "@/api/data";

export default {
  name: "slist",
  data() {
    return {
      inputCategory: "",
      brandName: "",
      brandId: "",
      pageSize: 10,
      pageNo: 1,
      total: null,
      drawer: false,
      rowData: {},
      brandList: [],
      multipleSelection: [],
    };
  },
  created() {
    this.getBrandlistPage();
  },
  methods: {
    search() {
      if (this.brandId || this.brandName) {
        brandlistPage({ brandName: this.brandName, brandCode: this.brandId, page: this.pageNo, size: this.pageSize }).then((res) => {
          this.brandList = res.data.data.records;
          this.total = res.data.data.total;
          console.log("brandList:", this.brandList);
        });
        this.$forceUpdate();
      }
      else {
        this.getBrandlistPage()
      }
      this.$forceUpdate();
    },
    reload() {
      this.getBrandlistPage()
    },
    add() {
      this.editRow({})
    },
    clean() {
      this.brandName = ''
      this.brandId = ''
      this.reload()
    },
    _pageSize(val) {
      this.pageSize = val;
      this.getBrandlistPage()
      this.$forceUpdate();
    },
    _page(val) {
      this.pageNo = val;
      this.getBrandlistPage()
      this.$forceUpdate();
    },
    deleteRow(row) {
      console.log("deleteRow", row)
      brandDelete({ brandKey: row.brandKey }).then(res => {
        if (res.data.code == 200) {
          this.$message.success("删除成功!");
          this.getBrandlistPage()
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
    getBrandlistPage() {
      brandlistPage({ brandName: "", brandCode: "", page: this.pageNo, size: this.pageSize }).then((res) => {
        this.brandList = res.data.data.records;
        this.total = res.data.data.total;
        console.log("brandList:", this.brandList);
      });
      this.$forceUpdate();
    },
    success() {
      this.drawer = false;
      this.rowData = {};
      this.getBrandlistPage();
    },
    //批量删除选择
    handleSelectionDelete(val) {
      this.multipleSelection = val
    },
    //根据 userId 批量删除用户
    handleDeleteList() {
      if (this.multipleSelection.length > 0) {
        let brandKeys = [];
        this.multipleSelection.forEach(item => {
          brandKeys.push({ brandKey: item.brandKey })
        })
        console.log(brandKeys);
        this.$confirm('删除操作, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          brandDeleteList(brandKeys).then(response => {
            this.getBrandlistPage();
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
    brandEdit,
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
