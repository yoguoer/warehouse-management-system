<template>
  <div>
    <div>
      <el-input placeholder="商品货号" v-model="goodsCode" type="text" clearable size="small"
        style="width:300px;margin-right:20px">
        <template slot="prepend">商品货号</template>
      </el-input>
      <el-input placeholder="商品名" v-model="goodsName" type="text" clearable size="small"
        style="width:300px;margin-right:20px">
        <template slot="prepend">商品名</template>
      </el-input>
      <el-select size="middle" v-model="supplierKey" placeholder="所属供应商" style="width:300px;margin-right:20px" clearable>
        <el-option v-for="item in supplyOptions" :key="item.supplierKey" :label="item.supplierName"
          :value="item.supplierKey" placeholder="所属供应商">
        </el-option>
      </el-select>
      <el-select size="middle" v-model="brandCode" placeholder="品牌" style="width:200px;margin-right:20px" clearable>
        <el-option v-for="item in brandOptions" :key="item.brandKey" :label="item.brandName" :value="item.brandCode">
        </el-option>
      </el-select>
      <el-select size="mini" v-model="state" placeholder="状态" style="width:200px;margin-right:20px;">
        <el-option label="上架" :value="1"></el-option>
        <el-option label="下架" :value="0"></el-option>
      </el-select>
      <el-button type="primary" size="small" @click="search()" icon="el-icon-search">查询</el-button>
      <el-button size="small" @click="clean()" icon="el-icon-refresh" type="warning">重置</el-button>
      <el-button type="success" size="small" icon="el-icon-plus" @click="add()">新增</el-button>
      <el-button class="el-icon-delete" type="danger" size="small" @click="handleDeleteList()">删除</el-button>
      <el-divider />
    </div>
    <leftCard :categoryType="categoryType" :title="title" style="height:600px;" ref="leftcard" />
    <div class="list-model">
      <el-table height="600px" :cell-style="{ padding: '5px' }" border :data="goodsList" tooltip-effect="dark"
        @selection-change="handleSelectionDelete" style="width: auto; margin-top: 20px"
        :header-cell-style="{ background: '#F2F6FC', color: '#606266' }">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="goodsCode" label="商品货号" sortable :show-overflow-tooltip="true" width="120px">
        </el-table-column>
        <el-table-column prop="goodsName" label="商品名" sortable :show-overflow-tooltip="true" width="500px">
        </el-table-column>
        <el-table-column prop="goodsBarcode" label="商品条码" :show-overflow-tooltip="true">
        </el-table-column>
        <el-table-column prop="modelCode" label="型号" :show-overflow-tooltip="true">
        </el-table-column>
        <el-table-column prop="supplierName" label="所属供应商" :show-overflow-tooltip="true" width="200px">
        </el-table-column>
        <el-table-column prop="brandCode" label="所属品牌" :show-overflow-tooltip="true">
        </el-table-column>
        <el-table-column prop="priceLatestPurchase" label="进价" :show-overflow-tooltip="true" width="80px">
        </el-table-column>
        <el-table-column prop="priceRetail" label="零售价" :show-overflow-tooltip="true" width="80px">
        </el-table-column>
        <el-table-column prop="priceWholesaler" label="批发价" :show-overflow-tooltip="true" width="80px">
        </el-table-column>
        <el-table-column prop="goodsUnit" label="单位" :show-overflow-tooltip="true" width="50px">
        </el-table-column>
        <el-table-column prop="state" label="状态" width="80px">
          <template slot-scope="scope">{{ scope.row.state == 1 ? '上架' : '下架' }}</template>
        </el-table-column>
        <el-table-column prop="categoryName" label="所属分类" :show-overflow-tooltip="true">
        </el-table-column>
        <el-table-column prop="createTime" label="添加时间" :show-overflow-tooltip="true" sortable width="120px">
          <template slot-scope="scope">{{ scope.row.createTime | datefmt('YYYY-MM-DD HH:mm:ss') }}</template>
        </el-table-column>
        <el-table-column fixed="right" width="250px" label="操作">
          <template slot-scope="scope">
            <el-button class="prohibitclick" @click="setON(scope.row)" type="text" size="small"
              v-if="scope.row.state == 0" icon="el-icon-sunny">上架</el-button>
            <el-button class="prohibitclick" @click="setDOWN(scope.row)" type="text" size="small"
              v-if="scope.row.state == 1" icon="el-icon-moon">下架</el-button>
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
      <goodsEdit v-if="drawer" :drawer="drawer" :rowData="rowData" @close="drawer = false" @success="_success()" />
    </div>
  </div>
</template>

<script>
import goodsEdit from "./goodsEdit";
import { goodslistPage, goodsDelete, goodsDeleteList } from "@/api/data";
import leftCard from '@/components/public/leftCard.vue'
import { Supplierlist, brandlist, goodsUpdate } from '@/api/data'

