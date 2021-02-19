<template>

    <b-col cols="12">
      <div class="widget box box-shadow">
          <div class="widget-header">
            <b-row>
              <b-col>
                <h4>分组 ({{helper.total||0}})</h4>
              </b-col>
              <Toolbar :query=query  @refresh="getList"/>
            </b-row>
          </div>
          
          <div class="widget-content" >
            <b-row>
              <b-col col cols="2"  v-for="p in items" :key="p.id">
                <b-card   class="mt-2 product_card" >
                    <template>
                      <h5  @click="detail(p)" class="mb-1">{{p.name}}</h5>
                    </template>
                  <b-card-text>
                    {{p.description}}                    
                  </b-card-text>
                </b-card>
              </b-col>
            </b-row>
          </div>

        <b-row class="mt-2" v-if="helper.total>10">
          <b-col> <b-pagination  v-model="query.page_num"  :total-rows="helper.total"></b-pagination></b-col>
        </b-row>
      </div>
    </b-col>

</template>

<script>
import Toolbar from "./ToolBar"
import {device} from "../../api/device"


import New from "./New"
import Tag from "../../components/tags/Tag"
export default {
  name:"DeviceGroup",
  components:{New,Toolbar,Tag},
  data(){
    return {
      total:0,
      helper:{
        total:0,
      },
      query:{
        date:{},
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
      device.groupList(this.query).then((res)=>{
          _this.items=res.data.list;
          _this.helper.total=res.data.total;
      })
    },
    detail(row){
      console.log(row);
      this.$router.push({name: 'groupDetail',params: { id: row.id }})
    }
  }
}
</script>

<style scoped>
.product_card {
  cursor: pointer;
}
</style>