<template>
  <div class="warehouse-config">
    <reloadAndsearch ref="search" :config="searchConfig" @search="getTableData" />
    <TableList :pageMethod="getTableData" :searchMethod="getTableData" :table-data="tableData"
      :tableColumn="tableColumn" :query.sync="query" :total="total" :loading="loadings.table" :height="'600px'">
      <template v-slot:column-supplier="{ row }">
        {{ row.supplierCode }}&nbsp;{{ row.supplierName }}
      </template>
      <template v-slot:column-supplierWarehouse="{ row }">
        {{ row.supplierWareHouseCode }}&nbsp;{{ row.supplierWareHouse }}
      </template>
      <template v-slot:column-status="{ row }">
        <div class="customFillet">
          <span :class="
            (statusClass[row.warehouseStatus] &&
              statusClass[row.warehouseStatus].class) ||
            'grey'
          "></span>
          <span>{{ row.warehouseStatusName }}</span>
        </div>
      </template>
      <template v-slot:column-createTime="{ row }">
        {{ getTime(row.createAt ? new Date(row.createAt) : "") }}
      </template>
      <template v-slot:column-prepareStuffStatus="{ row }">
        <el-checkbox :checked="row.prepareStuffStatus == 2" @change="changeStatus('prepareStuff', row)">备案</el-checkbox>
      </template>
      <template v-slot:column-stockScheduleStatus="{ row }">
        <el-checkbox :checked="row.stockScheduleStatus == 2"
          @change="changeStatus('stockSchedule', row)">排产</el-checkbox>
      </template>
      <template v-slot:column-stockProductStatus="{ row }">
        <el-checkbox :checked="row.stockProductStatus == 2" @change="changeStatus('stockProduct', row)"
          disabled>成品</el-checkbox>
      </template>
      <template v-slot:column-prepareSendStatus="{ row }">
        <el-checkbox :checked="row.prepareSendStatus == 2" @change="changeStatus('prepareSend', row)"
          disabled>备发</el-checkbox>
      </template>
      <template v-slot:column-todo="{ row }">
        <el-popconfirm :title="(row.warehouseStatus == 1 ? '确定设为正常？' : '确定设为关仓？')"
          @confirm="changeWareHouseStatus(row)">
          <el-button slot="reference" class="prohibitclick" type="text">{{ (row.warehouseStatus == 1 ? '设为关仓' :
              '设为正常')
          }}</el-button>
        </el-popconfirm>
      </template>
    </TableList>
    <addSupplierWarehouse ref="addSupplierWarehouse" :visible="supplierWarehouseVisible"
      @close="supplierWarehouseVisible = false" v-if="supplierWarehouseVisible"></addSupplierWarehouse>
  </div>
</template>

