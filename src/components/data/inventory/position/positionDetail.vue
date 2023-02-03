<template>
  <el-drawer size="95%" title="货位详情" :direction="direction" :visible.sync="myDrawer" :close-on-press-escape="false"
    :show-close="true" :before-close="handleClose" :wrapperClosable="false">
    <div style="padding:0 20px;">
      <el-row style="margin-bottom: 20px;">
        <el-col :span="5">
          <span>货位编号: {{ row.positionCode }}</span>
        </el-col>
        <el-col :span="5">
          <span>货位类型: {{ row.positionType == 'store' ? '存储位' : '集货位' }}</span>
        </el-col>
        <el-col :span="5">
          <span>最大重量: {{ row.maxWeight }}</span>
        </el-col>
        <el-col :span="5">
          <span>最大容量(m3): {{ row.maxCapacity }}</span>
        </el-col>
      </el-row>
      <el-row>
        <el-tabs type="card" v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="仓库" name="position-inventory">
          </el-tab-pane>
          <el-tab-pane label="区域" name="position-area">
          </el-tab-pane>
          <!-- <router-view></router-view> -->
          <keep-alive>
            <router-view v-if="$route.meta.keepAlive"></router-view>
          </keep-alive>
          <router-view v-if="!$route.meta.keepAlive"></router-view>
        </el-tabs>
      </el-row>
    </div>
  </el-drawer>
</template>
  
  
<script>

export default {
  name: 'positionDetail',
  data() {
    return {
      activeName: 'position-inventory',
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
    let rowData = localStorage.getItem('positionDetail')
    if (rowData) {
      this.row = JSON.parse(rowData)
    }
    // this.$router.push({ name: this.activeName, params: { rowData: this.row, positionKey: this.row.positionKey } })
  },

  methods: {
    //换tabs
    handleClick(v) {
      this.activeName = v.name
      this.$router.push({ name: this.activeName, params: { rowData: this.row, positionKey: this.row.positionKey } })
      this.$forceUpdate();
    },
    handleClose(done) {
      // this.$parent.isShow = false
      // this.myDrawer = this.drawer
      // this.myDrawer = false
      localStorage.removeItem('positionDetail')
      this.$router.push({ name: "position" })
    }
  },
}
</script>
<style lang="scss">

</style>