<template>
  <div>
    <b-form @submit="onSubmit" >
      <b-form-group
        label="Name:"
        description="User name"
      >
        <b-form-input
          v-model="form.name"
          type="text"
          required
          placeholder="Enter User Name"
        ></b-form-input>
      </b-form-group>
       <b-form-group
        label="Account:"
        description="Account"
      >
        <b-form-input
          v-model="form.account"
          type="text"
          required
          placeholder="Enter User Account"
        ></b-form-input>
      </b-form-group>

      <b-form-group
        label="Email:"
        description="Email"
      >
        <b-form-input
          v-model="form.email"
          type="text"
          placeholder="Enter User Email"
        ></b-form-input>
      </b-form-group>

      <b-form-group
        label="Phone:"
        description="Phone"
      >
        <b-form-input
          v-model="form.phone"
          type="text"
          placeholder="Enter User Phone"
        ></b-form-input>
      </b-form-group>

      <b-form-group
        label="Desc:"
        description="User desc"
      >
        <b-form-input
          v-model="form.description"
          type="text"
          placeholder="enter User description"
        ></b-form-input>
      </b-form-group>
      <b-form-group
        label="Password:"
        description="User password"
      >
        <b-form-input
          v-model="form.password"
          type="text"
          placeholder="enter password"
        ></b-form-input>
      </b-form-group>
       <b-button type="submit" variant="primary">Submit</b-button>
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