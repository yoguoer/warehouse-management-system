<template>
  <div>
    <!-- 顶部 -->
    <div style="padding:10px;">
      <!-- <el-button type="primary" plain size="small" icon="el-icon-refresh" @click="reload">刷新</el-button> -->
      <!-- <el-button type="primary" plain size="small" icon="el-icon-plus" @click="add">新增</el-button> -->
      <!-- <el-divider/> -->
    </div>
    <!-- 搜索 -->
    <section class="search-area">
      <el-form :model="searchDouble" ref="searchForm" size="small">
        <el-row v-show="config.length" :gutter="20" v-for="(col, idx) in searchConfig" :key="idx"
          :class="idx == 0 ? '' : 'down-row'">
          <template v-for="(el, index) in col">
            <el-col :span="el.span || 4.5" :key="el.field || index"
              v-if="!(isSearchRows && !isShowMore && idx == 0 && index == cuttingNum - 1)" class="form-item"
              :style="{ display: (ifHidden ? 'none' : 'inline') }">
              <template v-if="el.slot">
                <slot :name="el.slot" :props="{
                  key: el.field,
                  label: el.label,
                  type: el.type,
                  attr: el,
                  data: searchDouble
                }"></slot>
              </template>
              <template v-else>
                <!-- <el-form-item :label="el.label" :prop="el.field"> -->
                <template v-if="el.type == 'value'">
                  <el-input :placeholder="el.placeholder || '请输入'" v-bind="el.attr" v-on="el.event"
                    :value="el.field ? searchDouble[el.field] : el.value" disabled>
                  </el-input>
                </template>
                <template v-else-if="el.type == 'cascader'">
                  <el-cascader :options="el.options" :show-all-levels="false" clearable :ref="'cascaders' + index"
                    v-model="searchDouble[el.field]" filterable :filter-method="filterMethods"
                    :placeholder="el.placeholder || '请选择'" @change="
                      () => treeHandleChange('cascaders' + index, el.field)
                    " v-bind="el.attr" :props="el.props">
                  </el-cascader>
                </template>
                <template v-else-if="el.type == 'rangeInpNum'">
                  <div style="display: inline-block">
                    <el-input-number v-model="searchDouble[el.field]" controls-position="right"
                      style="width: calc(50% - 16px)" @change="rangeInpNumChange(el)" :min="0" :max="999999"
                      :placeholder="el.label">
                    </el-input-number>
                    <span style="margin: 0 10px">~</span>
                    <el-input-number v-model="searchDouble[el.field2]" controls-position="right"
                      style="width: calc(50% - 16px)" :min="searchDouble[el.field]" :max="999999"
                      :placeholder="el.label">
                    </el-input-number>
                  </div>
                </template>
                <template v-else-if="el.type == 'select'">
                  <el-select :placeholder="el.placeholder || '请输入'" v-bind="el.attr" v-model="searchDouble[el.field]"
                    clearable :multiple-limit="el.multipleLimit ? el.multipleLimit : 20" :multiple="el.multiple"
                    :filterable="el.filterable === false ? el.filterable : true" remote :remote-method="
                      el.callbacks ? el.callbacks.remoteMethod || null : null
                    " :ref="el.field" reserve-keyword v-on="el.event" @change="selectChange(el)">
                    <el-option v-for="item in el.options" :label="
                      item.hasOwnProperty('label')
                        ? item.label
                        : item.name || item[el.option.label]
                    " :key="item.value || (el.option && item[el.option.value])"
                      :value="item.hasOwnProperty('value') ? item.value : item.code || item[el.option.value]">
                      <span style="float: left" v-if="el.showCode">
                        [{{ item.value || item[el.option.value] }}]{{
                            item.label || item[el.option.label]
                        }}
                      </span>
                    </el-option>
                  </el-select>
                </template>
                <template v-else-if="el.type == 'number'">
                  <el-input-number v-model="searchDouble[el.field]" :controls="false"
                    :placeholder="el.placeholder || '请输入'" v-bind="el.attr" :max="el.max || 9999999999" v-on="el.event"
                    clearable></el-input-number>
                </template>
                <template v-else-if="el.type == 'radio'">
                  <el-radio-group v-model="searchDouble[el.field]" v-bind="el.attr" v-on="el.event">
                    <el-radio :label="item.value" v-for="(item, index) in el.options" :key="index">{{ item.label }}
                    </el-radio>
                  </el-radio-group>
                </template>
                <template v-else-if="el.type == 'date'">
                  <div style="display: flex; align-items: center;">
                    <div v-if="el.tag" class="form-item-tag">{{ el.tag }}</div>
                    <el-date-picker v-model="searchDouble[el.field]" :type="el.datePickerType || 'date'" clearable
                      :start-placeholder="el.startPlaceholder || '开始时间'" :end-placeholder="el.endPlaceholder || '结束时间'"
                      v-bind="el.attr" v-on="el.event" size="medium"></el-date-picker>
                  </div>
                </template>
                <template v-else-if="el.type == 'textarea'">
                  <el-input :placeholder="el.placeholder || '请输入'" v-bind="el.attr" v-on="el.event"
                    v-model="searchDouble[el.field]" :maxlength="el.maxlength || 500" type="textarea"></el-input>
                </template>
                <template v-else>
                  <el-input :placeholder="el.placeholder || '请输入'" v-bind="el.attr" v-on="el.event" clearable
                    v-model.trim="searchDouble[el.field]" :maxlength="el.maxlength || 50">
                    <template slot="prepend">{{ el.label }}</template>
                  </el-input>
                </template>
                <!-- </el-form-item> -->
              </template>
            </el-col>
          </template>
          <template v-if="((idx == searchConfig.length - 1))">
            <el-col :span="6" :class="(col.length == cuttingNum && idx != 0 ? 'down-row' : '')">
              <div class="button-box">
                <el-button :loading="loadingTable" type="primary" size="small" icon="el-icon-search"
                  @click="searchForm()">
                  搜索
                </el-button>
                <el-button @click="resetForm()" size="small" icon="el-icon-sort" type="warning">重置</el-button>
                <el-button type="success" size="small" icon="el-icon-plus" @click="add()"
                  :style="{ display: hidden ? 'none' : 'inline' }">新增</el-button>
                <el-button class="el-icon-delete" type="danger" size="small" @click="handleDeleteList()" :style="{ display: hidden1 ? 'none' : 'inline' }">删除</el-button>
                <el-button type="text" @click="moreFilter" size="small" v-if="isSearchRows">{{ isShowMore ? '收起' : '展开'
                }}查询</el-button>
                <!-- <el-button type="danger" size="small" icon="el-icon-refresh" @click="reload">刷新</el-button> -->
              </div>
            </el-col>
          </template>
        </el-row>
        <el-divider />
      </el-form>
    </section>
  </div>
