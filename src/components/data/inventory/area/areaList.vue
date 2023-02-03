<template>
  <div>
    <div>
      <el-input placeholder="区域号" v-model="inputArea" clearable type="text" size="small"
        style="width: 300px; margin-right: 20px">
        <template slot="prepend">区域号</template>
      </el-input>
      <el-input placeholder="区域名称" v-model="inputName" clearable type="text" size="small"
        style="width: 300px; margin-right: 20px">
        <template slot="prepend">区域名称</template>
      </el-input>
      <el-select size="small" clearable v-model="inputInventory" placeholder="所属仓库"
        style="width:200px;margin-right:20px;">
        <el-option v-for="item in options1" :key="item.inventoryKey" :label="item.inventoryName"
          :value="item.inventoryKey">
        </el-option>
      </el-select>
      <el-select size="mini" v-model="status" placeholder="区域状态" style="width:200px;margin-right:20px;" clearable>
        <el-option label="未启用" :value="1"></el-option>
        <el-option label="启用" :value="2"></el-option>
        <el-option label="停用" :value="3"></el-option>
      </el-select>
      <el-button type="primary" size="small" @click="search()" icon="el-icon-search">查询</el-button>
      <el-button size="small" @click="clean()" icon="el-icon-refresh" type="warning">重置</el-button>
      <el-button type="success" size="small" icon="el-icon-plus" @click="add()">新增</el-button>
      <el-button class="el-icon-delete" type="danger" size="small" @click="handleDeleteList()">删除</el-button>
      <!-- <el-button type="danger"  size="small" icon="el-icon-refresh" @click="reload()">刷新</el-button> -->
      <el-divider />
    </div>
    <div class="list-model">
      <!-- <leftCard :categoryType="categoryType" :title="title" style="margin-top:0" ref="leftcard"/> -->
      <el-table height="600px" :cell-style="{ padding: '5px' }" border :data="list" tooltip-effect="dark"
        @selection-change="handleSelectionDelete" style="width: auto; margin-top: 20px"
        :header-cell-style="{ background: '#F2F6FC', color: '#606266' }">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="districtCode" label="区域号">
        </el-table-column>
        <el-table-column prop="districtName" label="区域名">
        </el-table-column>
        <el-table-column prop="sort" label="排序"> </el-table-column>
        <el-table-column prop="status" label="区域状态">
          <template slot-scope="scope">
            <!-- <span>{{ scope.row.status == 1 ? '未启用' : (scope.row.status == 2 ? '启用' : '停用') }}</span> -->
            <el-tag type="warning" size="medium" v-if="scope.row.status == 1">未启用</el-tag>
            <el-tag type="success" size="medium" v-if="scope.row.status == 2">启用</el-tag>
            <el-tag type="danger" size="medium" v-if="scope.row.status == 3">停用</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="inventoryKey" label="所属仓库">
        </el-table-column>
        <el-table-column prop="description" label="备注">
        </el-table-column>
        <el-table-column fixed="right" width="210px" label="操作">
          <template slot-scope="scope">
            <el-button @click="openDetail(scope.row)" type="text" icon="el-icon-document">详情</el-button>
            <el-button @click="editRow(scope.row)" type="text" icon="el-icon-edit">编辑</el-button>
            <el-button @click.native.prevent="deleteRow(scope.row)" type="text" icon="el-icon-delete">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="page-box">
        <el-pagination layout="total, sizes, prev, pager, next, jumper" :page-sizes="[10, 20, 50, 100]"
          :current="parseInt(pageNo)" :total="total" :page-size.sync="pageSize" :page-no.sync="pageNo"
          @current-change="_page" @size-change="_pageSize"></el-pagination>
      </div>

      <areaEdit v-if="areaEditVisable" :dialogVisible="areaEditVisable" :rowData="rowData"
        @close="areaEditVisable = false" @success="success()"></areaEdit>
      <!-- <areaDetail v-if="isShow" :drawer="isShow" :rowData="rowData" @close="isShow = false" @success="success()" /> -->
    </div>
  </div>
