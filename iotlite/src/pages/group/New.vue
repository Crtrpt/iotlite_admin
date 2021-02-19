<template>
  <div>
    <b-form @submit="onSubmit" >
      <b-form-group
        label="名称:"
        description="产品名称"
      >
        <b-form-input
          v-model="form.name"
          type="text"
          required
          placeholder="输入产品名称"
        ></b-form-input>
      </b-form-group>
      <b-form-group
        label="描述:"
        description="产品描述"
      >
        <b-form-input
          v-model="form.description"
          type="text"
          required
          placeholder="输入对产品的描述"
        ></b-form-input>
      </b-form-group>

       <b-button type="submit" variant="primary">提交</b-button>
    </b-form>
  </div>
</template>

<script>
import {device} from "../../api/device"

export default {
  name:"New",
  data(){
    return {
      form:{

      }
    }
  },
  methods:{
    onSubmit(){
       var _this=this;
        device.groupSave(this.form).then((res)=>{
            if(res.code==0){
              _this.$emit("close",true);
            }
        })
    }
  }
}
</script>

<style scoped>

</style>