<script>
import reloadAndsearch from "@/components/public/reloadAndsearch/reloadAndsearch.vue";
import TableList from "@/components/public/tableList";
import addSupplierWarehouse from '@/components/suppliers/warehouseConfig/addSupplierWarehouse.vue';
import { dateFormat1 } from "@/utils/util.js";
export default {
  components: { reloadAndsearch, TableList, addSupplierWarehouse },
  data() {
    return {
      supplierList: [],
      supplierWarehouseStatus: [
        { dictValue: "正常", dictCode: 1 },
        { dictValue: "关仓", dictCode: 2 },
      ],

      // 表格数据
      tableData: [],
      total: 0,
      loadings: {
        table: false,
      },
      query: {
        pageNo: 1,
        pageSize: 10,
      },
      supplierWarehouseVisible: false
    };
  },
  computed: {
    searchConfig() {
      return [
        {
          label: "所属供应商",
          placeholder: "请选择供应商",
          field: "supplier",
          type: "select",
          showCode: true,
          options: this.supplierList,
          multiple: true,
          option: {
            label: "supplierName",
            value: "supplierCode",
          },
        },
        {
          label: "供应商仓库",
          placeholder: "请输入编码/名称",
          field: "supplierWareHouse",
          type: "input",
        },
        {
          label: "状态",
          placeholder: "请选择状态",
          multiple: true,
          options: this.supplierWarehouseStatus,
          option: {
            label: "dictValue",
            value: "dictCode",
          },
          field: "supplierStatusList",
          type: "select",
        },
      ];
    },
    tableColumn() {
      return [
        { label: "所属供应商", slots: { name: "column-supplier" } },
        { label: "供应商仓库", slots: { name: "column-supplierWarehouse" } },
        { label: "状态", slots: { name: "column-status" }, width: 100 },
        {
          label: "备料",
          slots: { name: "column-prepareStuffStatus" },
          width: 100,
          align: "center"
        },
        {
          label: "排产",
          slots: { name: "column-stockScheduleStatus" },
          width: 100,
          align: "center"
        },
        {
          label: "成品",
          slots: { name: "column-stockProductStatus" },
          width: 100,
          align: "center"
        },
        {
          label: "备发",
          slots: { name: "column-prepareSendStatus" },
          width: 100,
          align: "center"
        },
        { slots: { name: "column-createTime" }, label: "创建时间", width: 200 },
        { label: "操作", slots: { name: "column-todo" }, width: 200 },
      ];
    },
    statusClass() {
      return {
        1: {
          class: "blue",
        },
        2: {
          class: "grey",
        },
      };
    },
  },
  methods: {
    getTableData() {
      const res = {
        records: [
          {
            supplierCode: "10104",
            supplierName: "汕头市怡佳塑胶制品有限公司",
            supplierWareHouseCode: "10104",
            supplierWareHouse: "汕头市怡佳塑胶制品有限公司",
            warehouseStatus: 1,
            warehouseStatusName: "正常",
            prepareStuffStatus: 2,
            stockScheduleStatus: 2,
            stockProductStatus: 2,
            prepareSendStatus: 2,
            createAt: "2022-12-05T06:04:38.000+0000",
          },
          {
            supplierCode: "10305",
            supplierName: "浙江骏丰日用品有限公司",
            supplierWareHouseCode: "10305",
            supplierWareHouse: "浙江骏丰日用品有限公司",
            warehouseStatus: 1,
            warehouseStatusName: "正常",
            prepareStuffStatus: 2,
            stockScheduleStatus: 2,
            stockProductStatus: 2,
            prepareSendStatus: 2,
            createAt: "2022-12-04T10:44:49.000+0000",
          },
          {
            supplierCode: "10054",
            supplierName: "义乌市英俊日用制品有限公司",
            supplierWareHouseCode: "10054",
            supplierWareHouse: "义乌市英俊日用制品有限公司",
            warehouseStatus: 2,
            warehouseStatusName: "关仓",
            prepareStuffStatus: 1,
            stockScheduleStatus: 1,
            stockProductStatus: 1,
            prepareSendStatus: 1,
            createAt: "2022-12-04T10:44:42.000+0000",
          },
          {
            supplierCode: "10072",
            supplierName: "义乌市百丽饰品厂",
            supplierWareHouseCode: "10072",
            supplierWareHouse: "义乌市百丽饰品厂",
            warehouseStatus: 1,
            warehouseStatusName: "正常",
            prepareStuffStatus: 2,
            stockScheduleStatus: 2,
            stockProductStatus: 2,
            prepareSendStatus: 2,
            createAt: "2022-12-02T08:41:36.000+0000",
          },
          {
            supplierCode: "13411",
            supplierName: "汕头市大江电子商务有限公司",
            supplierWareHouseCode: "13411",
            supplierWareHouse: "汕头市大江电子商务有限公司",
            warehouseStatus: 1,
            warehouseStatusName: "正常",
            prepareStuffStatus: 2,
            stockScheduleStatus: 2,
            stockProductStatus: 2,
            prepareSendStatus: 2,
            createAt: "2022-11-25T09:41:29.000+0000",
          },
          {
            supplierCode: "10957",
            supplierName: "上海容本生活用品有限公司",
            supplierWareHouseCode: "10957",
            supplierWareHouse: "上海容本生活用品有限公司",
            warehouseStatus: 1,
            warehouseStatusName: "正常",
            prepareStuffStatus: 2,
            stockScheduleStatus: 2,
            stockProductStatus: 2,
            prepareSendStatus: 2,
            createAt: "2022-11-25T09:41:12.000+0000",
          },
          {
            supplierCode: "10652",
            supplierName: "青田县陈明饰品有限公司",
            supplierWareHouseCode: "10652",
            supplierWareHouse: "青田县陈明饰品有限公司",
            warehouseStatus: 1,
            warehouseStatusName: "正常",
            prepareStuffStatus: 2,
            stockScheduleStatus: 2,
            stockProductStatus: 2,
            prepareSendStatus: 2,
            createAt: "2022-11-24T03:04:29.000+0000",
          },
          {
            supplierCode: "10025",
            supplierName: "东莞市莞美饰品有限公司",
            supplierWareHouseCode: "10025",
            supplierWareHouse: "东莞市莞美饰品有限公司",
            warehouseStatus: 1,
            warehouseStatusName: "正常",
            prepareStuffStatus: 2,
            stockScheduleStatus: 2,
            stockProductStatus: 2,
            prepareSendStatus: 2,
            createAt: "2022-11-24T03:00:43.000+0000",
          },
          {
            supplierCode: "10002",
            supplierName: "浙江新光饰品股份有限公司",
            supplierWareHouseCode: "10002",
            supplierWareHouse: "浙江新光饰品股份有限公司",
            warehouseStatus: 1,
            warehouseStatusName: "正常",
            prepareStuffStatus: 2,
            stockScheduleStatus: 2,
            stockProductStatus: 2,
            prepareSendStatus: 2,
            createAt: "2022-11-23T07:23:32.000+0000",
          },
          {
            supplierCode: "10601",
            supplierName: "南通拓驰鞋业有限公司",
            supplierWareHouseCode: "10601",
            supplierWareHouse: "南通拓驰鞋业有限公司",
            warehouseStatus: 1,
            warehouseStatusName: "正常",
            prepareStuffStatus: 2,
            stockScheduleStatus: 2,
            stockProductStatus: 2,
            prepareSendStatus: 2,
            createAt: "2022-11-23T07:07:50.000+0000",
          },
        ],
        total: 108,
        size: 10,
        current: 1,
        pages: 11,
      };
      this.tableData = res.records;
      this.total = res.total;
    },
    getSupplierList() {
      const res = {
        records: [
          {
            supplierName: "温州智博印业有限公司",
            supplierCode: "10100",
          },
          {
            supplierName: "广州乐言文创实业有限公司",
            supplierCode: "10000",
          },
          {
            supplierName: "福州旭和电子有限公司",
            supplierCode: "10101",
          },
          {
            supplierName: "福建集成伞业有限公司",
            supplierCode: "10001",
          },
          {
            supplierName: "宁波开利控股集团股份有限公司",
            supplierCode: "10102",
          },
          {
            supplierName: "浙江新光饰品股份有限公司",
            supplierCode: "10002",
          },
          {
            supplierName: "福建时嘉贸易有限公司",
            supplierCode: "10103",
          },
          {
            supplierName: "汕头市怡佳塑胶制品有限公司",
            supplierCode: "10104",
          },
          {
            supplierName: "广州市盛兆日用品实业有限公司",
            supplierCode: "10003",
          },
          {
            supplierName: "昆山天源精工工业有限公司",
            supplierCode: "10105",
          },
          {
            supplierName: "浙江艳庄化妆品有限公司",
            supplierCode: "10004",
          },
          {
            supplierName: "义乌市亿利文体用品有限公司",
            supplierCode: "10106",
          },
          {
            supplierName: "广州莉来雅化妆品有限公司",
            supplierCode: "10005",
          },
          {
            supplierName: "广州市玛莉安化妆用品有限公司",
            supplierCode: "10006",
          },
          {
            supplierName: "深圳市华增科技有限公司",
            supplierCode: "10107",
          },
          {
            supplierName: "恩平市卓尔礼品实业有限公司",
            supplierCode: "10202",
          },
          {
            supplierName: "惠州市惠城区三千梳刷厂",
            supplierCode: "10007",
          },
          {
            supplierName: "佛山卡蛙科技股份有限公司",
            supplierCode: "10203",
          },
          {
            supplierName: "广州市贝奇皮具有限公司",
            supplierCode: "10008",
          },
          {
            supplierName: "广州市柏姿生物科技有限公司",
            supplierCode: "10108",
          },
        ],
        total: 3549,
        size: 20,
        current: 1,
        pages: 178,
      };
      this.supplierList = res.records;
    },
    getTime(time, patern = "yyyy-MM-dd hh:mm:ss") {
      if (time) {
        return dateFormat1(time, patern);
      }
      return "-";
    },
    changeStatus(patern, item) {
      console.log(patern);
    },
    changeWareHouseStatus(row) {
      console.log(row);
    },
    add() {
      this.supplierWarehouseVisible = true
    }
  },
  created() {
    this.getSupplierList();
  },
};
</script>

<style lang="scss" scoped>
.customFillet {
  display: flex;
  align-items: center;

  span {
    &:first-child {
      border-radius: 50%;
      width: 6px;
      height: 6px;
      margin-right: 4px;
    }
  }

  .blue {
    background-color: #656df6;
  }

  .grey {
    background-color: #cccccc;
  }
}

.prohibitclick {
  padding: 8px 0;
}
</style>