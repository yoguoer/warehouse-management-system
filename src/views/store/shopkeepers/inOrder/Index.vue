<template>
    <div class="wrap-definition">
        <el-tabs type="border-card" v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="销售" name="sales">
                <!-- <salesList ref="sales" /> -->
            </el-tab-pane>
            <el-tab-pane label="采购" name="purchase">
                <!-- <purchaseList ref="purchase" /> -->
            </el-tab-pane>
            <router-view></router-view>
        </el-tabs>
    </div>
</template>

<script>

export default {
    name: 'sales',
    data() {
        return {
            activeName: 'sales',
        }
    },
    props: {},
    methods: {
    //换tabs
    handleClick(v) {
      this.activeName = v.name
      this.$router.push({ name: this.activeName })
      this.$forceUpdate();
    },
    },
    watch: {},
    created() {
    },
    mounted() {
    },
    computed: {
    },
    mounted() {
    },
    beforeDestroy() {
    },
    components: {
    }
}
</script>

<style lang="scss" rel="stylesheet/scss" scoped>
.wrap-definition {
    background: #fff;

    .top-title {
        color: #0066ff;
        font-size: 18px;
        line-height: 50px;
    }

    //  .el-divider--horizontal{
    //      margin: 20px 0;
    //    }
    .el-input-group {
        width: 300px;
        margin-right: 20px;
    }

    .text {
        font-size: 14px;
    }

    .item {
        margin-bottom: 18px;
    }

    .clearfix:before,
    .clearfix:after {
        display: table;
        content: "";
    }

    .clearfix:after {
        clear: both
    }

    .el-drawer__open .el-drawer.btt {
        height: 60% !important;
    }

    .el-dropdown-link {
        cursor: pointer;
        color: #409EFF;
    }

    .el-icon-arrow-down {
        font-size: 12px;
    }
}
</style>