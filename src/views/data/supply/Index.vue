<template>
  <div class="wrap-definition">
    <el-tabs type="border-card" v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="供应商列表" name="supplyList">
      </el-tab-pane>
      <el-tab-pane label="地址" name="supplyAddress">
      </el-tab-pane>
      <el-tab-pane label="联系人" name="supplyContact">
      </el-tab-pane>
      <el-tab-pane label="开票信息" name="supplyBank">
      </el-tab-pane>
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
      activeName: 'supplyList'
   }
  },
  props: {},
  methods: {
    //换tabs
    handleClick (v) {
      this.activeName=v.name
      this.$router.push({ name: this.activeName })
    },
  },
  watch: {},
  created () {
  },
  computed: {
  },
  mounted () {
  },
  beforeDestroy () {
  },
}
</script>

<style lang="scss" rel="stylesheet/scss" scoped>
.wrap-definition {
  background: #fff;
}
</style>