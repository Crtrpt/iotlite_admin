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
  watch:{
    "form.fence":{
      handler(){
         this.renderFence()
      },
      deep:true
    }
  },
  methods:{
    setFence(){
        console.log("设置分组围栏")
    },
    renderFence(){
      var _this=this;
       var map=this.map;
        console.log(this.form.fence);
        var fenceLayer=L.geoJSON(JSON.parse(this.form.fence), {
            style: function (feature) {
                return {
                  color: "#3388ff",
                  stroke: true
                };
            },
            onEachFeature:function(f,layer) {
              console.log("绘制图形")
              layer.bindPopup("这是什么?")

              layer.on("",()=>{
                
              })
            }
        })
        
        //围栏图层
        // var fenceLayer=L.layerGroup();
        map.pm.setGlobalOptions({ pinning: true, snappable: true ,layerGroup:fenceLayer})  
        fenceLayer.addTo(map);

        map.on('pm:drag',(e)=>{
          console.log("更新")
        })

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

        this.renderFence()
        

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