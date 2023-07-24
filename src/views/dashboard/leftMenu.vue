<template>
  <el-drawer style="top:52px!important;" :visible.sync="drawer" :direction="direction" :modal="true" size="15%"
    :before-close="Close">
    <el-menu default-active="1-1" class="el-menu-vertical-demo" @select="goTo" @open="handleOpen" @close="handleClose"
      background-color="#fdfdfd" text-color="#000" active-text-color="#409eff">

      <el-submenu :index="item.componentName" v-for="item in menuList" :key="item.componentName+ ''">
        <template slot="title">
          <i :class="item.icon"></i>
          <span>{{item.name}}</span>
        </template>
        <el-menu-item-group v-for="children in item.children" :key="children.componentName+ ''">
          <el-menu-item :index="children.componentName">{{children.name}}</el-menu-item>
        </el-menu-item-group>
      </el-submenu>

      <el-menu-item index="userManage" v-if="userType == 0">
        <i class="el-icon-s-home"></i>
        <span slot="title">用户管理</span>
      </el-menu-item>
    </el-menu>
  </el-drawer>
</template>

<script>
export default {
  name: "leftMenu",
  data() {
    return {
      drawer: false,
      direction: 'ltr',
      userType: "",
      selOneMenu: ""
    };
  },
  props:{
    menuList:[]
  },
  created() {
    this.userType = this.$parent.userType
  },
  methods: {
    goTo(index) {
      this.$router.push({ name: index })
      if (index == 'purchasingStrategy' || index == 'purchasingBusiness' || index == 'purchasingQueries' || index == 'transferInput') {
        this.$parent.getMenuSubList('purchasing')
        this.$parent.selOneMenu = 'purchasing'
        this.$parent.isShow = true
        this.selOneMenu = 'purchasing'

      } else if (index == 'marketingBusiness' || index == 'marketingQueries' || index == 'transferOutput') {
        this.$parent.getMenuSubList('marketing')
        this.$parent.selOneMenu = 'marketing'
        this.$parent.isShow = true
        this.selOneMenu = 'marketing'

      } else if (index == 'suppliers' || index == 'shopkeepers') {
        this.$parent.getMenuSubList('store')
        this.$parent.selOneMenu = 'store'
        this.$parent.isShow = true
        this.selOneMenu = 'store'

      } else if (index == 'business' || index == 'finance' || index == 'goods' || index == 'shop' || index == 'inventory' || index == 'supply' || index == 'customer') {
        this.$parent.getMenuSubList('data')
        this.$parent.selOneMenu = 'data'
        this.$parent.isShow = true
        this.selOneMenu = 'data'

      } else if (index == 'salesIntegrate' || index == 'purchaseIntegrate' || index == 'inventoryIntegrate' || index == 'customerIntegrate' || index == 'supplierIntegrate' || index == "transferIntegrate") {
        this.$parent.getMenuSubList('dataIntegrate')
        this.$parent.selOneMenu = 'dataIntegrate'
        this.$parent.isShow = true
        this.selOneMenu = 'dataIntegrate'

      } else {
        this.$parent.getMenuSubList('')
        this.$parent.selOneMenu = ''
        this.selOneMenu = ''

      }
      console.log(this.$parent.selOneMenu)
      sessionStorage.setItem("selOneMenu", this.selOneMenu)
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
    openOrClose() {
      this.drawer = !this.drawer
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
