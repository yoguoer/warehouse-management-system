<template>
    <div>
        <el-dialog :visible.sync="visible" :before-close="close" :top="patern == 'stockSchedule' ? '15vh' : '25vh'" :modal-append-to-body="false"
            :closeOnClickModal="false" :width="patern == 'stockSchedule' ? '700px' : '450px'">
            <template slot="title">
                <div class="dialog-title">
                    {{titleObj[patern].title}}
                    <div class="title-tips">
                        {{titleObj[patern].tips}}
                    </div>
                </div>
            </template>
            <div class="dialog_body">

                <el-row :gutter="20">
                        <el-col :span="12">
                            <div>供应商仓库</div>
                        </el-col>
                        <el-col :span="12">
                            <div>SKU</div>
                        </el-col>
                    </el-row>
                    <el-row :gutter="20">
                        <el-col :span="12">
                            <div class="supplier-warehouse-text">
                                <span>{{ rawData.supplierWareHouseCode }} </span>
                                <span>{{ rawData.supplierWareHouse }}</span>
                            </div>
                        </el-col>
                        <el-col :span="12">
                            <div class="supplier-warehouse-text">
                                <span>{{ rawData.sku }} </span>
                                <br />
                                <span>{{ rawData.skuName }}</span>
                            </div>
                        </el-col>
                    </el-row>

                <!-- 录入备料 -->
                <template v-if="patern == 'prepareStuff'">
                    <el-form size="small" :model="prepareStuffForm" :rules="rules" :inline="true" label-position="top" label-width="70px" ref="prepareStuffForm">
                        <el-row :gutter="20">
                            <el-col :span="12">
                                <el-form-item prop="ctrlType" label="调整方式" class="is-required">
                                    <el-radio v-model="prepareStuffForm.ctrlType" label="1">增加</el-radio>
                                    <el-radio v-model="prepareStuffForm.ctrlType" label="2">减少</el-radio>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item prop="ctrlNum" label="数量" class="is-required ctrl-num">
                                    <el-input-number v-model="prepareStuffForm.ctrlNum" controls-position="right" @change="handleCtrlNumChange" :min="1" :max="rawData.product ? rawData.product : 2"></el-input-number>
                                </el-form-item>
                                <div class="ctrl-num-limit" v-if="(prepareStuffForm.ctrlType == 2)">
                                    <span>调整不可超过当前总数量 </span>
                                    <span>{{rawData.product ? rawData.product : 2}}</span>
                                </div>
                            </el-col>
                        </el-row>
                    </el-form>
                </template>

                <!-- 录入成品 -->
                <template v-if="patern == 'product'">
                    <el-form size="small" :model="productForm" :rules="rules" :inline="true" label-position="top" label-width="70px" ref="productForm">
                        <el-row :gutter="20">
                            <el-col :span="12">
                                <el-form-item prop="ctrlType" label="调整方式" class="is-required">
                                    <el-radio v-model="productForm.ctrlType" label="1">增加</el-radio>
                                    <el-radio v-model="productForm.ctrlType" label="2">减少</el-radio>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item prop="ctrlNum" label="数量" class="is-required ctrl-num">
                                    <el-input-number v-model="productForm.ctrlNum" controls-position="right" @change="handleCtrlNumChange" :min="1" :max="(productForm.ctrlType == 2 ? rawData.product : 999999999)"></el-input-number>
                                </el-form-item>
                                <div class="ctrl-num-limit" v-if="(productForm.ctrlType == 2)">
                                    <span>调整不可超过当前总数量 </span>
                                    <span>{{rawData.product}}</span>
                                </div>
                            </el-col>
                        </el-row>
                    </el-form>
                </template>

                <!-- 录入排产 -->
                <template v-if="patern == 'stockSchedule'">
                    <el-table :data="list" border height="250px" size="mini">
                        <el-table-column prop="oldExpectTime" label="预计完工日期" width="130"></el-table-column>
                        <el-table-column prop="oldStockSchedule" label="数量" width="100"></el-table-column>
                        <el-table-column prop="expectTime" label="调整后日期">
                            <template slot="header">
                                <div class="requried-item">调整后日期</div>
                            </template>
                            <template slot-scope="scope">
                                <el-date-picker type="date" v-model="scope.row.expectTime" value-format="yyyy-MM-dd" placeholder="选择日期" size="mini"
                                style="width: 100%"></el-date-picker>
                            </template>
                        </el-table-column>
                        <el-table-column prop="expectTime" label="调整后数量">
                            <template slot="header">
                                <div class="requried-item">调整后数量</div>
                            </template>
                            <template slot-scope="scope">
                                <el-input-number size="small" :min="1" step-strictly v-model="scope.row.stockSchedule" controls-position="right" />
                            </template>
                        </el-table-column>
                        <el-table-column label="操作" width="100">
                            <template slot-scope="scope">
                                <el-button class="prohibitclick" type="text" @click="deleteStockSchedule(scope.$index)">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <el-button class="add-button" type="primary" size="small" @click="addStockSchedule()">新增排产</el-button>
                </template>
                <div class="dialog_footer">
                    <el-button type="" size="small" @click="close()">取消</el-button>
                    <el-button type="primary" size="small" @click="submit()">确定</el-button>
                </div>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import { dateFormat1 } from '@/utils/util.js';
