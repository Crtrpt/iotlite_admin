<template>
  <div class="mt-1 mx-1">
      <b-row>
         <b-col cols="12" class="mt-2 mb-2">
            <b-button-toolbar>
                <b-button-group  class="mr-2">
                  <b-button size="sm" variant="primary" @click="setFence" >分组围栏</b-button>
                </b-button-group>
            </b-button-toolbar>
      </b-col>
        <b-col cols="12" id="map">
        </b-col>
      </b-row>
  </div>
</template>

<script>

import {product} from "../../../api/product"
import {device} from "../../../api/device"
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
    setFence(){
        console.log("设置分组围栏")
    },
    init(){ 
        var _this=this;

        var baseMaps = {

        };

        var  overlayMaps={
          
        }

        var map= this.map=L.map('map',{}).setView([31.23573822772999, 121.48664474487306], 18)

        map.pm.setLang('zh');  
        map.pm.addControls({  
          position: 'topleft',  
          drawCircle: true,  
        });  

        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
        }).addTo(this.map);
        
        _this.layerContrl=L.control.layers(null, null).addTo(map);

        L.marker([31.23573822772999, 121.48664474487306]).addTo(this.map)
        .bindPopup('当前位置')
        .openPopup();


        var fenceLayer=L.geoJSON(this.form.fence, {
            style: function (feature) {
                return {
                  color: "#3388ff",
                  stroke: true
                };
            },
            onEachFeature:function(f,layer) {
              console.log("绘制图形")
              layer.bindPopup("这是什么?")
            }
        })

        this.loadProduct();
        this.loadDevice();
        //围栏图层
        // var fenceLayer=L.layerGroup();
        map.pm.setGlobalOptions({ pinning: true, snappable: true ,layerGroup:fenceLayer})  
        fenceLayer.addTo(map);
        map.on('pm:drawend', (e) => { 
          console.log("绘制完成")
          console.log(e);
          // window.localStorage.setItem("FENCE"+this.form.id,JSON.stringify(fenceLayer.toGeoJSON()));
          device.groupSaveFence({
            id:_this.form.id,
            fence:JSON.stringify(fenceLayer.toGeoJSON()),
          }).then(res=>{
            console.log(res);
          })
        })

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