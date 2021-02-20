<template>
  <b-container fluid>
  <b-row class="mb-2">
    <b-col cols="12">
      <h3>分组# {{form.name}}</h3>
      <p>{{form.description}}</p>
    </b-col>
  </b-row>
  <b-row>
    <b-col>
      <b-nav tabs>
        <b-nav-item to="device"  active-class="active"> 设备</b-nav-item>
        <b-nav-item to="map"  active-class="active" >设备地图</b-nav-item>
        <!-- <b-nav-item to="3d"  active-class="active" >3d</b-nav-item> -->
      </b-nav>
      <router-view  class="content" :form=form></router-view>
    </b-col>
  </b-row>
</b-container>
</template>

<script>
import {device} from "../../api/device"
export default {
  name:"GroupDetail",
  components:{
    
  },
  mounted(){
    this.getInfo()
  },
  methods:{
    changeTags(payload){
      var _this=this;
      product.changeTags({
        productSn:this.form.sn,
        tags:payload
      }).then(res=>{
      })
    },
    getInfo(){
      var _this=this;
      console.log(this.$route.params.id)
      device.groupInfo({
        id:this.form.id
      }).then(res=>{
        _this.form=res.data
      })
    }
  },
  data(){
    return {
      form:{
        id:0||this.$route.params.id,
      },
      list:[
      ],
    }
  }
}
</script>

<style scoped>

</style>