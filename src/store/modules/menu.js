const state = {
  menu: [], // 所有菜单
  firstMenu: [], // 横向的一级菜单导航
  subMenu: [],  // 纵向子菜单导航
  selFirstMenu: [] // 选择的一级菜单
}

const actions = {
  saveMenu({commit}, menu) {
    commit('SET_MENU', menu)
  },
  saveFirstMenu({commit}, firstMenu) {
    commit('SET_FIRST_MENU', firstMenu)
  },
  saveSubMenu({commit}, subMenu) {
    commit('SET_SUB_MENU', subMenu)
  },
  saveSelFirstMenu({commit}, selFirstMenu) {
    commit('SET_SEL_FIRST_MENU', selFirstMenu)
  }
}

// 更新状态
const mutations = {
  SET_MENU: (state, menu) => {
    state.menu = menu
  },
  SET_FIRST_MENU: (state, firstMenu) => {
    state.firstMenu = firstMenu
  },
  SET_SUB_MENU: (state, subMenu) => {
    state.subMenu = subMenu
  },
  SET_SEL_FIRST_MENU: (state, selFirstMenu) => {
    state.selFirstMenu = selFirstMenu
  },
}

// 获取状态信息
const getters = {
  getMenu (state) {
    return state.menu
   },
   getFristMenu (state) {
    return state.firstMenu
   },
   getSubMenu (state) {
    return state.subMenu
   },
   getSelFirstMenu (state) {
    return state.selFirstMenu
   }
}

export default {
  getters,
  state,
  mutations,
  actions
}
