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
          工号：{{ userInfo.userCode || '-' }}
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
        <div class="text item">
          性别：
          <img :src="sex_girl" alt="" style="width:15px;" v-if="userInfo.userSex == '女'" />
          <img :src="sex_boy" alt="" style="width:15px;" v-else-if="userInfo.userSex == '男'" />
          {{ userInfo.userSex || '-' }}
        </div>
        <div class="text item">
          角色：{{ userInfo.userType == 0 ? '超级管理员' : (userInfo.userType == 1 ? '仓库管理员' 
          : (userInfo.userType == 2 ? '普通用户' : '未配置')) }}
        </div>
        <div class="text item">
          类型：{{
            userInfo.userBelong == 0 ? '门店用户' : (userInfo.userBelong == 1 ? '供应商用户'
              : (userInfo.userBelong == 2 ? '全局用户' : '-'))
          }}
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
import sex_girl from '@/assets/images/sex_girl.png'
import sex_boy from '@/assets/images/sex_boy.png'

export default {
  name: 'userHome',
  data() {
    return {
      sex_girl: sex_girl,
      sex_boy: sex_boy,
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
      let user = this.$store.state.user
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
