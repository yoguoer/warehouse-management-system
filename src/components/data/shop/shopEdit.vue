<template>
  <div>
    <el-dialog :title="ifCreate ? '新增门店信息' : '编辑门店信息'" :visible.sync="dialogVisible" :before-close="close" top="25vh"
      :modal-append-to-body="false" :closeOnClickModal="false" width="1200px">
      <div class="dialog_body">
        <el-form size="small" :model="form" :rules="rules" ref="form" :inline="true" label-width="120px">
          <el-row>
            <el-col :span="10">
              <el-form-item prop="shopCode" label="门店编码">
                <el-input v-model="form.shopCode" class="readonly-copy" placeholder="门店编码" readonly
                  v-if="ifCreate == false"></el-input>
                <el-input v-model="form.shopCode" placeholder="门店编码" class="form_text" v-else clearable></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item prop="shopName" label="门店名称">
                <el-input v-model="form.shopName" placeholder="门店编码" class="form_text" clearable></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <!-- 1A-直营店，1B-加盟店，1C-合作店，1D-代理店，1E-代理商', -->
              <el-form-item prop="cooperationType" label="经营类型">
                <el-select size="mini" v-model="form.cooperationType" placeholder="请选择" style="width: 350px" clearable>
                  <el-option label="直营店" :value="1"></el-option>
                  <el-option label="加盟店" :value="2"></el-option>
                  <el-option label="合作店" :value="3"></el-option>
                  <el-option label="代理店" :value="4"></el-option>
                  <el-option label="代理商" :value="5"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="所属分类" v-model="form.categoryKey" prop="categoryKey">
                <listBoxF style="width:350px">
                  <template slot="content">
                    <treeselect class="treeSelect-option" v-model="valueC" :multiple="multiple" clearable style="width: 350px"
                      :normalizer="normalizerC" :options="list" placeholder="请选择" @select="selectNodeC" />
                  </template>
                </listBoxF>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="业务人员" prop="businessPersonCode">
                <el-input v-model="form.businessPersonCode" class="form_text" placeholder="业务人员" clearable></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="责任人员" prop="liablePersonCode">
                <el-input v-model="form.liablePersonCode" class="form_text" placeholder="责任人员" clearable></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="店铺状态" prop="shopStatus">
                <!-- //3A-开业，3B-关店，3C-搬迁，3D-筹备，3E-暂停，3F-预关店',    -->
                <el-select size="mini" v-model="form.shopStatus" placeholder="请选择" style="width: 350px" clearable>
                  <el-option label="开业" :value="1"></el-option>
                  <el-option label="关店" :value="2"></el-option>
                  <el-option label="搬迁" :value="3"></el-option>
                  <el-option label="筹备" :value="4"></el-option>
                  <el-option label="暂停" :value="5"></el-option>
                  <el-option label="预关店" :value="6"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="备注" prop="description">
                <el-input v-model="form.description" clearable placeholder="备注" style="width: 350px"></el-input>
              </el-form-item>
          </el-col>
          </el-row>
          <el-row style="white-space: nowrap;">
            <el-form-item label="联系地址" prop="address" v-model="form.address">
              <checkAddress ref="address" v-model="form.address" :form="form.address" style="width: 80%" />
            </el-form-item>
          </el-row>
        </el-form>
        <div class="dialog_footer">
          <el-button type="" size="small" @click="close()">取消</el-button>
          <el-button type="primary" size="small" @click="save('form')" v-if="ifCreate">创建</el-button>
          <el-button type="primary" size="small" @click="update('form')" v-if="!ifCreate">确定</el-button>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { shopUpdate, shopAdd, getCategoryTree } from "@/api/data";
import listBoxF from "@/components/public/listBoxF/listBoxF.vue";
import checkAddress from "@/components/public/checkAddress.vue";
/**
 * 树形组件 用于选择框
 */
