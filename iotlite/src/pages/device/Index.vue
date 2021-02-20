<template>
    <b-col cols="12">
      <div class="widget box box-shadow">
          <div class="widget-header">
              <b-row>
                <b-col>
                  <h4>设备({{helper.total||0}})</h4>
                </b-col>
                <Toolbar  :query=query  @refresh="getList" />
              </b-row>
          </div>
           <div class="widget-content" >
              <b-row>
                <b-col col cols="2"  v-for="p in items" :key="p.id">
                  
                      <b-card  class="mt-2">
                      <template>
                          <router-link  active-class="active" :to="{name: 'deviceDetail',params: { id: p.id }}">
                             <h5  class="mb-1">{{p.name}}</h5>
                         </router-link>
                      </template>

                        <b-card-text>

                          <div class="card_sub_script_left_top">
                            <b-link v-b-tooltip.hover :title="'产品版本:'+p.product.sn"   >
                              <b-icon   icon="app-indicator"></b-icon>  {{p.sn}}
                            </b-link>
                          </div>

                          <div class="card_sub_script">
                            <b-link v-b-tooltip.hover :title="'新版本'+p.product.ver"  v-if="p.ver!=p.product.ver" >
                               {{p.ver}}  <b-icon   icon="arrow-up-square"></b-icon>
                            </b-link>
                             <b-link v-if="p.ver==p.product.ver" >
                               {{p.ver}}  <b-icon   icon="bookmark"></b-icon>
                            </b-link>
                          </div>
                          {{p.description}} 
                          {{p.product.name}}
                          <Tag v-model="p.tags" @input="payload=>changeTags(payload,p)"/>
                          <DeviceGroup v-model="p.deviceGroup" />
                        </b-card-text>
                      </b-card>
                 
                </b-col>
              </b-row>
              <b-pagination 
                class="mt-2"  
                v-if="helper.total>10" 
                v-model ="query.pageNum"  
                :per-page="query.pageSize"
                :total-rows="helper.total"
              >
              </b-pagination>
           </div>
      </div>
    </b-col>
</template>

<script>
import Toolbar from "./ToolBar"

import Tag from "../../components/tags/Tag"
import {device} from "../../api/device"
import DeviceGroup from "../../components/tags/DeviceGroup";

export default {
  name:"Device",
  components:{Toolbar,Tag,DeviceGroup},
  data(){
    return {
      helper:{
        total:0,
      },
       query:{
         date:{},
        organizationId:0,
        words:"",
        pageNum:1,
        pageSize:36,
      },
      items:[        
      ],
    }
  },
  watch:{
    "query":{
      handler(){
        this.getList()
      },
      deep:true
    }
  },
  mounted(){
    this.getList();
  },
  methods:{
    changeTags(payload,d){
      device.changeTags({
        sn:d.sn,
        productSn:d.product.sn,
        tags:payload
      }).then(res=>{
      })
    },
    getList(){
      var _this=this;
      device.list(this.query).then((res)=>{
          _this.items=res.data.list;
          _this.helper.total=res.data.total;
      })
    },
    detail(row){
      console.log(row);
      this.$router.push({name: 'userDetail',params: { id: row.item.id }})
    }
  }
}
</script>

<style scoped>

</style>