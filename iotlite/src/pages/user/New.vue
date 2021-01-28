<template>
  <div>
    <b-form @submit="onSubmit" >
      <b-form-group
        label="名称:"
        description="用户名称"
      >
        <b-form-input
          v-model="form.name"
          type="text"
          required
          placeholder="输入用户名称"
        ></b-form-input>
      </b-form-group>
       <b-form-group
        label="账户:"
        description="账户"
      >
        <b-form-input
          v-model="form.account"
          type="text"
          required
          placeholder="输入登录账户"
        ></b-form-input>
      </b-form-group>

      <b-form-group
        label="邮箱:"
        description="邮箱"
      >
        <b-form-input
          v-model="form.email"
          type="text"
          placeholder="输入用户邮箱"
        ></b-form-input>
      </b-form-group>

      <b-form-group
        label="手机号:"
        description="手机号"
      >
        <b-form-input
          v-model="form.phone"
          type="text"
          placeholder="用户手机号"
        ></b-form-input>
      </b-form-group>

      <b-form-group
        label="描述:"
        description="用户描述信息"
      >
        <b-form-input
          v-model="form.description"
          type="text"
          placeholder="用户描述"
        ></b-form-input>
      </b-form-group>
      <b-form-group
        label="密码:"
        description="输入密码"
      >
        <b-form-input
          v-model="form.password"
          type="text"
          placeholder="输入密码"
        ></b-form-input>
      </b-form-group>
       <b-button type="submit" variant="primary">提交</b-button>
    </b-form>
  </div>
</template>

<script>
import {user} from "../../api/user"

export default {
  name:"New",
  props:{
    data:{
      type:Object
    }
  },
  mounted(){
    this.form.organizationId=this.data.organizationId||0
  },
  data(){
    return {
      UserType:[
          "烟感","燃气"
      ],
      form:{

      }
    }
  },
  methods:{
    onSubmit(){
        user.save(this.form).then((res)=>{

        })
    }
  }
}
</script>

<style scoped>

</style>