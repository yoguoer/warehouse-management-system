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

      //*******************************数据集成模块*******************************
      //数据集成
      {
        path: '/dataIntegrate',
        name: 'dataIntegrate',
        redirect: {
          name: "salesIntegrate"
        },
        component: () => import('@/views/dataIntegrate/Index.vue'),
        children: [
          //数据集成-销售统计
          {
            path: '/dataIntegrate/salesIntegrate',
            name: 'salesIntegrate',
            redirect: {
              name: "salesIntegrateListS"
            },
            component: () => import('@/views/dataIntegrate/salesIntegrate/Index.vue'),
            children: [
              //数据集成-销售统计-门店
              {
                path: '/salesIntegrate/salesIntegrateListS',
                name: 'salesIntegrateListS',
                component: () => import('@/components/dataIntegrate/salesIntegrate/salesIntegrateListS.vue')
              },
              //数据集成-销售统计-商品
              {
                path: '/salesIntegrate/salesIntegrateList',
                name: 'salesIntegrateListG',
                component: () => import('@/components/dataIntegrate/salesIntegrate/salesIntegrateListG.vue')
              },
            ]
          },
          //数据集成-采购统计
          {
            path: '/dataIntegrate/purchaseIntegrate',
            name: 'purchaseIntegrate',
            redirect: {
              name: "purchaseIntegrateListS"
            },
            component: () => import('@/views/dataIntegrate/purchaseIntegrate/Index.vue'),
            children: [
              //数据集成-采购统计-门店
              {
                path: '/purchaseIntegrateList/purchaseIntegrateListS',
                name: 'purchaseIntegrateListS',
                component: () => import('@/components/dataIntegrate/purchaseIntegrate/purchaseIntegrateListS.vue')
              },
              //数据集成-采购统计-商品
              {
                path: '/purchaseIntegrateList/salesIntegrateListG',
                name: 'purchaseIntegrateListG',
                component: () => import('@/components/dataIntegrate/purchaseIntegrate/purchaseIntegrateListG.vue')
              },
            ]
          },
          //数据集成-库存盘点
          {
            path: '/dataIntegrate/inventoryIntegrate',
            name: 'inventoryIntegrate',
            redirect: {
              name: "countManagement"
            },
            component: () => import('@/views/dataIntegrate/inventoryIntegrate/Index.vue'),
            children: [
              //数据集成-库存盘点-盘点
              {
                path: '/inventoryIntegrate/countManagement',
                name: 'countManagement',
                component: () => import('@/components/dataIntegrate/inventoryIntegrate/countManagement/countManagementList.vue')
              },
              //数据集成-库存盘点-审批
              {
                path: '/inventoryIntegrate/countCheck',
                name: 'countCheck',
                component: () => import('@/components/dataIntegrate/inventoryIntegrate/countCheck/countCheckList.vue')
              },
            ]
          },
          //数据集成-客户评估
          {
            path: '/dataIntegrate/customerIntegrate',
            name: 'customerIntegrate',
            redirect: {
              name: "customerIntegrateList"
            },
            component: () => import('@/views/dataIntegrate/customerIntegrate/Index.vue'),
            children: [
              //数据集成-客户评估
              {
                path: '/dataIntegrate/customerIntegrateList',
                name: 'customerIntegrateList',
                component: () => import('@/components/dataIntegrate/customerIntegrate/customerIntegrateList.vue')
              },
            ]
          },
          //数据集成-供应商评估
          {
            path: '/dataIntegrate/supplierIntegrate',
            name: 'supplierIntegrate',
            redirect: {
              name: "supplierIntegrateList"
            },
            component: () => import('@/views/dataIntegrate/supplierIntegrate/Index.vue'),
            children: [
              //数据集成-供应商评估
              {
                path: '/dataIntegrate/supplierIntegrateList',
                name: 'supplierIntegrateList',
                component: () => import('@/components/dataIntegrate/supplierIntegrate/supplierIntegrateList.vue')
              },
            ]
          }
        ],
      },
      //*******************************资料模块*******************************
      //资料
      {
        path: '/data',
        name: 'data',
        redirect: {
          name: "customer"
        },
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
      //销售-销售业务-销售订单-零售单
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
      //销售-销售业务-销售退货单
      {
        path: '/marketing/marketingBusiness/MreturnOrder',
        name: 'MreturnOrder',
        component: () => import('@/components/marketing/marketingBusiness/MreturnOrder/MreturnOrderList.vue')
      },
      //销售-销售业务-发货管理
      {
        path: '/marketing/marketingBusiness/outputOrder',
        name: 'outputOrder',
        component: () => import('@/components/marketing/marketingBusiness/outputOrder/outputOrderList.vue')
      },
      //销售-调货出库单
      {
        path: '/marketing/transferOutput',
        name: 'transferOutput',
        component: () => import('@/views/marketing/transferOutput/Index.vue'),
        redirect: {
          name: "MonCheck"
        },
        children: [
          //销售-调货出库单-审批
          {
            path: '/marketing/transferOutput/MonCheck',
            name: 'MonCheck',
            component: () => import('@/components/marketing/transferOutput/onCheck/onCheckList.vue')
          },
          //销售-调货出库单-已审批
          {
            path: '/marketing/transferOutput/MhasCheck',
            name: 'MhasCheck',
            component: () => import('@/components/marketing/transferOutput/hasCheck/hasCheckList.vue')
          },
        ]
      },
      //销售-销售查询
      {
        path: '/marketing/marketingQueries',
        name: 'marketingQueries',
        redirect: {
          name: "Mbills"
        },
        component: () => import('@/views/marketing/marketingQueries/Index.vue')
      },
      //销售-销售查询-销售单据查询
      {
        path: '/marketing/marketingQueries/bills',
        name: 'Mbills',
        component: () => import('@/components/marketing/marketingQueries/bills/billsList.vue')
      },
      //销售-销售查询-销售明细查询
      {
        path: '/marketing/marketingQueries/details',
        name: 'Mdetails',
        component: () => import('@/components/marketing/marketingQueries/details/detailsList.vue')
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
      //采购-采购业务-采购退货单
      {
        path: '/purchasing/purchasingBusiness/PreturnOrder',
        name: 'PreturnOrder',
        component: () => import('@/components/purchasing/purchasingBusiness/PreturnOrder/PreturnOrderList.vue')

      },
      //采购-采购业务-回货管理
      {
        path: '/purchasing/purchasingBusiness/returnCargo',
        name: 'returnCargo',
        component: () => import('@/components/purchasing/purchasingBusiness/returnCargo/returnCargoList.vue')
      },
      //采购-采购业务-采购入库单
      {
        path: '/purchasing/purchasingBusiness/enterWarehouse',
        name: 'enterWarehouse',
        component: () => import('@/components/purchasing/purchasingBusiness/enterWarehouse/enterWarehouseList.vue')
      },
      //采购-调货入库单
      {
        path: '/purchasing/transferInput',
        name: 'transferInput',
        component: () => import('@/views/purchasing/transferInput/Index.vue'),
        redirect: {
          name: "PonCheck"
        },
        children: [
          //采购-调货入库单-审批
          {
            path: '/marketing/transferInput/onCheck',
            name: 'PonCheck',
            component: () => import('@/components/purchasing/transferInput/onCheck/onCheckList.vue')
          },
          //采购-调货入库单-已审批
          {
            path: '/marketing/transferInput/hasCheck',
            name: 'PhasCheck',
            component: () => import('@/components/purchasing/transferInput/hasCheck/hasCheckList.vue')
          },
        ]
      },
      //采购-采购查询
      {
        path: '/purchasing/purchasingQueries',
        name: 'purchasingQueries',
        redirect: {
          name: "Pbills"
        },
        component: () => import('@/views/purchasing/purchasingQueries/Index.vue')
      },
      //采购-采购查询-采购单据查询
      {
        path: '/purchasing/purchasingQueries/bills',
        name: 'Pbills',
        component: () => import('@/components/purchasing/purchasingQueries/bills/billsList.vue')
      },
      //采购-采购查询-采购明细查询
      {
        path: '/purchasing/purchasingQueries/details',
        name: 'Pdetails',
        component: () => import('@/components/purchasing/purchasingQueries/details/detailsList.vue')
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
        component: () => import('@/components/purchasing/purchasingStrategy/lack/lackList.vue')
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
        component: () => import('@/views/store/suppliers/stockManagement/Index.vue'),
        redirect: {
          name: "sInOrder"
        },
        children: [
          //库存-供应商库存-供应商库存管理-在单  
          {
            path: '/store/stockManagement/sInOrder',
            name: 'sInOrder',
            component: () => import('@/components/store/suppliers/stockManagement/sInOrder/sInOrderList.vue')
          },
          //库存-供应商库存-供应商库存管理-退货
          {
            path: '/store/stockManagement/sReturn',
            name: 'sReturn',
            component: () => import('@/components/store/suppliers/stockManagement/sReturn/sReturnList.vue')
          },
          //库存-供应商库存-供应商库存管理-已发货
          {
            path: '/store/stockManagement/sOutput',
            name: 'sOutput',
            component: () => import('@/components/store/suppliers/stockManagement/sOutput/sOutputList.vue')
          },
        ]
      },
      //库存-供应商库存-供应商库存流水
      {
        path: '/store/suppliers/stockFlow',
        name: 'stockFlow',
        component: () => import('@/components/store/suppliers/stockFlow/stockFlowList.vue')
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
        component: () => import('@/views/store/shopkeepers/inOrder/Index.vue'),
        redirect: {
          name: "sales"
        },
        children: [
          //库存-零售商库存-在单库存-销售  
          {
            path: '/store/inOrder/sales',
            name: 'sales',
            component: () => import('@/components/store/shopkeepers/inOrders/sales/salesList.vue')
          },
          //库存-零售商库存-在单库存-采购  
          {
            path: '/store/inOrder/purchase',
            name: 'purchase',
            component: () => import('@/components/store/shopkeepers/inOrders/purchase/purchaseList.vue')
          },
        ]
      },
      //库存-零售商库存-仓库库存  
      {
        path: '/store/shopkeepers/warehouse',
        name: 'warehouse',
        component: () => import('@/components/store/shopkeepers/warehouse/warehouseList.vue')
      },
      //库存-零售商库存-仓库配置 
      {
        path: '/store/shopkeepers/configure',
        name: 'configure',
        component: () => import('@/components/store/shopkeepers/configure/configList.vue')
      }
    ]
  },
  {
    path: "/404",
    name: '404',
    component: () => import("@/views/error/404"),
  }, {
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