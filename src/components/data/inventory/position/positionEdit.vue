<template>
  <div>
    <el-dialog :title="ifCreate ? '新增货位信息' : '编辑'" :visible.sync="dialogVisible" width="1200px" :before-close="close"
      top="25vh" :modal-append-to-body="false" :close-on-click-modal="false">
      <div class="dialog_body">
        <el-form size="middle" :model="form" ref="form" :inline="true" :rules="rules" label-width="120px">
          <el-row>
            <el-col :span="10">
              <el-form-item label="货位编号:" prop="positionCode">
                <el-input v-model="form.positionCode" class="form_text" placeholder="货位编号" clearable></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="最大容量(m3):" prop="maxCapacity">
                <el-input v-model="form.maxCapacity" type="Number" class="form_text" placeholder="最大容量(m3)" clearable></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="最大重量" prop="maxWeight">
                <el-input v-model="form.maxWeight" type="Number" class="form_text" placeholder="最大重量" clearable></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="货位类型" prop="positionType">
                <el-select size="mini" v-model="form.positionType" placeholder="请选择" style="width: 350px" clearable>
                  <el-option label="存储位" value="store"></el-option>
                  <el-option label="集货位" value="assemble"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="所属仓库:" prop="inventoryKey">
                <el-select size="middle" v-model="form.inventoryKey" placeholder="所属仓库" style="width:350px;" clearable
                  @change="getdistrictlist()">
                  <el-option v-for="item in options1" :key="item.inventoryKey" :label="item.inventoryName"
                    :value="item.inventoryKey">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="所属区域:" prop="inventoryDistrictkey">
                <el-select size="middle" v-model="form.inventoryDistrictkey" placeholder="所属区域" style="width:350px;" clearable>
                  <el-option v-for="item in options2" :key="item.inventoryDistrictkey" :label="item.districtName"
                    :value="item.inventoryDistrictkey">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-form-item label="备注:" prop="description">
              <el-input v-model="form.description" type="textarea" class="form_text" placeholder="备注" clearable></el-input>
            </el-form-item>
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
import { positionUpdate, positionAdd, districtlist, inventorylist } from "@/api/data";

export default {
  name: "positionEdit",
  components: {},
  props: {
    dialogVisible: {
      default: false,
    },
    rowData: {},
  },
  data() {
    return {
      form: {
        inventoryKey: "",
        positionCode: "", //货位编号
        positionType: "", //货位类型：store：存储位 assemble: 集货位
        maxWeight: "", //最大重量
        maxCapacity: "", //最大容量(m3)
        description: "", //备注
        positionKey: "",
        inventoryDistrictkey: "",
      },
      ifCreate: true,
      options1: [],
      options2: [],
      list: [],
      rules: {
        positionCode: [
          { required: true, message: '请输入货位编号', trigger: 'blur' },
        ],
        positionType: [
          { required: true, message: '请输入货位类型', trigger: 'blur' },
        ],
        maxWeight: [
          { required: true, message: '请输入最大重量', trigger: 'blur' },
        ],
        maxCapacity: [
          { required: true, message: '请输入最大容量(m3)', trigger: 'blur' },
        ],
        inventoryKey: [
          { required: true, message: '请输入所属仓库', trigger: 'blur' },
        ],
        // inventoryDistrictkey: [
        //   { required: true, message: '请输入所属区域', trigger: 'blur' },
        // ]
      }
    };
  },
  created() {
    if (this.rowData.inventoryKey) {
      this.ifCreate = false;
      this.form.inventoryKey = this.rowData.inventoryKey
      this.form.positionCode = this.rowData.positionCode
      this.form.positionType = this.rowData.positionType
      this.form.maxWeight = this.rowData.maxWeight
      this.form.maxCapacity = this.rowData.maxCapacity
      this.form.description = this.rowData.description
      this.form.positionKey = this.rowData.positionKey
      this.form.inventoryDistrictkey = this.rowData.inventoryDistrictkey
    } else {
      this.form.inventoryKey = this.$route.params.inventoryKey || ""
      this.form.inventoryDistrictkey = this.$route.params.rowData.inventoryDistrictkey || ""
      this.ifCreate = true
    }
    this.getinventorylist()
    districtlist({ inventoryKey: this.form.inventoryKey })
      .then((res) => {
        if (res.data.code === 200) {
          this.options2 = res.data.data
        } else {
          this.$message.error(res.msg);
        }
      })
      .catch((e) => {
        console.log(e);
      });
  },
  methods: {
    getinventorylist() {
      inventorylist()
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
    getdistrictlist() {
      let data = ""
      if (this.form.inventoryKey) {
        this.form.inventoryDistrictkey = ""
        data = this.form.inventoryKey
      }
      districtlist({ inventoryKey: data })
        .then((res) => {
          if (res.data.code === 200) {
            this.options2 = res.data.data
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
    },
    //更新
    update(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          positionUpdate(this.form)
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
      this.$refs[formName].validate((valid) => {
        if (valid) {
          positionAdd(this.form)
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
        inventoryKey: "",
        positionCode: "", //货位编号
        positionType: "", //货位类型：store：存储位 assemble: 集货位
        maxWeight: "", //最大重量
        maxCapacity: "", //最大容量(m3)
        description: "", //备注
        positionKey: "",
        inventoryDistrictkey: "",
      }
    }
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
