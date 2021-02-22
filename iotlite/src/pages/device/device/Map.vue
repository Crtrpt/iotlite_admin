<template>
  <div class="mt-1 mx-1">
      <b-row>
        <b-col cols="12" class="mt-2 mb-2" >
          <b-form inline>

            <b-form-checkbox   size="sm" v-model="query.nearby" > 显示附近设备 </b-form-checkbox>
            <b-form-input
             :disabled=!query.nearby
             class="ml-2"
             size="sm"
             v-model="query.radius"
             placeholder="附近范围">
             </b-form-input>

            <b-form-checkbox  class="ml-2" size="sm" v-model="query.fence" > 显示围栏 </b-form-checkbox>

            <p class="ml-2 mt-2" v-if="currentMousePostion!=null">当前位置: {{currentMousePostion.latlng.lng}} ,{{currentMousePostion.latlng.lat}} </p>

            <p class="ml-2 mt-2" v-if="clickPostion!=null">点击位置: {{clickPostion.latlng.lng}} ,{{clickPostion.latlng.lat}} </p>

          </b-form>
        </b-col>
      </b-row>
      <b-row>
        <b-col cols="12" >
          <div id="map"></div>
        </b-col>
      </b-row>
  </div>
</template>

<script>

/**
 * 增加室内地图支持
 */
import {device} from "../../../api/device"
// import FreeDraw from "leaflet-freedraw";
import 'leaflet-contextmenu'

// const freeDraw  = new FreeDraw();


export default {
  name:"Map",
  props:{
    form:Object
  },
  data(){
    return {
      clickPostion:null,
      currentMousePostion:null,
      map:null,
      deviceMarker:null,
      circle:null,
      query:{
        fence:false,
        nearby:false,
        radius:200,
      }
    }
  },
  watch:{
    query:{
      deep:true,
      handler:function(){
        this.loadDevice()
      }
    }
  },
  methods:{
    init(){
        var _this=this;
        console.log(_this.form?.meta)
        var map= this.map=L.map('map',{
          contextmenu: true,
          contextmenuWidth: 140,
          contextmenuItems: [
              {
                text: '右键菜单',
                callback: ()=>{
                  console.log("菜单")
                }
              }
            ]
        })
        .setView([13.361389338970184, 38.11554879133282], _this.form.meta?.zoom??18)
       
        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
        }).addTo(this.map);

        this.map.on("click",(res)=>{
            _this.clickPostion=res
        }),

        // this.map.addLayer(freeDraw);

        // freeDraw.on()
        this.map.on("mousemove",(res)=>{
            _this.currentMousePostion=res
        })

        this.map.on("zoomanim",(res)=>{
          device.setDeviceMeta({
            productSn:_this.form.product.sn,
            deviceSn:_this.form.sn,
            "key":"zoom",
            "value":this.map.getZoom()
          })
        })

        this.loadDevice();
    },
    loadDevice(){
       var _this=this;
       device.location(Object.assign(
       {
          productSn:_this.form.product.sn,
          deviceSn:_this.form.sn,
       },this.query
       )).then((res)=>{
         console.log(res);
        if(_this.deviceMarker){
           _this.deviceMarker.remove();
           if(_this.circle){
              _this.circle.remove();
           }
        }
       
       _this.deviceMarker=L.marker([res.data.location.y,res.data.location.x]).addTo(_this.map)
        .bindPopup(`产品名称:${_this.form.product.name} </b><br> 
                  产品序号:${_this.form.product.sn} </b><br> 
                  设备名称:${_this.form.name} </b><br> 
                  设备序号:${_this.form.sn} </b><br>
                  设备位置: ${res.data.location.y},${res.data.location.x}`);
                  console.log( _this.map);
      _this.map.setView([res.data.location.y,res.data.location.x])
        //TODO 不同的产品分层显示

        //显示附近的时候才显示附近范围的圆
        if(_this.query.nearby){
          _this.circle = L.circle([res.data.location.y,res.data.location.x], {
              color: '#00000000',
              fillColor: '#0000ff',
              fillOpacity: 0.1,
              radius:_this.query.radius,
          }).addTo(this.map);
        }
      })
    }
  },

  mounted() {
     this.init();
  }
}
</script>

<style scoped>
#map {
  height: 600px;
}
</style>