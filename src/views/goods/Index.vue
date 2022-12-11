<template>
    <div class="wrap-definition" style="padding:20px;">
  <!-- 商品 -->
      <!-- <div>
        <div style="display:-webkit-inline-box">
          <span  class="special">新增日期</span> 
          <el-date-picker
            v-model="timepick"
            type="daterange"
            align="right"
            size="big"
            unlink-panels
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            :picker-options="pickerOptions"
            style="margin:0 20px 0 -1px;padding:5px;height:36px;border-radius:0 4px 4px 0;vertical-align:bottom">
            </el-date-picker>
        </div>
        <el-button type="primary" size="small" @click="search">查询</el-button>
        <el-button  size="small" @click="goodsName='',goodsId='',goodsState=''">重置</el-button>
      </div> -->
      <goodsList ref="good"/>
    </div>
  </template>
  
  <script>
  import goodsList from '@/components/data/business/goods/goodsList.vue'
  
  export default {
    name: 'businessEntity',
     data() {
      return {
        data: [],
  
        pickerOptions: {
          shortcuts: [{
            text: '最近一周',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近一个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近三个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit('pick', [start, end]);
            }
          }]
        },
        timepick:''
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
      goodsList,
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
   .el-divider--horizontal{
       margin: 20px 0;
     }
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
      font-size: 14px;
    }
  }
  </style>