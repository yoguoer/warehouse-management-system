<template>
  <div class="wrap-definition">
    <el-tabs type="border-card" v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="仓库" name="inventoryList">
      </el-tab-pane>
      <el-tab-pane label="区域" name="areaList">
      </el-tab-pane>
      <el-tab-pane label="货位" name="positionList">
      </el-tab-pane>
      <!-- <router-view></router-view> -->
      <keep-alive>
        <router-view v-if="$route.meta.keepAlive"></router-view>
      </keep-alive>
      <router-view v-if="!$route.meta.keepAlive"></router-view>
    </el-tabs>
  </div>
</template>

<script>
export default {
  name: 'supply',
  data() {
    return {
      activeName: 'inventoryList',
    }
  },
  props: {},
  methods: {
    //换tabs
    handleClick(v) {
      this.activeName = v.name
      this.$router.push({ name: this.activeName })
      this.$forceUpdate();
    },
  },
  watch: {},
  created() {
  },
  computed: {
  },
  mounted() {
  },
  beforeDestroy() {
  }
}
</script>

<style lang="scss" rel="stylesheet/scss" scoped>
.wrap-definition {
  background: #fff;

  .top-title {
    color: #0066ff;
    font-size: 18px;
    line-height: 50px;
  }

  .el-divider--horizontal {
    margin: 20px 0;
  }

  .el-input-group {
    width: 300px;
    margin-right: 20px;
  }

  .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }

  .clearfix:after {
    clear: both
  }

  .el-drawer__open .el-drawer.btt {
    height: 60% !important;
  }

  .el-dropdown-link {
    cursor: pointer;
    color: #409EFF;
  }

  .el-icon-arrow-down {
    font-size: 12px;
  }

  .special {
    background-color: #F5F7FA;
    color: #909399;
    display: table-cell;
    position: relative;
    border: 1px solid #DCDFE6;
    border-radius: 4px 0 0 4px;
    padding: 0 20px;
    width: 1px;
    line-height: 30px;
    white-space: nowrap;
  }
}
</style>