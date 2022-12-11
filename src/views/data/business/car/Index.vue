<template>
  <div class="wrap-definition" style="padding:20px;">
    <carList ref="carList"/>
  </div>
</template>

<script>
import carList from '@/components/data/business/car/carList'

export default {
  name: 'supply',
   data() {
    return {
   }
  },
  props: {},
  methods: {

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
  components: {
    carList,
  }
}
</script>

<style lang="scss" rel="stylesheet/scss" scoped>
.wrap-definition {
  background: #fff;
 .top-title{
    color:#0066ff;
    font-size: 18px;
    line-height: 50px;
 }
//  .el-divider--horizontal{
//      margin: 20px 0;
//    }
   .el-input-group{
    width:300px;
    margin-right:20px;
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
.el-drawer__open .el-drawer.btt{
  height:60%!important;
}
  .el-dropdown-link {
    cursor: pointer;
    color: #409EFF;
  }
  .el-icon-arrow-down {
    font-size: 12px;
  }
}
</style>