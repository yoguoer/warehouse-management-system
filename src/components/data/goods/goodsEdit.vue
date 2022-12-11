<template>
  <div>
    <el-drawer size="95%" :title="ifCreate ? '添加商品' : '商品编辑'" :visible.sync="drawer" :direction="direction"
      :close-on-press-escape="false" :show-close="false" :wrapperClosable="false">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <h4 class="msg-title">基础信息</h4>
        <el-row>
          <el-col :span="7">
            <el-form-item label="商品名" prop="goodsName">
              <el-input v-model="ruleForm.goodsName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="7">
            <el-form-item label="商品货号" prop="goodsCode">
              <el-input v-model="ruleForm.goodsCode"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="7">
            <el-form-item label="商品条码" prop="goodsBarcode">
              <el-input v-model="ruleForm.goodsBarcode"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="7">
            <el-form-item label="供应商" prop="supplierKey">
              <el-input v-model="ruleForm.supplierKey"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="7">
            <el-form-item label="默认仓库" prop="inventoryKey">
              <el-select size="middle" v-model="ruleForm.inventoryKey" placeholder="默认仓库" style="width:405px;" >
                <el-option v-for="item in options" :key="item.inventoryKey" :label="item.inventoryName"
                  :value="item.inventoryKey">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="7">
            <el-form-item label="所属分类" v-model="ruleForm.categoryKey" prop="categoryKey" size="middle">
              <listBoxF>
                <template slot="content">
                  <treeselect class="treeSelect-option" v-model="value" :normalizer="normalizer" :options="list" 
                    placeholder="请选择" @select="selectNode" style="width:405px;" />
                </template>
              </listBoxF>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="7">
            <el-form-item label="型号" prop="modelCode">
              <el-input v-model="ruleForm.modelCode"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="7">
            <el-form-item label="所属品牌" prop="brandCode">
              <el-select size="middle" v-model="ruleForm.brandCode" placeholder="品牌" style="width:405px;" >
                <el-option v-for="item in options1" :key="item.brandKey" :label="item.brandName" :value="item.brandCode">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="7">
            <el-form-item label="状态" prop="state">
              <el-select size="middle" v-model="ruleForm.state" placeholder="请选择" style="width:405px;" >
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
                <el-input v-model="ruleForm.goodsUnit"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="单位类型" prop="unitType">
                <el-input v-model="ruleForm.unitType"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="换算关系" prop="unitDescription">
                <el-input v-model="ruleForm.unitDescription"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="7">
              <el-form-item label="重量：克" prop="weight">
                <el-input v-model="ruleForm.weight"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="体积" prop="volume">
                <el-input v-model="ruleForm.volume"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="账面库存" prop="inventoryBook">
                <el-input v-model="ruleForm.inventoryBook"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="7">
              <el-form-item label="批发价" prop="priceWholesaler">
                <el-input v-model="ruleForm.priceWholesaler"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="零售价" prop="priceRetail">
                <el-input v-model="ruleForm.priceRetail"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="参考成本" prop="priceCostReference">
                <el-input v-model="ruleForm.priceCostReference"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="7">
              <el-form-item label="最近进价" prop="priceLatestPurchase">
                <el-input v-model="ruleForm.priceLatestPurchase"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="成本均价" prop="priceCostAverage">
                <el-input v-model="ruleForm.priceCostAverage"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="预设进价" prop="priceCostPreset">
                <el-input v-model="ruleForm.priceCostPreset"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-row>

        <el-row style="width: 1200px">
          <el-form-item style="float: right; margin-top: 5%">
            <el-button type="primary" @click="update()" v-if="ifCreate == false">保存</el-button>
            <el-button type="primary" @click="save()" v-else>立即创建</el-button>
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
import {goodsAdd,getCategoryTree, inventorylist,  goodsUpdate, brandlist} from "@/api/data";
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
        inventoryKey: "", //默认仓库
        modelCode: "", //型号
        state: "", //0下架 1上架
        supplierKey: "", //供应商
        goodsUnit: "", //商品单位
        unitType: "", //单位类型：BASIC：基本单位， SUPPORT：辅助单位
        unitDescription: "", //换算关系
        weight: "", //重量：克
        volume: "", //体积
        inventoryBook: "", //账面库存
        priceWholesaler: "", //批发价
        priceRetail: "", //零售价
        priceLatestPurchase: "", //最近进价
        priceCostAverage: "", //成本均价
        priceCostPreset: "", //预设进价
        priceCostReference: "", //参考成本
        brandCode:""
      },
      list: [],
      options: [],
      options1: [],
      rules: {},
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
    this.getinventorylist();
    this.getbrandlist();
    if (this.rowData.goodsKey) {
      this.ifCreate = false;
      this.ruleForm.categoryKey = this.rowData.categoryKey;
      this.ruleForm.createTime = this.rowData.createTime;
      this.ruleForm.goodsBarcode = this.rowData.goodsBarcode;
      this.ruleForm.goodsCode = this.rowData.goodsCode;
      this.ruleForm.goodsHeadPic = this.rowData.goodsHeadPic;
      this.ruleForm.goodsKey = this.rowData.goodsKey;
      this.ruleForm.inventoryKey = this.rowData.inventoryKey;
      this.ruleForm.goodsName = this.rowData.goodsName;
      this.ruleForm.brandCode = this.rowData.brandCode;
      this.ruleForm.modelCode = this.rowData.modelCode;
      this.ruleForm.state = this.rowData.state;
      this.ruleForm.supplierKey = this.rowData.supplierKey;
      this.ruleForm.goodsUnit= this.rowData.goodsUnit
      this.ruleForm.unitType= this.rowData.unitType
      this.ruleForm.unitDescription= this.rowData.unitDescription
      this.ruleForm.weight= this.rowData.weight
      this.ruleForm.volume= this.rowData.volume
      this.ruleForm.inventoryBook= this.rowData.inventoryBook
      this.ruleForm.priceWholesaler= this.rowData.priceWholesaler
      this.ruleForm.priceRetail= this.rowData.priceRetail
      this.ruleForm.priceLatestPurchase= this.rowData.priceLatestPurchase
      this.ruleForm.priceCostAverage= this.rowData.priceCostAverage
      this.ruleForm.priceCostPreset= this.rowData.priceCostPreset
      this.ruleForm.priceCostReference= this.rowData.priceCostReference
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
    getinventorylist() {
      inventorylist()
        .then((res) => {
          if (res.data.code === 200) {
            this.options = res.data.data
          } else {
            this.$message.error(res.msg);
          }
        })
        .catch((e) => {
          console.log(e);
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
              if(item.categoryName=='全部'){
                let index=res.data.data.indexOf(item)
                res.data.data.splice(index,1)
              }
            });
          this.list = res.data.data;
        } else {
          console.log("error");
        }
      });
    },
    //更新
    update() {
      console.log("this.ruleForm", this.ruleForm);
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
    },
    //创建
    save() {
      this.ruleForm.createTime = moment().format("YYYY-MM-DD HH:mm:ss");
      // console.log(moment().format("YYYY-MM-DD HH:mm:ss")); //当前时间 （24小时制）
      console.log("this.ruleForm", this.ruleForm);
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
        inventoryKey: "", //默认仓库
        modelCode: "", //型号
        state: "", //0下架 1上架
        supplierKey: "", //供应商
        brandCode:"",
        goodsUnit: "", //商品单位
        unitType: "", //单位类型：BASIC：基本单位， SUPPORT：辅助单位
        unitDescription: "", //换算关系
        weight: "", //重量：克
        volume: "", //体积
        inventoryBook: "", //账面库存
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
  }
}
</style>
