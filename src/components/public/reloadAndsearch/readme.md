# pagination页脚组件
## 说明和注意事项
1. 点击筛选按钮时，会触发search事件。事件中会带着筛选条件已填写的数据

## 属性说明
> vxeTable.vue

|  属性   | 类型  |requried|  备注  |
|  ----  | ----  | ---- |  ---- |
| config | Array<Object> |  是  | 设置查询条件  |
| showNum | number |  否  | 设置多查询条件多时的未展开是的条件数  |

## config元素说明
|  名称   | 类型  |required|  备注  |
|  ----  | ----  | ---- |  ---- |
|  label  | string  | 是 |  查询条件的显示名称 |
|  field  | string  | 是 |  查询条件的字段 |
|  type  | string  | 是 |  输入框的类型（select、input、number、radio、date、textarea） |
|  placeholder  | string  | 否 |  输入框的提示语 |
|  value  | string  | 否 |  输入框的初始化内容 |

## select
|  名称   | 类型  |required|  备注  |
|  ----  | ----  | ---- |  ---- |
|  label   | string  |required|  select选项的名称  |
|  value   | string  |required|  select选项的名段  |

## radio
|  名称   | 类型  |required|  备注  |
|  ----  | ----  | ---- |  ---- |
|  label   | string  |required|  radio选项的字段  |
|  value   | string  |required|  radio选项的名称  |

## search事件
|  名称   | 类型  |描述|  备注  |
|  ----  | ----  | ---- |  ---- |
|  search  | Object  | 事件中包含搜索条件的数据，是由config数组中的filed字段组成的对象 |  ---- |

## 示例

```vue
<template>
 <search ref="search" :config="searchConfig" @search="getList"></search>
</template>
<script>
import search from '@/components/search/search.vue';
export default {

  methods: {
    getList (data = {}, pageNum = 1, pageSize = 10) {
      // 获取筛选条件填写的数据
      const searchData = this.$refs.search.search;
    }
  }
}
</script>
```