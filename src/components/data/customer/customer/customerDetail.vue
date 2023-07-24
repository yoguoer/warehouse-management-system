<template>
  <el-drawer size="95%" title="客户详情" :direction="direction" :visible.sync="isShow" :close-on-press-escape="false"
    :show-close="true" :before-close="handleClose" :wrapperClosable="false">
    <div style="padding:0 20px;">
      <el-row style="margin-bottom: 20px;">
        <el-col :span="5">
          <span>客户编号: {{ row.customerCode }}</span>
        </el-col>
        <el-col :span="5">
          <span>客户名称: {{ row.customerName }}</span>
        </el-col>
        <el-col :span="5">
          <span>联系电话: {{ row.customerTel }}</span>
        </el-col>
        <el-col :span="5">
          <span>所属分类: {{ row.categoryName }}</span>
        </el-col>
      </el-row>
      <el-row>
        <!-- <router-view></router-view> -->
        <keep-alive>
          <router-view v-if="$route.meta.keepAlive"></router-view>
        </keep-alive>
        <router-view v-if="!$route.meta.keepAlive"></router-view>
      </el-row>
    </div>
  </el-drawer>
</template>


<script>
export default {
  name: 'customerDetail',
  data() {
    return {
      direction: 'btt',
      temp: "",
      row: {},
    }
  },
  props: {
    isShow: {
      type: Boolean,
      default: true,
    },
    rowData: {},
  },
  components: {
  },
  watch: {

  },
  created() {
    let rowData = localStorage.getItem('customerDetail')
    if (rowData) {
      this.row = JSON.parse(rowData)
    }
    // this.row = this.$route.params.rowData || this.rowData
    // this.$router.push({ name: 'customer-contact', params: { rowData: this.row, customerKey: this.row.customerKey } })
  },

  methods: {
    handleClose() {
      // this.$parent.isShow=false
      // this.$router.push({ name: "customerList" })
      // this.isShow = false
      localStorage.removeItem('customerDetail')
      this.$router.push({
        name: 'customerList'
      })
    },
  },
}
</script>
<style lang="scss">
.el-select {
  width: 100%;
}

.demo-ruleForm {
  padding: 0 80px 50px 80px;
}

.msg-title {
  font-weight: 500;
  margin-right: 20px;
  line-height: 60px;
}

.list-box-f .cont {
  width: 260px;
}

.el-select {
  width: 100%;
}

.demo-ruleForm {
  padding: 0 80px 50px 80px;
}

.toAddcontact {
  border: 1px solid #eee;
  padding: 20px;
  margin: 20px;
  width: 1300px;
  float: left;

  .add-ruleForm {
    padding: 50px 80px 10px 80px;
  }
}
</style>