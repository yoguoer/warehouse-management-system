<template>
    <el-dialog title="编辑个人信息" :visible.sync="dialogFormVisible" width="500px" :before-close="close"
        :close-on-click-modal="false" :modal-append-to-body="false">
        <el-form :model="form" :rules="rules" label-width="80px" ref="ruleForm">
            <el-form-item label="姓名" prop="userName">
                <el-input v-model="form.userName"></el-input>
            </el-form-item>
            <el-form-item label="工号" prop="userCode">
                <el-input v-model="form.userCode"></el-input>
            </el-form-item>
            <el-form-item label="手机号码" prop="userPhone">
                <el-input v-model="form.userPhone" id="userPhone"></el-input>
            </el-form-item>
            <el-form-item label="邮箱" prop="userEmail">
                <el-input v-model="form.userEmail"></el-input>
            </el-form-item>
            <el-form-item label="性别" prop="userSex">
                <!-- <el-input v-model="form.userSex"></el-input> -->
                <el-select size="middle" v-model="form.userSex" clearable placeholder="性别" style="width:100%">
                    <el-option label="男" value="男"></el-option>
                    <el-option label="女" value="女"></el-option>
                </el-select>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="close()">取 消</el-button>
            <el-button type="primary" @click="submit('ruleForm')">确 定</el-button>
        </div>
    </el-dialog>
</template>

<script>
import { updateUserById } from '@/api/api'
import qs from "qs";

export default {
    name: 'guestEdit',
    data() {
        return {
            form: {
                userId: "",//用户id
                userCode: "",//工号
                userType: "",//用户角色
                userName: "",//用户姓名
                userPhone: "",//手机号码
                userEmail: "",//邮箱
                userSex: "",//性别
            },
            rules: {
                userPhone: [
                    { required: true, message: '请输入用户手机', trigger: 'blur' }
                ],
                userCode: [
                    { required: true, message: '请输入用户工号', trigger: 'blur' }
                ],
                userName: [
                    { required: true, message: '请输入用户姓名', trigger: 'blur' }
                ],
                userEmail: [
                    { required: true, message: '请输入邮箱', trigger: 'blur' }
                ],
            }
        }
    },
    props: {
        dialogFormVisible: {
            type: Boolean,
            default: true
        },
        rowData: {}
    },
    watch: {

    },
    created() {
        this.form.userId = this.rowData.userId//用户id
        this.form.userCode = this.rowData.userCode
        this.form.userType = this.rowData.userType//用户角色
        this.form.userName = this.rowData.userName//用户姓名
        this.form.userPhone = this.rowData.userPhone//手机号码
        this.form.userEmail = this.rowData.userEmail//邮箱
        this.form.userSex = this.rowData.userSex//性别
        // console.log(this.rowData)
    },
    methods: {
        checkPhone() {
            var userPhone = document.getElementById('userPhone').value;
            if (!(/\d{11}$/.test(userPhone))) {
                // alert("手机号码有误，请重填");
                this.$message({
                    type: 'error',
                    message: '手机号码有误，请重填!'
                })
                return false;
            } else if (!(/^1[3456789]\d{9}$/.test(userPhone))) {
                // alert("此号码无效，请重填");
                this.$message({
                    type: 'error',
                    message: '此号码无效，请重填!'
                })
                return false;
            } else {
                return true;
            }
        },
        submit(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    if (this.checkPhone()) {
                        this.$parent.dialogFormVisible = false
                        let postData = qs.stringify({
                            userId: this.form.userId,//用户 Id
                            userCode: this.form.userCode,
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
                    }
                } else {
                    console.log('error submit!!');
                    return false;
                }
            })
        },
        close() {
            this.$parent.dialogFormVisible = false
        }
    }
}
</script>
<style lang="scss">

</style>