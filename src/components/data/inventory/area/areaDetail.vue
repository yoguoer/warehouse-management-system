<template>
    <el-drawer size="95%" title="区域详情" :direction="direction" :visible.sync="myDrawer"
      :close-on-press-escape="false" :show-close="true" :before-close="handleClose" :wrapperClosable="false">
      <div style="padding:0 20px;">
        <el-row style="margin-bottom: 20px;">
          <el-col :span="5"> 
            <span>分区号:  {{row.districtCode}}</span>
          </el-col>
          <el-col :span="5">
            <span>排序:  {{row.sort}}</span>
          </el-col>
          <el-col :span="5">
            <span>所属仓库:  {{row.inventoryKey}}</span>
          </el-col>
          <el-col :span="5">
            <span>备注:  {{row.description}}</span>
          </el-col>
        </el-row>
        <el-row>
          <el-tabs type="card" v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="仓库" name="area-inventory">
            </el-tab-pane>
            <el-tab-pane label="货位" name="area-position">
            </el-tab-pane>
            <router-view></router-view>
          </el-tabs>
        </el-row>
      </div>
    </el-drawer>
  </template>
  
  
  <script>
  
  export default {
    name: 'areaDetail',
    data() {
      return {
        activeName: 'area-inventory',
        direction: 'btt',
        row:{},
        myDrawer:true,
      }
    },
    props: {
      drawer: {
        default: true,
      },
      rowData: {},
    },
    components: {
    },
    watch: {
  
    },
    created() {
      this.row=this.$route.params.rowData||this.rowData
      this.$router.push({ name: this.activeName, params:{rowData:this.row,inventoryDistrictkey:this.row.inventoryDistrictkey }})
    },
  
    methods: {
      //换tabs
      handleClick (v) {
        this.activeName=v.name
        this.$router.push({ name: this.activeName, params:{rowData:this.row,inventoryDistrictkey:this.row.inventoryDistrictkey }})
        this.$forceUpdate();
      },
      handleClose(done) {
         this.$parent.isShow=false
         this.myDrawer=this.drawer
         this.myDrawer=false
         this.$router.push({ name: "inventory" })
      }
    },
  }
  </script>
  <style lang="scss">

  </style>