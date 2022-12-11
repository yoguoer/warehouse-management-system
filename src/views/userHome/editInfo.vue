<template>
    <el-dialog title="编辑个人信息" :visible.sync="dialogFormVisible" width="500px" :before-close="close">
    <el-form :model="form" :rules="rules" label-width="80px">
        <el-form-item label="姓名" prop="userName">
            <el-input v-model="form.userName"></el-input>
        </el-form-item>
        <el-form-item label="手机号码" prop="userPhone">
            <el-input v-model="form.userPhone"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="userEmail">
            <el-input v-model="form.userEmail"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="userSex">
            <!-- <el-input v-model="form.userSex"></el-input> -->
            <el-select size="middle" v-model="form.userSex" clearable  placeholder="性别" style="width:100%">
                <el-option label="男" value="男"></el-option>
                <el-option label="女" value="女"></el-option>
            </el-select>
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
            userId: "",//用户id
            userType: "",//用户角色
            userName: "",//用户姓名
            userPhone: "",//手机号码
            userEmail: "",//邮箱
            userSex:"",//性别
        },
        rules: {
            userPhone: [
                {required: true, message: '请输入用户手机', trigger: 'blur'}
            ],
            userName: [
                {required: true, message: '请输入用户姓名', trigger: 'blur'}
            ],
            userEmail: [
                {required: true, message: '请输入邮箱', trigger: 'blur'}
            ],
        }
    }
    },
    props: {
        dialogFormVisible:true,
        rowData:{}
    },
    watch:{

    },
    created(){
        this.form.userId=this.rowData.userId//用户id
        this.form.userType=this.rowData.userType//用户角色
        this.form.userName=this.rowData.userName//用户姓名
        this.form.userPhone=this.rowData.userPhone//手机号码
        this.form.userEmail=this.rowData.userEmail//邮箱
        this.form.userSex=this.rowData.userSex//性别
        // console.log(this.rowData)
    },
    methods:{
        submit(){
            this.$parent.dialogFormVisible = false
            let postData = this.qs.stringify({
                userId: this.form.userId,//用户 Id
                userType: this.form.userType,//用户角色
                userName: this.form.userName,//用户姓名
                userPhone: this.form.userPhone,//手机号码
                userEmail: this.form.userEmail,//邮箱
                userSex: this.form.userSex
            });
            updateUserById(postData).then(response => {
                this.$message({
                    type: 'success',
                    message: '已编辑!'
                })
                this.$parent.getUserInfo()
            }).catch(error => {
                console.log(error)
            });
        },
        close(){
            this.$parent.dialogFormVisible = false
        }
    }
}
</script>
<style lang="scss">

</style>