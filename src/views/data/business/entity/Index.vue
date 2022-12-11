<template>
  <div class="wrap-definition">
<!-- 供应商 -->
    <entityList ref="entityList"/>
  </div>
</template>

<script>
import entityList from '@/components/data/business/entity/entityList'

export default {
  name: 'entity',
   data() {
    return {
   }
  },
  props: {},
  methods: {

  },
  watch: {},
  created () {
  },
  computed: {
  },
  mounted () {
  },
  beforeDestroy () {
  },
  components: {
    entityList,
  }
}
</script>

<style lang="scss" rel="stylesheet/scss" scoped>
.wrap-definition {
  background: #fff;
}
</style>