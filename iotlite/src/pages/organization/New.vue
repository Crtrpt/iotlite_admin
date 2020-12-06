<template>
  <div>
    <b-form @submit="onSubmit" >
      <b-form-group
        id="input-group-1"
        label="Name:"
        description="Organization name"
      >
        <b-form-input
          v-model="form.name"
          type="text"
          required
          placeholder="Enter Organization Name"
        ></b-form-input>
      </b-form-group>

      <b-form-group
        id="input-group-1"
        label="Desc:"
        description="Organization desc"
      >
        <b-form-input
          v-model="form.description"
          type="text"
          placeholder="enter Organization description"
        ></b-form-input>
      </b-form-group>
     
       <b-button type="submit" variant="primary">Submit</b-button>
    </b-form>
  </div>
</template>

<script>
import {organization} from "../../api/organization"
export default {
  name:"New",
  props:{
    data:{
      type:Object
    }
  },
  data(){
    return {
      OrganizationType:[
          "烟感","燃气"
      ],
      form:{
          uuid:"",
          fid:"0"
      }
    }
  },
  mounted(){
    this.form.fid=this.data.organizationId||0
  },
  methods:{
    onSubmit(){
        organization.save(this.form).then((res)=>{
          
        })
    }
  }
}
</script>

<style scoped>

</style>