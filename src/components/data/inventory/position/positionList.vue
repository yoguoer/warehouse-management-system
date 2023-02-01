<template>
  <div>
    <div>
      <el-input placeholder="货位编号" v-model="inputCode" clearable type="text" size="small"
        style="width: 300px; margin-right: 20px">
        <template slot="prepend">货位编号</template>
      </el-input>
      <el-select size="small" v-model="inputInventory" clearable placeholder="所属仓库"
        style="width:200px;margin-right:20px;" @change="getdistrictlist()">
        <el-option v-for="item in options1" :key="item.inventoryKey" :label="item.inventoryName"
          :value="item.inventoryKey">
        </el-option>
      </el-select>
      <el-select size="small" v-model="inputDistrict" clearable placeholder="所属区域"
        style="width:200px;margin-right:20px;">
        <el-option v-for="item in options2" :key="item.inventoryDistrictkey" :label="item.districtName"
          :value="item.inventoryDistrictkey">
        </el-option>
      </el-select>
      <el-select size="small" v-model="inputpositionType" placeholder="货位类型" clearable
        style="width:200px;margin-right:20px;">
        <el-option label="存储位" value="store" key="store"></el-option>
        <el-option label="集货位" value="assemble" key="assemble"></el-option>
      </el-select>
      <el-button type="primary" size="small" @click="search()" icon="el-icon-search">查询</el-button>
      <el-button size="small" @click="clean()" icon="el-icon-refresh" type="warning">重置</el-button>
      <el-button type="success" size="small" icon="el-icon-plus" @click="add()">新增</el-button>
      <el-button class="el-icon-delete" type="danger" size="small" @click="handleDeleteList()">删除</el-button>
      <!-- <el-button type="danger"  size="small" icon="el-icon-refresh" @click="reload()">刷新</el-button> -->
      <el-divider />
    </div>
    <div class="list-model">
      <!-- <leftCard :categoryType="categoryType" :title="title" style="margin-top:0"/> -->
      <el-table height="600px" :cell-style="{ padding: '5px' }" border :data="list" tooltip-effect="dark"
        @selection-change="handleSelectionDelete" style="width: auto; margin-top: 20px"
        :header-cell-style="{ background: '#F2F6FC', color: '#606266' }">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="positionCode" label="货位编号">
        </el-table-column>
        <el-table-column prop="positionType" label="货位类型">
          <template slot-scope="scope">{{ scope.row.positionType == 'store' ? '存储位' : '集货位' }}</template>
        </el-table-column>
        <el-table-column prop="maxWeight" label="最大重量">
        </el-table-column>
        <el-table-column prop="maxCapacity" label="最大容量(m3)">
        </el-table-column>
        <el-table-column prop="inventoryDistrictkey" label="所属区域">
        </el-table-column>
        <el-table-column prop="description" label="备注">
        </el-table-column>
        <el-table-column fixed="right" width="210px" label="操作" style="margin-top: -10px!important;">
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

      <positionEdit v-if="positionEditVisable" :dialogVisible="positionEditVisable" :rowData="rowData"
        @close="positionEditVisable = false" @success="success()"></positionEdit>
      <positionDetail v-if="isShow" :drawer="isShow" :rowData="rowData" @close="isShow = false" @success="success()" />
    </div>
  </div>
</template>

<script>
import positionEdit from "./positionEdit.vue";
import positionDetail from "./positionDetail";
// import leftCard from '@/components/public/leftCard.vue'
import { positionlistPage, positionDelete, districtlist, inventorylist, positionDeleteList } from "@/api/data";

export default {
  name: "positionList",
  data() {
    return {
      inputCode: "",
      inputDistrict: "",
      inputInventory: "",
      inputpositionType: "",
      pageSize: 10,
      pageNo: 1,
      isShow: false,
      total: null,
      rowData: {},
      positionEditVisable: false,
      title: "库位分类",
      categoryType: "WAREHOUSE",
      list: [],
      options1: [],
      options2: [],
      multipleSelection: [],
      query: {
        inventoryDistrictkey: "",
        inventoryKey: ""
      },
    };
  },
  props: {},
  components: {
    positionEdit,
    positionDetail
    // leftCard
  },
  created() {
    if (this.$route.params.inventoryKey) {
      this.query.inventoryKey = this.$route.params.inventoryKey
    } if (this.$route.params.rowData) {
      this.query.inventoryDistrictkey = this.$route.params.rowData.inventoryDistrictkey || ""
    }
    this.getinventorylist()
    this.getPositionlistPage();
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
    getdistrictlist() {
      let data = ""
      if (this.inputInventory) {
        this.inputDistrict = ""
        data = this.inputInventory
      }
      districtlist({ inventoryKey: data })
        .then((res) => {
          if (res.data.code === 200) {
            this.options2 = res.data.data
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
      this.getPositionlistPage();
      this.$forceUpdate();
    },
    _page(val) {
      this.pageNo = val;
      this.getPositionlistPage();
      this.$forceUpdate();
    },
    search() {
      positionlistPage({
        positionCode: this.inputCode,
        inventoryDistrictkey: this.inputDistrict,
        positionType: this.inputpositionType,
        inventoryKey: this.inputInventory,
        page: this.pageNo,
        size: this.pageSize
      }).then((res) => {
        this.list = res.data.data.records;
        this.total = res.data.data.total;
        // console.log("区域");
        // console.log(this.total, this.list);
      });
      this.$forceUpdate();
    },
    deleteRow(row) {
      console.log("deleteRow", row);
      positionDelete({ positionKey: row.positionKey }).then(
        (res) => {
          if (res.data.code == 200) {
            this.$message.success("删除成功!");
            this.getPositionlistPage();
            this.$forceUpdate();
          } else {
            this.$message.error("删除失败!");
          }
        }
      );
    },
    //详情
    openDetail(row) {
      this.rowData = row;
      this.isShow = true//详情
      // this.$router.push({ name: "inventory-detail",params:{rowData:this.rowData} })
    },
    editRow(row) {
      this.rowData = row;
      this.positionEditVisable = true;
    },
    handleClose(done) {
      done();
    },
    clean() {
      this.inputCode = ''
      this.inputDistrict = ''
      this.inputInventory = ''
      this.inputpositionType = ''
      this.reload()
    },
    getPositionlistPage() {
      positionlistPage({
        inventoryKey: this.query.inventoryKey || "",
        inventoryDistrictkey: this.query.inventoryDistrictkey || "",
        positionType: "",
        positionCode: "",
        page: this.pageNo,
        size: this.pageSize,
      }).then((res) => {
        this.list = res.data.data.records;
        this.total = res.data.data.total;
        // console.log("库位");
        // console.log(this.total, this.list);
      });
      this.$forceUpdate();
    },
    success() {
      this.positionEditVisable = false;
      this.rowData = {};
      this.getPositionlistPage();
    },
    reload() {
      this.getPositionlistPage()
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
        let positionkeys = [];
        this.multipleSelection.forEach(item => {
          positionkeys.push({ positionKey: item.positionKey })
        })
        console.log(positionkeys);
        this.$confirm('删除操作, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          positionDeleteList(positionkeys).then(response => {
            this.getPositionlistPage();
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
