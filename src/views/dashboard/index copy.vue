<template>
  <div id="app">
<!-- 顶部 -->
    <el-header height="75px">
      <div class="header">
        <div class="app-header-left">
          <!-- 左边菜单按钮 -->
          <el-image style="padding:0 5px" :src="openMenu" alt="1" v-if="menuState=='close'" @click="setMenu()"/>
          <el-image style="padding:0 5px" :src="closeMenu" alt="1" v-if="menuState=='open'" @click="setMenu()"/>
          <!-- 标题 -->
          <div class="title-container">
            <a>
              <el-image :src="logo" alt="1" />
              <span class="title" style="cursor: pointer;" @click="goDefault()">智能库存管理系统</span>
            </a>
            <div class="nav-box">
              <div class="clearfix list" v-for="(item, index) in menuList" :key="index" @click="_changeMenu(item.componentName)">
                <template>
                  <div :class="selOneMenu === item.componentName ? 'nav-links active':'nav-links'">
                    {{item.name}}
                  </div>
                </template>
              </div>
            </div>
          </div>
        </div>
        <div class="app-header-right">
          <div>
            <el-dropdown :hide-on-click="false" @command="handleCommand">
              <span class="el-dropdown-link header-user-msg">
                <span>{{ userName }}</span>
                <i class="el-icon-user-solid" />
                <i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="userManage" v-if="role=='admin'">管理用户</el-dropdown-item>
                <el-dropdown-item command="appUser">用户信息</el-dropdown-item>
                <el-dropdown-item command="logout">退出</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </div>
      </div>
      <el-menu v-if="isShow && subMenuList.length != 0" class="el-menu-vertical-demo"  @select="handleSelect" mode="horizontal">
        <div v-for="(group,index) in subMenuList" :key="index">
            <el-menu-item v-if="!group.children" :index="group.componentName+''">
              <!-- <i :class="'iconfont ' + group.icon"></i> -->
              <span slot="title">{{ group.name }}</span>
            </el-menu-item>

            <el-submenu v-if="group.children" :index="group.componentName+''">
              <template slot="title">
                <!-- <i :class="'icon' + group.icon"></i> -->
                <span slot="title">{{ group.name }}</span>
              </template>
              <el-menu-item v-for="(item, indexitem) in group.children" :key="indexitem" :index="item.componentName+''">
                <i :class="'icon' + group.icon"></i>
                <span slot="title">{{ item.name }}</span>
              </el-menu-item>
            </el-submenu>
         </div>
      </el-menu>
    </el-header>
<!-- 主要内容 -->
    <el-main>
      <router-view></router-view>
    </el-main>
    <leftMenu ref="left"/>
  </div>
</template>

<script>
// import {
//   authLogout
// } from "@/api/api.js";
import logo from "@/assets/images/logo.png";
import openMenu from "@/assets/images/openMenu.png";
import closeMenu from "@/assets/images/closeMenu.png";
import leftMenu from './leftMenu.vue'

