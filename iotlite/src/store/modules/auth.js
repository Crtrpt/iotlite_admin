// initial state
var state = {
  isLogin:false||(window.localStorage.token==undefined?false:true),
  username:window.localStorage.username
};

// getters
var getters = {

};

// actions
var actions = {
};

// mutations
var mutations = {
  login(state){
    state.isLogin=true
  },
  logout(state){
    state.isLogin=false
  }
};

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}