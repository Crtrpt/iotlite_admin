<template>
  <b-row class="mt-2">
      <b-col cols="2" v-for="(ctr,i) in  form.product.spec.control" :key="i">
            
        <b-card  @click="deviceControl(ctr)" >
          <template>
               <a href="javascript:void(0);"> <h5  class="mb-1">{{ctr.name}}</h5>
               </a>
          </template>
          <b-card-text>
            <vue-loading v-if="ctr.status!=null" class="card_sub_script" type="spin" color="var(--primary)" :size="{ width: '20px', height: '20px' }"></vue-loading>
            {{ctr.desc}}</b-card-text>
        </b-card>
      </b-col>
  </b-row>
</template>

<script>
import {device} from "../../../api/device"
export default {
  name:"Model",
  props:{
    form:Object
  },
  mounted(){
    console.log(this.form);
  },
  methods:{
    deviceControl(ctr){
        this.$set(ctr,"status","4347389")
        device.action({
          productSn:this.form.product.sn,
          deviceSn:this.form.sn,
          name:ctr.name
        });
    },
  }
}
</script>

<style scoped>

</style>