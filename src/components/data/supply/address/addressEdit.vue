<template>
    <el-dialog :title="ifCreate ? '新增供应商地址' : '编辑供应商地址'" :visible.sync="drawer" :direction="direction"
        :close-on-press-escape="false" :show-close="false" :wrapperClosable="false" :append-to-body="true"
        width="780px">
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px">
            <el-form-item label="供应商" prop="supplierAddressKey">
                <el-select v-model="ruleForm.supplierAddressKey" placeholder="请选择供应商" clearable filterable remote
                    :remote-method="remoteMethod" style="width: 100%">
                    <el-option v-for="(item, index) in supplierList" :key="item.supplierKey + index"
                        :label="item.supplierName" :value="item.supplierKey">
                        <div>[{{ item.supplierCode }}]{{ item.supplierName }}</div>
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="地址类型" prop="addressType">
                <el-input v-model="ruleForm.addressType" placeholder="地址类型" clearable></el-input>
            </el-form-item>
            <el-form-item label="联系地址" prop="address" v-model="ruleForm.address">
                <checkAddress ref="address" v-model="ruleForm.address" :form="ruleForm.address" />
            </el-form-item>
            <el-row class="dialog_footer">
                <el-button type="primary" @click="save('ruleForm')" v-if="ifCreate == false">提交</el-button>
                <el-button type="primary" @click="create('ruleForm')" v-else>立即创建</el-button>
                <el-button @click="close()">关闭</el-button>
            </el-row>
        </el-form>
    </el-dialog>
</template>

<script>
import { supplierAddressUpdate, supplierAddressAdd, Supplierlist } from "@/api/data";
import checkAddress from '@/components/public/checkAddress.vue'
export default {
    components: {
        checkAddress
    },
    data() {
        var validatePass = (rule, value, callback) => {

            if (this.ruleForm.address.province == '' || this.ruleForm.address.detail == '') {
                callback(new Error('请输入完整地址'));
            }
            callback();
        };
        return {
            // 供应商下拉列表
            supplierList: [],

            direction: 'btt',
            ifCreate: false,
            ruleForm: {
                addressKey: "",
                addressType: "",
                supplierAddressKey: "",
                address: {
                    province: "",
                    city: "",
                    district: "",
                    detail: ""
                }
            },
            rules: {
                supplierAddressKey: [
                    { required: true, message: '请选择供应商', trigger: 'blur' },
                ],
                addressType: [
                    { required: true, message: '请输入地址类型', trigger: 'blur' },
                ],
                address: [
                    { required: true, validator: validatePass, trigger: 'blur' },
                ]
            }
        }
    },
    props: {
        drawer: {
            default: true,
        },
        rowData: {},
        multiple: {
            type: Boolean,
            default: false
        },
    },
    methods: {
        close() {
            this.$parent.drawer = false
        },
        //新增
        create(formName) {
            let data = {
                // addressKey:this.ruleForm.addressKey,
                province: this.ruleForm.address.province,
                city: this.ruleForm.address.city,
                district: this.ruleForm.address.district,
                detail: this.ruleForm.address.detail,
                addressType: this.ruleForm.addressType,
                supplierAddressKey: this.ruleForm.supplierAddressKey,
            }
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    supplierAddressAdd(data)
                        .then((res) => {
                            if (res.data.code === 200) {
                                this.$parent.success()
                                this.$forceUpdate()
                                this.$message.success('新增成功!')
                                this._handleFresh()
                            } else {
                                this.$message.error(res.msg)
                            }
                        })
                        .catch((e) => {
                            console.log(e);
                        });
                } else {
                    console.log('error submit!!');
                    return false;
                }
            })
        },
        //更新
        save(formName) {
            let data = {
                addressKey: this.ruleForm.addressKey,
                province: this.ruleForm.address.province,
                city: this.ruleForm.address.city,
                district: this.ruleForm.address.district,
                detail: this.ruleForm.address.detail,
                addressType: this.ruleForm.addressType,
                supplierAddressKey: this.ruleForm.supplierAddressKey,
            }
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    supplierAddressUpdate(data)
                        .then((res) => {
                            if (res.data.code === 200) {
                                this.$parent.success()
                                this.$forceUpdate()
                                this.$message.success('修改成功!')
                                this._handleFresh()
                            } else {
                                this.$message.error(res.msg)
                            }
                        })
                        .catch((e) => {
                            console.log(e);
                        });
                } else {
                    console.log('error submit!!');
                    return false;
                }
            })
        },
        getSupplierlist() {
            Supplierlist().then(res => {
                if (res.data.code == 200) {
                    this.supplierList = res.data.data
                } else {
                    this.$message.error("获取失败!");
                }
            });
        },
        remoteMethod(e) {
            this.getSupplierList(e)
        }
    },
    watch: {

    },
    created() {
        this.getSupplierlist()
        if (this.rowData.addressKey) {
            this.ruleForm.addressKey = this.rowData.addressKey
            this.ruleForm.address.province = this.rowData.province
            this.ruleForm.address.city = this.rowData.city
            this.ruleForm.address.district = this.rowData.district
            this.ruleForm.address.detail = this.rowData.detail
            this.ruleForm.addressType = this.rowData.addressType
            this.ruleForm.supplierAddressKey = this.rowData.supplierAddressKey
        } else {
            this.ifCreate = true
        }
    }
}
</script>

<style lang="scss" scoped>
/deep/ .input-number {
    .el-input-number__decrease,
    .el-input-number__increase {
        height: 34px;
        display: flex;
        align-items: center;
        justify-content: center;
    }
}
</style>