<template>
 
<div class="ui middle aligned center aligned grid login" style="height:100vh">
  <div class="column">
    <h2 class="ui teal image header">
      <div class="content">
        登录账户
      </div>
    </h2>
    <form class="ui large form" @submit="onSubmit" @reset="onReset">
      <div class="ui stacked segment">
        <div class="field">
          <div class="ui left icon input">
            <i class="user icon"></i>
            <input type="text" name="email" placeholder="账户" v-model="form.account">
          </div>
        </div>
        <div class="field">
          <div class="ui left icon input">
            <i class="lock icon"></i>
            <input type="password" name="password" placeholder="密码" v-model="help.password">
          </div>
        </div>
        <button type="submit" class="ui fluid large teal submit button">登录</button>
      </div>

      <div class="ui error message"></div>

    </form>

    <div class="ui message">
           已经有账号了?  <router-link :to="{name: 'register'}" class="ui basic primary left pointing label"> 注册一个</router-link>

    </div>
  </div>
</div>
</template>

<script>

import { auth } from '../../api/auth';
import { sha256 } from 'js-sha256';
export default {
  name: 'Auth',
  components: { },
  data(){
     return {
      msg: '',
      help: {
        password: process.env.VUE_APP_USER_PASSWORD,
      },
      form: {
        account: process.env.VUE_APP_USER_NAME,
        password: null,
        checked: [1, 1],
      },
    };
  },
    methods: {
    onSubmit(evt) {
      var _this=this
      evt.preventDefault();
      this.form.password = sha256(this.help.password);
      auth.login(this.form).then((res) => {
        window.localStorage.token=res.token
        _this.$store.commit('auth/login')

        console.log(this.$store.state)
      });
      console.log(this.form);
    },
    onReset(evt) {
      evt.preventDefault();
    },
  },
};
</script>

<style scoped>
   .column {
      max-width: 450px;
    }
</style>
