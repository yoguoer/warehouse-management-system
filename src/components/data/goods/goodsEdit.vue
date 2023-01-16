<template>
  <div>
    <el-drawer size="95%" :title="ifCreate ? '添加商品' : '编辑商品'" :visible.sync="drawer" :direction="direction"
      :close-on-press-escape="false" :show-close="false" :wrapperClosable="false">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-row>
          <h4 class="msg-title">基础信息</h4>
          <el-row>
            <el-col :span="7">
              <el-form-item label="商品名" prop="goodsName">
                <el-input v-model="ruleForm.goodsName" clearable placeholder="商品名"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="商品货号" prop="goodsCode">
                <el-input v-model="ruleForm.goodsCode" clearable placeholder="商品货号"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="商品条码" prop="goodsBarcode">
                <el-input v-model="ruleForm.goodsBarcode" clearable placeholder="商品条码"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="7">
              <el-form-item label="所属供应商" prop="supplierKey">
                <el-select size="middle" v-model="ruleForm.supplierKey" placeholder="所属供应商" style="width:100%;"
                  clearable>
                  <el-option v-for="item in supplyOptions" :key="item.supplierKey" :label="item.supplierName"
                    :value="item.supplierKey" placeholder="所属供应商">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="所属分类" v-model="ruleForm.categoryKey" prop="categoryKey" size="middle">
                <listBoxF style="width:100%">
                  <template slot="content">
                    <treeselect class="treeSelect-option" v-model="value" :normalizer="normalizer" :options="list"
                      clearable placeholder="请选择" @select="selectNode" style="width:405px;" />
                  </template>
                </listBoxF>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="所属品牌" prop="brandCode">
                <el-select size="middle" v-model="ruleForm.brandCode" placeholder="品牌" style="width:100%">
                  <el-option v-for="item in options1" :key="item.brandKey" :label="item.brandName"
                    :value="item.brandCode">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="7">
              <el-form-item label="型号" prop="modelCode">
                <el-input v-model="ruleForm.modelCode" clearable placeholder="型号"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="状态" prop="state">
                <el-select size="middle" v-model="ruleForm.state" placeholder="请选择" style="width:100%">
                  <el-option label="上架" :value="1"></el-option>
                  <el-option label="下架" :value="0"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <span class="msg-title">商品单位信息</span>
            <el-row>
              <el-col :span="7">
                <el-form-item label="商品单位" prop="goodsUnit">
                  <el-input v-model="ruleForm.goodsUnit" clearable placeholder="商品单位"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="7">
                <el-form-item label="单位类型" prop="unitType">
                  <el-input v-model="ruleForm.unitType" clearable placeholder="单位类型"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="7">
                <el-form-item label="换算关系" prop="unitDescription">
                  <el-input v-model="ruleForm.unitDescription" clearable placeholder="换算关系"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="7">
                <el-form-item label="重量：克" prop="weight">
                  <el-input v-model="ruleForm.weight" clearable placeholder="重量：克" type="Number"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="7">
                <el-form-item label="体积" prop="volume">
                  <el-input v-model="ruleForm.volume" clearable placeholder="体积" type="Number"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="7">
                <el-form-item label="预设进价" prop="priceCostPreset">
                  <el-input v-model="ruleForm.priceCostPreset" clearable placeholder="预设进价" type="Number"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="7">
                <el-form-item label="批发价" prop="priceWholesaler">
                  <el-input v-model="ruleForm.priceWholesaler" clearable placeholder="批发价" type="Number"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="7">
                <el-form-item label="零售价" prop="priceRetail">
                  <el-input v-model="ruleForm.priceRetail" clearable placeholder="零售价" type="Number"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="7">
                <el-form-item label="参考成本" prop="priceCostReference">
                  <el-input v-model="ruleForm.priceCostReference" clearable placeholder="参考成本" type="Number"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="7">
                <el-form-item label="最近进价" prop="priceLatestPurchase">
                  <el-input v-model="ruleForm.priceLatestPurchase" clearable placeholder="最近进价"
                    type="Number"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="7">
                <el-form-item label="成本均价" prop="priceCostAverage">
                  <el-input v-model="ruleForm.priceCostAverage" clearable placeholder="成本均价" type="Number"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-row>
        </el-row>
        <el-row>
          <el-form-item style="display:inline-block;float:right;margin:10px 30%">
            <el-button type="primary" @click="update('ruleForm')" v-if="ifCreate == false">保存</el-button>
            <el-button type="primary" @click="save('ruleForm')" v-else>立即创建</el-button>
            <!-- <el-button @click="resetForm('ruleForm')">重置</el-button> -->
            <el-button @click="close()">关闭</el-button>
          </el-form-item>
        </el-row>
      </el-form>
    </el-drawer>
  </div>
