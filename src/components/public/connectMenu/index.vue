<template>
  <div class="self-drawer">
    <div class="drawer-title">角色菜单权限配置</div>
    <div class="drawer-content">
      <div class="tree-box">
        <!--  默认展示全部节点 -->
        <el-tree ref="tree" v-model="menuSelVal" :data="menuList" show-checkbox node-key="menuKey" :expand-on-click-node="false" :props="defaultProps" :default-expand-all="true"></el-tree>
      </div>
      <div class="drawer-bootom-button">
        <el-dropdown class="dropdown" size="small" @command="handleCommand">
          <el-button size="small">树操作<i class="el-icon-arrow-up"></i></el-button>
          <el-dropdown-menu slot="dropdown" divided>
            <el-dropdown-item command="a">全选</el-dropdown-item>
            <el-dropdown-item command="b">取消全选</el-dropdown-item>
            <el-dropdown-item command="c">展开全部</el-dropdown-item>
            <el-dropdown-item command="d">折叠全部</el-dropdown-item>
            <el-dropdown-item command="e">重置选择</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
        <el-button size="small" @click="closeMethed">取消</el-button>
        <el-button type="primary" size="small" @click="saveNode(0)">保存</el-button>
        <el-button type="primary" size="small" @click="saveNode(1)">保存并关闭</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import { getRoleMenu, menuGetTreeMenu, roleEditMenu } from '@/api/platform.js'
export default {
  components: {},
  props: {
    selItem: { type: Object, default: () => {} },
    closeMethed: Function, // 关闭回调
    // 主键
    primaryKey: {
      type: String,
      default: 'roleKey'
    }
  },
  data() {
    return {
      menuSelVal: [],
      menuList: [],
      // tree 配置
      defaultProps: {
        children: 'children',
        label: 'name',
      },
      defaultSelectedMenu: [], // 详情接口 默认选中的菜单id
    }
  },
  created() {
    this.getAllMenu()
  },
  methods: {
    getData() {
      let param = {}
      param[this.primaryKey] = this.selItem[this.primaryKey]
      getRoleMenu(param).then((res) => {
        if (res.code === 200) {
          this.defaultSelectedMenu = res.data
          this.$nextTick(() => {
            if (this.defaultSelectedMenu.length != 0) {
              const arr = []
              this.defaultSelectedMenu.forEach(item => {
                if (this.$refs.tree.getNode(item).childNodes.length === 0) {
                  arr.push(item)
                }
              })
              this.$refs.tree.setCheckedKeys(arr)
            }
          })
          // this.$refs.tree.setCheckedKeys(res.data, true)
        } else {
          this.$message({
            message: res.msg,
            type: 'warning',
          })
        }
      })
    },
    // 保存提交
    saveNode(close) {
      let zi = this.$refs.tree.getCheckedKeys()
      let fu =this.$refs.tree.getHalfCheckedKeys()
      zi.concat(fu)
      let param = {
        menuKeys: zi.concat(fu)|| [],
      }
      param[this.primaryKey] = this.selItem[this.primaryKey]
      roleEditMenu(param).then((res) => {
        if (res.code === 200) {
          // console.log('保存角色-菜单:', res)
          this.$message.success(res.msg)
          close && this.closeMethed && this.closeMethed()
        } else {
          this.$message({
            message: res.msg,
            type: 'warning',
          })
        }
      })
    },
    // 获取菜单
    getAllMenu() {
      menuGetTreeMenu().then((res) => {
        if (res.code === 200) {
          this.menuList = res.data
          this.getData()
        }
      })
    },
    // 树操作
    handleCommand(command) {
      switch (command) {
        case 'a':
          // console.log('全选')
          this.$refs.tree.setCheckedNodes(this.menuList)
          break
        case 'b':
          // console.log('清空选择')
          this.$refs.tree.setCheckedKeys([], false)
          break
        case 'c':
          // console.log('展开全部节点')
          this.$refs.tree.$children.forEach((i) => (i.expanded = true))
          break
        case 'd':
          // console.log('折叠全部节点')
          this.$refs.tree.$children.forEach((i) => (i.expanded = false))
          break
        case 'e':
          this.$refs.tree.setCheckedKeys(this.defaultSelectedMenu, true)
      }
    },
  },
}
</script>

<style lang="scss" scoped>
div {
  box-sizing: border-box;
}
.self-drawer {
  position: relative;
  height: 100%;

  .drawer-title {
    height: 48px;
    line-height: 48px;
    background-color: #f1f1f1;
    padding: 0 15px;
    font-size: 14px;
  }
  .drawer-content {
    height: calc(100% - 100px);
    padding: 15px;

    .drawer-work {
      padding-bottom: 15px;
    }
  }
  .drawer-bootom-button {
    position: absolute;
    bottom: 0;
    width: 100%;
    border-top: 1px solid #e8e8e8;
    padding: 10px 16px;
    text-align: right;
    left: 0;
    background: #fff;
    border-radius: 0 0 2px 2px;
  }
  .tree-box {
    height: 100%;
    overflow: auto;
  }
}
.dropdown {
  float: left;
  i[class^='el-icon-arrow'] {
    margin-left: 12px;
  }
}
</style>
