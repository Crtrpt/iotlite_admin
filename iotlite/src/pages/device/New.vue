<template>
  <div>
    <b-form @submit="onSubmit" >
      <b-form-group
        id="input-group-1"
        label="Sn:"
        description="device sb"
      >
        <b-form-input
          v-model="form.sn"
          type="text"
          required
          placeholder="Enter Device Sn"
        ></b-form-input>
      </b-form-group>

      <b-form-group
        id="input-group-1"
        label="Name:"
        description="device name"
      >
        <b-form-input
          v-model="form.name"
          type="text"
          required
          placeholder="Enter Device Name"
        ></b-form-input>
      </b-form-group>
      <b-form-group
        id="input-group-1"
        label="Desc:"
        description="device desc"
      >
        <b-form-input
          v-model="form.description"
          type="text"
          required
          placeholder="enter device description"
        ></b-form-input>
      </b-form-group>

            <b-form-group
        id="input-group-1"
        label="Product:"
        description="device product"
      >
        <ProductSelect v-model="form.productId"></ProductSelect>
      </b-form-group>

      <b-form-group
        id="input-group-1"
        label="Gateway:"
        description="device gateway"
      >
        <GatewaySelect v-model="form.gatewayId"></GatewaySelect>
      </b-form-group>

       <b-button type="submit" variant="primary">Submit</b-button>
    </b-form>
  </div>
</template>

<script>
import {device} from "../../api/device"
import GatewaySelect from '../../components/gateway/GatewaySelect.vue'
import ProductSelect from '../../components/product/ProductSelect.vue'
export default {
  components: { ProductSelect ,GatewaySelect},
  name:"New",
  data(){
    return {
      productType:[
          "烟感","燃气"
      ],
      form:{

      }
    }
  },
  methods:{
    onSubmit(){
        device.save(this.form).then((res)=>{

        })
    }
  }
}
</script>

<style scoped>

</style>