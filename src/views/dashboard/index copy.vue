<template>
  <div id="app">
    <el-header height="75px">
      <div class="header">
        <div class="app-header-left">
          <div :class="menuDrawer ? 'm-toggle-sidebar change' : 'm-toggle-sidebar'" @click="setMenu('look')">
            <i class="iconfont icon-caidan_zhankai"></i>
          </div>
          <div class="title-container">
            <a href="/dataIntegration">
              <el-image :src="logo" alt="1" />
              <span class="title">智能供应链</span>
            </a>
            <div class="nav-box">
              <div class="clearfix list" v-for="(item, index) in menuList" :key="index" @click="_changeMenu(item.componentName)">
                <template v-if="item.name !== '个人中心'">
                  <div :class="selOneMenu === item.componentName ? 'nav-links active':'nav-links'">
                    {{item.name}}
                  </div>
                </template>
              </div>
            </div>
          </div>
          <v-menu>
            <template v-slot:addbtn>
              <li class="addbtn" @click="setMenu('edit')" v-show="!menuDrawer">
                <i class="iconfont icon-zhankai"></i>
              </li>
            </template>
          </v-menu>
        </div>
        <div class="app-header-right">
          <div>
            <el-dropdown :hide-on-click="false" @command="handleCommand">
              <span class="el-dropdown-link header-user-msg">
                <i class="el-icon-user-solid" />
                <span>{{ this.name }}</span>
                <i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="appUser">用户信息</el-dropdown-item>
                <el-dropdown-item command="logout">退出</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </div>
      </div>
      <el-menu v-if="subMenuList.length != 0" class="el-menu-vertical-demo" :collapse="isCollapse" @select="handleSelect" mode="horizontal">
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
    <el-main>
      <router-view></router-view>
    </el-main>
    <!-- <div class="center">
      <el-container class="sub-menu"> -->
        <!-- <el-aside  v-if="subMenuList.length != 0" style="width:auto">
          <div class="collapsed" @click="isCollapse ? isCollapse=false : isCollapse=true">
            <i class="el-icon-right collapsed-icon"></i>
          </div>
          
        </el-aside> -->
        <!-- <el-main>
          <router-view></router-view>
        </el-main> -->
      <!-- </el-container>
      
    </div> -->
    <el-drawer class="drawermenu" v-if="menuDrawer" :withHeader="false" :visible.sync="menuDrawer" direction="ltr" size="440px" :modal-append-to-body="false" :close-on-press-escape="true">
      <v-slideMenu :menu-show-sign="menuShowSign"  @closeMenuDrawer="closeMenuDrawer"/>
    </el-drawer>
  </div>
</template>

