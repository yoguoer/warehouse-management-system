<template>
<div>
    <div class="welmsg">
        <!-- 欢迎区域 -->
        <div style="margin:10px;">
            <el-card shadow="always" body-style="padding:30px;display: inline-flex;height:300px;width:830px;">
                <div><img :src="welcomePic" alt="" style="width:200px;margin:5px;"/></div>
                <div style="margin: 30px 0;">
                    <span> <el-tag type="danger" style="font-size:24px;">{{ userInfo.userName }}</el-tag>，欢迎来到商品库存管理系统！</span>
                    <DateUtils/>
                </div>
            </el-card>
        </div>
        <!-- 库存预警 -->
        <div style="margin:10px;">
            <el-card shadow="always" body-style="padding:30px;display: inline-flex;height:300px;width:830px;">
                <div><img :src="alertMsg1" alt="" style="width:200px;margin:5px;"/></div>
                <div style="">
                    <span> <el-tag type="success" style="font-size:24px;">库存预警</el-tag></span>
                </div>
            </el-card>
        </div>
    </div>
    <div class="welmsg">
        <!-- 连续未动销预警 -->
        <div style="margin:10px;">
            <el-card shadow="always" body-style="padding:30px;display: inline-flex;height:300px;width:830px;">
                <div><img :src="alertMsg2" alt="" style="width:200px;margin:5px;"/></div>
                <div style="">
                    <span> <el-tag type="success" style="font-size:24px;">连续未动销预警</el-tag></span>
                </div>
            </el-card>
        </div>
        <!-- 统计图表 -->
        <div style="margin:10px;">
            <el-card shadow="always" body-style="padding:30px;display: inline-flex;height:300px;width:830px;">
                <div><img :src="welcomemsg" alt="" style="width:200px;margin:5px;"/></div>
                <div style="">
                    <span> <el-tag type="success" style="font-size:24px;">预警信息</el-tag></span>
                </div>
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

export default {
    data() {
        return {
            userInfo: "",
            welcomePic:welcomePic,
            welcomemsg:welcomemsg,
            alertMsg1:alertMsg1,
            alertMsg2:alertMsg2
        }
    },
    components:{
        DateUtils
    },
    created() {
        this.userInfo = JSON.parse(localStorage.getItem("userInfo"))
        // getUserInfo(user).then(res => {
        //     this.userInfo=res.data.data
        //     console.log(this.userInfo)
        // }).catch(err => {
        //         console.log(err);
        // });
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
.el-card__body{
    
}
</style>>