// import the component
import Treeselect from "@riophae/vue-treeselect";
// import the styles
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "shopEdit",
  components: {
    listBoxF,
    Treeselect,
    checkAddress
  },
  props: {
    dialogVisible: {
      default: false,
    },
    rowData: {},
    multiple: {
      type: Boolean,
      default: false
    },
  },
  data() {
    return {
      form: {
        shopCode: "",
        shopName: "",
        businessPersonCode: "",
        liablePersonCode: "",
        cooperationType: "",
        shopStatus: "",
        description:"",
        shopKey: "",
        categoryKey: "",
        address: {
          // 省provinceCode
          province: "",
          // 市cityCode
          city: "",
          // 区districtCode
          district: "",
          //详细地址address
          detail: "",
        }, //联系地址
      },
      ifCreate: true,
      list: [],
      rules: {
        shopCode: [
          { required: true, message: '请输入门店编码', trigger: 'blur' },
        ],
        shopName: [
          { required: true, message: '请输入门店名称', trigger: 'blur' },
        ],
        businessPersonCode: [
          { required: true, message: '请输入业务人员', trigger: 'blur' },
        ],
        liablePersonCode: [
          { required: true, message: '请输入责任人员', trigger: 'blur' },
        ],
        cooperationType: [
          { required: true, message: '请选择经营类型', trigger: 'blur' },
        ],
        shopStatus: [
          { required: true, message: '请选择店铺状态', trigger: 'blur' },
        ],
        categoryKey: [
          { required: true, message: '请选择所属分类', trigger: 'blur' },
        ],
        address: [
          { required: true, message: '请输入地址', trigger: 'blur' },
        ],
      }
    };
  },
  watch: {
    valueC: {
      deep: true,
      handler(val) {
        this.$emit('getSelectVal', val)
      }
    }
  },
  created() {
    // this.getbusinessTree()
    this.getTree()
    if (this.rowData.shopKey) {
      this.ifCreate = false;
      this.form.shopKey = this.rowData.shopKey
      this.form.shopName = this.rowData.shopName
      this.form.shopCode = this.rowData.shopCode
      this.form.businessPersonCode = this.rowData.businessPersonCode
      this.form.liablePersonCode = this.rowData.liablePersonCode
      this.form.cooperationType = this.rowData.cooperationType
      this.form.shopStatus = this.rowData.shopStatus
      this.form.categoryKey = this.rowData.categoryKey
      this.form.description=this.rowData.description
      this.form.address.province = this.rowData.province;
      this.form.address.city = this.rowData.city;
      this.form.address.district = this.rowData.district;
      this.form.address.detail = this.rowData.detail;
      console.log(this.rowData);
    } else {
      this.ifCreate = true;
    }
    if (this.form.categoryKey) {
      this.valueC = this.form.categoryKey;
    } else {
      this.valueC = null;
    }
  },
  methods: {
    // 自定义参数键值名称
    normalizerC(node) {
      //去掉children=[]的children属性
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.categoryKey,
        label: node.categoryName,
        children: node.children,
        level: node.level
      }
    },
    selectNodeC(node) {
      this.form.categoryKey = node.categoryKey || ''
      this.valueC = node.categoryKey
    },
    getTree() {
      let params = {
        categoryType: "SHOP",
      }
      getCategoryTree(params).then(res => {
        if (res.data.code === 200) {
          res.data.data.forEach(item => {
            if (item.categoryName == '全部') {
              let index = res.data.data.indexOf(item)
              res.data.data.splice(index, 1)
            }
          });
          this.list = res.data.data
          console.log(this.list);
        } else {
          console.log('error');
        }
      })
    },
    close() {
      this.$emit("close");
      this.value = null;
    },
    //更新
    update(formName) {
      this.form.categoryKey = this.valueC
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let data={
            province: this.form.address.province,
            city: this.form.address.city,
            district: this.form.address.district,
            detail: this.form.address.detail,
            shopCode: this.form.shopCode,
            shopName: this.form.shopName,
            businessPersonCode: this.form.businessPersonCode,
            liablePersonCode: this.form.liablePersonCode,
            cooperationType: this.form.cooperationType,
            shopStatus: this.form.shopStatus,
            description: this.form.description,
            shopKey: this.form.shopKey,
            categoryKey: this.form.categoryKey
          }
          shopUpdate(data)
            .then((res) => {
              if (res.data.code === 200) {
                this.$message.success("修改成功!");
                this.$parent.success();
              } else {
                this.$message.error(res.data.msg);
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

    //新建
    save(formName) {
      this.form.categoryKey = this.valueC
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let data={
            province: this.form.address.province,
            city: this.form.address.city,
            district: this.form.address.district,
            detail: this.form.address.detail,
            shopCode: this.form.shopCode,
            shopName: this.form.shopName,
            businessPersonCode: this.form.businessPersonCode,
            liablePersonCode: this.form.liablePersonCode,
            cooperationType: this.form.cooperationType,
            shopStatus: this.form.shopStatus,
            description: this.form.description,
            categoryKey: this.form.categoryKey
          }
          console.log(data);
          shopAdd(data)
            .then((res) => {
              if (res.data.code == 200) {
                this.$message.success("创建成功!");
                this.$parent.success();
              } else {
                this.$message.error(res.data.msg);
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
  },
};
</script>

<style lang="scss" src="@/assets/styles/element/input.scss" scoped>
.dialog_body {
  margin-top: -10px;

  .form_text {
    width: 350px;
  }

  .form_select {
    width: 250px;
  }
}

.dialog_footer {
  border-top: 1px solid #eeeeee;
  padding-top: 10px;
  text-align: right;
}

.el-radio-group {
  font-size: 0;
  margin: 5px;
}
</style>
