<template>
    <div>
        <div class="welmsg">
            <!-- 欢迎区域 -->
            <div style="margin:10px;">
                <el-card shadow="always" body-style="padding:30px;display: inline-flex;height:300px;width:830px;">
                    <div><img :src="welcomePic" alt="" style="width:200px;margin:5px;" /></div>
                    <el-form>
                        <el-row>
                            <div style="margin: 30px 0;">
                                <span> <el-tag type="danger" style="font-size:24px;">{{
                                    userInfo.userName
                                }}</el-tag>，欢迎来到商品库存管理系统！</span>
                                <DateUtils />
                            </div>
                        </el-row>
                        <el-row>
                            <el-col :span="6">
                                <a href="/purchasing/purchasingBusiness/purchasingOrder" @click="setSelFirst('purchasing')">
                                    <div><img :src="input" alt="" style="width:80px;margin:5px;" /></div>
                                    <span style="margin-left:20px;">采购</span>
                                </a>
                            </el-col>
                            <el-col :span="6">
                                <a href="/marketing/marketingBusiness/retailOrder" @click="setSelFirst('marketing')">
                                    <div><img :src="output" alt="" style="width:80px;margin:5px;" /></div>
                                    <span style="margin-left:20px;">销售</span>
                                </a>
                            </el-col>
                            <el-col :span="6">
                                <a href="/store/shopkeepers/overview" @click="setSelFirst('store')">
                                    <div><img :src="inventory" alt="" style="width:80px;margin:5px;" /></div>
                                    <span style="margin-left:20px;">库存</span>
                                </a>
                            </el-col>
                            <el-col :span="6">
                                <a href="/data" @click="setSelFirst('data')">
                                    <div><img :src="data" alt="" style="width:80px;margin:5px;" /></div>
                                    <span style="margin-left:20px;">资料</span>
                                </a>
                            </el-col>
                        </el-row>
                    </el-form>
                </el-card>
            </div>
            <!-- 满仓预警 -->
            <div style="margin:10px;">
                <el-card shadow="always" body-style="padding:30px;display: inline-flex;height:300px;width:830px;">
                    <div>
                        <img :src="alertMsg1" alt="" style="width:200px;margin:5px;" />
                        <span> <el-tag type="success"
                                style="font-size:24px;text-align: center;display: block;">满仓预警</el-tag></span>
                        <el-button style="margin:20px 50px;" @click="goMore(1)">查看全部</el-button>
                    </div>
                    <el-table :data="tableData1" height="300" :cell-style="{ padding: '5px' }" border
                        style="width: 100%" tooltip-effect="dark">
                        <el-table-column label="预警对象" :show-overflow-tooltip="true">
                            <template slot-scope="scope">
                                <span>{{ scope.row.shopName }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="预警商品" :show-overflow-tooltip="true">
                            <template slot-scope="scope">
                                <span>{{ scope.row.goodsName }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="alertTime" label="预警时间" :show-overflow-tooltip="true">
                        </el-table-column>
                        <el-table-column prop="status" label="状态" :show-overflow-tooltip="true">
                            <template slot-scope="scope"><span style="color:#2d8bf0">{{
                                scope.row.status == 1 ? '已处理'
                                    : '未处理'
                            }}</span></template>
                        </el-table-column>
                    </el-table>
                </el-card>
            </div>
        </div>
        <div class="welmsg">
            <!-- 连续未动销预警 -->
            <div style="margin:10px;">
                <el-card shadow="always" body-style="padding:30px;display: inline-flex;height:300px;width:830px;">
                    <div>
                        <img :src="alertMsg2" alt="" style="width:200px;margin:5px;" />
                        <span> <el-tag type="success"
                                style="font-size:24px;text-align: center;display: block;">连续未动销预警</el-tag></span>
                        <el-button style="margin:20px 50px;" @click="goMore(0)">查看全部</el-button>
                    </div>
                    <el-table :data="tableData2" height="300" :cell-style="{ padding: '5px' }" border
                        style="width: 100%" tooltip-effect="dark">
                        <el-table-column label="预警对象" :show-overflow-tooltip="true">
                            <template slot-scope="scope">
                                <span>{{ scope.row.shopName }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="预警商品" :show-overflow-tooltip="true">
                            <template slot-scope="scope">
                                <span>{{ scope.row.goodsName }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="alertTime" label="预警时间" :show-overflow-tooltip="true">
                        </el-table-column>
                        <el-table-column prop="status" label="状态" :show-overflow-tooltip="true">
                            <template slot-scope="scope"><span style="color:#2d8bf0">{{
                                scope.row.status == 1 ? '已处理'
                                    : '未处理'
                            }}</span></template>
                        </el-table-column>
                    </el-table>
                </el-card>
            </div>
            <!-- 缺货预警 -->
            <div style="margin:10px;">
                <el-card shadow="always" body-style="padding:30px;display: inline-flex;height:300px;width:830px;">
                    <div>
                        <img :src="welcomemsg" alt="" style="width:200px;margin:5px;" />
                        <span> <el-tag type="success"
                                style="font-size:24px;text-align: center;display: block;">缺货预警</el-tag></span>
                        <el-button style="margin:20px 50px;" @click="goMore(2)">查看全部</el-button>
                    </div>
                    <el-table :data="tableData3" height="300" :cell-style="{ padding: '5px' }" border
                        style="width: 100%" tooltip-effect="dark">
                        <el-table-column label="预警对象" :show-overflow-tooltip="true">
                            <template slot-scope="scope">
                                <span>{{ scope.row.shopName }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="预警商品" :show-overflow-tooltip="true">
                            <template slot-scope="scope">
                                <span>{{ scope.row.goodsName }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="alertTime" label="预警时间" :show-overflow-tooltip="true">
                        </el-table-column>
                        <el-table-column prop="status" label="状态" :show-overflow-tooltip="true">
                            <template slot-scope="scope"><span style="color:#2d8bf0">{{
                                scope.row.status == 1 ? '已处理'
                                    : '未处理'
                            }}</span></template>
                        </el-table-column>
                    </el-table>
                </el-card>
            </div>
        </div>
    </div>
</template>

<script>
// import {getUserInfo} from "../../api/login";
import DateUtils from "./DateUtils.vue"
import welcomePic from '@/assets/svg/welcome-pic.svg'
import welcomemsg from '@/assets/svg/welcome-msg.svg'
import alertMsg1 from '@/assets/svg/alertMsg1.svg'
import alertMsg2 from '@/assets/svg/alertMsg2.svg'
import input from '@/assets/svg/input.svg'
import output from '@/assets/svg/output.svg'
import data from '@/assets/svg/data.svg'
import inventory from '@/assets/svg/inventory.svg'
import { alertInfolistPage } from "@/api/welcome";
import { METHODS } from "http"

export default {
    data() {
        return {
            userInfo: "",
            welcomePic: welcomePic,
            welcomemsg: welcomemsg,
            alertMsg1: alertMsg1,
            alertMsg2: alertMsg2,
            input: input,
            output: output,
            data: data,
            inventory: inventory,
            tableData1: [],//满仓预警
            tableData2: [],//连续未动销预警
            tableData3: [],//缺货预警
        }
    },
    components: {
        DateUtils
    },
    created() {
        this.userInfo = JSON.parse(localStorage.getItem("userInfo"))
        this.getalertInfolistPage()
    },
    methods: {
        setSelFirst(name){
            this.$store.dispatch("saveSelFirstMenu", name)
            console.log(name)
        },
        getalertInfolistPage() {
            let params = {
                type: "",//预警类型（0：连续未动销预警，1：满仓预警，2：缺货预警）
                status: "",//状态（0：未处理，1：已处理）
                shopSupplierCode: "",
                goodsCode: "",
                page: 1,
                size: 200,
            }
            alertInfolistPage(params).then(res => {
                if (res.data.code == 200) {
                    //预警类型（0：连续未动销预警，1：满仓预警，2：缺货预警）
                    res.data.data.records.forEach(item => {
                        if (item.type == 0) {
                            this.tableData2.push(item)
                        } else if (item.type == 1) {
                            this.tableData1.push(item)
                        } else if (item.type == 2) {
                            this.tableData3.push(item)
                        }
                    });
                } else {
                    this.$message.error("获取失败!");
                }
            });
        },
        goMore(val) {
            console.log("查看全部")
            this.$router.push({ name: 'alertInfo', params: { type: val } })
        }
    }
}
</script>
<style scoped>
.welmsg {
    position: relative;
    z-index: 999;
    font-size: 24px;
    float: left;
    display: inline-flex;
}
/* .card{
    background: url("../../assets/images/background.png");
    background-size: cover;
} */
</style>