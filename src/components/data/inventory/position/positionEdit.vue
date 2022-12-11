<template>
  <div>
    <el-dialog :title="ifCreate ? '新增货位信息' : '编辑'" :visible.sync="dialogVisible" width="600px" :before-close="close"
      top="25vh" :modal-append-to-body="false">
      <div class="dialog_body">
        <el-form size="middle" :model="form" :inline="true" :rules="rules" label-width="120px">
          <el-form-item label="货位编号:" prop="positionCode">
            <el-input v-model="form.positionCode" class="form_text" placeholder="货位编号"></el-input>
          </el-form-item>
          <el-form-item label="最大容量(m3):" prop="maxCapacity">
            <el-input v-model="form.maxCapacity" type="Number" class="form_text" placeholder="最大容量(m3)"></el-input>
          </el-form-item>
          <el-form-item label="最大重量" prop="maxWeight">
            <el-input v-model="form.maxWeight" type="Number" class="form_text" placeholder="最大重量"></el-input>
          </el-form-item>
          <el-form-item label="货位类型" prop="positionType">
            <el-radio-group v-model="form.positionType">
              <el-radio label="store" value="store" style="margin-right: 20px">存储位</el-radio>
              <el-radio label="assemble" value="assemble">集货位</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="所属仓库:" prop="inventoryKey">
            <el-select size="middle" v-model="form.inventoryKey" placeholder="所属仓库" style="width:350px;"
              @change="getdistrictlist()">
              <el-option v-for="item in options1" :key="item.inventoryKey" :label="item.inventoryName"
                :value="item.inventoryKey">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="所属区域:" prop="inventoryDistrictkey">
            <el-select size="middle" v-model="form.inventoryDistrictkey" placeholder="所属区域" style="width:350px;">
              <el-option v-for="item in options2" :key="item.inventoryDistrictkey" :label="item.districtCode"
                :value="item.inventoryDistrictkey">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="备注:" prop="description">
            <el-input v-model="form.description" type="textarea" class="form_text" placeholder="备注"></el-input>
          </el-form-item>
        </el-form>
        <div class="dialog_footer">
          <el-button type="" size="middle" @click="close()">取消</el-button>
          <el-button type="primary" size="middle" @click="save()" v-if="ifCreate">创建</el-button>
          <el-button type="primary" size="middle" @click="update()" v-if="!ifCreate">确定</el-button>
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
        inventoryDistrictkey: "",
        inventoryKey: "",
        positionCode: "", //货位编号
        positionType: "", //货位类型：store：存储位 assemble: 集货位
        maxWeight: "", //最大重量
        maxCapacity: "", //最大容量(m3)
        description: "", //备注
        positionKey: ""
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
    this.getinventorylist()
    if (this.rowData.positionCode) {
      this.ifCreate = false;
      this.form.inventoryDistrictkey = this.rowData.inventoryDistrictkey
      this.form.inventoryKey = this.rowData.inventoryKey
      this.form.positionCode = this.rowData.positionCode
      this.form.positionType = this.rowData.positionType
      this.form.maxWeight = this.rowData.maxWeight
      this.form.maxCapacity = this.rowData.maxCapacity
      this.form.description = this.rowData.description
      this.form.positionKey = this.rowData.positionKey
      console.log(this.rowData);
    } else {
      this.ifCreate = true
    }
    console.log(this.userList);
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
    update() {
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
    },
    //创建
    save() {
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
    },
    reset() {
      this.form = {
        inventoryDistrictkey: "",
        inventoryKey: "",
        positionCode: "", //货位编号
        positionType: "", //货位类型：store：存储位 assemble: 集货位
        maxWeight: "", //最大重量
        maxCapacity: "", //最大容量(m3)
        description: "", //备注
        positionKey: ""
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
