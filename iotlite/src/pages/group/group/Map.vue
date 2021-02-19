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
      productLayer:{

      }
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

        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
        }).addTo(this.map);
        
        _this.layerContrl=L.control.layers(null, null).addTo(map);

        L.marker([31.23573822772999, 121.48664474487306]).addTo(this.map)
        .bindPopup('当前位置')
        .openPopup();

        this.map.on("click",(res)=>{
          console.log(res);
        })
        this.loadProduct();
        this.loadDevice();  
    },
    loadProduct(){
       var _this=this;
       product.all({}).then((res)=>{
          res.data.forEach(e=>{
            console.log("增加产品图层")
             var littleton = L.marker([31.23573822772999, 121.48594474487306]).bindPopup(e.label);
             var a=L.layerGroup([littleton]).addTo(this.map);
            _this.layerContrl.addOverlay(a,e.label);
          })
          _this.layerContrl.expand();
      })
    },
    loadDevice(){
       var _this=this;
       product.mapDeviceList(Object.assign(
       {
          productSn:this.form.sn,
       },this.query
       )).then((res)=>{
          _this.items=res.data.list;
          _this.helper.total=res.data.total;
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