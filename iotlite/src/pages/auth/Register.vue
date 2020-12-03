<template>
 <div>

<div class="ui middle aligned center aligned grid login" style="height:100vh">


  <div class="column">
    <h2 class="ui teal image header">
      <div class="content">
        注册
      </div>
    </h2>
    <form class="ui large form" @submit="onSubmit" @reset="onReset">
      <div class="ui stacked segment">
        <div class="field">
          <div class="ui left icon input">
            <i class="user icon"></i>
            <input type="text" name="email" placeholder="账号" v-model="form.account">
          </div>
        </div>
        <div class="field">
          <div class="ui left icon input">
            <i class="lock icon"></i>
            <input type="password" name="password" placeholder="密码" v-model="form.password">
          </div>
        </div>
        <div class="field">
          <div class="ui left icon input">
            <i class="mail icon"></i>
            <input type="email" name="email" placeholder="邮箱" v-model="form.email">
          </div>
        </div>
         <div class="field">
          <div class="ui left icon input">
            <i class="phone icon"></i>
            <input type="phone" name="phone" placeholder="手机号" v-model="form.phone">
          </div>
        </div>
        <button type="submit" class="ui fluid large teal submit button">注册</button>
      </div>

      <div class="ui error message"></div>

    </form>

    <div class="ui message">
      已经有账号了?  <router-link :to="{name: 'login'}" class="ui basic primary left pointing label">  去登录</router-link>
    </div>
  </div>
</div>

 </div>
</template>

<script>

import { auth } from '../../api/auth';
import { sha256 } from 'js-sha256';
export default {
  name: 'Register',
  components: {  },
  data(){
     return {
      open:true,
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
      auth.register(this.form).then((res) => {
        if(res.code==0){
          console.log("注册成功")
        }else{
          console.log("注册失败")
        }
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
