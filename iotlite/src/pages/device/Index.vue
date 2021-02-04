<template>
    <b-col cols="12">
      <div class="widget box box-shadow">
          <div class="widget-header">
              <b-row>
                <b-col>
                  <h4>设备</h4>
                </b-col>
                <b-col cols="12" class="mt-2 mb-2">
                  <b-button-toolbar>
                    <b-button-group  class="mr-2">
                      <b-modal id="new" title="新建设备" hide-footer>
                        <New />
                      </b-modal>
                      <b-button variant="primary" v-b-modal.new  >新建设备</b-button>
                    </b-button-group>

                    <importFile></importFile>

                  </b-button-toolbar>
                </b-col>
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
import importFile from "../../components/export/ImportFile"

import {device} from "../../api/device"
import New from "./New"
export default {
  name:"Device",
  components:{New,importFile},
  data(){
    return {
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
          _this.helper.total=res.total;
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