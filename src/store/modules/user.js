const state = {
  userId:"",
  userName:"",
  userType:"",
  userBelong:"",
  userPhone:"",
  userCode:""
}

const actions = {
  saveUserId({commit}, userId) {
    commit('SET_USERID', userId)
  },
  saveUserName({commit}, userName) {
    commit('SET_USERNAME', userName)
  },
  saveUserCode({commit}, userCode) {
    commit('SET_USERCODE', userCode)
  },
  saveUserPhone({commit}, userPhone) {
    commit('SET_USERPHONE', userPhone)
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
  SET_USERCODE: (state, userCode) => {
    state.userCode = userCode
  },
  SET_USERPHONE: (state, userPhone) => {
    state.userPhone = userPhone
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
   getUserCode (state) {
    return state.userCode
   },
   getUserPhone (state) {
    return state.userPhone
   },
}

export default {
  getters,
  state,
  mutations,
  actions
}
