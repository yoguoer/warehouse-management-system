<template>
  <div class="wrap-definition">
    <el-button type="success" size="small" icon="el-icon-plus" @click="add()">新增</el-button>
    <el-button class="el-icon-delete" type="danger" size="small" @click="handleDeleteList()">删除</el-button>
    <el-divider />

    <TableList :pageMethod="getTableData" :searchMethod="getTableData" :table-data="tableData" :tableColumn="tableColumn"
      height="500px" :multiCheck="multiCheck">
      <template v-slot:column-address="props">
        <span>{{ props.row.province }},{{ props.row.city }},{{ props.row.district }},{{ props.row.detail }}</span>
      </template>
      <template v-slot:column-todo="props">
        <el-button class="prohibitclick" @click="editRow(props.row)" type="text" size="small">编辑</el-button>
        <el-button class="prohibitclick" @click="deleteRow(props.row)" type="text" size="small">删除</el-button>
      </template>
    </TableList>

    <el-dialog :title="title" :visible.sync="dialogVisible" :append-to-body='true' :close-on-click-modal="false"
      :before-close="handleClose">
      <el-form :model="addressForm" :rules="rules" ref="addressForm" label-width="100px" class="add-ruleForm">
        <el-row>
          <el-col :span="10">
            <el-form-item label="地址类型" prop="addressType">
              <el-input v-model="addressForm.addressType"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="供应商地址" prop="address" v-model="addressForm.address">
              <checkAddress ref="address" v-model="addressForm.address" :form="addressForm.address" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleClose()">取 消</el-button>
        <el-button type="primary" @click="create('addressForm')" v-if="ifCreate">确 定</el-button>
        <el-button type="primary" @click="save('addressForm')" v-else>确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import TableList from "@/components/public/tableList";
import { supplierAddressAdd, supplierAddressUpdate, supplierAddressDelete, addressDeleteList } from "@/api/data";
import checkAddress from '@/components/public/checkAddress.vue'

export default {
  name: 'addressTable',
  data() {
    return {
      tableData: [],
      dialogVisible: false,
      ifCreate: false,
      multipleSelection: [],
      multiCheck: true,
      title: "编辑联系地址",
      addressForm: {
        addressKey: "",
        addressType: "",
        supplierAddressKey: "",
        address: {
          province: "",
          city: "",
          district: "",
          detail: ""
        }
      },
      rules: {
        addressType: [
          { required: true, message: '请输入地址类型', trigger: 'blur' },
        ],
        address: [
          { required: true, message: '请输入联系地址', trigger: 'blur' },
        ]
      }
    }
  },
  props: {
    addressList: []
  },
  watch: {},
  created() {
    this.tableData = this.addressList
    console.log("addressList", this.tableData)
  },
  computed: {
    tableColumn() {
      return [
        { prop: "addressType", label: "地址类型" },
        // { prop: "address", label: "供应商地址" },
        { slots: { name: "column-address" }, label: "供应商地址" },
        { slots: { name: "column-todo" }, label: "操作" },
      ];
    },
  },
  components: {
    TableList,
    checkAddress
  },
  methods: {
    getTableData() {
      return this.tableData
    },
    //编辑
    editRow(row) {
      this.addressForm.addressKey = row.addressKey
      this.addressForm.address.province = row.province
      this.addressForm.address.city = row.city
      this.addressForm.address.district = row.district
      this.addressForm.address.detail = row.detail
      this.addressForm.addressType = row.addressType
      this.addressForm.supplierAddressKey = row.supplierAddressKey
      this.dialogVisible = true
    },
    //添加
    add() {
      this.title = "新增联系地址"
      this.addressForm.supplierAddressKey = this.$parent.$parent.$parent.$parent.$parent.row.supplierKey
      this.dialogVisible = true
      this.ifCreate = true
    },
    //删除
    deleteRow(row) {
      this.$confirm('删除操作, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        supplierAddressDelete({ addressKey: row.addressKey })
          .then((res) => {
            if (res.data.code === 200) {
              this.$message.success('删除成功!')
              this._handleFresh()
            } else {
              this.$message.error(res.msg)
            }
          })

      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    _handleFresh() {
      this.$parent.$parent.$parent.$parent.$parent.getData()
      this.dialogVisible = false
      this.reset()
      this.$forceUpdate();
    },
    handleClose() {
      this.dialogVisible = false
      this.reset()
    },
    //新增
    create(formName) {
      let data = {
        // addressKey:this.addressForm.addressKey,
        province: this.addressForm.address.province,
        city: this.addressForm.address.city,
        district: this.addressForm.address.district,
        detail: this.addressForm.address.detail,
        addressType: this.addressForm.addressType,
        supplierAddressKey: this.addressForm.supplierAddressKey,
      }
      this.$refs[formName].validate((valid) => {
        if (valid) {
          supplierAddressAdd(data)
            .then((res) => {
              if (res.data.code === 200) {
                this.$forceUpdate()
                this.$message.success('新增成功!')
                this._handleFresh()
              } else {
                this.$message.error(res.msg)
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
    //更新
    save(formName) {
      let data = {
        addressKey: this.addressForm.addressKey,
        province: this.addressForm.address.province,
        city: this.addressForm.address.city,
        district: this.addressForm.address.district,
        detail: this.addressForm.address.detail,
        addressType: this.addressForm.addressType,
        supplierAddressKey: this.addressForm.supplierAddressKey,
      }
      this.$refs[formName].validate((valid) => {
        if (valid) {
          supplierAddressUpdate(data)
            .then((res) => {
              if (res.data.code === 200) {
                this.$forceUpdate()
                this.$message.success('修改成功!')
                this._handleFresh()
              } else {
                this.$message.error(res.msg)
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
      this.addressForm = {
        addressKey: "",
        addressType: "",
        supplierAddressKey: "",
        address: {
          province: "",
          city: "",
          district: "",
          detail: ""
        }
      }
    },
    //批量删除选择
    handleSelectionDelete(val) {
      this.multipleSelection = val
    },
    handleDeleteList() {
      if (this.multipleSelection.length > 0) {
        let addressKeys = [];
        this.multipleSelection.forEach(item => {
          addressKeys.push({ addressKey: item.addressKey })
        })
        console.log(addressKeys);
        this.$confirm('删除操作, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          addressDeleteList(addressKeys).then(() => {
            this.getTableData();
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
            this._handleFresh()
          }).catch(error => {
            console.log(error);
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
          this.multipleSelection = []
        });
      } else {
        this.$message({
          type: 'error',
          message: '至少选择一项'
        });
      }
    },
  },
}
</script>

<style lang="scss" rel="stylesheet/scss" scoped>
.wrap-definition {
  background: #fff;
}

.page-box {
  text-align: right;
  margin: 20px;
}
</style>