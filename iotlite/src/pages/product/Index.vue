<template>
  <b-container fluid>
  <b-row>
    <b-col>
      <h1>Product</h1>
    </b-col>
    <b-col cols="12" class="mt-2 mb-2">
      <b-button-toolbar key-nav aria-label="Toolbar with button groups">
        <b-button-group >
          <b-modal id="new" title="New Product">
            <New :data="query" />
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
      <b-card  :title="p.name" img-src="/img/product.jpg" img-alt="Image" img-top >
        <b-card-text>
          {{p.description}} 
        </b-card-text>
        <a href="javascript:void(0);" @click="detail(p)" > <b-icon icon="gear-fill"/></a>
      </b-card>
    </b-col>
  </b-row>
</b-container>
</template>

<script>
import {product} from "../../api/product"

import New from "./New"
export default {
  name:"Product",
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
      product.list(this.query).then((res)=>{
          _this.items=res.data.list;
      })
    },
    detail(row){
      console.log(row);
      this.$router.push({name: 'productDetail',params: { id: row.id }})
    }
  }
}
</script>

<style scoped>

</style>