</template>

<script>
import listBoxF from "@/components/public/listBoxF/listBoxF.vue";
import moment from 'moment'
import { goodsAdd, getCategoryTree, inventorylist, goodsUpdate, brandlist, Supplierlist } from "@/api/data";
/**
 * 树形组件 用于选择框
 */
// import the component
import Treeselect from "@riophae/vue-treeselect";
// import the styles
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "supplyEdit",
  data() {
    return {
      ifCreate: true,
      direction: "btt",

      ruleForm: {
        categoryKey: "",
        createTime: "", //添加时间
        goodsBarcode: "", //商品条码
        goodsCode: "", //商品货号
        goodsHeadPic: "", //商品头图
        goodsKey: "", //商品ID
        goodsName: "", //商品名
        modelCode: "", //型号
        state: "", //0下架 1上架
        supplierKey: "", //供应商
        goodsUnit: "", //商品单位
        unitType: "", //单位类型：BASIC：基本单位， SUPPORT：辅助单位
        unitDescription: "", //换算关系
        weight: "", //重量：克
        volume: "", //体积
        priceWholesaler: "", //批发价
        priceRetail: "", //零售价
        priceLatestPurchase: "", //最近进价
        priceCostAverage: "", //成本均价
        priceCostPreset: "", //预设进价
        priceCostReference: "", //参考成本
        brandCode: ""//品牌
      },
      list: [],
      options1: [],
      supplyOptions: [],
      rules: {
        goodsBarcode: [
          { required: true, message: '请输入商品条码', trigger: 'blur' },
        ],
        goodsCode: [
          { required: true, message: '请输入商品货号', trigger: 'blur' },
        ],
        goodsName: [
          { required: true, message: '请输入商品名', trigger: 'blur' },
        ],
        supplierKey: [
          { required: true, message: '请选择供应商', trigger: 'blur' },
        ],
        goodsUnit: [
          { required: true, message: '请输入商品单位', trigger: 'blur' },
        ],
        unitType: [
          { required: true, message: '请输入单位类型', trigger: 'blur' },
        ],
        unitDescription: [
          { required: true, message: '请输入换算关系', trigger: 'blur' },
        ],
        state: [
          { required: true, message: '请选择状态', trigger: 'blur' },
        ],
        modelCode: [
          { required: true, message: '请输入型号', trigger: 'blur' },
        ],
        categoryKey: [
          { required: true, message: '请选择所属分类', trigger: 'blur' },
        ],
        weight: [
          { required: true, message: '请输入重量', trigger: 'blur' },
        ],
        volume: [
          { required: true, message: '请输入体积', trigger: 'blur' },
        ],
        priceWholesaler: [
          { required: true, message: '请输入批发价', trigger: 'blur' },
        ],
        priceRetail: [
          { required: true, message: '请输入零售价', trigger: 'blur' },
        ],
        priceLatestPurchase: [
          { required: true, message: '请输入最近进价', trigger: 'blur' },
        ],
        priceCostAverage: [
          { required: true, message: '请输入成本均价', trigger: 'blur' },
        ],
        priceCostPreset: [
          { required: true, message: '请输入预设进价', trigger: 'blur' },
        ],
        priceCostReference: [
          { required: true, message: '请输入参考成本', trigger: 'blur' },
        ],
        brandCode: [
          { required: true, message: '选择品牌', trigger: 'blur' },
        ]
      },
    };
  },
  props: {
    drawer: {
      default: true,
    },
    rowData: {},
    userList: {},
  },
  components: {
    listBoxF,
    Treeselect,
  },
  watch: {
    // 监听选中值的变化
    value: {
      deep: true,
      handler(val) {
        this.$emit("getSelectVal", val);
      },
    },
  },
  created() {
    this.getTree();
    this.getbrandlist();
    this.getSupplierlist()
    if (this.rowData.goodsKey) {
      this.ifCreate = false;
      this.ruleForm.categoryKey = this.rowData.categoryKey;
      this.ruleForm.createTime = this.rowData.createTime;
      this.ruleForm.goodsBarcode = this.rowData.goodsBarcode;
      this.ruleForm.goodsCode = this.rowData.goodsCode;
      this.ruleForm.goodsHeadPic = this.rowData.goodsHeadPic;
      this.ruleForm.goodsKey = this.rowData.goodsKey;
      this.ruleForm.goodsName = this.rowData.goodsName;
      this.ruleForm.brandCode = this.rowData.brandCode;
      this.ruleForm.modelCode = this.rowData.modelCode;
      this.ruleForm.state = this.rowData.state;
      this.ruleForm.supplierKey = this.rowData.supplierKey;
      this.ruleForm.goodsUnit = this.rowData.goodsUnit
      this.ruleForm.unitType = this.rowData.unitType
      this.ruleForm.unitDescription = this.rowData.unitDescription
      this.ruleForm.weight = this.rowData.weight
      this.ruleForm.volume = this.rowData.volume
      this.ruleForm.priceWholesaler = this.rowData.priceWholesaler
      this.ruleForm.priceRetail = this.rowData.priceRetail
      this.ruleForm.priceLatestPurchase = this.rowData.priceLatestPurchase
      this.ruleForm.priceCostAverage = this.rowData.priceCostAverage
      this.ruleForm.priceCostPreset = this.rowData.priceCostPreset
      this.ruleForm.priceCostReference = this.rowData.priceCostReference
    } else {
      this.ifCreate = true;
    }
    if (this.ruleForm.categoryKey) {
      this.value = this.ruleForm.categoryKey;
    } else {
      this.value = null;
    }
  },
  methods: {
    // 自定义参数键值名称
    normalizer(node) {
      //去掉children=[]的children属性
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.categoryKey,
        label: node.categoryName,
        children: node.children,
        level: node.level,
      };
    },
    selectNode(node) {
      this.ruleForm.categoryKey = node.categoryKey || "";
      this.value = node.categoryKey;
      // console.log("selectNode(node)：",this.ruleForm)
    },
    getbrandlist() {
      brandlist()
        .then((res) => {
          if (res.data.code === 200) {
            this.options1 = res.data.data
          } else {
            this.$message.error(res.msg);
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },
    getSupplierlist() {
      Supplierlist().then(res => {
        if (res.data.code == 200) {
          this.supplyOptions = res.data.data
        } else {
          this.$message.error("获取失败!");
        }
      });
    },
    close() {
      this.$emit("close");
      this.reset();
      this.value = null;
    },
    getTree() {
      let params = {
        categoryType: "GOODS",
      };
      getCategoryTree(params).then((res) => {
        if (res.data.code === 200) {
          res.data.data.forEach(item => {
            if (item.categoryName == '全部') {
              let index = res.data.data.indexOf(item)
              res.data.data.splice(index, 1)
            }
          });
          this.list = res.data.data;
        } else {
          console.log("error");
        }
      });
    },
    //更新
    update(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // console.log("this.ruleForm", this.ruleForm);
          goodsUpdate(this.ruleForm)
            .then((res) => {
              if (res.data.code === 200) {
                this.$message.success("修改成功!");
                this.$parent.success();
              } else {
                this.$message.error(res.msg);
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
    //创建
    save(formName) {
      this.ruleForm.createTime = moment().format("YYYY-MM-DD HH:mm:ss");
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // console.log(moment().format("YYYY-MM-DD HH:mm:ss")); //当前时间 （24小时制）
          // console.log("this.ruleForm", this.ruleForm);
          goodsAdd(this.ruleForm)
            .then((res) => {
              if (res.data.code == 200) {
                this.$message.success("创建成功!");
                this.$parent.success();
              } else {
                this.$message.error(res.msg);
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
    reset() {
      this.ruleForm = {
        categoryKey: "",
        createTime: "", //添加时间
        goodsBarcode: "", //商品条码
        goodsCode: "", //商品货号
        goodsHeadPic: "", //商品头图
        goodsKey: "", //商品ID
        goodsName: "", //商品名
        modelCode: "", //型号
        state: "", //0下架 1上架
        supplierKey: "", //供应商
        brandCode: "",
        goodsUnit: "", //商品单位
        unitType: "", //单位类型：BASIC：基本单位， SUPPORT：辅助单位
        unitDescription: "", //换算关系
        weight: "", //重量：克
        volume: "", //体积
        priceWholesaler: "", //批发价
        priceRetail: "", //零售价
        priceLatestPurchase: "", //最近进价
        priceCostAverage: "", //成本均价
        priceCostPreset: "", //预设进价
        priceCostReference: "", //参考成本
      };
    },
  },
};
</script>
<style lang="scss">
.demo-ruleForm {
  padding: 0 80px 50px 80px;
}

.msg-title {
  font-weight: 500;
  margin-right: 20px;
  line-height: 60px;
}

.toAddcontact {
  border: 1px solid #eee;
  padding: 20px;
  margin: 20px;
  width: 1300px;
  float: left;

  .add-ruleForm {
    padding: 50px 80px 10px 80px;
    width: 100%;
  }
}
</style>
