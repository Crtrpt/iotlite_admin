<template>
  <b-row class="mt-2">
      <b-col cols="2" v-for="(ctr,i) in  form.product.spec.control" :key="i">
        <b-card :title="ctr.name" @click="deviceControl(ctr.name)" >
          <b-card-text>{{ctr.desc}}</b-card-text>
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
    deviceControl(ctrName){
        console.log(ctrName)
        device.action({
          productSn:this.form.product.sn,
          deviceSn:this.form.sn,
          name:ctrName
        });
    },
  }
}
</script>

<style scoped>

</style>