</template>

<script>
import areaEdit from "./areaEdit.vue";
// import areaDetail from "./areaDetail";
// import leftCard from '@/components/public/leftCard.vue'
import { districtlistPage, districtDelete, districtDeleteList, inventorylist } from "@/api/data";

export default {
  name: "inventoryList",
  data() {
    return {
      pageSize: 10,
      pageNo: 1,
      total: null,
      rowData: {},
      areaEditVisable: false,
      title: "区域分类",
      categoryType: 'WAREHOUSE',
      list: [],
      inputInventory: "",
      status: "",
      inputArea: "",
      inputName: "",
      // isShow: false,
      multipleSelection: [],
      options1: []
    };
  },
  props: {
  },
  components: {
    areaEdit,
    // areaDetail
    // leftCard
  },
  created() {
    this.getinventorylist()
    this.getDistrictlistPage()
  },
  methods: {
    getinventorylist() {
      inventorylist()
        .then((res) => {
          if (res.data.code === 200) {
            this.options1 = res.data.data
          } else {
            this.$message.error(res.msg);
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },
    _pageSize(val) {
      this.pageSize = val;
      this.getDistrictlistPage()
      this.$forceUpdate();
    },
    _page(val) {
      this.pageNo = val;
      this.getDistrictlistPage()
      this.$forceUpdate();
    },
    //详情
    openDetail(row) {
      this.rowData = row;
      // this.isShow = true//详情
      localStorage.setItem('areaDetail', JSON.stringify(row))
      this.$router.push({ name: "area-detail", params: { inventoryDistrictkey: row.inventoryDistrictkey, rowData: this.rowData } })
    },
    search() {
      districtlistPage({ districtCode: this.inputArea, districtName: this.inputName, inventoryKey: this.inputInventory, status: this.status, page: this.pageNo, size: this.pageSize }).then((res) => {
        this.list = res.data.data.records;
        this.total = res.data.data.total
        // console.log("区域")
        // console.log(this.total, this.list);
      });
      this.$forceUpdate();
    },
    deleteRow(row) {
      console.log("deleteRow", row)
      districtDelete({ inventoryDistrictkey: row.inventoryDistrictkey }).then(res => {
        if (res.data.code == 200) {
          this.$message.success("删除成功!");
          this.getDistrictlistPage()
          this.$forceUpdate()
        } else {
          this.$message.error("删除失败!");
        }
      });
    },
    editRow(row) {
      this.rowData = row;
      this.areaEditVisable = true;
    },
    handleClose(done) {
      done();
    },
    getDistrictlistPage() {
      districtlistPage({ districtCode: "", inventoryKey: this.$route.params.inventoryKey || "", page: this.pageNo, size: this.pageSize }).then((res) => {
        this.list = res.data.data.records;
        this.total = res.data.data.total
        // console.log("区域")
        // console.log(this.total, this.list);
      });
      this.$forceUpdate();
    },
    success() {
      this.rowData = {};
      this.areaEditVisable = false
      this.getDistrictlistPage()
      this.$forceUpdate()
    },
    clean() {
      this.inputArea = ''
      this.inputName = ''
      this.inputInventory = ''
      this.status = ''
      this.reload()
    },
    reload() {
      this.getDistrictlistPage()
      this.getinventorylist()
    },
    add() {
      this.editRow({})
    },
    //批量删除选择
    handleSelectionDelete(val) {
      this.multipleSelection = val
    },
    handleDeleteList() {
      if (this.multipleSelection.length > 0) {
        let inventoryDistrictkeys = [];
        this.multipleSelection.forEach(item => {
          inventoryDistrictkeys.push({ inventoryDistrictkey: item.inventoryDistrictkey })
        })
        console.log(inventoryDistrictkeys);
        this.$confirm('删除操作, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          districtDeleteList(inventoryDistrictkeys).then(response => {
            this.getDistrictlistPage();
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
</style>
