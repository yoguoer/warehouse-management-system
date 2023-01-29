<template>
  <div class="userHome">
    <el-card class="box-card">
      <div class="avatar" style="width:200px;margin:5px;">
        <el-image :src="edit" alt="1" />
      </div>
      <div class="functions">
        <div class="text item" @click="changePwd()">
          <el-button style="width: 100%!important;">修改密码</el-button>
        </div>
        <div class="text item" @click="editInfo()">
          <el-button style="width: 100%!important;">修改个人信息</el-button>
        </div>
      </div>
    </el-card>

    <el-card class="box-card">
      <div class="avatar" style="width:200px;margin:5px;">
        <el-image :src="avatar" alt="1" />
      </div>
      <div class="message">
        <div class="text item">
          用户名：{{ userInfo.userId || '-' }}
        </div>
        <div class="text item">
          姓名：{{ userInfo.userName || '-' }}
        </div>
        <div class="text item">
          用户手机：{{ userInfo.userPhone || '-' }}
        </div>
        <div class="text item">
          邮箱：{{ userInfo.userEmail || '-' }}
        </div>
      </div>
    </el-card>
    <editInfo v-if="dialogFormVisible" :dialogFormVisible="dialogFormVisible" :rowData="rowData"
      @close="dialogFormVisible = false" ref="editdialog" />
    <changePwd v-if="dialogVisible" :dialogVisible="dialogVisible" :rowData="rowData" @close="dialogVisible = false"
      ref="editdialog" />
  </div>
</template>

<script>
import avatar from "@/assets/svg/avatar.svg";
import edit from "@/assets/svg/edit.svg";
import { getUserInfo } from "../../api/login";
import editInfo from "./editInfo.vue"
import changePwd from "./changePwd.vue"


export default {
  name: 'userHome',
  data() {
    return {
      avatar: avatar,
      edit: edit,
      userInfo: [],
      dialogFormVisible: false,
      dialogVisible: false,
      rowData: {}
    }
  },
  components: {
    editInfo,
    changePwd
  },
  created() {
    this.getUserInfo()
  },
  methods: {
    getUserInfo() {
      let user = JSON.parse(localStorage.getItem("userInfo"))
      getUserInfo(user).then(res => {
        this.userInfo = res.data.data || []
        localStorage.setItem("userInfo", JSON.stringify(this.userInfo))
        // console.log(this.userInfo)
      }).catch(err => {
        console.log(err);
      });
    },
    editInfo() {
      this.rowData = this.userInfo
      this.dialogFormVisible = true
      // console.log("修改个人信息")
    },
    changePwd() {
      this.rowData = this.userInfo
      this.dialogVisible = true
      // console.log("修改个人信息")
    },
  }
}
</script>

<style scoped>
.userHome {
  position: relative;
  z-index: 999;
  font-size: 24px;
  float: left;
  display: inline-flex;
}

.functions {
  cursor: pointer;
  float: right
}

.text {
  font-size: 18px;
}

.item {
  padding: 18px 0;
}

.avatar {
  display: flex;
  float: left;
}

.message {
  float: right
}

.box-card {
  width: 500px;
  margin: 20px;
  display: flex;
}
</style>
