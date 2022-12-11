<template>
  <div>
    <!-- <el-button type="primary" plain size="small" icon="el-icon-refresh" @click="reload()">刷新</el-button> -->
    <!-- <el-button type="primary" plain size="small" icon="el-icon-plus" @click="add()">新增</el-button> -->
    <!-- <el-divider/> -->
    <div>
      <el-input placeholder="区域号" v-model="inputArea" clearable type="text" size="small" 
        style="width: 300px; margin-right: 20px">
        <template slot="prepend">区域号</template>
      </el-input>
      <el-select size="small" clearable v-model="inputInventory" placeholder="所属仓库"
        style="width:200px;margin-right:20px;" @change="getdistrictlist()">
        <el-option v-for="item in options1" :key="item.inventoryKey" :label="item.inventoryName"
          :value="item.inventoryKey">
        </el-option>
      </el-select>
      <el-button type="primary" size="small" @click="search()" icon="el-icon-search">查询</el-button>
      <el-button size="small" @click="clean()" icon="el-icon-refresh" type="warning">重置</el-button>
      <el-button type="success" size="small" icon="el-icon-plus" @click="add()">新增</el-button>
      <el-button class="el-icon-delete" type="danger" size="small" @click="handleDeleteList()">删除</el-button>
      <!-- <el-button type="danger"  size="small" icon="el-icon-refresh" @click="reload()">刷新</el-button> -->
      <el-divider/>
    </div>
    <div class="list-model">
      <!-- <leftCard :categoryType="categoryType" :title="title" style="margin-top:0" ref="leftcard"/> -->
      <el-table height="600px" :cell-style="{ padding: '5px' }" border :data="list" tooltip-effect="dark" @selection-change="handleSelectionDelete"
        style="width: auto; margin-top: 20px" :header-cell-style="{background:'#F2F6FC',color:'#606266'}">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="districtCode" label="区域号">
        </el-table-column>
        <el-table-column prop="inventoryKey" label="所属仓库">
        </el-table-column>
        <el-table-column prop="sort" label="排序"> </el-table-column>
        <el-table-column prop="description" label="备注">
        </el-table-column>
        <el-table-column fixed="right" width="210px" label="操作">
          <template slot-scope="scope">
            <el-button @click="openDetail(scope.row)" type="text" icon="el-icon-document">详情</el-button>
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

      <areaEdit v-if="areaEditVisable" :dialogVisible="areaEditVisable" :rowData="rowData"
        @close="areaEditVisable = false" @success="_success()"></areaEdit>
        <areaDetail v-if="isShow" :drawer="isShow" :rowData="rowData" @close="isShow = false" @success="success()" />
    </div>
  </div>
</template>

<script>
import areaEdit from "./areaEdit.vue";
import areaDetail from "./areaDetail";
// import leftCard from '@/components/public/leftCard.vue'
import { districtlistPage,districtDelete,districtDeleteList,inventorylist } from "@/api/data";

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
      inputArea: "",
      isShow:false,
      options1:[]
    };
  },
  props: {
  },
  components: {
    areaEdit,
    areaDetail
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
      this.isShow=true//详情
      // this.$router.push({ name: "inventory-detail",params:{rowData:this.rowData} })
    },
    search() {
      districtlistPage({ districtCode: this.inputArea, inventoryKey: this.inputInventory, page: this.pageNo, size: this.pageSize }).then((res) => {
        this.list = res.data.data.records;
        this.total = res.data.data.total
        console.log("区域")
        console.log(this.total, this.list);
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
      districtlistPage({ districtCode: "", inventoryKey: this.$route.params.inventoryKey||"", page: this.pageNo, size: this.pageSize }).then((res) => {
        this.list = res.data.data.records;
        this.total = res.data.data.total
        console.log("区域")
        console.log(this.total, this.list);
      });
      this.$forceUpdate();
    },
    success() {
      this.rowData = {};
      this.areaEditVisable = false
      this.getDistrictlistPage()
      this.$forceUpdate()
    },
    clean(){
      this.inputArea = ''
      this.inputInventory = ''
      this.reload()
    },
    reload(){
      this.getDistrictlistPage()
      this.getinventorylist()
    },
    add(){
      this.editRow({})
    },
    //批量删除选择
    handleSelectionDelete(val) {
      this.multipleSelection = val
    },
    //根据 userId 批量删除用户
    handleDeleteList() {
      let inventoryDistrictkeys = [];
      this.multipleSelection.forEach(item => {
        inventoryDistrictkeys.push({inventoryDistrictkey:item.inventoryDistrictkey})
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
      });
    },
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
</style>