</template>

<script>
// import minisoCascader from '../minisoCascader';
import { mapState, mapGetters, mapMutations } from 'vuex';
export default {
  name: 'MinisoSearch',
  props: {
    query: {
      // 默认初始化翻页数据
      type: Object,
      default() {
        return {
          pageNo: 1,
          pageSize: 10
        };
      }
    },
    config: {
      type: Array,
      default: () => []
    },
    showNum: {
      type: Number,
      default: 4
    },
    ifHidden: {
      //是否隐藏搜索区域（默认不隐藏）
      type: Boolean,
      default: false
    },
    isCache: {
      // 搜索缓存
      type: Boolean,
      default: false
    },
    hidden: {
      // 是否隐藏新增按钮
      type: Boolean,
      default: false
    },
    hidden1: {
      // 是否隐藏删除按钮
      type: Boolean,
      default: false
    },
    cacheName: {
      // 搜索缓存
      type: String,
      default: ''
    },
    cuttingNum: {
      // 切割多维以一行几个为标准
      type: Number,
      default: 4
    },
    cacheTablePage: {
      // 缓存表格的页脚信息
      type: Object,
      default() {
        return {};
      }
    },
    getNextFunction: Function, // 联动调用的下一个方法
  },
  data() {
    return {
      searchDouble: {},
      firstConfig: [],
      moreConfig: [],
      searchConfig: [],
      isShowMore: false,
      search: {},
    };
  },
  computed: {
    ...mapState(['loadingTable']),
    ...mapGetters(['getSearchQuery']),
    isSearchRows() {
      return (
        this.config.reduce((preVal, currVal) => {
          return (currVal.span || 6) + preVal;
        }, 0) > 24
      );
    }
  },
  watch: {
    config: {
      handler(v) {
        const status = !this.isShowMore;

        this.firstConfig = this.splitArr(v.slice(0, this.showNum));
        //
        this.moreConfig = this.splitArr(v);
        if (status) {
          this.searchConfig = this.firstConfig;
        } else {
          this.searchConfig = this.moreConfig;
        }
      },
      deep: true
    },
    searchDouble: {
      handler() {
        this.$emit('change');
      },
      deep: true
    }
  },
  created() {
    this.firstConfig = this.splitArr(this.config.slice(0, this.showNum));
    //
    this.moreConfig = this.splitArr(this.config);
    this.searchConfig = this.firstConfig;

    //

    this.initSearchData();
  },
  mounted() {
    //
    this.initCaches();
    // this.$once('starSearch', this.searchForm);
    // 初始化自动初始化
    this.config.map((res) => {
      if (res.type === 'select' && res.callbacks?.remoteMethod) {
        res.callbacks.remoteMethod('');
      }
    });
  },
  methods: {
    // ...mapMutations(['setSearchQuery']),
    treeHandleChange(ref, key) {
      this.$nextTick(() => {
        const nodesInfo = this.$refs[ref][0].getCheckedNodes();

        if (nodesInfo.length) {
          this.searchDouble['new' + key] = nodesInfo
            .filter((item) => item.checked)
            .map((item) => {
              return { level: item.level, value: item.value };
            });
        } else {
          this.searchDouble['new' + key] = null;
        }
      });
    },
    rangeInpNumChange(el) {
      if (
        this.searchDouble[el.field2] &&
        this.searchDouble[el.field2] < this.searchDouble[el.field]
      ) {
        this.searchDouble[el.field2] = this.searchDouble[el.field];
      }
    },
    filterMethods(node, key) {
      return node.label.indexOf(key) > -1 || node.value.indexOf(key) > -1;
    },
    splitArr(data) {
      const arr = [];

      for (let i = 0; i < data.length; i += this.cuttingNum) {
        arr.push(data.slice(i, i + this.cuttingNum));
      }
      return arr;
    },
    initSearchData() {
      const length = this.config.length;

      for (let i = 0; i < length; i++) {
        const item = this.config[i];

        if (item.value || item.value === 0) {
          this.$set(this.search, item.field, item.value);
        }
      }
    },
    searchForm() {
      // 防止分页实时读取搜索框内容
      this.search = JSON.parse(JSON.stringify(this.searchDouble));
      // cache 搜索条件到路径当中
      let { pageNo = 1, pageSize = 10 } = this.searchDouble;

      if (this.isCache) {
        this.cachesQuery();
        // pageNo = pageNo ? pageNo : 1;
        // pageSize = pageSize ? pageSize : 10;
      } else {
        pageNo = this.query.pageNo;
        pageSize = this.query.pageSize;
      }

      console.log('search', pageNo, pageSize);
      this.$emit('search', pageNo, pageSize);
    },
    // 读取query
    initCaches() {
      if (!this.isCache) {
        this.searchForm();
        return;
      }
      const searchQuery =
        this.getSearchQuery[this.cacheName || this.$route.path];

      if (searchQuery) {
        this.isShowMore = searchQuery.isShowMore;
        this.$set(this.searchDouble, 'pageNo', searchQuery.pageNo || 1);
        this.$set(this.searchDouble, 'pageSize', searchQuery.pageSize || 10);
        this.config.forEach((conf) => {
          if (searchQuery[conf.field]) {
            if (conf.type === 'select' && conf.multiple) {
              conf.options = searchQuery[conf.field + 'Opt'];
            }
          }
          if (conf.field2) {
            this.$set(this.searchDouble, conf.field2, searchQuery[conf.field2]);
          }
          this.$set(this.searchDouble, conf.field, searchQuery[conf.field]);
        });
      }
      this.searchForm();
    },
    // 保存至query
    cachesQuery() {
      if (!this.isCache) {
        return;
      }
      const cacheList = {},
        // query = JSON.parse(JSON.stringify(this.$route.query)),
        search = JSON.parse(JSON.stringify(this.search));

      this.config.forEach((conf) => {
        const ref = this.$refs[conf.field];

        // 有远程搜索的情况下
        if (conf.type === 'select' && ref?.length) {
          const list = [],
            selects = ref[0].selected;

          // 多选与单选的结果
          if (selects.length) {
            selects.forEach((selected) => {
              list.push({
                [conf.option?.label || 'label']: selected.label,
                [conf.option?.value || 'value']: selected.value
              });
            });
          }
          if (list.length) {
            // object路径显示存在问题，因此转为字符串
            cacheList[conf.field + 'Opt'] = list;
          }
        }
        if (search[conf.field] === '[]') {
          delete search[conf.field];
        }
      });
      // // 存储到vuex
      // this.setSearchQuery({
      //   key: this.cacheName || this.$route.path,
      //   value: {
      //     // ...query,
      //     ...cacheList,
      //     ...search,
      //     // ...this.cacheTablePage,
      //     isShowMore: this.isShowMore
      //   }
      // });
    },
    resetForm() {
      const searchDouble = JSON.parse(JSON.stringify(this.searchDouble)),
        keys = Object.keys(searchDouble),
        length = keys.length;

      for (let i = 0; i < length; i++) {
        const current = keys[i];

        searchDouble[current] = this.initValue(searchDouble[current]);
      }

      searchDouble.pageNo = 1;
      searchDouble.pageSize = 10;
      this.search = this.searchDouble = searchDouble;

      // this.searchDouble = {};
      // this.setSearchQuery({
      //   key: this.cacheName || this.$route.path,
      //   value: {
      //     ...searchDouble,
      //     isShowMore: this.isShowMore
      //   }
      // });
      //如果是有分类leftCard的
      if (this.$parent.inputCategory != "" & this.$parent.inputCategory != null) {
        this.$parent.cleanInput()
      }
      this.$parent.getTableData()
    },
    initValue(value) {
      if (Array.isArray(value)) {
        return [];
      }
      return '';
    },
    moreFilter() {
      const status = !this.isShowMore;

      if (status) {
        this.searchConfig = this.moreConfig;
      } else {
        this.searchConfig = this.firstConfig;
      }

      this.isShowMore = status;
    },
    selectChange(el) {
      if (el.codeName) {
        const code = this.search[el.field];
        if (code) {
          const item = el.options.find((op) => {
            return op.value === code;
          });
          this.search[el.codeName] = (item || {}).label;
        } else {
          this.search[el.codeName] = '';
        }
      }
      // 为了能够取出联动方法的参数
      this.search = JSON.parse(JSON.stringify(this.searchDouble));
      //如果选择后有联动的方法调用，则需要再调用下一个方法
      this.getNextFunction && this.getNextFunction()

    },
    // reload(){
    //   this.$parent.reload()
    // },
    add() {
      this.$parent.add()
    },
    handleDeleteList() {
      this.$parent.handleDeleteList()
    },
  }
  //   components: { minisoCascader }
};
</script>

<style lang="scss" scoped>
.search-area {
  padding: 0 10px;
}

.form-item {
  // padding-right: 50px;

  .form-item-tag {
    color: #606266;
    font-size: 14px;
    white-space: nowrap;
    padding-right: 15px;
  }

  /deep/ .el-select,
  .el-cascader {
    width: 100%;
  }

  /deep/ .el-input-number {
    width: 100%;

    .el-input__inner {
      text-align: left;
    }
  }

  /deep/ .el-date-editor {
    width: 100%;
  }

  .el-range-editor--small.el-input__inner {
    margin-top: 1px;
  }
}

.el-autocomplete {
  width: 100%;
}

.down-row {
  margin-top: 15px;
}

.button-box {
  height: 36px;
  display: flex;
  align-items: center;
}
</style>
  