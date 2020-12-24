<template>
  <div>
    <b-form @submit="onSubmit" >
      <b-form-group
        id="input-group-1"
        label="Name:"
        description="product name"
      >
        <b-form-input
          v-model="form.name"
          type="text"
          required
          placeholder="Enter Product Name"
        ></b-form-input>
      </b-form-group>
      <b-form-group
        id="input-group-1"
        label="Desc:"
        description="product desc"
      >
        <b-form-input
          v-model="form.description"
          type="text"
          required
          placeholder="enter product description"
        ></b-form-input>
      </b-form-group>
      <b-form-group
        id="input-group-1"
        label="Type:"
        description="gateway or device"
      >
       <b-form-radio-group>
        <b-form-radio v-model="form.type"  value="0">Device</b-form-radio>
        <b-form-radio v-model="form.type"  value="1">Gateway</b-form-radio>
        </b-form-radio-group>
      </b-form-group>
       <b-button type="submit" variant="primary">Submit</b-button>
    </b-form>
  </div>
</template>

<script>
import {product} from "../../api/product"

export default {
  name:"New",
  data(){
    return {
      productType:[
          "设备","网关"
      ],
      form:{

      }
    }
  },
  methods:{
    onSubmit(){
       var _this=this;
       
        product.save(this.form).then((res)=>{
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