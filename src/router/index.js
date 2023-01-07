import Vue from "vue";
import VueRouter from "vue-router";
Vue.use(VueRouter);

const routes = [{
    path: "/",
    redirect: {
      name: 'login'
    },
    component: () => import("@/views/login"),
  },
  //*******************************登录页*******************************
  {
    path: "/login",
    name: "login",
    component: () => import("@/views/login"),
  },

  //*******************************仪表板*******************************
  {
    path: "/success",
    name: "success",
    component: () => import("@/views/dashboard"),
    redirect: {
      name: 'welcome'
    },
    children: [
      //系统超级管理员可见的页面
      {
        path: "/userManage",
        name: "userManage",
        component: () => import("@/views/system/Index.vue"),
      },
      //用户个人信息页
      {
        path: "/userHome",
        name: "userHome",
        component: () => import("@/views/userHome"),
      },
      //渐变页
      {
        path: "/welcome",
        name: "welcome",
        component: () => import("@/views/login/welcome.vue"),
      },
      //测试页
      {
        path: "/HelloWorld",
        name: "HelloWorld",
        component: () => import("@/components/HelloWorld"),
      },
      //预警信息页
      {
        path: "/alertInfo/:type",
        name: "alertInfo",
        component: () => import("@/views/login/alertInfo/Index.vue"),
      },


//*******************************资料模块*******************************
      //资料
      {
        path: '/data',
        name: 'data',
        redirect: {name: "customer"},
        component: () => import('@/views/data/Index.vue'),
        children: [

          //资料-业务实体-商品管理
          {
            path: '/data/goods',
            name: 'goods',
            component: () => import('@/views/data/goods/Index.vue')
          },

          //资料-仓库管理
          {
            path: '/data/inventory',
            name: 'inventory',
            component: () => import('@/views/data/inventory/Index.vue'),
            redirect: {
              name: 'inventory'
            },
            // ----------------Tabs里面------------------
            children: [
              //资料-仓库管理-库存
              {
                path: '/data/inventory',
                name: 'inventory',
                component: () => import('@/components/data/inventory/inventory/inventoryList.vue')
              },
              //资料-业务实体-库存-详情
              {
                path: '/data/inventory/detail',
                name: 'inventory-detail',
                component: () => import('@/components/data/inventory/inventory/inventoryDetail.vue'),
                children: [
                  //资料-业务实体-库存-详情-区域
                  {
                    path: '/data/inventory/:inventoryKey',
                    name: 'inventory-area',
                    component: () => import('@/components/data/inventory/area/areaList.vue')
                  },
                  //资料-业务实体-库存-详情-货位
                  {
                    path: '/data/inventory/:inventoryKey',
                    name: 'inventory-position',
                    component: () => import('@/components/data/inventory/position/positionList.vue')
                  },
                ]
              },

              //资料-仓库管理-区域
              {
                path: '/data/area',
                name: 'area',
                component: () => import('@/components/data/inventory/area/areaList.vue')
              },
              //资料-业务实体-区域-详情
              {
                path: '/data/area/detail',
                name: 'area-detail',
                component: () => import('@/components/data/inventory/area/areaDetail.vue'),
                children: [
                  //资料-业务实体-区域-详情-仓库
                  {
                    path: '/data/area/:inventoryDistrictkey',
                    name: 'area-inventory',
                    component: () => import('@/components/data/inventory/inventory/inventoryList.vue')
                  },
                  //资料-业务实体-区域-详情-货位
                  {
                    path: '/data/area/:inventoryDistrictkey',
                    name: 'area-position',
                    component: () => import('@/components/data/inventory/position/positionList.vue')
                  },
                ]
              },

              //资料-仓库管理-货位
              {
                path: '/data/position',
                name: 'position',
                component: () => import('@/components/data/inventory/position/positionList.vue')
              },
              //资料-业务实体-货位-详情
              {
                path: '/data/position/detail',
                name: 'position-detail',
                component: () => import('@/components/data/inventory/position/positionDetail.vue'),
                children: [
                  //资料-业务实体-货位-详情-仓库
                  {
                    path: '/data/position/:positionKey',
                    name: 'position-inventory',
                    component: () => import('@/components/data/inventory/inventory/inventoryList.vue')
                  },
                  //资料-业务实体-货位-详情-区域
                  {
                    path: '/data/position/:positionKey',
                    name: 'position-area',
                    component: () => import('@/components/data/inventory/area/areaList.vue')
                  },
                ]
              },
            ]
          },
          //资料-供应商
          {
            path: '/data/supply',
            name: 'supply',
            component: () => import('@/views/data/supply/Index.vue'),
            redirect: {
              name: 'supplyList'
            },
            // ----------------Tabs里面------------------
            children: [
              //资料-供应商-供应商
              {
                path: '/data/supplyList',
                name: 'supplyList',
                component: () => import('@/components/data/supply/supplyList.vue')
              },
            ]
          },

          //资料-客户
          {
            path: '/data/customer',
            name: 'customer',
            component: () => import('@/views/data/customer/Index.vue'),
            redirect: {
              name: 'customerList'
            },
            // ----------------Tabs里面------------------
            children: [
              //资料-客户-客户
              {
                path: '/data/customerList',
                name: 'customerList',
                component: () => import('@/components/data/customer/customer/customerList.vue')
              },
              //资料-客户-联系人
              {
                path: '/data/contactList',
                name: 'contactList',
                component: () => import('@/components/data/customer/contact/contactList.vue')
              },
            ]
          },
          //资料-客户-客户详情页
          {
            path: '/data/ccustomerList/detail',
            name: 'customer-detail',
            component: () => import('@/components/data/customer/customer/customerDetail.vue'),
            children: [
              //资料-客户-客户详情页-所属联系人
              {
                path: '/data/customerList/:customerKey',
                name: 'customer-contact',
                component: () => import('@/components/data/customer/contact/contactList.vue')
              },
            ]
          },


          //资料-业务实体
          {
            path: '/data/business',
            name: 'business',
            redirect: {
              name: "brand"
            },
            component: () => import('@/views/data/business/Index.vue'),
            children: [
              //资料-业务实体-业务主体
              {
                path: '/data/business/entity',
                name: 'entity',
                component: () => import('@/views/data/business/entity/Index.vue'),
              },
              //资料-业务实体-品牌
              {
                path: '/data/business/brand',
                name: 'brand',
                component: () => import('@/views/data/business/brand/Index.vue')
              },
              //资料-业务实体-车辆
              {
                path: '/data/business/car',
                name: 'car',
                component: () => import('@/views/data/business/car/Index.vue')
              },
            ]
          },

          //资料-财务账户
          {
            path: '/data/finance',
            name: 'finance',
            redirect: {
              name: "bank"
            },
            component: () => import('@/views/data/finance/Index.vue'),
            children: [
              //资料-账务实体-银行
              {
                path: '/data/finance/bank',
                name: 'bank',
                component: () => import('@/views/data/finance/bank/Index.vue')
              },
            ]
          },
          //资料-门店管理
          {
            path: '/data/shopList',
            name: 'shop',
            component: () => import('@/views/data/shop/Index.vue')
          },
        ]
      },


//*******************************销售模块*******************************
      //销售
      {
        path: '/marketing',
        name: 'marketing',
        redirect: {
          name: "marketingBusiness"
        },
        component: () => import('@/views/marketing/Index.vue')
      },
      //销售-销售业务
      {
        path: '/marketing/marketingBusiness',
        name: 'marketingBusiness',
        redirect: {
          name: "retailOrder"
        },
        component: () => import('@/views/marketing/marketingBusiness/Index.vue')
      },
      //销售-销售业务-零售单
      {
        path: '/marketing/marketingBusiness/retailOrder',
        name: 'retailOrder',
        component: () => import('@/components/marketing/marketingBusiness/retailOrder/retailOrderList.vue')
      },
      //销售-销售业务-销售订单
      {
        path: '/marketing/marketingBusiness/salesOrder',
        name: 'salesOrder',
        component: () => import('@/components/marketing/marketingBusiness/salesOrder/salesOrderList.vue')
      },
      //销售-销售查询
      {
        path: '/marketing/marketingQueries',
        name: 'marketingQueries',
        redirect: {
          name: "bills"
        },
        component: () => import('@/views/marketing/marketingQueries/Index.vue')
      },
      //销售-销售查询-销售单据查询
      {
        path: '/marketing/marketingQueries/bills',
        name: 'bills',
        component: () => import('@/views/marketing/marketingQueries/bills/Index.vue')
      },
      //销售-销售查询-销售明细查询
      {
        path: '/marketing/marketingQueries/details',
        name: 'details',
        component: () => import('@/views/marketing/marketingQueries/details/Index.vue')
      },


//*******************************采购模块*******************************
      //采购
      {
        path: '/purchasing',
        name: 'purchasing',
        redirect: {
          name: "purchasingBusiness"
        },
        component: () => import('@/views/purchasing/Index.vue')
      },
      //采购-采购业务
      {
        path: '/purchasing/purchasingBusiness',
        name: 'purchasingBusiness',
        redirect: {
          name: "purchasingOrder"
        },
        component: () => import('@/views/purchasing/purchasingBusiness/Index.vue')
      },
      //采购-采购业务-采购订单
      {
        path: '/purchasing/purchasingBusiness/purchasingOrder',
        name: 'purchasingOrder',
        component: () => import('@/components/purchasing/purchasingBusiness/purchasingOrder/purchasingOrderList.vue')
        
      },
      //采购-采购业务-回货管理
      {
        path: '/purchasing/purchasingBusiness/returnCargo',
        name: 'returnCargo',
        component: () => import('@/views/purchasing/purchasingBusiness/returnCargo/Index.vue')
      },
      //采购-采购业务-采购入库单
      {
        path: '/purchasing/purchasingBusiness/enterWarehouse',
        name: 'enterWarehouse',
        component: () => import('@/views/purchasing/purchasingBusiness/enterWarehouse/Index.vue')
      },
      //采购-采购查询
      {
        path: '/purchasing/purchasingQueries',
        name: 'purchasingQueries',
        redirect: {
          name: "bills"
        },
        component: () => import('@/views/purchasing/purchasingQueries/Index.vue')
      },
      //采购-采购查询-采购单据查询
      {
        path: '/purchasing/purchasingQueries/bills',
        name: 'bills',
        component: () => import('@/views/purchasing/purchasingQueries/bills/Index.vue')
      },
      //采购-采购查询-采购明细查询
      {
        path: '/purchasing/purchasingQueries/details',
        name: 'details',
        component: () => import('@/views/purchasing/purchasingQueries/details/Index.vue')
      },
      //采购-采购策略
      {
        path: '/purchasing/purchasingStrategy',
        name: 'purchasingStrategy',
        redirect: {
          name: "warning"
        },
        component: () => import('@/views/purchasing/purchasingStrategy/Index.vue')
      },
      //采购-采购策略-库存预警补货
      {
        path: '/purchasing/purchasingStrategy/warning',
        name: 'warning',
        component: () => import('@/views/purchasing/purchasingStrategy/warning/Index.vue')
      },
      //采购-采购策略-缺货补货
      {
        path: '/purchasing/purchasingStrategy/lack',
        name: 'lack',
        component: () => import('@/views/purchasing/purchasingStrategy/lack/Index.vue')
      },
      //采购-采购策略-智能补货
      {
        path: '/purchasing/purchasingStrategy/smart',
        name: 'smart',
        component: () => import('@/views/purchasing/purchasingStrategy/smart/Index.vue')
      },


//*******************************库存模块*******************************
      //库存
      {
        path: '/store',
        name: 'store',
        redirect: {
          name: "suppliers"
        },
        component: () => import('@/views/store/Index.vue')
      },

      //库存-供应商库存
      {
        path: '/store/suppliers',
        name: 'suppliers',
        redirect: {
          name: "configuration"
        },
        component: () => import('@/views/store/suppliers/Index.vue')
      },
      //库存-供应商库存-供应商库存管理
      {
        path: '/store/suppliers/stockManagement',
        name: 'stockManagement',
        component: () => import('@/views/store/suppliers/stockManagement/Index.vue')
      },
      //库存-供应商库存-供应商库存流水
      {
        path: '/store/suppliers/records',
        name: 'stockFlow',
        component: () => import('@/views/store/suppliers/stockFlow/Index.vue')
      },
      //库存-供应商库存-供应商仓库配置
      {
        path: '/store/suppliers/configuration',
        name: 'configuration',
        component: () => import('@/components/store/suppliers/configuration/configList.vue')
      },


      //库存-零售商库存    
      {
        path: '/store/shopkeepers',
        name: 'shopkeepers',
        redirect: {
          name: "configure"
        },
        component: () => import('@/views/store/shopkeepers/Index.vue')
      },
      //库存-零售商库存-库存总览  
      {
        path: '/store/shopkeepers/overview',
        name: 'overview',
        component: () => import('@/components/store/shopkeepers/overview/overviewList.vue')
      },
      //库存-零售商库存-在单库存  
      {
        path: '/store/shopkeepers/inOrder',
        name: 'inOrder',
        component: () => import('@/views/store/shopkeepers/inOrder/Index.vue')
      },
      //库存-零售商库存-仓库库存  
      {
        path: '/store/shopkeepers/warehouse',
        name: 'warehouse',
        component: () => import('@/views/store/shopkeepers/warehouse/Index.vue')
      },
      //库存-零售商库存-仓库配置 
      {
        path: '/store/shopkeepers/configure',
        name: 'configure',
        component: () => import('@/components/store/shopkeepers/configure/configList.vue')
      },


//*******************************配货模块*******************************
      //配货
      {
        path: '/distribution',
        name: 'distribution',
        redirect: {
          name: "demandManagement"
        },
        component: () => import('@/views/distribution/Index.vue')
      },
      //配货-需求管理
      {
        path: '/distribution/demandManagement',
        name: 'demandManagement',
        redirect: {
          name: "requirementList"
        },
        component: () => import('@/views/distribution/demandManagement/Index.vue')
      },
      //配货-需求管理-需求单
      {
        path: '/distribution/demandManagement/requirementList',
        name: 'requirementList',
        component: () => import('@/views/distribution/demandManagement/requirementList/Index.vue')
      },
      //配货-配货管理-任务清单
      {
        path: '/distribution/distributionManagement',
        name: 'distributionManagement',
        redirect: {
          name: "planLlist"
        },
        component: () => import('@/views/distribution/distributionManagement/Index.vue')
      },
      //配货-配货管理-建议单   
      {
        path: '/distribution/distributionManagement/adviceList',
        name: 'adviceList',
        component: () => import('@/views/distribution/distributionManagement/adviceList/Index.vue')
      },
      //配货-配货管理-计划单
      {
        path: '/distribution/distributionManagement/planLlist',
        name: 'planLlist',
        component: () => import('@/views/distribution/distributionManagement/planLlist/Index.vue')
      },
      //配货-配货管理-任务清单
      {
        path: '/distribution/distributionManagement/taskLlist',
        name: 'taskLlist',
        component: () => import('@/views/distribution/distributionManagement/taskLlist/Index.vue')
      },
      //配货-门店配货
      {
        path: '/distribution/outletDistribution',
        name: 'outletDistribution',
        component: () => import('@/views/distribution/outletDistribution/Index.vue')
      },
    ]
  },
  {
    path: "/404",
    name: '404',
    component: () => import("@/views/error/404"),
  },
  {
    path: "/401",
    name: '401',
    component: () => import("@/views/error/401"),
  },
]

// 解决导航栏或者底部导航tabBar中的vue-router在3.0版本以上频繁点击菜单报错的问题。
const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch((err) => err);
};

const router = new VueRouter({
  mode: "history",
  base: '/iscm',
  routes,
});

// const router = new VueRouter({
//   mode: "history",
//   base: process.env.BASE_URL,
//   routes,
// });

router.beforeEach((to, from, next) => {
  if (to.path === '/login') {
    next()
  }
  if (to.path === '/') {
    next('/404')
  } else {
    next()
  }
})

export default router;