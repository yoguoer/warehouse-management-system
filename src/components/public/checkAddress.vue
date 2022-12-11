<!-- 地址选择 & 省市区三级联动 -->
<template>
  <el-form ref="form" :model="form" label-width="120px">
      <el-row>
        <el-select
          v-model="form.province"
          placeholder="请选择省"
          @change="changePro"
        >
          <el-option
            v-for="item in addressData"
            :key="item.code"
            :label="item.name"
            :value="item.name"
          >
          </el-option>
        </el-select>
        <el-select
          v-model="form.city"
          placeholder="请选择市"
          @change="changeCity"
        >
          <el-option
            v-for="item in cityData"
            :key="item.code"
            :label="item.name"
            :value="item.name"
          >
          </el-option>
        </el-select>
        <el-select
          v-model="form.district"
          placeholder="请选择区"
          @change="changeArea"
        >
          <el-option
            v-for="item in areaData"
            :key="item.code"
            :label="item.name"
            :value="item.name"
          >
          </el-option>
        </el-select>
        <el-input placeholder="详细地址" v-model="form.detail" style="width:200px;"></el-input>
      </el-row>
      <!-- <h3>测试当前选中地址：{{result}}</h3> -->
  </el-form>
  </template>
   
  <script>
  import address from "@/utils/address/address.json"; //全国省市区街道数据
  export default {
    data() {
      return {
        //  省数据
        addressData: [],
        //  市数据
        cityData: [],
        // 区数据
        areaData: [],
      };
    },
    props:{
      // 街道数据
      form:{
        province: "",
        // 市
        city: "",
        // 区
        district: "",
        //详细地址
        detail:"",
      }
    },
    created() {
      // 省份数据初始化
      this.addressData = address;
      // console.log("this.form",this.form)
  
      if (this.form.province != "") {
        let cityData = this.addressData.filter(item => item.name == this.form.province)
        if (cityData.length) {
          this.cityData = cityData[0].children
  
          let areaData = this.cityData.filter(item => item.name == this.form.city)
          if (areaData.length) {
            this.areaData = areaData[0].children
          }
        }
      }
    },
    computed:{
      //选择结果
      result(){
        if(!this.form.district){
          return ''
        }else if(this.form.district && this.form.detail){
          return ''+this.form.province+","+this.form.city+","+this.form.district+","+this.form.detail
        }else{
          return ''+this.form.province+","+this.form.city+","+this.form.district
        }
      }
    },
    methods: {
      reset(){
          this.form= {
              // 省
              province: "",
              // 市
              city: "",
              // 区
              district: "",
              //详细地址
              detail:"",
          }
      },
      // 省份更改
      changePro(e) {
        // 从省中过滤出市的数据
        this.cityData = this.addressData.filter((item) => {
          return item.name == e;
        })[0].children;
        // 省发生改变的时候 清空输入框市区街道的内容
        this.form.district = "";
        this.form.city = "";
        // 省发生更改时 该表空区街道数据的内容
        this.areaData = [];
      },
      // 市更改
      changeCity(e) {
        // 获取到区的数据
        this.areaData = this.cityData.filter(
          (item) => item.name == e
        )[0].children;
        // 清空数据后面对应数组的数据
        this.form.district = "";
      },
      // 区更改
      changeArea(e) {
        let temp = this.areaData.filter((item) => item.name == e);
        // 获取到区的code码
        this.form.regionalNumber = temp[0].code;
        // 获取到街道的数据
        this.jdData = this.areaData.filter((item) => item.name == e)[0].children;
      },
    },
  };
  </script>
  
  <style lang="scss" rel="stylesheet/scss" scoped>
  .el-row{
      display: inline;
  }
  .el-row{
      display: inline-flex;
      flex: auto;
      .el-select {
          margin: 0 20px 0 0;
          width: 122px;
      }
  }
  </style>