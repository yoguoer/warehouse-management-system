<template>
    <el-dialog title="修改密码" :visible.sync="dialogVisible" width="500px" :before-close="close">
    <el-form :model="form" label-width="80px" :rules="rules">
        <el-form-item label="原始密码" prop="currentPwd">
            <el-input v-model="form.currentPwd"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="passWord">
            <el-input v-model="form.passWord"></el-input>
        </el-form-item>
        <el-form-item  label="确认密码" prop="checkPass">
              <el-input  placeholder="确认密码" ref="surepassWord" type="passWord" v-model="form.checkPass" maxlength="20" />
            </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
        <el-button @click="close()">取 消</el-button>
        <el-button type="primary" @click="submit()">确 定</el-button>
    </div>
    </el-dialog>
</template>

<script>
import { updateUserById } from '@/api/api'

export default {
    name: 'guestEdit',
    data(){
    return{
        form: {
            userId:"",
            currentPwd:"",
            passWord:"",
            checkPass:""
        },
        rules: {
            currentPwd: [
                {required: true, message: '请输入原始密码', trigger: 'blur'}
            ],
            passWord: [
                {required: true, message: '请输入新密码', trigger: 'blur'}
            ],
            checkPass: [
                {required: true, message: '请确认密码', trigger: 'blur'}
            ],
        }
    }
    },
    props: {
        dialogVisible:true,
        rowData:{}
    },
    watch:{

    },
    created(){
    },
    methods:{
        submit(){
            if(this.form.passWord!=this.form.checkPass){
                this.$message({
                    type: 'error',
                    message: '密码不一致!'
                });
            }else{
                if(this.form.currentPwd==this.$parent.userInfo.passWord){
                this.$parent.dialogVisible = false
                let postData = this.qs.stringify({
                    userId: this.$parent.userInfo.userId,//用户 userId
                    passWord: this.form.passWord,//用户密码
                });
                updateUserById(postData).then(response => {
                    this.$message({
                        type: 'success',
                        message: '密码修改成功!'
                    })
                    this.$parent.getUserInfo()
                    }).catch(error => {
                        console.log(error)
                    });
                }else{
                    this.$message({
                        type: 'error',
                        message: '原始密码错误!'
                    });
                }
            }
        },
        close(){
            this.$parent.dialogVisible = false
        }
    }
}
</script>
<style lang="scss">

</style>