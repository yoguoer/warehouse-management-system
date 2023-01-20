<template>
  <div class="login">
    <div class="login-header">
      <div class="logo">
        <el-image :src="logoimg"></el-image>
        <div>商品库存管理系统</div>
      </div>
    </div>
    <div class="login-main clearfix">
      <div class="login-fl fl"><img :src="loginimg" alt=""></div>
      <div class="login-fr fl">
        <ul class="login-tab">
          <li :class="tabval == item ? 'active' : ''" v-for='(item, index) in tablist' :key="index"
            @click="ontab(item)">
            <span>{{ item }}</span>
          </li>
        </ul>
        <el-form ref="ruleForm" :model="formdata" :rules="rules">
          <el-form-item prop="userPhone" v-if="tabval !== '注册'">
            <el-input placeholder="用户手机" @keyup.enter.native="submitform('ruleForm', tabval)"
              v-model="formdata.userPhone"></el-input>
          </el-form-item>
          <template v-if="tabval == '注册'">
            <el-form-item prop="userCode">
              <el-input type="text" placeholder="工号" v-model="formdata.userCode"
                @keyup.enter.native="submitform('ruleForm', tabval)" maxlength="20" />
            </el-form-item>
            <el-form-item prop="userName">
              <el-input type="text" placeholder="姓名" v-model="formdata.userName"
                @keyup.enter.native="submitform('ruleForm', tabval)" maxlength="20" />
            </el-form-item>
            <el-form-item prop="userPhone">
              <el-input type="text" placeholder="手机号码" v-model="formdata.userPhone"
                @keyup.enter.native="submitform('ruleForm', tabval)" maxlength="20" />
            </el-form-item>
            <el-form-item prop="userEmail">
              <el-input type="text" placeholder="邮箱" v-model="formdata.userEmail"
                @keyup.enter.native="submitform('ruleForm', tabval)" maxlength="20" />
            </el-form-item>
            <el-form-item prop="userSex">
              <el-select size="middle" v-model="formdata.userSex" clearable placeholder="性别" style="width:100%;">
                <el-option label="男" value="男"></el-option>
                <el-option label="女" value="女"></el-option>
              </el-select>
              <!-- <el-input type="text"  placeholder="性别" v-model="formdata.userSex" @keyup.enter.native="submitform('ruleForm',tabval)" maxlength="20"/> -->
            </el-form-item>
          </template>

          <el-form-item prop="passWord">
            <el-input placeholder="密码" @keyup.enter.native="submitform('ruleForm', tabval)" type="passWord"
              v-model="formdata.passWord"></el-input>
          </el-form-item>

          <template v-if="tabval == '注册'">
            <el-form-item prop="checkPass">
              <el-input placeholder="确认密码" ref="surepassWord" type="passWord" v-model="formdata.checkPass"
                @keyup.enter.native="submitform('ruleForm', tabval)" maxlength="20" />
            </el-form-item>
          </template>

          <el-form-item class="login-text clearfix" v-if="tabval == '注册'">
            <el-link class="form-forget fr" :underline="false" @click="toEnable()">已有账号，请联系超级管理员配置权限后登录</el-link>
          </el-form-item>
          <el-form-item>
            <el-button @click="submitform('ruleForm', tabval)" @keyup.enter="submitform('ruleForm', tabval)">{{
              tabval
            }}</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import loginimg from '@/assets/images/login0.png'
import logoimg from '@/assets/images/logo.png'
import { toLogin, toRegister } from "../../api/login";

