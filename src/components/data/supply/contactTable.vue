<template>
  <div class="wrap-definition">
    <el-button type="success" size="small" icon="el-icon-plus" @click="add()">新增</el-button>
    <el-button class="el-icon-delete" type="danger" size="small" @click="handleDeleteList()">删除</el-button>
    <el-divider />

    <TableList :pageMethod="getTableData" :searchMethod="getTableData" :table-data="tableData"
      :tableColumn="tableColumn">
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
      <el-form :model="contactForm" :rules="rules2" ref="contactForm" label-width="100px" class="add-ruleForm">
        <el-row>
          <el-col :span="10">
            <el-form-item label="联系人编号" prop="contactNumber">
              <el-input v-model="contactForm.contactNumber"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="联系人姓名" prop="contactName">
              <el-input v-model="contactForm.contactName"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="联系电话" prop="contactTel">
              <el-input v-model="contactForm.contactTel"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="联系邮箱" prop="contactEmail">
              <el-input v-model="contactForm.contactEmail"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="联系地址" prop="address" v-model="contactForm.address">
              <checkAddress ref="address" v-model="contactForm.address" :form="contactForm.address" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleClose()">取 消</el-button>
        <el-button type="primary" @click="create('contactForm')" v-if="ifCreate">确 定</el-button>
        <el-button type="primary" @click="save('contactForm')" v-else>确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import TableList from "@/components/public/tableList";
import { Contactadd, Contactupdate, Contactdelete, ContactdeleteList } from "@/api/data";
import checkAddress from '@/components/public/checkAddress.vue'

export default {
  name: 'contactTable',
  data() {
    return {
      tableData: [],
      dialogVisible: false,
      multipleSelection: [],
      title: "编辑联系人",
      ifCreate: false,
      contactForm: {
        contactSupplierKey: "",
        contactKey: "",
        contactNumber: "",
        contactName: "",
        contactEmail: "",
        contactTel: "",
        address: {
          province: "",
          city: "",
          district: "",
          detail: ""
        }
      },
      rules2: {
        contactNumber: [
          { required: true, message: '请输入联系人编号', trigger: 'blur' },
        ],
        contactName: [
          { required: true, message: '请输入联系人姓名', trigger: 'blur' },
        ],
        contactEmail: [
          { required: true, message: '请输入联系邮箱', trigger: 'blur' },
        ],
        contactTel: [
          { required: true, message: '请输入联系电话', trigger: 'blur' },
        ],
        address: [
          { required: true, message: '请输入联系地址', trigger: 'blur' },
        ],
      }
    }
  },
  props: {
    contactList: []
  },
  watch: {},
  created() {
    this.tableData = this.contactList
    console.log("contactList", this.tableData)
  },
  computed: {
    tableColumn() {
      return [
        { prop: "contactNumber", label: "联系人编号" },
        { prop: "contactName", label: "联系人姓名" },
        { prop: "contactTel", label: "联系电话" },
        { prop: "contactEmail", label: "联系邮箱" },
        // { prop: "address", label: "联系地址" },
        { slots: { name: "column-address" }, label: "联系地址" },
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
      this.contactForm.contactNumber = row.contactNumber
      this.contactForm.contactName = row.contactName
      this.contactForm.contactEmail = row.contactEmail
      this.contactForm.contactTel = row.contactTel
      this.contactForm.address.province = row.province
      this.contactForm.address.city = row.city
      this.contactForm.address.district = row.district
      this.contactForm.address.detail = row.detail
      this.contactForm.contactKey = row.contactKey
      this.contactForm.contactSupplierKey = row.contactSupplierKey
      this.dialogVisible = true
    },
    //添加
    add() {
      this.title = "新增联系人"
      this.contactForm.contactSupplierKey = this.$parent.$parent.$parent.$parent.$parent.row.supplierKey
      this.dialogVisible = true
      this.ifCreate = true
    },
    //删除
    deleteRow(row) {
      Contactdelete({ contactKey: row.contactKey })
        .then((res) => {
          if (res.data.code === 200) {
            this.$message.success('删除成功!')
            this._handleFresh()
          } else {
            this.$message.error(res.msg)
          }
        })
    },
    //添加
    create(formName) {
      let data = {
        contactName: this.contactForm.contactName,
        province: this.contactForm.address.province,
        city: this.contactForm.address.city,
        district: this.contactForm.address.district,
        detail: this.contactForm.address.detail,
        contactEmail: this.contactForm.contactEmail,
        contactTel: this.contactForm.contactTel,
        contactSupplierKey: this.contactForm.contactSupplierKey,
        // contactKey:this.contactForm.contactKey,
        contactNumber: this.contactForm.contactNumber
      }
      this.$refs[formName].validate((valid) => {
        if (valid) {
          Contactadd(data)
            .then((res) => {
              if (res.data.code === 200) {
                this.$forceUpdate()
                this.$message.success('添加成功!')
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
        contactName: this.contactForm.contactName,
        province: this.contactForm.address.province,
        city: this.contactForm.address.city,
        district: this.contactForm.address.district,
        detail: this.contactForm.address.detail,
        contactEmail: this.contactForm.contactEmail,
        contactTel: this.contactForm.contactTel,
        contactSupplierKey: this.contactForm.contactSupplierKey,
        contactKey: this.contactForm.contactKey,
        contactNumber: this.contactForm.contactNumber
      }
      this.$refs[formName].validate((valid) => {
        if (valid) {
          Contactupdate(data)
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
    handleClose() {
      this.dialogVisible = false
      this.reset()
    },
    _handleFresh() {
      this.$parent.$parent.$parent.$parent.$parent.getData()
      this.dialogVisible = false
      this.reset()
      this.$forceUpdate();
    },
    reset() {
      this.contactForm = {
        contactSupplierKey: "",
        contactKey: "",
        contactNumber: "",
        contactName: "",
        contactEmail: "",
        contactTel: "",
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
    //根据 userId 批量删除用户
    handleDeleteList() {
      if (this.multipleSelection.length > 0) {
        let contactKeys = [];
        this.multipleSelection.forEach(item => {
          contactKeys.push({ contactKey: item.contactKey })
        })
        console.log(contactKeys);
        this.$confirm('删除操作, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          ContactdeleteList(contactKeys).then(() => {
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
</style>