<script>
import {
  menuGetUserMenu,
  customGet,
  customReset,
  userGetLoginUserInfo,
} from "@/api/platform.js";
import {
  authLogout
} from "@/api/api.js";
import logo from "@/assets/images/logo.png";
import vSlideMenu from "@/components/dashboard/vSlideMenu.vue";
import vMenu from "@/components/dashboard/vMenu.vue";
export default {
  name: "App",
  components: { vMenu, vSlideMenu },
  data() {
    return {
      isCollapse: false,
      logo: logo,
      menuDrawer: false, // 菜单展开标识
      MenuFirstlevel: [], // 全部一级菜单
      MenuFirstlevelShow: [], // 选择展示一级菜单
      menuShowSign: "look", // 菜单展示标识 look/edit
      name: "", // 用户名
      selOneMenu: '',
      menuList: this.$store.state.common.menuList,
      // menuList:[{name:'系统设置',componentName:'settings'}],
      //未有接口，先写死以测试功能
      // subMenuList:[
      //   {name:'数据集成',componentName:'dataIntegration'},
      //   {name:'销售',componentName:'marketingStrategy',children:[{name:'销售策略',componentName:'marketingStrategy'}]},
      //   {name:'采购',componentName:'purchasing',children:[{name:'采购策略',componentName:'purchasingStrategy'},{name:'采购业务',componentName:'purchasingBusiness'},{name:'采购查询',componentName:'purchasingQueries'}]},
      //   {name:'库存',componentName:'repertory',children:[{name:'供应商库存',componentName:'suppliers'},{name:'零售商库存',componentName:'shopkeepers'}]},
      //   {name:'资料',componentName:'document',children:[{name:'业务实体',componentName:'businessEntity'},{name:'账务实体',componentName:'financialAccount'},{name:'商品管理',componentName:'goodsManagement'},{name:'仓库管理',componentName:'storeManagement'}]},
      // ]
    }
  },
  mounted() {
  },
  created() {
    this.getFloorMenu()
    this.getmenu()
    this.getInfo()
  },
  computed: {
    subMenuList: function () {
      return this.$store.getters.getSubMenu;
    },
  },
  watch: {
  },
  methods: {
    handleSelect (index) {
      this.$router.push({ name: index })
    },
    setMenu(val) {
      console.log('让我看看菜单里面有什么')
      this.menuDrawer === true ?  this.menuDrawer = false : this.menuDrawer = true
      this.menuShowSign = val
    },
    getInfo() {
      userGetLoginUserInfo().then((res) => {
        if (res.code === 0) {
          this.name = res.data.name;
          console.log(this.name);
        } else {
          this.$message({
            message: res.msg,
            type: "warning",
          });
        }
      });
    },
    // 关闭一级配置菜单栏目
    closeMenuDrawer(val) {
      if (val) {
        this.setSlideMenu(val);
      }
      this.menuDrawer = false;
    },
    handleCommand(val) {
      console.log(val, '?')
      if (val === 'appUser') {
        this.$router.push({ name: "appUser" });
      } 
      if (val == "logout") {
        console.log('退出')
        this.$router.push({ name: "login" });
        authLogout().then((res) => {
          if (res.code === 200) {
            localStorage.clear()
            this.$message({ message: res.msg, type: "success" });
            this.$store.dispatch("saveMenu", []);
            this.$store.dispatch("saveSubMenu", []);
            this.$store.dispatch("saveFirstMenu", []);
            this.$store.dispatch("saveSelFirstMenu", []);
            this.$router.push({ name: "login" });
          } else {
            this.$message({ message: res.msg, type: "warning" });
          }
        });
      }
    },
    setSlideMenu(val) {
      customReset(val).then((res) => {
        if (res.code === 200) {
          this.getFloorMenu();
        } else {
          this.$message({
            message: res.msg,
            type: "warning",
          });
        }
      });
    },
    // 获取横向导航一级菜单
    getFloorMenu() {
      customGet().then((res) => {
        if (res.code === 200) {
          this.$store.dispatch("saveFirstMenu", res.data);
        } else {
          this.$message({
            message: res.msg,
            type: "warning",
          });
        }
      });
    },
    // 获取横向导航一级菜单 end
    // 获取全部菜单
    getmenu() {
      menuGetUserMenu().then((res) => {
        console.log(res, "menuGetUserMenu");
        if (res.code === 200) {
          this.$store.dispatch("saveMenu", res.data);
        } else {
          this.$message({
            message: res.msg,
            type: "warning",
          })
        }
      })
    },
     _changeMenu (id) {
      this.menuDisabledSign = false
      this.subMenuList = []
      this.selOneMenu = id
      this.$router.push({ name: id })
      this.getMenuSubList(id)
    },
    getMenuSubList (id) {
      this.subMenuList = []
      this.menuList.map((item) => {
        if (item.componentName === id) {
          this.subMenuList = item.children
          this.menuDisabledSign = true
        }
      })
    },   
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
  height: 42px;
  line-height: 42px;
  background: #2d303a;
  // box-shadow: 0 2px 4px 0 rgb(0 67 203 / 20%), 0 2px 6px 0 rgb(16 72 130 / 16%);
  color: #fff;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
}
.m-toggle-sidebar {
  width: 56px;
  text-align: center;
  // border-right:1px solid #888;
  height: 42px;
  line-height: 42px;
  background-color: #247cdb;
  cursor: pointer;
  background: linear-gradient(90deg, #1783d0 0%, #0f41f9 100%);
  i {
    color: #fff;
  }
  &.change {
    transform: rotate(180deg);
  }
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
  margin-top: 7px;
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
        color: $hoverFontColor;
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
      height: 50px;
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
