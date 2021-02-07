<template>
  <b-container fluid>
  <b-row class="mb-2">
    <b-col cols="12">
      <h3>产品# {{form.name}}</h3>
      <p class="small">{{form.ver}} </p>
      <p>{{form.description}}</p>
    </b-col>
    <b-col cols="12">
        <Tag v-model="form.tags"/>
    </b-col>
  </b-row>
  <b-row>
    <b-col>
      <b-nav tabs>
        <b-nav-item to="base"  active-class="active" >基本信息</b-nav-item>
        <b-nav-item to="model"  active-class="active" ><b-icon icon="code"  /> 物模型</b-nav-item>
        <b-nav-item to="device"  active-class="active"> 设备</b-nav-item>
        <b-nav-item to="map"  active-class="active" >设备地图</b-nav-item>
        <b-nav-item to="3d"  active-class="active" >3d</b-nav-item>
      </b-nav>
      <router-view  class="content" :form=form></router-view>
    </b-col>
  </b-row>
</b-container>
</template>

<script>
import {product} from "../../api/product"
import Tag from "../../components/tags/Tag"
export default {
  name:"ProductDetail",
  components:{
    Tag
  },
  mounted(){
    this.getInfo()
  },
  methods:{
    getInfo(){
      var _this=this;
      console.log(this.$route.params.id)
      product.info({
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