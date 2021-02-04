<template>
    <b-col cols="12">
      <div class="widget box box-shadow">
          <div class="widget-header">
              <b-row>
                <b-col>
                  <h4>设备({{helper.total||0}})</h4>
                </b-col>
                <Toolbar />
              </b-row>
          </div>
           <div class="widget-content" >
              <b-row>
                
                <b-col col cols="2"  v-for="p in items" :key="p.id">
                   <router-link  active-class="active" :to="{name: 'deviceDetail',params: { id: p.id }}">
                      <b-card  :title="p.name"  class="mt-2">
                        <b-card-text>
                          {{p.description}} 
                          {{p.product.name}}
                        </b-card-text>
                      </b-card>
                  </router-link>
                </b-col>
              </b-row>
           </div>
      </div>
    </b-col>
</template>

<script>
import Toolbar from "./ToolBar"
import {device} from "../../api/device"

export default {
  name:"Device",
  components:{Toolbar},
  data(){
    return {
      helper:{},
       query:{
        organizationId:0,
        words:"",
        pageNum:1,
        pageSize:10,
      },
      items:[        
      ],
    }
  },
  mounted(){
    this.getList();
  },
  methods:{
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