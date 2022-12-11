<template>
  <div>
    <el-dialog :title="ifCreate ? '新增区域信息' : '编辑'" :visible.sync="dialogVisible" width="600px" :before-close="close"
      top="25vh" :modal-append-to-body="false">
      <div class="dialog_body">
        <el-form size="middle" :model="form" :rules="rules" :inline="true" label-width="100px">
          <el-form-item label="区域号:" prop="districtCode">
            <el-input v-model="form.districtCode" class="form_text" placeholder="区域号"></el-input>
          </el-form-item>
          <el-form-item label="所属仓库:" prop="inventoryKey">
            <el-select size="middle" v-model="form.inventoryKey" placeholder="所属仓库" style="width:350px;">
              <el-option v-for="item in options" :key="item.inventoryKey" :label="item.inventoryName"
                :value="item.inventoryKey">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="排序" prop="sort">
            <el-input v-model="form.sort" type="Number" class="form_text" placeholder="排序"></el-input>
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
import { districtUpdate, districtAdd,inventorylist } from "@/api/data";

export default {
  name: "areaEdit",
  components: {},
  props: {
    dialogVisible: {
      default: false,
    },
    rowData: {},
    userList: {},
  },
  data() {
    return {
      form: {
        description: "",
        districtCode: "",
        inventoryDistrictkey: "",
        inventoryKey: "",
        sort: "",
      },
      ifCreate: true,
      options: [],
      rules: {
        districtCode: [
          { required: true, message: '请输入区域号', trigger: 'blur' },
        ],
        inventoryKey: [
          { required: true, message: '请输入品牌编号', trigger: 'blur' },
        ],
        sort: [
          { required: true, message: '请输入排序', trigger: 'blur' },
        ]
      }
    };
  },
  created() {
    this.getinventorylist()
    if (this.rowData.inventoryDistrictkey) {
      this.ifCreate = false;
      this.form.people = this.rowData.people;
      this.form.description = this.rowData.description
      this.form.districtCode = this.rowData.districtCode
      this.form.inventoryDistrictkey = this.rowData.inventoryDistrictkey
      this.form.inventoryKey = this.rowData.inventoryKey
      this.form.sort = this.rowData.sort
      console.log(this.rowData);
    } else {
      this.ifCreate = true;
    }
  },
  methods: {
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
      this.reset();
      this.$emit("close");
    },
    //更新
    update() {
      districtUpdate(this.form)
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
      districtAdd(this.form)
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
        description: "",
        districtCode: "",
        inventoryDistrictkey: "",
        inventoryKey: "",
        sort: "",
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
</style>
