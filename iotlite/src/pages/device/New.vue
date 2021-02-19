<template>
  <div>
    <b-form @submit="onSubmit" >

      <b-tabs  class="simple-tab">
      <b-tab title="基础" active>
              <b-form-group
        
        label="设备序列号:"
        description="设备序列号"
      >
        <b-form-input
          v-model="form.sn"
          type="text"
          required
          placeholder="输入设备序列号"
        ></b-form-input>
      </b-form-group>

      <b-form-group
        
        label="设备名称:"
        description="设备名称"
      >
        <b-form-input
          v-model="form.name"
          type="text"
          required
          placeholder="输入设备名称"
        ></b-form-input>
      </b-form-group>
      <b-form-group
        
        label="描述:"
        description="设备描述"
      >
        <b-form-input
          v-model="form.description"
          type="text"
          placeholder="输入设备描述"
        ></b-form-input>
      </b-form-group>

      <b-form-group
        label="产品型号:"
        description="产品型号"
      >
        <ProductSelect v-model="form.productId"></ProductSelect>
      </b-form-group>
      </b-tab>
      <b-tab title="高级">
          <b-form-group
        label="数量:"
        description="创建设备的数量"
      >
      <b-form-input
          v-model="form.count"
          type="number"
          placeholder="创建设备的数量 默认为 1个"
        ></b-form-input>
      </b-form-group>

      <b-form-group
        label="分组:"
        description="设备加入的分组"
      >
        <b-form-input
          v-model="form.deviceGroup"
          placeholder="设备加入的分组 多个分组，分割"
        ></b-form-input>
      </b-form-group>
      
      <b-form-group
        label="批次:"
        description="创建设备的批次"
      >
        <b-form-input
          v-model="form.batch"
          type="number"
          placeholder="设备创建的批次"
        ></b-form-input>
      </b-form-group>
      </b-tab>
    </b-tabs>
       <b-button type="submit" variant="primary">保存</b-button>
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
          "设备","网关"
      ],
      form:{
        count:1
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