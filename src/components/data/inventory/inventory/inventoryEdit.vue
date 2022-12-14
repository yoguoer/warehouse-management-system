<template>
  <div>
    <el-dialog :title="ifCreate ? '新增仓库信息' : '编辑'" :visible.sync="dialogVisible" width="1200px" :before-close="close"
      top="25vh" :modal-append-to-body="false" :close-on-click-modal="false">
      <div class="dialog_body">
        <el-form size="middle" :model="form" :rules="rules" ref="form" :inline="true" label-width="100px">
          <el-row>
            <el-col :span="10">
              <el-form-item prop="inventoryName" label="仓库名">
                <el-input v-model="form.inventoryName" placeholder="仓库名" class="form_text" clearable></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item prop="inventoryCode" label="仓库编号">
                <el-input v-model="form.inventoryCode" placeholder="仓库编号" class="form_text" clearable></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="仓库类型:" prop="inventoryType">
                <!-- <el-radio-group v-model="form.inventoryType">
                  <el-radio label="WAREHOUSE" value="WAREHOUSE" style="margin-right: 20px">仓库</el-radio>
                  <el-radio label="CAR" value="CAR">车辆</el-radio>
                </el-radio-group> -->
                <!-- 1.供应商仓库 2.门店仓 3.大仓 4.渠道仓 5.直发仓 -->
                <el-select size="mini" v-model="form.inventoryType" placeholder="请选择" style="width: 350px" clearable>
                  <el-option label="供应商仓库" :value="1"></el-option>
                  <el-option label="门店仓" :value="2"></el-option>
                  <el-option label="大仓" :value="3"></el-option>
                  <el-option label="渠道仓" :value="4"></el-option>
                  <el-option label="直发仓" :value="5"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="仓库状态" prop="status">
                <!-- 1.正常，2.关仓', -->
                <el-select size="mini" v-model="form.status" placeholder="请选择" style="width: 350px" clearable>
                  <el-option label="正常" :value="1"></el-option>
                  <el-option label="关仓" :value="2"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="所属分类" v-model="form.categoryKey" prop="categoryKey">
                <listBoxF style="width: 350px">
                  <template slot="content">
                    <treeselect class="treeSelect-option" v-model="value" :normalizer="normalizer" :options="list" clearable
                      style="width:350px;" placeholder="请选择" @select="selectNode" />
                  </template>
                </listBoxF>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="联系人:" prop="contactName">
                <el-input v-model="form.contactName" class="form_text" placeholder="联系人" clearable></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="电话:" prop="tel">
                <el-input v-model="form.tel" class="form_text" placeholder="电话" clearable></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="邮编:" prop="zipCode">
                <el-input v-model="form.zipCode" class="form_text" placeholder="邮编" clearable></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row style="white-space: nowrap;">
            <el-form-item label="联系地址" prop="address" v-model="form.address">
              <checkAddress ref="address" v-model="form.address" :form="form.address" style="width: 80%" />
            </el-form-item>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="备注:" prop="description">
                <el-input v-model="form.description" type="textarea" class="form_text" placeholder="备注" clearable></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div class="dialog_footer">
          <el-button type="" size="middle" @click="close()">取消</el-button>
          <el-button type="primary" size="middle" @click="save('form')" v-if="ifCreate">创建</el-button>
          <el-button type="primary" size="middle" @click="update('form')" v-if="!ifCreate">确定</el-button>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { inventoryUpdate, inventoryAdd, getCategoryTree } from "@/api/data";
import checkAddress from "@/components/public/checkAddress.vue";
import listBoxF from "@/components/public/listBoxF/listBoxF.vue";
/**
 * 树形组件 用于选择框
 */