export default {
  name: 'indexPage',
  data() {
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.formdata.passWord) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      logoimg: logoimg,
      loginimg: loginimg,
      formdata: {
        userName: '',
        userId: '',
        passWord: '',
        userPhone: '',
        userEmail: '',
        checkPass: '',
        userSex: '',
        userCode: ''
      },
      rules: {
        userPhone: [
          { required: true, message: '请输入用户手机', trigger: 'blur' }
        ],
        passWord: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
        checkPass: [
          { validator: validatePass2, trigger: 'blur' }
        ]
      },
      tablist: ['登录', '注册'],
      tabval: '登录',
      code: ''
    }
  },

  created() {
  },

  methods: {
    //提交表单
    submitform(formName, val) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (val === '登录') {
            this.login()
          } else {
            this.register()
          }
        } else {
          return false
        }
      })
    },
    //登录
    login() {
      let data = {
        userPhone: this.formdata.userPhone,
        passWord: this.formdata.passWord
      }
      // console.log(data)
      toLogin(data).then(res => {
        if (res.data.code == 200) {
          if (res.data.data.user.userType == -1) {
            this.$message({
              type: 'error',
              message: '暂未配置权限，请联系超级管理员处理!'
            })
          } else {
            this.$message({
              type: 'success',
              message: '登录成功!'
            })
            //登陆成功
            console.log(res.data.data)
            localStorage.setItem("userInfo", JSON.stringify(res.data.data.user))
            localStorage.setItem("token", res.data.data.token)
            localStorage.setItem("userId", res.data.data.user.userId)
            //登陆，默认到欢迎页面
            sessionStorage.setItem("selOneMenu", '')
            this.$router.push({ name: 'welcome' });
          }
        } else {
          this.$message({
            type: 'error',
            message: '账户名或密码错误！'
          });
        }
      }).catch(error => {
        console.log(error);
      });
    },
    //注册
    register() {
      let data = {
        userId: this.formdata.userId,
        userName: this.formdata.userName,//用户姓名
        userEmail: this.formdata.userEmail,//邮箱
        passWord: this.formdata.passWord,//密码
        userPhone: this.formdata.userPhone,//手机号码
        userSex: this.formdata.userSex,
        userCode: this.formdata.userCode,
        userType: -1,
      }
      // console.log(data)
      toRegister(data).then(res => {
        if (res.data.code == 200) {
          this.$message({
            type: 'success',
            message: '注册成功!'
          });
          console.log(res.data.data)
          this.tabval = "登录"
          this.$refs.ruleForm.resetFields()
        } else {
          this.$message({
            type: 'error',
            message: '注册失败!'
          });
        }
      }).catch(error => {
        console.log(error);
      });
    },
    //切换
    ontab(val) {
      this.tabval = val
      // this.$nextTick(() => {
      //   this.$refs.ruleForm.resetFields()
      // })
      this.formdata = {
        userName: '',
        userId: '',
        passWord: '',
        userPhone: '',
        userEmail: '',
        checkPass: '',
        userSex: '',
        userCode: ''
      }
    },
    toEnable() {

    }
  }
}
</script>

<style lang="scss" scoped>
.el-form-item {
  margin-bottom: 22px;
}

.login {
  background-color: #f9f9f9;
  height: 100%;
}

.login-header {
  width: 100%;
  height: 70px;
  line-height: 70px;
  text-align: left;
  z-index: 10;
  font-family: "微软雅黑";
  box-shadow: 0 3px 4px #f9f9f9;
  background-color: #fff;

  .logo {
    margin: 0 auto;
    width: 1200px;
    display: flex;
    font-size: 18px;

    .el-image {
      color: #2288fe;
      width: 25px;
      height: 31px;
      padding-top: 18px;
      padding-right: 10px;
    }
  }
}

.login-main {
  height: 600px;
  width: 1000px;
  background-color: #fff;
  margin: 0 auto;
  margin-top: 70px;
  background: rgba(255, 255, 255, 1);
  border-radius: 4px;
  display: flex;

  .login-fl {
    width: 422px;
    overflow: hidden;

    img {
      height: 100%;
      margin-left: -205px;
    }
  }

  .login-fr {
    padding-top: 30px;
    width: 578px;
    text-align: center;

    .login-tab {
      display: flex;
      font-size: 16px;
      margin: 0 143px;
      color: #a1a6bb;
      padding-bottom: 30px;

      li {
        width: 50%;
        border-bottom: 2px solid #f0f2f7;

        &:hover {
          border-color: #198cff;
          color: #4E4E4E;
        }

        &.active {
          color: #4E4E4E;
          border-color: #198cff;
        }
      }

      span {
        cursor: pointer;
        display: block;
        text-align: center;
        padding: 10px 0;
      }
    }

    .el-form {
      padding: 0 143px;
    }

    .form-forget {
      // padding-left: 100px;
    }

    .el-button {
      background-color: #198cff;
      color: #fff;
      width: 294px;
      border-radius: 30px;
    }

    .el-select {
      /deep/.el-input__inner {
        width: 100%;
        border-radius: 30px;
      }
    }

    .el-input {
      width: 100%;

      /deep/.el-input__inner {
        width: 100%;
        border-radius: 30px;
      }
    }
  }
}
</style>
