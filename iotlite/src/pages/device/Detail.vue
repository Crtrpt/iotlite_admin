<template>
  <b-container fluid>
  <b-row>
    <b-col>
      <h3>设备信息# {{form.name}}</h3>
      <p>{{form.description}}</p>
    </b-col>
  </b-row>
  <b-row>
    <b-col>
      <b-nav tabs>
        <b-nav-item to="base"  active-class="active" >基础信息</b-nav-item>
        <b-nav-item to="control"  active-class="active" >设备控制</b-nav-item>
        <b-nav-item to="metric"  active-class="active" >数据指标</b-nav-item>
        <b-nav-item to="log"   active-class="active" >设备日志</b-nav-item>
        <b-nav-item to="map"   active-class="active" >设备位置</b-nav-item>
      </b-nav>
      <router-view class="content" :form=form></router-view>
    </b-col>
  </b-row>
</b-container>
</template>

<script>
import {device} from "../../api/device"
export default {
  name:"deviceDetail",
  mounted(){
    this.getInfo()
  },
  methods:{
    getInfo(){
      var _this=this;
      console.log(this.$route.params.id)
      device.info({
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
        {
          id:"",
          name:"燃气",
          description:"CQ2010"
        },
        {
          id:"",
          name:"烟感",
          description:"CQ2010"
        },
        
      ],
    }
  }
}
</script>

<style scoped>

</style>