// import the component
import Treeselect from "@riophae/vue-treeselect";
// import the styles
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "inventoryEdit",
  components: {
    checkAddress,
    listBoxF,
    Treeselect,
  },
  props: {
    dialogVisible: {
      default: false,
    },
    rowData: {},
  },
  data() {
    return {
      form: {
        categoryKey: "", //分类
        contactName: "", //联系人
        inventoryCode: "", //仓库编号
        inventoryKey: "",
        inventoryName: "", //仓库名
        inventoryType: "", //仓库类型：WAREHOUSE：仓库，CAR：车辆
        status: "",
        tel: "", //电话
        zipCode: "", //邮编
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
        description: "", //备注
      },
      ifCreate: true,
      list: [],
      rules: {
        inventoryName: [
          { required: true, message: '请输入仓库名', trigger: 'blur' },
        ],
        inventoryCode: [
          { required: true, message: '请输入仓库编号', trigger: 'blur' },
        ],
        inventoryType: [
          { required: true, message: '请输入仓库类型', trigger: 'blur' },
        ],
        contactName: [
          { required: true, message: '请输入联系人', trigger: 'blur' },
        ],
        categoryKey: [
          { required: true, message: '请输入所属分类', trigger: 'blur' },
        ],
        tel: [
          { required: true, message: '请输入电话', trigger: 'blur' },
        ],
        zipCode: [
          { required: true, message: '请输入邮编', trigger: 'blur' },
        ],
        address: [
          { required: true, message: '请输入地址', trigger: 'blur' },
        ],
        status: [
          { required: true, message: '请选择仓库状态', trigger: 'blur' },
        ]
      }
    };
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
    if (this.rowData.contactName) {
      this.ifCreate = false;
      this.form.categoryKey = this.rowData.categoryKey;
      this.form.contactName = this.rowData.contactName;
      this.form.inventoryCode = this.rowData.inventoryCode;
      this.form.inventoryKey = this.rowData.inventoryKey;
      this.form.inventoryName = this.rowData.inventoryName;
      this.form.inventoryType = this.rowData.inventoryType;
      this.form.tel = this.rowData.tel;
      this.form.zipCode = this.rowData.zipCode;
      this.form.address.province = this.rowData.province;
      this.form.address.city = this.rowData.city;
      this.form.address.district = this.rowData.district;
      this.form.address.detail = this.rowData.detail;
      this.form.description = this.rowData.description;
      this.form.status = this.rowData.status;

      console.log(this.rowData);
    } else {
      this.ifCreate = true;
    }
    if (this.form.categoryKey) {
      this.value = this.form.categoryKey;
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
      this.form.categoryKey = node.categoryKey || "";
      this.value = node.categoryKey;
    },

    getTree() {
      let params = {
        categoryType: "WAREHOUSE",
      };
      getCategoryTree(params).then((res) => {
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
          console.log("error");
        }
      });
    },

    close() {
      this.reset();
      this.$emit("close");
      this.value = null;
    },

    //更新
    update(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let data = {
            categoryKey: this.value,
            contactName: this.form.contactName,
            inventoryCode: this.form.inventoryCode,
            inventoryKey: this.form.inventoryKey,
            inventoryName: this.form.inventoryName,
            inventoryType: this.form.inventoryType,
            tel: this.form.tel,
            zipCode: this.form.zipCode,
            province: this.form.address.province,
            city: this.form.address.city,
            district: this.form.address.district,
            detail: this.form.address.detail,
            description: this.form.description,
            status: this.form.status
          };
          inventoryUpdate(data)
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

    //新建
    save(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let data = {
            categoryKey: this.form.categoryKey,
            contactName: this.form.contactName,
            inventoryCode: this.form.inventoryCode,
            inventoryKey: this.form.inventoryKey,
            inventoryName: this.form.inventoryName,
            inventoryType: this.form.inventoryType,
            tel: this.form.tel,
            zipCode: this.form.zipCode,
            province: this.form.address.province,
            city: this.form.address.city,
            district: this.form.address.district,
            detail: this.form.address.detail,
            status: this.form.status,
            description: this.form.description,
          };
          console.log(data);
          inventoryAdd(data)
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
      this.form = {
        categoryKey: "", //分类
        contactName: "", //联系人
        inventoryCode: "", //仓库编号
        inventoryKey: "",
        inventoryName: "", //仓库名
        inventoryType: "", //仓库类型：WAREHOUSE：仓库，CAR：车辆
        tel: "", //电话
        zipCode: "", //邮编
        status: "",
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
        description: "", //备注
      };
    },
  },
};
</script>

<style lang="scss" scoped>
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
