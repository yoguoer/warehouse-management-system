<template>
    <div>
      <el-dialog title="新建" :visible.sync="visible" :before-close="close"
        top="25vh" :modal-append-to-body="false" :closeOnClickModal="false" width="450px">
        <div class="dialog_body">
          <el-form size="small" :model="form" :rules="rules" :inline="true" label-width="70px" ref="supplierForm">
            <el-form-item prop="supplier" label="供应商">
                <el-select v-model="form.supplier" clearable>
                    <el-option v-for="supplier in supplierList" :key="supplier.supplierCode" :value="supplier.supplierCode" :label="supplier.name">
                        <span>[{{supplier.supplierCode}}] </span>
                        <span>{{supplier.name}}</span>
                    </el-option>
                </el-select>
            </el-form-item>
          </el-form>
          <div class="dialog_footer">
            <el-button type="" size="small" @click="close()">取消</el-button>
            <el-button type="primary" size="small" @click="submit()">创建</el-button>
          </div>
        </div>
      </el-dialog>
    </div>
  </template>
  
<script>

export default {
  name: "addSupplierWarehouse",
  components: {},
  props: {
    visible: {
      default: false,
    },
  },
  data() {
    return {
      form: {
          supplier: ""
      },
      rules: {
        supplier: [
            { required: true, message: '请选择供应商', trigger: 'blur' }
          ],
      },
      supplierList: []
    };
  },
  created() {
    this.getSupplierList()
  },
  methods: {
    close() {
      this.$emit("close");
    },
    getSupplierList() {
        const res = {
            "records": [
                {
                    "supplierCode": "10100",
                    "name": "温州智博印业有限公司",
                    "departmentGroupName": null,
                    "departmentGroupCode": null,
                    "outsideCode": "10100",
                    "type": 10
                },
                {
                    "supplierCode": "10000",
                    "name": "广州乐言文创实业有限公司",
                    "departmentGroupName": null,
                    "departmentGroupCode": null,
                    "outsideCode": "10000",
                    "type": 10
                },
                {
                    "supplierCode": "10101",
                    "name": "福州旭和电子有限公司",
                    "departmentGroupName": null,
                    "departmentGroupCode": null,
                    "outsideCode": "10101",
                    "type": 10
                },
                {
                    "supplierCode": "10001",
                    "name": "福建集成伞业有限公司",
                    "departmentGroupName": null,
                    "departmentGroupCode": null,
                    "outsideCode": "10001",
                    "type": 10
                },
                {
                    "supplierCode": "10102",
                    "name": "宁波开利控股集团股份有限公司",
                    "departmentGroupName": null,
                    "departmentGroupCode": null,
                    "outsideCode": "10102",
                    "type": 10
                },
                {
                    "supplierCode": "10002",
                    "name": "浙江新光饰品股份有限公司",
                    "departmentGroupName": null,
                    "departmentGroupCode": null,
                    "outsideCode": "10002",
                    "type": 10
                },
                {
                    "supplierCode": "10103",
                    "name": "福建时嘉贸易有限公司",
                    "departmentGroupName": null,
                    "departmentGroupCode": null,
                    "outsideCode": "10103",
                    "type": 10
                },
                {
                    "supplierCode": "10104",
                    "name": "汕头市怡佳塑胶制品有限公司",
                    "departmentGroupName": null,
                    "departmentGroupCode": null,
                    "outsideCode": "10104",
                    "type": 10
                },
                {
                    "supplierCode": "10003",
                    "name": "广州市盛兆日用品实业有限公司",
                    "departmentGroupName": null,
                    "departmentGroupCode": null,
                    "outsideCode": "10003",
                    "type": 10
                },
                {
                    "supplierCode": "10105",
                    "name": "昆山天源精工工业有限公司",
                    "departmentGroupName": null,
                    "departmentGroupCode": null,
                    "outsideCode": "10105",
                    "type": 10
                },
                {
                    "supplierCode": "10004",
                    "name": "浙江艳庄化妆品有限公司",
                    "departmentGroupName": null,
                    "departmentGroupCode": null,
                    "outsideCode": "10004",
                    "type": 10
                },
                {
                    "supplierCode": "10106",
                    "name": "义乌市亿利文体用品有限公司",
                    "departmentGroupName": null,
                    "departmentGroupCode": null,
                    "outsideCode": "10106",
                    "type": 10
                },
                {
                    "supplierCode": "10005",
                    "name": "广州莉来雅化妆品有限公司",
                    "departmentGroupName": null,
                    "departmentGroupCode": null,
                    "outsideCode": "10005",
                    "type": 10
                },
                {
                    "supplierCode": "10006",
                    "name": "广州市玛莉安化妆用品有限公司",
                    "departmentGroupName": null,
                    "departmentGroupCode": null,
                    "outsideCode": "10006",
                    "type": 10
                },
                {
                    "supplierCode": "10107",
                    "name": "深圳市华增科技有限公司",
                    "departmentGroupName": null,
                    "departmentGroupCode": null,
                    "outsideCode": "10107",
                    "type": 10
                },
                {
                    "supplierCode": "10202",
                    "name": "恩平市卓尔礼品实业有限公司",
                    "departmentGroupName": null,
                    "departmentGroupCode": null,
                    "outsideCode": "10202",
                    "type": 10
                },
                {
                    "supplierCode": "10007",
                    "name": "惠州市惠城区三千梳刷厂",
                    "departmentGroupName": null,
                    "departmentGroupCode": null,
                    "outsideCode": "10007",
                    "type": 10
                },
                {
                    "supplierCode": "10203",
                    "name": "佛山卡蛙科技股份有限公司",
                    "departmentGroupName": null,
                    "departmentGroupCode": null,
                    "outsideCode": "10203",
                    "type": 10
                },
                {
                    "supplierCode": "10008",
                    "name": "广州市贝奇皮具有限公司",
                    "departmentGroupName": null,
                    "departmentGroupCode": null,
                    "outsideCode": "10008",
                    "type": 10
                },
                {
                    "supplierCode": "10108",
                    "name": "广州市柏姿生物科技有限公司",
                    "departmentGroupName": null,
                    "departmentGroupCode": null,
                    "outsideCode": "10108",
                    "type": 10
                }
            ],
            "total": 3549,
            "size": 20,
            "current": 1,
            "pages": 178
        }
        this.supplierList = res.records
    },
    submit() {
        this.$refs['supplierForm'].validate((valid) => {
          if (valid) {
            this.$message.success('新建成功')
            this.close()
          } else {
            console.log('error submit!!');
            return false;
          }
        });
    }
  },
};
</script>
<style lang="scss" src="@/assets/styles/element/input.scss"  scoped>
  
  </style>
  