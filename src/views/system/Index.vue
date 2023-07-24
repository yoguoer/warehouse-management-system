<template>
    <div style="background-color:#fff;padding:10px;top:-20px;position:relative">
        <!-- 顶部查询 -->
        <el-form :inline="true">
            <el-form-item>
                <el-input v-model="search1" size="small" placeholder="输入姓名查询" clearable>
                </el-input>
            </el-form-item>
            <el-form-item>
                <el-select size="small" v-model="search2" placeholder="请选择用户角色" clearable>
                    <el-option label="超级管理员" value="0"></el-option>
                    <el-option label="仓库管理员" value="1"></el-option>
                    <el-option label="普通用户" value="2"></el-option>
                    <el-option label="未配置" value="-1"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-select size="small" v-model="search3" placeholder="请选择用户类型" clearable>
                    <el-option label="门店用户" value="0"></el-option>
                    <el-option label="供应商用户" value="1"></el-option>
                    <el-option label="全局用户" value="2"></el-option>
                    <el-option label="未配置" value="-1"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button class="el-icon-search" type="primary" size="small" @click="handleSearch()">查询</el-button>
            </el-form-item>
            <el-form-item>
                <el-button size="small" @click="clean()" icon="el-icon-refresh" type="warning">重置</el-button>
            </el-form-item>
            <el-form-item>
                <el-button class="el-icon-circle-plus-outline" type="success" size="small"
                    @click="dialogAdd = true">添加</el-button>
            </el-form-item>
            <el-form-item>
                <el-button class="el-icon-delete" type="danger" size="small" @click="handleDeleteList()">删除</el-button>
            </el-form-item>
        </el-form>
        <!-- 表格数据 -->
        <el-table ref="multipleTable" :data="tableData" border highlight-current-row :cell-style="{ padding: '5px' }"
            style="width: 100%;min-height:550px;" @selection-change="handleSelectionDelete">
            <el-table-column type="selection" width="55">
            </el-table-column>
            <el-table-column label="工号" sortable>
                <template slot-scope="scope">
                    <span>{{ scope.row.userCode || '-' }}</span>
                </template>
            </el-table-column>
            <el-table-column label="姓名" sortable>
                <template slot-scope="scope">
                    <span>{{ scope.row.userName || '-' }}</span>
                </template>
            </el-table-column>
            <el-table-column label="手机号码">
                <template slot-scope="scope">
                    <span>{{ scope.row.userPhone || '-' }}</span>
                </template>
            </el-table-column>
            <el-table-column label="邮箱">
                <template slot-scope="scope">
                    <span>{{ scope.row.userEmail || '-' }}</span>
                </template>
            </el-table-column>
            <el-table-column label="性别">
                <template slot-scope="scope">
                    <img :src="sex_girl" alt="" style="width:15px;" v-if="scope.row.userSex == '女'" />
                    <img :src="sex_boy" alt="" style="width:15px;" v-else-if="scope.row.userSex == '男'" />
                    {{ scope.row.userSex }}
                </template>
            </el-table-column>
            <el-table-column label="用户角色">
                <template slot-scope="scope">
                    <el-tag :type="scope.row.userType == 0 ? 'danger' : (scope.row.userType == 1 ? 'primary'
                        : (scope.row.userType == 2 ? 'success' : 'warning'))" disable-transitions>
                        {{
                            scope.row.userType == 0 ? '超级管理员' : (scope.row.userType == 1 ? '仓库管理员'
                                : (scope.row.userType == 2 ? '普通用户' : '未配置'))
                        }}
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column label="用户类型">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.userBelong" :type="scope.row.userBelong == 0 ? 'primary'
                        : (scope.row.userBelong == 1 ? 'success'
                            : (scope.row.userBelong == 2 ? 'danger' : 'warning'))" disable-transitions>
                        {{
                            scope.row.userBelong == 0 ? '门店用户'
                            : (scope.row.userBelong == 1 ? '供应商用户'
                                : (scope.row.userBelong == 2 ? '全局用户' : "未配置"))
                        }}
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)">编辑
                    </el-button>
                    <el-button type="text" icon="el-icon-delete" @click="handleDelete(scope.$index, scope.row)"
                        v-if="scope.row.userId != userInfo.userId">删除
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <!-- 新增 -->
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm" size="medium">
            <el-dialog title="添加" v-if="dialogAdd" :append-to-body='true' :visible.sync="dialogAdd"
                :before-close="handleClose" width="600px" :close-on-click-modal="false">
                <el-form-item label="工号" prop="userCode">
                    <el-input v-model="ruleForm.userCode"></el-input>
                </el-form-item>
                <el-form-item label="用户角色" prop="userType">
                    <el-select v-model="ruleForm.userType" placeholder="请选择用户角色" prop="userType" style="width:100%">
                        <el-option label="超级管理员" :value="0"></el-option>
                        <el-option label="仓库管理员" :value="1"></el-option>
                        <el-option label="普通用户" :value="2"></el-option>
                        <el-option label="未配置" :value="-1" disabled></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="姓名" prop="userName">
                    <el-input v-model="ruleForm.userName"></el-input>
                </el-form-item>
                <el-form-item label="手机号码" prop="userPhone">
                    <el-input v-model="ruleForm.userPhone" id="userPhone"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="userEmail">
                    <el-input v-model="ruleForm.userEmail"></el-input>
                </el-form-item>
                <el-form-item label="性别" prop="userSex">
                    <el-select size="middle" v-model="ruleForm.userSex" clearable placeholder="性别" style="width:100%">
                        <el-option label="男" value="男"></el-option>
                        <el-option label="女" value="女"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="用户类型" prop="userBelong">
                    <el-select size="small" v-model="ruleForm.userBelong" placeholder="请选择用户类型" clearable
                        style="width:100%">
                        <el-option label="门店用户" value="0"></el-option>
                        <el-option label="供应商用户" value="1"></el-option>
                        <el-option label="全局用户" value="2"></el-option>
                        <el-option label="未配置" value="-1" disabled></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="用户密码" prop="passWord">
                    <el-input v-model="ruleForm.passWord"></el-input>
                </el-form-item>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="emptyUserData()" size="medium">取 消</el-button>
                    <el-button @click="addUser('ruleForm')" type="primary" size="medium">确 定</el-button>
                </span>
            </el-dialog>
        </el-form>
        <!-- 编辑 -->
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm1" label-width="100px" class="demo-ruleForm" size="medium">
            <el-dialog title="编辑" v-if="dialogUpdate" :append-to-body='true' :visible.sync="dialogUpdate"
                :before-close="handleClose" width="600px" :close-on-click-modal="false">
                <el-form-item label="工号" prop="userCode">
                    <el-input v-model="ruleForm.userCode"></el-input>
                </el-form-item>
                <el-form-item label="用户角色" prop="userType">
                    <el-select v-model="ruleForm.userType" placeholder="请选择用户角色" prop="userType" style="width:100%">
                        <el-option label="超级管理员" :value="0"></el-option>
                        <el-option label="仓库管理员" :value="1"></el-option>
                        <el-option label="普通用户" :value="2"></el-option>
                        <el-option label="未配置" :value="-1" disabled></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="姓名" prop="userName">
                    <el-input v-model="ruleForm.userName"></el-input>
                </el-form-item>
                <el-form-item label="手机号码" prop="userPhone">
                    <el-input v-model="ruleForm.userPhone" id="userPhone"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="userEmail">
                    <el-input v-model="ruleForm.userEmail"></el-input>
                </el-form-item>
                <el-form-item label="性别" prop="userSex">
                    <!-- <el-input v-model="ruleForm.userSex"></el-input> -->
                    <el-select size="middle" v-model="ruleForm.userSex" clearable placeholder="性别" style="width:100%">
                        <el-option label="男" value="男"></el-option>
                        <el-option label="女" value="女"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="用户类型" prop="userBelong">
                    <el-select size="small" v-model="ruleForm.userBelong" placeholder="请选择用户类型" clearable
                        style="width:100%">
                        <el-option label="门店用户" value="0"></el-option>
                        <el-option label="供应商用户" value="1"></el-option>
                        <el-option label="全局用户" value="2"></el-option>
                        <el-option label="未配置" value="-1" disabled></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="用户密码" prop="passWord">
                    <el-input v-model="ruleForm.passWord"></el-input>
                </el-form-item>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="emptyUserData()" size="medium">取 消</el-button>
                    <el-button @click="updateUser('ruleForm1')" type="primary" size="medium">确 定</el-button>
                </span>
            </el-dialog>
        </el-form>
        <div class="page-box">
            <el-pagination layout="total, sizes, prev, pager, next, jumper" :page-sizes="[10, 20, 50, 100]"
                :current-page="parseInt(pageNo)" :total="total" :page-size.sync="pageSize" :page-no.sync="pageNo"
                @current-change="_page" @size-change="_pageSize"></el-pagination>
        </div>
    </div>
