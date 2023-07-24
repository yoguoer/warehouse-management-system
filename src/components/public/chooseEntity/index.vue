<template>
  <el-select v-model="entity" placeholder="请选择主体" class="form_text" clearable :style="{ width: `${width}px` }">
    <el-option v-for="(item, index) in options" :key="index" :label="item.entityName"
      :value="item.entityCode"></el-option>
  </el-select>
</template>

<script>
import { entityList } from "@/api/data";

export default {
  name: 'chooseEntity',
  data() {
    return {
      options: [],
      entity: ""
    }
  },
  props: {
    // 从外部传进来下拉框的宽度。
    width: {
      type: String,
      default: '350'
    },
    entityCode: {
      type: String,
      default: ''
    }
  },
  watch: {},
  created() {
    this.getEntityList()
    this.entity = this.entityCode
  },
  computed: {
  },
  mounted() {
  },
  computed: {
    //选择结果
    result() {
      return this.entity
    }
  },
  methods: {
    getEntityList() {
      entityList().then((res) => {
        if (res.code === 200) {
          this.options = res.data;
        } else {
          console.log("error");
        }
      })
    }
  },
}
</script>

<style lang="scss" rel="stylesheet/scss" scoped>

</style>