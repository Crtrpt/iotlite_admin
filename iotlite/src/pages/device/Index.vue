<template>
  <b-container fluid>
  <b-row>
    <b-col>
      <h1>device</h1>
    </b-col>
    <b-col cols="12" class="mt-2 mb-2">
      <b-button-toolbar key-nav aria-label="Toolbar with button groups">
        <b-button-group >
          <b-modal id="new" title="New device">
            <New />
          </b-modal>
          <b-button variant="primary" v-b-modal.new>New</b-button>
        </b-button-group>

        <!-- <b-button-group class="ml-1">
          <b-button variant="secondary">Import</b-button>
          <b-button variant="secondary">Export</b-button>
        </b-button-group> -->
      </b-button-toolbar>
    </b-col>
  </b-row>

  <b-row>
    <b-col col cols="2"  v-for="p in items" :key="p.id">
      <b-card  :title="p.name" img-src="/img/product.jpg" img-alt="Image" img-top>
        <b-card-text>
          {{p.description}} 
          {{p.product.name}}
        </b-card-text>
        <router-link :to="{name: 'deviceDetail',params: { id: p.id }}"> <b-icon icon="gear-fill"/></router-link>
      </b-card>
    </b-col>
  </b-row>
</b-container>
</template>

<script>
import {device} from "../../api/device"
import New from "./New"
export default {
  name:"Device",
  components:{New},
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