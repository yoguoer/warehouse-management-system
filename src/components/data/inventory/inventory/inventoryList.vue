<template>
  <div>
    <div>
      <el-input placeholder="仓库编号" v-model="inventoryCode" type="text" size="small"
        style="width: 300px; margin-right: 20px" clearable>
        <template slot="prepend">仓库编号</template>
      </el-input>
      <el-input placeholder="仓库名称" v-model="inventoryName" type="text" size="small"
        style="width: 300px; margin-right: 20px" clearable>
        <template slot="prepend">仓库名称</template>
      </el-input>
      <el-select size="mini" v-model="inventoryType" placeholder="请选择仓库类型" style="width:200px;margin-right:20px;"
        clearable>
        <el-option label="供应商仓库" :value="1"></el-option>
        <el-option label="零售商仓库" :value="2"></el-option>
      </el-select>
      <el-select size="mini" v-model="status" placeholder="仓库状态" style="width:200px;margin-right:20px;">
        <el-option label="正常" :value="1"></el-option>
        <el-option label="关仓" :value="2"></el-option>
      </el-select>
      <el-button type="primary" size="small" @click="search()" icon="el-icon-search">查询</el-button>
      <el-button size="small" @click="clean()" icon="el-icon-refresh" type="warning">重置</el-button>
      <el-button type="success" size="small" icon="el-icon-plus" @click="add()">新增</el-button>
      <el-button class="el-icon-delete" type="danger" size="small" @click="handleDeleteList()">删除</el-button>
      <el-divider />
    </div>
    <div class="list-model">
      <leftCard ref="leftcard" :categoryType="categoryType" :title="title" style="margin-top:0;height:600px;" />
      <el-table height="600px" :cell-style="{ padding: '5px' }" border :data="list" tooltip-effect="dark"
        @selection-change="handleSelectionDelete" style="width: auto; margin-top: 20px"
        :header-cell-style="{ background: '#F2F6FC', color: '#606266' }" class="table-fixed">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="inventoryCode" label="仓库编号" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="inventoryName" sortable label="仓库名" :show-overflow-tooltip="true"> </el-table-column>
        <el-table-column prop="inventoryType" label="仓库类型" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>{{
              scope.row.inventoryType == 1 ? '供应商仓库'
              : (scope.row.inventoryType == 2 ? '零售商仓库' : '-')
            }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="contactName" label="联系人" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="tel" label="电话" :show-overflow-tooltip="true"> </el-table-column>
        <el-table-column prop="zipCode" label="邮编" :show-overflow-tooltip="true"> </el-table-column>
        <el-table-column prop="province" sortable label="省" :show-overflow-tooltip="true"> </el-table-column>
        <el-table-column prop="city" label="市" :show-overflow-tooltip="true"> </el-table-column>
        <el-table-column prop="district" label="区" :show-overflow-tooltip="true"> </el-table-column>
        <el-table-column prop="detail" label="详细地址" :show-overflow-tooltip="true"> </el-table-column>
        <el-table-column prop="status" label="仓库状态">
          <template slot-scope="scope">
            <el-tag type="success" size="medium" v-if="scope.row.status == 1">正常</el-tag>
            <el-tag type="danger" size="medium" v-if="scope.row.status == 2">关仓</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="categoryName" label="所属分类" :show-overflow-tooltip="true">
        </el-table-column>
        <el-table-column prop="description" label="备注" :show-overflow-tooltip="true"> </el-table-column>
        <el-table-column fixed="right" width="210px" label="操作">
          <template slot-scope="scope">
            <el-button @click="openDetail(scope.row)" type="text" icon="el-icon-document">详情</el-button>
            <el-button @click="editRow(scope.row)" type="text" icon="el-icon-edit"  v-if="userType<2&&userBelong!=1">编辑</el-button>
            <el-button @click.native.prevent="deleteRow(scope.row)" type="text" icon="el-icon-delete"  v-if="userType<2&&userBelong!=1">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="page-box">
        <el-pagination layout="total, sizes, prev, pager, next, jumper" :page-sizes="[10, 20, 50, 100]"
          :current-page="parseInt(pageNo)" :total="total" :page-size="pageSize" :page-no="pageNo" @current-change="_page"
          @size-change="_pageSize"></el-pagination>
      </div>
      <inventoryEdit ref="inventoryEdit" v-if="inventoryEditVisable" :dialogVisible="inventoryEditVisable"
        :rowData="rowData" @close="inventoryEditVisable = false" @success="success()" />
      <inventoryDetail v-if="isShow" :drawer="isShow" :rowData="rowData" @close="isShow = false" @success="success()" />
    </div>
  </div>
</template>

<script>
import inventoryEdit from "../inventory/inventoryEdit.vue";
import leftCard from "@/components/public/leftCard.vue";
import inventoryDetail from "./inventoryDetail";
import { inventorylistPage, inventoryDelete, inventoryDeleteList } from "@/api/data";

export default {
  name: "inventoryList",
  data() {
    return {
      pageSize: 10,
      pageNo: 1,
      total: null,
      rowData: {},
      inventoryEditVisable: false,
      title: "仓库分类",
      categoryType: "WAREHOUSE",
      list: [],
      inputCategory: "",
      inventoryName: "",
      status: "",
      inventoryCode: "",
      inventoryType: "",
      isShow: false,
      userType:"",
      userBelong:"",
      multipleSelection: [],
    };
  },
  props: {},
  components: {
    inventoryEdit,
    leftCard,
    inventoryDetail
  },
  created() {
    this.userType = this.$store.state.user.userType
    this.userBelong = this.$store.state.user.userBelong
    this.getInventorylistPage();
  },
  methods: {
    _pageSize(val) {
      this.pageSize = val;
      this.getInventorylistPage();
      this.$forceUpdate();
    },
    _page(val) {
      this.pageNo = val;
      this.getInventorylistPage();
      this.$forceUpdate();
    },
    deleteRow(row) {
      // console.log("deleteRow", row)
      this.$confirm('删除操作, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        inventoryDelete({ inventoryKey: row.inventoryKey }).then(res => {
          if (res.data.code == 200) {
            this.$message.success("删除成功!");
            this.getInventorylistPage()
            this.$forceUpdate()
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
      this.rowData = row;
      this.inventoryEditVisable = true;
    },
    handleClose(done) {
      done();
    },
    clean() {
      this.inventoryName = ''
      this.status = ''
      this.inventoryCode = ''
      this.inventoryType = ''
      this.inputCategory = ''
      this.$refs.leftcard.isActive = ''
      this.pageNo = 1
      this.getInventorylistPage()
      this.$refs.leftcard.getTree()
    },
    //详情
    openDetail(row) {
      this.rowData = row;
      // this.isShow = true//详情
      localStorage.setItem('inventoryDetail', JSON.stringify(row))
      this.$router.push({ name: "inventory-detail", params: { inventoryKey: row.inventoryKey, rowData: this.rowData } })
    },
    search() {
      inventorylistPage({
        inventoryName: this.inventoryName,
        inventoryCode: this.inventoryCode,
        inventoryType: this.inventoryType,
        categoryKey: this.inputCategory,
        status: this.status,
        page: 1,
        size: this.pageSize,
      }).then((res) => {
        this.list = res.data.data.records;
        this.total = res.data.data.total;
        // console.log("仓库");
        // console.log(this.total, this.list);
      });
      this.$forceUpdate()
    },
    getInventorylistPage() {
      inventorylistPage({ page: this.pageNo, size: this.pageSize }).then(
        (res) => {
          this.list = res.data.data.records;
          this.total = res.data.data.total;
          // console.log("仓库");
          // console.log(this.total, this.list);
        }
      );
      this.$forceUpdate();
    },
    success() {
      this.rowData = {};
      this.inventoryEditVisable = false
      this.getInventorylistPage()
      this.$forceUpdate()
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
        let inventorykeys = [];
        this.multipleSelection.forEach(item => {
          inventorykeys.push({ inventoryKey: item.inventoryKey })
        })
        console.log(inventorykeys);
        this.$confirm('删除操作, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          inventoryDeleteList(inventorykeys).then(() => {
            this.getInventorylistPage();
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
