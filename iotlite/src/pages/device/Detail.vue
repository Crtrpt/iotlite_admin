<template>
  <b-container fluid>
  <b-row>
    <b-col>
      <h1>Device# {{form.name}}</h1>
      <p>{{form.description}}</p>
    </b-col>
  </b-row>
  <!-- <b-row>
    <b-col col cols="12" class="mt-2 mb-2">
      <b-button-toolbar key-nav aria-label="Toolbar with button groups">
        <b-button-group >
          <b-button variant="secondary">Edit</b-button>
        </b-button-group>

        <b-button-group  class="ml-1">
          <b-button variant="secondary">Export</b-button>
        </b-button-group>
      </b-button-toolbar>
    </b-col>
  </b-row> -->
  <b-row>
    <b-col>
      <b-nav tabs>
        <b-nav-item to="base" active>Base</b-nav-item>
        <b-nav-item to="control" > Control</b-nav-item>
        <b-nav-item to="metric" > Metric</b-nav-item>
        <b-nav-item to="log"  >Log</b-nav-item>
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