<template>
  <div class="mt-1 mx-1">
      <b-row>
        <b-col cols="12" id="map">
        </b-col>
      </b-row>
  </div>
</template>

<script>

import {product} from "../../../api/product"
export default {
  name:"Map",
  props:{
    form:Object
  },
  data(){
    return {
      map:null,
      layerContrl:null,
      productLayer:[],
    }
  },
  methods:{
    init(){ 
        var _this=this;

        var baseMaps = {

        };

        var  overlayMaps={
          
        }

        var map= this.map=L.map('map').setView([31.23573822772999, 121.48664474487306], 18)

        this.map.on("zoomanim",(res)=>{
          product.setProductMetas({
            productSn:_this.form.sn,
            "data":{
              zoom:this.map.getZoom(),
              center:this.map.getCenter()
            },
          })
        })

        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
        }).addTo(this.map);
        
        _this.layerContrl=L.control.layers(null, null).addTo(map);


        this.map.on("click",(res)=>{
          console.log(res);
        })
        this.loadProduct();
        
    },
    loadProduct(){
       var _this=this;
       product.all({}).then((res)=>{
          res.data.forEach(e=>{
            console.log("增加产品图层"+e.sn)
            var product= this.productLayer[e.sn]= L.layerGroup([])
            var a= product.addTo(this.map);
            _this.layerContrl.addOverlay(a,e.label);
          })
          _this.layerContrl.expand();

          this.loadDevice();  
      })
    },
    loadDevice(){
       var _this=this;
       product.mapDeviceList(Object.assign(
       {
          productSn:this.form.sn,
       },this.query
       )).then((res)=>{
         res.data.forEach(e=>{
            if(e.location){
              console.log("增加设备点")
              console.log(e)
              L.marker([e.location.y, e.location.x])
              .addTo(_this.productLayer[e.productSn])
              .bindPopup("deviceSn:"+e.deviceSn+" / productSn:"+e.productSn)
              .openPopup();
            }else{
              console.log("显示没有位置信息的设备")
            }
         })
         
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