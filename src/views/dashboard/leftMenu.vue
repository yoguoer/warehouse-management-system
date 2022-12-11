<template>
  <el-drawer
    style="top:52px!important;"
    :visible.sync="drawer"
    :direction="direction"
    :modal="true"
    size="15%"
    :before-close="Close">
    <el-menu
      default-active="1-1"
      class="el-menu-vertical-demo"
      @select="goTo"
      @open="handleOpen"
      @close="handleClose"
      background-color="#fdfdfd"
      text-color="#000"
      active-text-color="#409eff">

      <el-submenu index="purchasing">
        <template slot="title">
          <i class="el-icon-s-custom"></i>
          <span>采购</span>
        </template>
        <el-menu-item-group>
          <el-menu-item index="purchasingQueries">采购查询</el-menu-item>
          <el-menu-item index="purchasingBusiness">采购业务</el-menu-item>
          <el-menu-item index="purchasingOrders">采购订单</el-menu-item>
        </el-menu-item-group>
      </el-submenu>

      <el-submenu index="marketing">
      <template slot="title">
        <i class="el-icon-s-shop"></i>
        <span slot="title">销售</span>
        </template>
        <el-menu-item-group>
          <el-menu-item index="marketingBusiness">销售业务</el-menu-item>
          <el-menu-item index="marketingQueries">销售查询</el-menu-item>
        </el-menu-item-group>
      </el-submenu>

      <el-submenu index="distribution">
        <template slot="title">
        <i class="el-icon-s-flag"></i>
        <span slot="title">配货</span>
        </template>
        <el-menu-item-group>
          <el-menu-item index="demandManagement">需求管理</el-menu-item>
          <el-menu-item index="distributionManagement">配货管理</el-menu-item>
        </el-menu-item-group>
      </el-submenu>

      <el-submenu index="store">
        <template slot="title">
        <i class="el-icon-s-home"></i>
        <span slot="title">库存</span>
        </template>
        <el-menu-item-group>
          <el-menu-item index="suppliers">供应商库存</el-menu-item>
          <el-menu-item index="shopkeepers">零售商库存</el-menu-item>
        </el-menu-item-group>
      </el-submenu>

      <el-submenu index="data">
        <template slot="title">
        <i class="el-icon-s-goods"></i>
        <span slot="title">资料</span>
        </template>
        <el-menu-item-group>
          <el-menu-item index="business">业务实体</el-menu-item>
          <el-menu-item index="finance">账务实体</el-menu-item>
          <el-menu-item index="goods">商品管理</el-menu-item>
          <el-menu-item index="inventory">仓库管理</el-menu-item>
          <el-menu-item index="customer">客户管理</el-menu-item>
          <el-menu-item index="supply">供应商</el-menu-item>
        </el-menu-item-group>
      </el-submenu>

      <el-menu-item index="userManage" v-if="role==0">
        <i class="el-icon-s-home"></i>
        <span slot="title">用户管理</span>
      </el-menu-item>
    </el-menu>
  </el-drawer>
</template>

<script>
export default {
  name:"leftMenu",
  data() {
      return {
        drawer: false,
        direction: 'ltr',
        role:""
      };
  },
  created(){
    this.role=this.$parent.role
  },
  methods: {
    goTo(index){
      this.$router.push({ name: index })
      if(index=='purchasingQueries'||index=='purchasingBusiness'||index=='purchasingOrders'){
        this.$parent.getMenuSubList('purchasing')
        this.$parent.selOneMenu='purchasing'
      }else if(index=='marketingBusiness'||index=='marketingQueries'){
        this.$parent.getMenuSubList('marketing')
        this.$parent.selOneMenu='marketing'
      }else if(index=='suppliers'||index=='shopkeepers'){
        this.$parent.getMenuSubList('store')
        this.$parent.selOneMenu='store'
      }else if(index=='demandManagement'||index=='distributionManagement'){
        this.$parent.getMenuSubList('distribution')
        this.$parent.selOneMenu='distribution'
      }else if(index=='business'||index=='finance'||index=='goods'||index=='inventory'||index=='customer'||index=='supply'){
        this.$parent.getMenuSubList('data')
        this.$parent.selOneMenu='data'
      }else{
        this.$parent.getMenuSubList('')
        this.$parent.selOneMenu=''
      }
    },
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
    Close(done) {
      done();
    },
    openOrClose(){
      this.drawer=!this.drawer
      console.log(this.drawer)
    }
  }
}
</script>
<style lang="scss" scoped>
//改变选中菜单的背景颜色
.el-menu-item.is-active {
  background-color: #ecf5ff !important;
}
</style>
