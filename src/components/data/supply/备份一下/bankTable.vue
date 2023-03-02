<template>
  <div class="wrap-definition">
    <el-button type="success" size="small" icon="el-icon-plus" @click="add()">新增</el-button>
    <el-button class="el-icon-delete" type="danger" size="small" @click="handleDeleteList()">删除</el-button>
    <el-divider />

    <TableList :pageMethod="getTableData" :searchMethod="getTableData" :table-data="tableData" :tableColumn="tableColumn"
      height="500px">
      <template v-slot:column-todo="props">
        <el-button class="prohibitclick" @click="editRow(props.row)" type="text" size="small">编辑</el-button>
        <el-button class="prohibitclick" @click="deleteRow(props.row)" type="text" size="small">删除</el-button>
      </template>
    </TableList>

    <el-dialog :title="title" :visible.sync="dialogVisible" :append-to-body='true' :close-on-click-modal="false"
      :before-close="handleClose">
      <el-form :model="bankForm" :rules="rules3" ref="bankForm" label-width="100px" class="add-ruleForm">
        <el-row>
          <el-col :span="10">
            <el-form-item label="开户银行" prop="bankName">
              <el-input v-model="bankForm.bankName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="账户名" prop="accountName">
              <el-input v-model="bankForm.accountName"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="银行账号" prop="accountNumber">
              <el-input v-model="bankForm.accountNumber"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="联系电话" prop="accountTel">
              <el-input v-model="bankForm.accountTel"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="税号" prop="taxNumber">
              <el-input v-model="bankForm.taxNumber"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleClose()">取 消</el-button>
        <el-button type="primary" @click="create('bankForm')" v-if="ifCreate">确 定</el-button>
        <el-button type="primary" @click="save('bankForm')" v-else>确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import TableList from "@/components/public/tableList";
import { supplierBillingAdd, supplierBillingUpdate, supplierBillingDelete, supplierBillingDeleteList } from "@/api/data";

export default {
  name: 'bankTable',
  data() {
    return {
      tableData: [],
      dialogVisible: false,
      ifCreate: false,
      multipleSelection: [],
      title: "编辑开票信息",
      bankForm: {
        supplierBillingKey: "",
        billingKey: "",
        bankName: "",
        accountNumber: "",
        accountName: "",
        accountTel: "",
        taxNumber: ""
      },
      rules3: {
        accountTel: [
          { required: true, message: '请输入联系电话', trigger: 'blur' },
        ],
        bankName: [
          { required: true, message: '请输入开户银行', trigger: 'blur' },
        ],
        accountNumber: [
          { required: true, message: '请输入银行账号', trigger: 'blur' },
        ],
        accountName: [
          { required: true, message: '请输入账户名', trigger: 'blur' },
        ],
        taxNumber: [
          { required: true, message: '请输入税号', trigger: 'blur' },
        ],
      }
    }
  },
  props: {
    bankList: []
  },
  watch: {},
  created() {
    this.tableData = this.bankList
    console.log("bankList", this.tableData)
  },
  computed: {
    tableColumn() {
      return [
        { prop: "bankName", label: "开户银行" },
        { prop: "accountNumber", label: "银行账号" },
        { prop: "accountName", label: "账户名" },
        { prop: "accountTel", label: "联系电话" },
        { prop: "taxNumber", label: "税号" },
        { slots: { name: "column-todo" }, label: "操作" },
      ];
    },
  },
  components: {
    TableList
  },
  methods: {
    getTableData() {
      return this.tableData
    },
    //编辑
    editRow(row) {
      this.bankForm.supplierBillingKey = row.supplierBillingKey
      this.bankForm.billingKey = row.billingKey
      this.bankForm.bankName = row.bankName
      this.bankForm.accountNumber = row.accountNumber
      this.bankForm.accountName = row.accountName
      this.bankForm.accountTel = row.accountTel
      this.bankForm.taxNumber = row.taxNumber
      this.dialogVisible = true
    },
    //添加
    add() {
      this.title = "新增开票信息"
      this.bankForm.supplierBillingKey = this.$parent.$parent.$parent.$parent.$parent.row.supplierKey
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
        supplierBillingDelete({ billingKey: row.billingKey })
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
        supplierBillingKey: this.bankForm.supplierBillingKey,
        // billingKey:this.bankForm.billingKey,
        bankName: this.bankForm.bankName,
        accountNumber: this.bankForm.accountNumber,
        accountName: this.bankForm.accountName,
        accountTel: this.bankForm.accountTel,
        taxNumber: this.bankForm.taxNumber
      }
      this.$refs[formName].validate((valid) => {
        if (valid) {
          supplierBillingAdd(data)
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
        supplierBillingKey: this.bankForm.supplierBillingKey,
        billingKey: this.bankForm.billingKey,
        bankName: this.bankForm.bankName,
        accountNumber: this.bankForm.accountNumber,
        accountName: this.bankForm.accountName,
        accountTel: this.bankForm.accountTel,
        taxNumber: this.bankForm.taxNumber
      }
      this.$refs[formName].validate((valid) => {
        if (valid) {
          supplierBillingUpdate(data)
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
      this.bankForm = {
        supplierBillingKey: "",
        billingKey: "",
        bankName: "",
        accountNumber: "",
        accountName: "",
        accountTel: "",
        taxNumber: ""
      }
    },
    //批量删除选择
    handleSelectionDelete(val) {
      this.multipleSelection = val
    },
    handleDeleteList() {
      if (this.multipleSelection.length > 0) {
        let billingKeys = [];
        this.multipleSelection.forEach(item => {
          billingKeys.push({ billingKey: item.billingKey })
        })
        console.log(billingKeys);
        this.$confirm('删除操作, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          supplierBillingDeleteList(billingKeys).then(() => {
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
.page-box {
  text-align: right;
  margin: 20px;
}

.wrap-definition {
  background: #fff;
}
</style>