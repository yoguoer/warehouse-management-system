<template>
  <el-drawer size="95%" title="仓库详情" :direction="direction" :visible.sync="myDrawer" :close-on-press-escape="false"
    :show-close="true" :before-close="handleClose" :wrapperClosable="false">
    <div style="padding:0 20px;">
      <el-row style="margin-bottom: 20px;">
        <el-col :span="5">
          <span>仓库编号: {{ row.inventoryCode }}</span>
        </el-col>
        <el-col :span="5">
          <span>仓库名: {{ row.inventoryName }}</span>
        </el-col>
        <el-col :span="5">
          <span>联系人: {{ row.contactName }}</span>
        </el-col>
        <el-col :span="5">
          <span>电话: {{ row.tel }}</span>
        </el-col>
      </el-row>
      <el-row>
        <el-tabs type="card" v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="区域" name="inventory-area">
          </el-tab-pane>
          <el-tab-pane label="货位" name="inventory-position">
          </el-tab-pane>
          <router-view></router-view>
        </el-tabs>
      </el-row>
    </div>
  </el-drawer>
</template>
  
  
<script>

export default {
  name: 'inventoryDetail',
  data() {
    return {
      activeName: 'inventory-area',
      direction: 'btt',
      row: {},
      myDrawer: true,
    }
  },
  props: {
    drawer: {
      default: true,
    },
    rowData: {},
  },
  components: {
  },
  watch: {

  },
  created() {
    this.row = this.$route.params.rowData || this.rowData
    this.$router.push({ name: this.activeName, params: { rowData: this.row, inventoryKey: this.row.inventoryKey } })
  },

  methods: {
    //换tabs
    handleClick(v) {
      this.activeName = v.name
      this.$router.push({ name: this.activeName, params: { rowData: this.row, inventoryKey: this.row.inventoryKey } })
      this.$forceUpdate();
    },
    handleClose(done) {
      this.$parent.isShow = false
      this.myDrawer = this.drawer
      this.myDrawer = false
      this.$router.push({ name: "inventory" })
    }
  },
}
</script>
<style lang="scss">

</style>