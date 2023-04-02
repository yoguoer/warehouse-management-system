<template>
  <div id="app">
    <!-- 顶部 -->
    <el-header height="75px">
      <div class="header">
        <!-- 顶部左侧 -->
        <div class="app-header-left">
          <!-- 左边菜单按钮 -->
          <el-image style="padding:0 5px" :src="openMenu" alt="1" v-if="menuState == 'close'" @click="setMenu()" />
          <el-image style="padding:0 5px" :src="closeMenu" alt="1" v-if="menuState == 'open'" @click="setMenu()" />
          <!-- 标题 -->
          <div class="title-container">
            <a>
              <el-image :src="logo" alt="1" />
              <span class="title" style="cursor: pointer;" @click="goDefault()">商品库存管理系统</span>
            </a>
            <div class="nav-box">
              <div class="clearfix list" v-for="(item, index) in menuList" :key="index"
                @click="_changeMenu(item.componentName)">
                <template>
                  <div :class="selOneMenu === item.componentName ? 'nav-links active' : 'nav-links'">
                    {{ item.name }}
                  </div>
                </template>
              </div>
            </div>
          </div>
        </div>
        <!-- 顶部右侧 -->
        <div class="app-header-right">
          <div>
            <el-dropdown :hide-on-click="false" @command="handleCommand">
              <span class="el-dropdown-link header-user-msg">
                <span>{{ userName }}</span>
                <i class="el-icon-user-solid" />
                <i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="userManage" v-if="userType == 0">管理用户</el-dropdown-item>
                <el-dropdown-item command="appUser">用户信息</el-dropdown-item>
                <el-dropdown-item command="logout">退出</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </div>
      </div>
      <!-- 顶部二级导航栏 -->
      <el-menu v-if="isShow && subMenuList.length != 0" class="el-menu-vertical-demo" @select="handleSelect"
        mode="horizontal">
        <div v-for="(group, index) in subMenuList" :key="index">
          <el-menu-item v-if="!group.children.length > 0" :index="group.componentName + ''">
            <!-- <i :class="'iconfont ' + group.icon"></i> -->
            <span slot="title">{{ group.name }}</span>
          </el-menu-item>

          <el-submenu v-if="group.children.length > 0" :index="group.componentName + ''">
            <template slot="title">
              <!-- <i :class="'icon' + group.icon"></i> -->
              <span slot="title">{{ group.name }}</span>
            </template>
            <el-menu-item v-for="(item, indexitem) in group.children" :key="indexitem" :index="item.componentName + ''">
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
    <leftMenu ref="left" :menuList="menuList" />
  </div>
</template>

<script>
import logo from "@/assets/images/logo.png";
import openMenu from "@/assets/images/openMenu.png";
import closeMenu from "@/assets/images/closeMenu.png";
import leftMenu from './leftMenu.vue'
import { menu } from "@/utils/menu.json";
import { menuGetUserMenu } from "../../api/login";

export default {
  name: "App",
  data() {
    return {
      isShow: 'false',
      logo: logo,
      openMenu: openMenu,
      closeMenu: closeMenu,
      userName: "", // 用户名
      userType: "",//角色
      menuType: "",//归属
      selOneMenu: '',
      menuState: 'close',//左侧菜单状态
      menuList: [],
      subMenuList: []
    }
  },
  mounted() {
    console.log("当前所在：", this.selOneMenu)
    this.getmenu()
  },

  created() {
    // console.log(this.$store.state.user)
    this.userType = this.$store.state.user.userType
    this.userName = this.$store.state.user.userName
    this.menuType = this.$store.state.user.userBelong
    if (this.$store.state.menu.selFirstMenu) {
      this.selOneMenu = this.$store.state.menu.selFirstMenu
      this.getMenuSubList(this.selOneMenu)
      this.isShow = true
    }
    //处理刷新后活跃标签信息丢失的问题
    if (sessionStorage.getItem("selOneMenu")) {
      this.selOneMenu = sessionStorage.getItem("selOneMenu")
      this.getMenuSubList(this.selOneMenu)
    }
    if (this.selOneMenu = 'welcome') {
      this.isShow = false
    }
    console.log(this.selOneMenu, "----")
  },
  components: {
    leftMenu
  },
  watch: {
  },
  methods: {
    getmenu() {
      menuGetUserMenu({ menuRole: this.userType, menuType: this.menuType }).then((res) => {
        console.log(res.data, "menuGetUserMenu");
        if (res.data.code === 200) {
          this.$store.dispatch("saveMenu", res.data.data);
          this.menuList = res.data.data.menu
        } else {
          this.$message({
            message: res.data.msg,
            type: "warning",
          })
        }
      })
    },
    handleSelect(index) {
      this.$router.push({ name: index })
    },
    setMenu() {
      if (this.menuState == 'close') {
        this.menuState = 'open'
        this.$refs.left.openOrClose()
      } else {
        this.menuState = 'close'
        this.$refs.left.openOrClose()
      }
    },
    handleCommand(val) {
      console.log(val, '?')
      if (val === 'appUser') {
        this.$router.push({ name: "userHome" });
        this.selOneMenu = ''
        this.isShow = false
      }
      else if (val === 'userManage') {
        this.$router.push({ name: "userManage" });
        this.selOneMenu = ''
        this.isShow = false
      }
      else if (val == "logout") {
        console.log('退出')
        this.$confirm('您确定要退出登录吗?', '提示', {
          confirmButtonText: '确定',  //确认按钮的文字显示
          type: 'warning',
          center: true, //文字居中显示

        })
          .then(() => {
            this.$message({
              type: 'success',
              message: '退出登录成功'
            })
            this.$router.push({ name: "login" });
            localStorage.clear()
            sessionStorage.clear()
          })
          .catch(() => {
            this.$message({
              type: 'info',
              message: '已取消退出登录'
            })
          })
      }
    },
    _changeMenu(id) {
      this.isShow = true
      this.subMenuList = []
      this.selOneMenu = id
      this.$router.push({ name: id })
      this.getMenuSubList(id)
      sessionStorage.setItem("selOneMenu", this.selOneMenu)
      console.log(this.selOneMenu)
    },
    getMenuSubList(id) {
      this.subMenuList = []
      this.menuList.map((item) => {
        if (item.componentName === id) {
          if (item.children) {
            this.subMenuList = item.children
          }
        }
      })
    },
    //默认到欢迎页面
    goDefault() {
      this.$router.push({ name: "welcome" });
      this.selOneMenu = ''
      this.isShow = false
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

.el-main {
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
        color: #2d8bf0;
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
        background: #ffffff;
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
