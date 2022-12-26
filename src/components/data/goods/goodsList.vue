<template>
  <div>
    <div>
      <el-input placeholder="商品名" v-model="goodsName" type="text" clearable size="small"
        style="width:300px;margin-right:20px">
        <template slot="prepend">商品名</template>
      </el-input>
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
        <!-- <el-table-column prop="goodsKey" label="商品ID" :show-overflow-tooltip="true">
        </el-table-column> -->
        <el-table-column prop="goodsCode" label="商品货号" :show-overflow-tooltip="true">
        </el-table-column>
        <el-table-column prop="goodsName" label="商品名" :show-overflow-tooltip="true">
        </el-table-column>
        <el-table-column prop="goodsBarcode" label="商品条码" :show-overflow-tooltip="true">
        </el-table-column>
        <el-table-column prop="modelCode" label="型号" :show-overflow-tooltip="true">
        </el-table-column>
        <el-table-column prop="inventoryName" label="默认仓库" :show-overflow-tooltip="true">
        </el-table-column>
        <el-table-column prop="supplierName" label="所属供应商" :show-overflow-tooltip="true">
        </el-table-column>
        <el-table-column prop="brandCode" label="所属品牌" :show-overflow-tooltip="true">
        </el-table-column>
        <el-table-column prop="state" label="状态">
          <template slot-scope="scope">{{ scope.row.state == 1 ? '上架' : '下架' }}</template>
        </el-table-column>
        <!-- <el-table-column prop="goodsHeadPic" label="商品头图">
        </el-table-column> -->
        <el-table-column prop="categoryName" label="所属分类" :show-overflow-tooltip="true">
        </el-table-column>
        <el-table-column prop="createTime" label="添加时间" :show-overflow-tooltip="true">
          <template slot-scope="scope">{{ scope.row.createTime | datefmt('YYYY-MM-DD HH:mm:ss') }}</template>
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
      <goodsEdit v-if="drawer" :drawer="drawer" :rowData="rowData" @close="drawer = false" @success="_success()" />
    </div>
  </div>
</template>

<script>
import goodsEdit from "./goodsEdit";
import { goodslistPage, goodsDelete, goodsDeleteList } from "@/api/data";
import leftCard from '@/components/public/leftCard.vue'

export default {
  name: "goodsList",
  data() {
    return {
      inputCategory: '',
      goodsName: '',
      pageSize: 10,
      pageNo: 1,
      total: null,
      ifCreate: false,
      rowData: {},
      drawer: false,
      goodsList: [],
      title: "商品分类",
      categoryType: 'GOODS',
      multipleSelection: [],
    };
  },
  created() {
    this.getGoodslistPage();
  },
  methods: {
    search() {
      goodslistPage({
        categoryKey: this.inputCategory,
        goodsName: this.goodsName,
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
      this.getGoodslistPage();
      this.$forceUpdate();
    },
    _page(val) {
      this.pageNo = val;
      this.getGoodslistPage();
      this.$forceUpdate();
    },
    deleteRow(row) {
      console.log("deleteRow", row);
      goodsDelete({ goodsKey: row.goodsKey }).then((res) => {
        if (res.data.code == 200) {
          this.$message.success("删除成功!");
          this.getGoodslistPage();
          this.$forceUpdate();
        } else {
          this.$message.error("删除失败!");
        }
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
        page: this.pageNo,
        size: this.pageSize,
      }).then((res) => {
        this.total = res.data.data.total
        this.goodsList = res.data.data.records;
        console.log("goodsList:", this.goodsList);
      });
      this.$forceUpdate();
    },
    success() {
      this.drawer = false;
      this.rowData = {};
      this.getGoodslistPage();
    },
    reload() {
      this.getGoodslistPage()
      this.$refs.leftcard.getTree()
    },
    add() {
      this.editRow({})
    },
    clean() {
      this.goodsName = ''
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