export default {
    name: "inputInfo",
    components: {},
    props: {
        visible: {
            default: false,
        },
        patern: String,
        rawData: Object,
    },
    data() {
        return {
            prepareStuffForm: {
                supplier: "",
                ctrlType: "1",
                ctrlNum: 1
            },
            productForm: {
                supplier: "",
                ctrlType: "1",
                ctrlNum: 1
            },
            rules: {
                supplier: [
                    { required: true, message: "请选择供应商", trigger: "blur" },
                ],
            },
            supplierList: [],

            titleObj: {
                prepareStuff: {
                    title: "录入备料",
                    tips: ""
                },
                stockSchedule: {
                    title: "录入排产",
                    tips: "系统将自动抵扣备料数"
                },
                product: {
                    title: "录入成品",
                    tips: '系统将自动抵扣备料与排产数'
                }
            },

            // 录入排产
            searchLoading: false,
            list: []
        };
    },
    created() {
        this.getSupplierList();
        
        if (this.patern == 'stockSchedule') {
            this.getStockScheduleList()
        }
    },
    methods: {
        close() {
            this.$emit("close");
        },
        getSupplierList() {
            const res = {
                records: [
                    {
                        supplierCode: "10100",
                        name: "温州智博印业有限公司",
                        departmentGroupName: null,
                        departmentGroupCode: null,
                        outsideCode: "10100",
                        type: 10,
                    },
                    {
                        supplierCode: "10000",
                        name: "广州乐言文创实业有限公司",
                        departmentGroupName: null,
                        departmentGroupCode: null,
                        outsideCode: "10000",
                        type: 10,
                    },
                    {
                        supplierCode: "10101",
                        name: "福州旭和电子有限公司",
                        departmentGroupName: null,
                        departmentGroupCode: null,
                        outsideCode: "10101",
                        type: 10,
                    },
                    {
                        supplierCode: "10001",
                        name: "福建集成伞业有限公司",
                        departmentGroupName: null,
                        departmentGroupCode: null,
                        outsideCode: "10001",
                        type: 10,
                    },
                    {
                        supplierCode: "10102",
                        name: "宁波开利控股集团股份有限公司",
                        departmentGroupName: null,
                        departmentGroupCode: null,
                        outsideCode: "10102",
                        type: 10,
                    },
                    {
                        supplierCode: "10002",
                        name: "浙江新光饰品股份有限公司",
                        departmentGroupName: null,
                        departmentGroupCode: null,
                        outsideCode: "10002",
                        type: 10,
                    },
                    {
                        supplierCode: "10103",
                        name: "福建时嘉贸易有限公司",
                        departmentGroupName: null,
                        departmentGroupCode: null,
                        outsideCode: "10103",
                        type: 10,
                    },
                    {
                        supplierCode: "10104",
                        name: "汕头市怡佳塑胶制品有限公司",
                        departmentGroupName: null,
                        departmentGroupCode: null,
                        outsideCode: "10104",
                        type: 10,
                    },
                    {
                        supplierCode: "10003",
                        name: "广州市盛兆日用品实业有限公司",
                        departmentGroupName: null,
                        departmentGroupCode: null,
                        outsideCode: "10003",
                        type: 10,
                    },
                    {
                        supplierCode: "10105",
                        name: "昆山天源精工工业有限公司",
                        departmentGroupName: null,
                        departmentGroupCode: null,
                        outsideCode: "10105",
                        type: 10,
                    },
                    {
                        supplierCode: "10004",
                        name: "浙江艳庄化妆品有限公司",
                        departmentGroupName: null,
                        departmentGroupCode: null,
                        outsideCode: "10004",
                        type: 10,
                    },
                    {
                        supplierCode: "10106",
                        name: "义乌市亿利文体用品有限公司",
                        departmentGroupName: null,
                        departmentGroupCode: null,
                        outsideCode: "10106",
                        type: 10,
                    },
                    {
                        supplierCode: "10005",
                        name: "广州莉来雅化妆品有限公司",
                        departmentGroupName: null,
                        departmentGroupCode: null,
                        outsideCode: "10005",
                        type: 10,
                    },
                    {
                        supplierCode: "10006",
                        name: "广州市玛莉安化妆用品有限公司",
                        departmentGroupName: null,
                        departmentGroupCode: null,
                        outsideCode: "10006",
                        type: 10,
                    },
                    {
                        supplierCode: "10107",
                        name: "深圳市华增科技有限公司",
                        departmentGroupName: null,
                        departmentGroupCode: null,
                        outsideCode: "10107",
                        type: 10,
                    },
                    {
                        supplierCode: "10202",
                        name: "恩平市卓尔礼品实业有限公司",
                        departmentGroupName: null,
                        departmentGroupCode: null,
                        outsideCode: "10202",
                        type: 10,
                    },
                    {
                        supplierCode: "10007",
                        name: "惠州市惠城区三千梳刷厂",
                        departmentGroupName: null,
                        departmentGroupCode: null,
                        outsideCode: "10007",
                        type: 10,
                    },
                    {
                        supplierCode: "10203",
                        name: "佛山卡蛙科技股份有限公司",
                        departmentGroupName: null,
                        departmentGroupCode: null,
                        outsideCode: "10203",
                        type: 10,
                    },
                    {
                        supplierCode: "10008",
                        name: "广州市贝奇皮具有限公司",
                        departmentGroupName: null,
                        departmentGroupCode: null,
                        outsideCode: "10008",
                        type: 10,
                    },
                    {
                        supplierCode: "10108",
                        name: "广州市柏姿生物科技有限公司",
                        departmentGroupName: null,
                        departmentGroupCode: null,
                        outsideCode: "10108",
                        type: 10,
                    },
                ],
                total: 3549,
                size: 20,
                current: 1,
                pages: 178,
            };
            this.supplierList = res.records;
        },
        handleCtrlNumChange(val) {
            console.log(val);
        },

        // 获取排产信息
        getStockScheduleList() {
            this.searchLoading = true;
            // const res = await searchStockSchedule({
            //   sku: this.row.sku,
            //   supplierWareHouseCode: this.row.supplierWareHouseCode,
            // });
            const res = {
                code: 200,
                msg: 'success',
                data: {
                    stockScheduleList: [{
                        expectTime: '2022-11-30',
                        stockSchedule: '1000',
                    }],
                },
                success: true,
            };
            this.list = res.data.stockScheduleList.map((item) => ({
                ...item,
                expectTime: dateFormat1(new Date(item.expectTime), 'yyyy-MM-dd'),
                oldExpectTime: dateFormat1(new Date(item.expectTime), 'yyyy-MM-dd'),
                oldStockSchedule: item.stockSchedule,
            }));
            this.searchLoading = false;
        },

        // 删除 排产列
        deleteStockSchedule(rowIndex) {
            this.list.splice(rowIndex, 1);
        },

        // 新增排产信息
        addStockSchedule() {
            this.list.push({
                sku: this.rawData.sku,
                skuName: this.rawData.skuName,
                supplierWareHouseCode: this.rawData.supplierWareHouseCode,
                oldExpectTime: '-',
                oldStockSchedule: '-',
                expectTime: dateFormat1(new Date(), 'yyyy-MM-dd'),
                stockSchedule: 1,
                isAdd: true,
            });
        },
        submit() {
            // 录入备料
            if (this.patern == 'prepareStuff') {
                this.inputPrepareStuff()
            // 录入成品
            } else if(this.patern == 'product') {
                this.inputProduct()
            // 录入排产
            } else if (this.patern == 'stockSchedule') {
                this.inputStockSchedule()
            }
        },
        // 录入备料
        inputPrepareStuff() {

        },
        // 录入成品
        inputProduct() {

        },
        // 录入排产
        inputStockSchedule() {
            const params = {
                sku: this.rawData.sku,
                skuName: this.rawData.skuName,
                stockSupplierScheduleList: this.list.map((item) => ({
                    ...item,
                    expectTime: `${item.expectTime} 00:00:00`,
                    tenant: '000000',
                })),
                supplierWareHouseCode: this.rawData.supplierWareHouseCode,
                tenant: '000000',
            };
            console.log(params);
        }
    },
};
</script>
<style lang="scss" scoped>
.supplier-warehouse-text {
    padding: 15px 0;
}

.ctrl-num {

    margin-bottom: 10px;
    /deep/ .el-input .el-input__inner {
        height: 29px;
    }
}

.ctrl-num-limit {
    font-size: 12px;
    color: #ccc;
}

.dialog-title {
    display: flex;
    align-items: center;

    .title-tips {
        margin-left: 15px;
        font-size: 13px;
        color: #ccc;
    }
}

.requried-item {
    &::before {
        content: '*';
        color: #F56C6C;
        margin-right: 4px;
    }
}

.add-button {
    width: 100%;
    margin-top: 20px;
}
</style>
