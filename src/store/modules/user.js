const state = {
  userId:"",
  userName:"",
  userType:"",
  userBelong:"",
}

const actions = {
  saveUserId({commit}, userId) {
    commit('SET_USERID', userId)
  },
  saveUserName({commit}, userName) {
    commit('SET_USERNAME', userName)
  },
  saveUserType({commit}, userType) {
    commit('SET_USERTYPE', userType)
  },
  saveUserbelong({commit}, userBelong) {
    commit('SET_USERBELONG', userBelong)
  }
}

// 更新状态
const mutations = {
  SET_USERID: (state, userId) => {
    state.userId = userId
  },
  SET_USERNAME: (state, userName) => {
    state.userName = userName
  },
  SET_USERTYPE: (state, userType) => {
    state.userType = userType
  },
  SET_USERBELONG: (state, userBelong) => {
    state.userBelong = userBelong
  },
}

// 获取状态信息
const getters = {
  getUserId (state) {
    return state.userId
   },
   getUserName (state) {
    return state.userName
   },
   getUserType (state) {
    return state.userType
   },
   getUserBelong (state) {
    return state.userBelong
   },
}

export default {
  getters,
  state,
  mutations,
  actions
}