export default {
  name: "goodsList",
  data() {
    return {
      inputCategory: '',
      goodsName: '',
      goodsCode: '',
      state: '',
      supplierKey: '',
      brandCode: '',
      pageSize: 10,
      pageNo: 1,
      total: null,
      ifCreate: false,
      rowData: {},
      drawer: false,
      supplyOptions: [],
      brandOptions: [],
      goodsList: [],
      title: "商品分类",
      categoryType: 'GOODS',
      multipleSelection: [],
    };
  },
  created() {
    this.getGoodslistPage()
    this.getbrandlist();
    this.getSupplierlist()
  },
  methods: {
    search() {
      goodslistPage({
        categoryKey: this.inputCategory,
        supplierKey: this.supplierKey,
        brandCode: this.brandCode,
        goodsName: this.goodsName,
        goodsCode: this.goodsCode,
        state: this.state,
        page: this.pageNo,
        size: this.pageSize,
      }).then((res) => {
        this.total = res.data.data.total
        this.goodsList = res.data.data.records;
        console.log("goodsList:", this.goodsList);
      });
      this.$forceUpdate();
    },
    _pageSize(val) {
      this.pageSize = val;
      this.search();
      this.$forceUpdate();
    },
    _page(val) {
      this.pageNo = val;
      this.search();
      this.$forceUpdate();
    },
    setON(row) {
      let Form = {
        goodsKey: row.goodsKey,
        state: 1
      }
      goodsUpdate(Form).then(res => {
        if (res.data.code == 200) {
          this.$message.success("上架成功!");
          this.getGoodslistPage()
          this.$forceUpdate()
        } else {
          this.$message.error("上架失败!");
        }
      })
    },
    setDOWN(row) {
      let Form = {
        goodsKey: row.goodsKey,
        state: 0
      }
      goodsUpdate(Form).then(res => {
        if (res.data.code == 200) {
          this.$message.success("下架成功!");
          this.getGoodslistPage()
          this.$forceUpdate()
        } else {
          this.$message.error("下架失败!");
        }
      })
    },
    deleteRow(row) {
      // console.log("deleteRow", row);
      this.$confirm('删除操作, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        goodsDelete({ goodsKey: row.goodsKey }).then((res) => {
          if (res.data.code == 200) {
            this.$message.success("删除成功!");
            this.getGoodslistPage();
            this.$forceUpdate();
          } else {
            this.$message.error("删除失败!");
          }
        });

      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    editRow(row) {
      console.log("edit");
      this.rowData = row;
      this.drawer = true;
    },
    getGoodslistPage() {
      goodslistPage({
        categoryKey: "",
        goodsName: "",
        goodsCode: "",
        supplierKey: "",
        brandCode: "",
        state: "",
        page: this.pageNo,
        size: this.pageSize,
      }).then((res) => {
        this.total = res.data.data.total
        this.goodsList = res.data.data.records;
        console.log("goodsList:", this.goodsList);
      });
      this.$forceUpdate();
    },
    getbrandlist() {
      brandlist()
        .then((res) => {
          if (res.data.code === 200) {
            this.brandOptions = res.data.data
          } else {
            this.$message.error(res.msg);
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },
    getSupplierlist() {
      Supplierlist().then(res => {
        if (res.data.code == 200) {
          this.supplyOptions = res.data.data
        } else {
          this.$message.error("获取失败!");
        }
      });
    },
    success() {
      this.drawer = false;
      this.rowData = {};
      this.getGoodslistPage();
    },
    reload() {
      this.getGoodslistPage()
      this.$refs.leftcard.getTree()
      this.$forceUpdate()
    },
    add() {
      this.editRow({})
    },
    clean() {
      this.goodsName = ''
      this.supplierKey = ''
      this.brandCode = ''
      this.goodsCode = ''
      this.state = ''
      this.inputCategory = ''
      this.$refs.leftcard.isActive = ''
      this.reload()
    },
    //批量删除选择
    handleSelectionDelete(val) {
      this.multipleSelection = val
    },
    handleDeleteList() {
      if (this.multipleSelection.length > 0) {
        let goodsKeys = [];
        this.multipleSelection.forEach(item => {
          goodsKeys.push({ goodsKey: item.goodsKey })
        })
        console.log(goodsKeys);
        this.$confirm('删除操作, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          goodsDeleteList(goodsKeys).then(() => {
            this.getGoodslistPage();
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
  props: {},
  components: {
    goodsEdit,
    leftCard
  },
};
</script>
<style lang="scss" scoped>
.page-box {
  text-align: right;
  margin: 20px;
}

.list-model {
  width: 100%;
}

.el-table {
  overflow-y: auto !important;
  overflow-x: auto !important;
}

.special {
  background-color: #F5F7FA;
  color: #909399;
  display: table-cell;
  position: relative;
  border: 1px solid #DCDFE6;
  border-radius: 4px 0 0 4px;
  padding: 2px 20px;
  width: 1px;
  line-height: 30px;
  white-space: nowrap;
  font-size: 14px;
}

.el-select .el-input__inner {
  height: 32px !important;
  line-height: 32px !important;
}
</style>
