<template>
  <el-drawer size="95%" title="供应商详情" :direction="direction" :visible.sync="isShow"
    :close-on-press-escape="false" :show-close="true" :before-close="handleClose" :wrapperClosable="false">
    <div style="padding:0 20px;">
      <el-row style="margin-bottom: 20px;">
        <el-col :span="5">
          <span>供应商编号:  {{row.supplierCode}}</span>
        </el-col>
        <el-col :span="5">
          <span>供应商名称:  {{row.supplierName }}</span>
        </el-col>
        <el-col :span="5">
          <span>所属分类:  {{row.categoryName}}</span>
        </el-col>
        <el-col :span="5">
          <span>备注:  {{row.superlierDescription}}</span>
        </el-col>
      </el-row>
      <el-row>
        <el-tabs type="border-card" v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="地址" name="supply-address">
          </el-tab-pane>
          <el-tab-pane label="联系人" name="supply-contact">
          </el-tab-pane>
          <el-tab-pane label="开票信息" name="supply-bank">
          </el-tab-pane>
          <keep-alive>
            <router-view v-if="$route.meta.keepAlive"></router-view>
          </keep-alive>
          <router-view v-if="!$route.meta.keepAlive"></router-view>
        </el-tabs>
      </el-row>
    </div>
  </el-drawer>
</template>


<script>
export default {
  name: 'supplyDetail',
  data() {
    return {
      activeName: 'supply-address',
      direction: 'btt',
      row:{},
      isShow: true
    }
  },
  watch: {

  },
  created() {
    let rowData = localStorage.getItem('supplyDetail')
    if (rowData) {
      this.row = JSON.parse(rowData)
    }
  },

  methods: {
    //换tabs
    handleClick (v) {
      this.activeName = v.name
      this.$router.push({ name: this.activeName })
    },
    handleClose() {
       this.isShow=false
      localStorage.removeItem('supplyDetail')
      this.$router.push({
        name: 'supplyList'
      })
    }
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