export default {
  name: "App",
  data() {
    return {
      role:"",
      isShow:'true',
      logo: logo,
      openMenu: openMenu,
      closeMenu: closeMenu, 
      userName: "", // 用户名
      selOneMenu: '',
      menuState:'close',//左侧菜单状态
      menuList: [
        {name:'采购',componentName:'purchasing',
        children:[{name:'采购查询',componentName:'purchasingQueries'},
                {name:'采购业务',componentName:'purchasingBusiness'},
                {name:'采购订单',componentName:'purchasingOrders'}]},
        {name:'销售',componentName:'marketing',
        children:[{name:'销售业务',componentName:'marketingBusiness',
                      children:[{name:'销售订单',componentName:'marketingBusiness'},
                      {name:'零售单',componentName:'retailOrder'}]},
                {name:'销售查询',componentName:'marketingQueries',
                      children:[{name:'销售单据查询',componentName:'bills'},
                      {name:'销售明细查询',componentName:'details'}]}]},
        {name:'库存',componentName:'store',
        children:[{name:'供应商库存',componentName:'suppliers',
                        children:[
                        {name:'供应商库存管理',componentName:'management'},
                        {name:'供应商库存流水',componentName:'records'}]},
                {name:'零售商库存',componentName:'shopkeepers',
                        children:[
                        {name:'库存总览',componentName:'overview'},
                        {name:'在单库存',componentName:'inOrder'},
                        {name:'仓库库存',componentName:'warehouse'},
                        {name:'库存配置',componentName:'configure'}]}]},
        {name:'配货',componentName:'distribution',
        children:[{name:'需求管理',componentName:'demandManagement'},
                {name:'配货管理',componentName:'distributionManagement'}],
        },
        {name:'资料',componentName:'data',
        children:[{name:'业务实体',componentName:'business',
                        children:[
                        {name:'品牌',componentName:'brand'},
                        {name:'车辆',componentName:'car'}]},
                  {name:'账务实体',componentName:'finance',
                  children:[
                        {name:'银行',componentName:'bank'}]},
                  {name:'商品管理',componentName:'goods'},
                  {name:'仓库管理',componentName:'inventory'},
                  {name:'客户管理',componentName:'customer'},
                  {name:'供应商',componentName:'supply'},]},
        // {name:'用户管理',componentName:'userManage'}
      ],
      // menuList:[{name:'系统设置',componentName:'settings'}],
      //未有接口，先写死以测试功能
      subMenuList:[]
    }
  },
  mounted() {
    console.log(this.selOneMenu)
  },
  created() {
    //判断用户身份
    let userInfo=JSON.parse(localStorage.getItem("userInfo"))
    this.role=userInfo.userType
    this.userName=userInfo.userName
    console.log('用户角色',this.role)
    //处理刷新后活跃标签信息丢失的问题
    if(sessionStorage.getItem("selOneMenu")){
      this.selOneMenu=sessionStorage.getItem("selOneMenu")
      this.getMenuSubList(this.selOneMenu)
    }
  },
  components: {
    leftMenu
  },
  watch: {
  },
  methods: {
    handleSelect (index) {
      this.$router.push({ name: index })
    },
    setMenu(){
      if(this.menuState=='close'){
        this.menuState='open'
        this.$refs.left.openOrClose()
      }else{
        this.menuState='close'
        this.$refs.left.openOrClose()
      }
    },
    handleCommand(val) {
      console.log(val, '?')
      if (val === 'appUser') {
        this.$router.push({ name: "userHome" });
        this.selOneMenu=''
        this.isShow=false
      } 
      else if (val === 'userManage') {
        this.$router.push({ name: "userManage" });
        this.selOneMenu=''
        this.isShow=false
      } 
      else if (val == "logout") {
        console.log('退出')
        this.$router.push({ name: "login" });
        localStorage.clear()
      }
    },
    _changeMenu (id) {
      this.isShow=true
      this.subMenuList = []
      this.selOneMenu = id
      this.$router.push({ name: id })
      this.getMenuSubList(id)
      sessionStorage.setItem("selOneMenu",this.selOneMenu)
      console.log(this.selOneMenu)
    },
    getMenuSubList (id) {
      this.subMenuList = []
      this.menuList.map((item) => {
        if (item.componentName === id) {
          if(item.children)
          {
            this.subMenuList = item.children
          }
        }
      })
    },  
    goDefault(){
      this.$router.push({ name: "welcome" });
      this.selOneMenu=''
      this.isShow=false
    } 
  },
};
</script>

<style lang="scss" scoped>
.header.el-header {
  padding: 0;
  z-index: 10000;
}
#query {
  height: 100%;
}
.el-container {
  height: 100%;
}
.header {
  width: 100%;
  height: 52px;
  z-index: 9999;
  line-height: 52px;
  background: #2d303a;
  // box-shadow: 0 2px 4px 0 rgb(0 67 203 / 20%), 0 2px 6px 0 rgb(16 72 130 / 16%);
  color: #fff;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
}

.title-container {
  padding: 0 15px;
  a {
    display: flex;
    color: #fff;
    font-size: 14px;
    text-decoration: none;
    .title {
      text-align: center;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
      font-size: 16px;
      margin: 0;
      margin-left: 4px;
      color: #fff;
      padding-left: 10px;
    }
  }
}
.el-image {
  width: 23px;
  height: 28px;
  margin-top: 10px;
}
.el-main{
  background-color: #f4f7fb;
}
.el-header {
  padding: 0;
}
.app-header-left {
  display: flex;
}
.app-header-right {
  padding: 0px 20px;
  .header-user-msg {
    cursor: pointer;
    span {
      color: #fff;
    }
    i {
      color: #fff;
    }
    &:hover {
      span {
        // color: $hoverFontColor;
        color:#2d8bf0;
      }
    }
  }
}
.el-icon-user-solid {
  padding-right: 5px;
}
.drawermenu {
  margin-top: 42px;
}
.addbtn {
  cursor: pointer;
  padding-left: 30px;
  height: 42px;
  &:hover {
    color: #2d8bf0;
  }
}
// 一级导航
.nav-box {
  height: 50px;
  line-height: 50px;
  position: absolute;
  left: 500px;
  top: 0;
  .list {
    width: 106px;
    float: left;
    position: relative;
    cursor: pointer;
    margin-right: 14px;
    .nav-links {
      text-align: center;
      width: 100%;
      height: 55px;
      color: #fff;
      position: relative;
      text-decoration: none;
      font-size: 15px;
      display: inline-block;
      text-align: center;
      &:hover {
        color: #2d8cf0;
      }
      &.active {
        background:#ffffff;
        color: #2D8BF0;
      }
  }
  }
}

// 菜单样式覆盖
$menuHeight: 32px;
.el-menu-vertical-demo {
  height: $menuHeight;
  display: flex;
}
.el-menu /deep/.el-submenu {
  height: $menuHeight;
  line-height: $menuHeight;
}
.el-menu /deep/.el-submenu__title {
  height: $menuHeight;
  line-height: $menuHeight;
}
.el-menu /deep/.el-menu-item {
  height: $menuHeight;
  line-height: $menuHeight;
}
</style>
