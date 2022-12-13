<template>
  <el-drawer size="95%" title="供应商详情" :direction="direction" :visible.sync="isShow" :close-on-press-escape="false"
    :show-close="true" :before-close="handleClose" :wrapperClosable="false">
    <div style="padding:0 20px;">
      <el-row style="margin-bottom: 20px;">
        <el-col :span="5">
          <span>供应商编号: {{ row.supplierCode }}</span>
        </el-col>
        <el-col :span="5">
          <span>供应商名称: {{ row.supplierName }}</span>
        </el-col>
        <el-col :span="5">
          <span>所属分类: {{ row.categoryKey }}</span>
        </el-col>
        <el-col :span="5">
          <span>备注: {{ row.superlierDescription }}</span>
        </el-col>
      </el-row>
      <el-row>
        <el-tabs type="card" v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="地址" name="supply-address">
            <addressTable :addressList="addressList" ref="addressTable" v-if="activeName == 'supply-address'" />
          </el-tab-pane>
          <el-tab-pane label="联系人" name="supply-contact">
            <contactTable :contactList="contactList" ref="contactTable" v-if="activeName == 'supply-contact'" />
          </el-tab-pane>
          <el-tab-pane label="开票信息" name="supply-bank">
            <bankTable :bankList="bankList" ref="bankTable" v-if="activeName == 'supply-bank'" />
          </el-tab-pane>
        </el-tabs>
      </el-row>
    </div>
  </el-drawer>
</template>


<script>
import bankTable from "./bankTable"
import addressTable from "./addressTable"
import contactTable from "./contactTable"

export default {
  name: 'supplyDetail',
  data() {
    return {
      activeName: 'supply-address',
      direction: 'btt',
      temp: "",
      addressList: [],
      bankList: [],
      contactList: [],
      row: {},
    }
  },
  props: {
    isShow: {
      default: true,
    },
    rowData: {},
  },
  components: {
    contactTable,
    addressTable,
    bankTable
  },
  watch: {
    // 监听选中值的变化
    rowData: {
      deep: true,
      handler(val) {
        this.row = this.rowData
        this.contactList = this.rowData.supplierContactList
        this.addressList = this.rowData.supplierAddressList
        this.bankList = this.rowData.supplierBillingList
        this.$forceUpdate();
      }
    },
  },
  created() {
    console.log("详情rowData", this.rowData)
    this.row = this.rowData
    this.contactList = this.rowData.supplierContactList
    this.addressList = this.rowData.supplierAddressList
    this.bankList = this.rowData.supplierBillingList
  },

  methods: {
    //换tabs
    handleClick(v) {
      this.activeName = v.name
      this.temp = this.activeName
      this.$forceUpdate();
    },
    handleClose() {
      this.$parent.isShow = false
    },
    getData() {
      console.log("更新删除后重新获取新数据")
      this.activeName = ""
      this.$parent.getData()
    },
    reload() {
      // this.handleClick({name:this.activeName})
      this.row = this.$parent.rowData
      this.contactList = this.$parent.rowData.supplierContactList
      this.addressList = this.$parent.rowData.supplierAddressList
      this.bankList = this.$parent.rowData.supplierBillingList
      // console.log("详情rowData", this.$parent.rowData)
      // console.log("contactList", this.contactList)
      // console.log("addressList", this.addressList)
      // console.log("bankList", this.bankList)
      this.activeName = this.temp
      this.$forceUpdate();
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