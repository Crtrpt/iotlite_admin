<template>
  <div>
    <b-form @submit="onSubmit" >
      <b-form-group
        
        label="名称:"
        description="机构名称"
      >
        <b-form-input
          v-model="form.name"
          type="text"
          required
          placeholder="机构名称"
        ></b-form-input>
      </b-form-group>

      <b-form-group
        
        label="机构描述:"
        description="描述"
      >
        <b-form-input
          v-model="form.description"
          type="text"
          placeholder="组织机构的描述信息"
        ></b-form-input>
      </b-form-group>
     
       <b-button type="submit" variant="primary">保存</b-button>
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
      var _this=this;
        organization.save(this.form).then((res)=>{
          console.log(_this.$bvModal.hide("new"));
            if(res.code==0){
              _this.$bvModal.msgBoxOk("保存成功")
            }else{
              _this.$bvModal.msgBoxOk(res.msg)
            }
        })
    }
  }
}
</script>

<style scoped>

</style>