</template>

<script>
import { createUser, deleteUserById, deleteUserByIdList, getUserList, updateUserById } from "../../api/api";
import qs from "qs";
import sex_girl from '@/assets/images/sex_girl.png'
import sex_boy from '@/assets/images/sex_boy.png'

export default {
    name: 'userManage',
    data() {
        return {
            sex_girl: sex_girl,
            sex_boy: sex_boy,
            ruleForm: {
                userId: null,//用户id
                userCode: null,//工号
                userType: null,//用户角色
                userName: null,//用户姓名
                userPhone: null,//手机号码
                userEmail: null,//邮箱
                userSex: null,//性别
                userBelong: null,
                passWord: null,
            },
            tableData: [],
            search1: '',
            search2: '',
            search3: '',
            dialogAdd: false,
            dialogUpdate: false,
            pageSize: 10,
            pageNo: 1,
            total: 0,
            disablePage: false,
            multipleSelection: [],
            userInfo: '',
            shopOptions: [],
            rules: {
                userCode: [
                    { required: true, message: '请设置工号', trigger: 'blur' },
                ],
                userType: [
                    { required: true, message: '请设置用户角色', trigger: 'blur' },
                ],
                userName: [
                    { required: true, message: '请设置用户姓名', trigger: 'blur' },
                ],
                userPhone: [
                    { required: true, message: '请设置手机号码', trigger: 'blur' },
                ],
                userEmail: [
                    { required: true, message: '请设置邮箱', trigger: 'blur' },
                ],
                userSex: [
                    { required: true, message: '请设置性别', trigger: 'blur' },
                ],
                userBelong: [
                    { required: true, message: '请设置用户类型', trigger: 'blur' },
                ],
                passWord: [
                    { required: true, message: '请设置用户密码', trigger: 'blur' },
                ],
            },
        };
    },
    //初始化
    created() {
        this.userInfo = JSON.parse(localStorage.getItem("userInfo"))
        this.getUserListPage()
    },

    methods: {
        getUserListPage() {
            let postData = ({
                page: this.pageNo,
                size: this.pageSize,
                userName: this.search1,
                userType: this.search2
            })
            getUserList(postData).then(res => {
                this.tableData = res.data.data.records;
                this.total = res.data.data.total
                this.$forceUpdate()
            }).catch(err => {
                console.log(err)
            });
        },
        _pageSize(val) {
            this.pageSize = val
            this.getUserListPage()
        },
        _page(val) {
            this.pageNo = val;
            this.getUserListPage()
        },
        checkPhone() {
            var userPhone = document.getElementById('userPhone').value;
            if (!(/\d{11}$/.test(userPhone))) {
                alert("手机号码有误，请重填");
                return false;
            } else if (!(/^1[3456789]\d{9}$/.test(userPhone))) {
                alert("此号码无效，请重填");
                return false;
            } else {
                return true;
            }
        },
        //添加用户
        addUser(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    if (this.checkPhone()) {
                        let postData = ({
                            userName: this.ruleForm.userName,//用户姓名
                            userCode: this.ruleForm.userCode,//工号
                            userEmail: this.ruleForm.userEmail,//邮箱
                            userPhone: this.ruleForm.userPhone,//手机号码
                            userType: this.ruleForm.userType,//用户类型
                            userSex: this.ruleForm.userSex,
                            userBelong: this.ruleForm.userBelong,
                            passWord: this.ruleForm.passWord,
                        });
                        createUser(postData).then(res => {
                            if (res.data.code == 200) {
                                this.getUserListPage()
                                this.$message({
                                    type: 'success',
                                    message: '已添加!'
                                });
                                this.emptyUserData()
                            } else {
                                this.$message({
                                    type: 'error',
                                    message: res.data.msg
                                });
                            }
                        }).catch(err => {
                            console.log(err)
                        });
                    }
                } else {
                    console.log('error submit!!');
                    return false;
                }
            })
        },

        //查询用户
        handleSearch() {
            let postData = qs.stringify({
                userName: this.search1,
                userType: this.search2,
                userBelong: this.search3,
                page: 1,
                size: 10,
            });
            getUserList(postData).then(res => {
                this.tableData = res.data.data.records
                this.total = res.data.data.total
            }).catch(err => {
                console.log(err)
            });
        },

        //编辑用户
        handleEdit(index, row) {
            this.dialogUpdate = true
            this.ruleForm = Object.assign({}, row, index)//这句是关键！！！
        },
        handleClose(done) {
            this.$confirm('确认关闭？')
                .then(_ => {
                    done()
                    this.emptyUserData()
                })
                .catch(_ => {
                })
        },

        /**
         * 清空绑定数据
         */
        emptyUserData() {
            this.dialogAdd = false
            this.dialogUpdate = false
            this.ruleForm = {
                userId: null,//用户id
                userCode: null,//工号
                userType: null,//用户角色
                userName: null,//用户姓名
                userPhone: null,//手机号码
                userEmail: null,//邮箱
                userSex: null,
                userBelong: null,
                passWord: null
            };
        },

        //根据 userId 删除用户
        handleDelete(index, row) {
            //console.log(index, row)
            this.$confirm('删除操作, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                let postData = qs.stringify({
                    userId: row.userId,
                });
                deleteUserById(postData).then(response => {
                    this.getUserListPage()
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                    //console.log(response);
                }).catch(error => {
                    console.log(error)
                });

            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },

        //批量删除选择
        handleSelectionDelete(val) {
            this.multipleSelection = val
        },

        //根据 userId 批量删除用户
        handleDeleteList() {
            let userIds = "";
            this.multipleSelection.forEach(item => {
                userIds += item.userId + ','
            })
            console.log(userIds);
            // let userIds= this.multipleSelection.map(item => item.userId).join()

            this.$confirm('删除操作, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                let postData = qs.stringify({
                    userIdList: userIds
                });
                console.log(postData);
                deleteUserByIdList(postData).then(response => {
                    this.getUserListPage();
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                    //console.log(response);
                }).catch(error => {
                    console.log(error);
                });
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },

        //更新用户
        updateUser(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    if (this.checkPhone()) {
                        let postData = qs.stringify({
                            userId: this.ruleForm.userId,//用户 Id
                            userCode: this.ruleForm.userCode,//工号
                            userType: this.ruleForm.userType,//用户角色
                            userName: this.ruleForm.userName,//用户姓名
                            userPhone: this.ruleForm.userPhone,//手机号码
                            userEmail: this.ruleForm.userEmail,//邮箱
                            userSex: this.ruleForm.userSex,
                            userBelong: this.ruleForm.userBelong,
                            passWord: this.ruleForm.passWord,
                        });
                        updateUserById(postData).then(res => {
                            if (res.data.code == 200) {
                                this.getUserListPage()
                                this.$message({
                                    type: 'success',
                                    message: '已编辑!'
                                });
                                this.emptyUserData()
                            } else {
                                this.$message({
                                    type: 'error',
                                    message: res.data.msg
                                });
                            }
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
        clean() {
            this.search1 = ''
            this.search2 = ''
            this.search3 = ''
            this.pageNo = 1
            this.getUserListPage()
        }
    },
}
</script>
<style scoped>
.page-box {
    text-align: right;
    margin: 20px;
}
</style>
