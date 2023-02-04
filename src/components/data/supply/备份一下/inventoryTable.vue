<template>
  <div class="wrap-definition">
    <el-button type="success" size="small" icon="el-icon-plus" @click="add()">新增</el-button>
    <el-button class="el-icon-delete" type="danger" size="small" @click="handleDeleteList()">删除</el-button>
    <el-divider />
    <div class="list-model">
      <el-table height="500px" :cell-style="{ padding: '5px' }" border :data="list" tooltip-effect="dark"
        @selection-change="handleSelectionDelete" style="width: auto; margin-top: 20px"
        :header-cell-style="{ background: '#F2F6FC', color: '#606266' }" class="table-fixed">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="inventoryCode" label="仓库编号"></el-table-column>
        <el-table-column prop="inventoryName" label="仓库名"> </el-table-column>
        <el-table-column prop="inventoryType" label="仓库类型">
          <template slot-scope="scope">
            <span>{{
              scope.row.inventoryType == 1 ? '供应商仓库'
                : (scope.row.inventoryType == 2 ? '门店仓'
                  : (scope.row.inventoryType == 3 ? '大仓'
                    : (scope.row.inventoryType == 4 ? '渠道仓'
                      : (scope.row.inventoryType == 5 ? '直发仓' : '-'))))
            }}</span>
          </template>
        </el-table-column>
        <!-- <el-table-column prop="belongKey" label="归属"></el-table-column> -->
        <el-table-column prop="contactName" label="联系人"></el-table-column>
        <el-table-column prop="tel" label="电话"> </el-table-column>
        <el-table-column prop="zipCode" label="邮编"> </el-table-column>
        <el-table-column prop="province" label="省"> </el-table-column>
        <el-table-column prop="city" label="市"> </el-table-column>
        <el-table-column prop="district" label="区" :show-overflow-tooltip="true"> </el-table-column>
        <el-table-column prop="detail" label="地址" :show-overflow-tooltip="true"> </el-table-column>
        <el-table-column prop="status" label="仓库状态">
          <template slot-scope="scope">
            <!-- <span>{{ scope.row.status == 1 ? '正常' : '关仓' }}</span> -->
            <el-tag type="success" size="medium" v-if="scope.row.status == 1">正常</el-tag>
            <el-tag type="danger" size="medium" v-if="scope.row.status == 2">关仓</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="categoryName" label="所属分类">
        </el-table-column>
        <el-table-column prop="description" label="备注" :show-overflow-tooltip="true"> </el-table-column>
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
    </div>
    <configEdit v-if="drawer" :drawer="drawer" :rowData="row" @close="drawer = false" @success="success()"
      :supplierInventoryList="supplierInventoryList" />
    <inventoryEdit ref="inventoryEdit" v-if="inventoryEditVisable" :dialogVisible="inventoryEditVisable"
      :rowData="row" @close="inventoryEditVisable = false" @success="success()" />
    <inventoryDetail v-if="isShow" :drawer="isShow" :rowData="row" @close="isShow = false" @success="success()" />
  </div>
</template>

<script>
import { supplierInventoryDelete, supplierInventoryDeleteList } from "@/api/warehouse";
import configEdit from "@/components/store/suppliers/configuration/configEdit.vue";
import inventoryEdit from "@/components/data/inventory/inventory/inventoryEdit.vue";
import inventoryDetail from "@/components/data/inventory/inventory/inventoryDetail.vue";
import { inventorylistPage } from "@/api/data";

export default {
  name: "inventoryList",
  data() {
    return {
      isShow: false,
      inventoryEditVisable: false,
      pageSize: 10,
      pageNo: 1,
      total: null,
      drawer: false,
      row: {},
      list: [],
      inputCategory: "",
      inventoryName: "",
      status: "",
      inventoryCode: "",
      multipleSelection: [],
      supplierInventoryList: []
    };
  },
  props: {
    rowData: {},
  },
  components: {
    configEdit,
    inventoryEdit,
    inventoryDetail
  },
  created() {
    this.row = this.rowData
    console.log("this.row", this.row)
  },
  mounted() {
    this.getInventorylistPage();
  },
  methods: {
    editRow(row) {
      this.row = row;
      this.inventoryEditVisable = true;
    },
    //详情
    openDetail(row) {
      this.row = row;
      this.isShow = true//详情
    },
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
      console.log("deleteRow", row)
      supplierInventoryDelete({ belongKey: this.row.belongKey, inventoryKey: row.inventoryKey }).then(res => {
        if (res.data.code == 200) {
          this.$message.success("删除成功!");
          this.getInventorylistPage()
          this.$forceUpdate()
        } else {
          this.$message.error("删除失败!");
        }
      });
    },
    getInventorylistPage() {
      inventorylistPage({ belongKey: this.row.supplierKey, page: this.pageNo, size: this.pageSize }).then(
        (res) => {
          this.list = res.data.data.records;
          this.total = res.data.data.total;
          console.log("仓库");
          // console.log(this.total, this.list);
        }
      );
      this.$forceUpdate();
    },
    success() {
      this.drawer = false;
      this.getInventorylistPage();
    },
    add() {
      this.drawer = true
      this.row={belongKey:this.row.supplierKey}
      console.log("rowrowrowrow",this.row);
    },
    //批量删除选择
    handleSelectionDelete(val) {
      this.multipleSelection = val
    },
    handleDeleteList() {
      if (this.multipleSelection.length > 0) {
        let SupplierInventorys = [];
        this.multipleSelection.forEach(item => {
          SupplierInventorys.push({ belongKey: item.supplierKey, inventoryKey: item.inventoryKey })
        })
        console.log(SupplierInventorys);
        this.$confirm('删除操作, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          supplierInventoryDeleteList(SupplierInventorys).then(() => {
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

.wrap-definition {
  background: #fff;
}
</style>
