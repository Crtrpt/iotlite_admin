<template>
  <b-container fluid>
  <b-row>
    <b-col>
      <h1>Product ({{helper.total||0}})</h1>
    </b-col>
    <b-col cols="12" class="mt-2 mb-2">
      <b-button-toolbar key-nav aria-label="Toolbar with button groups">
        <b-button-group >
          <b-modal id="new" title="New Product" hide-footer>
            <New :data="query" @close="$bvModal.hide('new')"/>
          </b-modal>
          <b-button variant="primary" v-b-modal.new>New</b-button>
        </b-button-group>
        <b-input-group class="ml-2">
           <template #append>
            <b-button @click="getList"><b-icon icon="search" ></b-icon></b-button>
          </template>
          <b-form-input @change="getList"  placeholder="search" v-model="query.words"></b-form-input>
        
        </b-input-group>
      </b-button-toolbar>
    </b-col>
  </b-row>

  <b-row>
    <b-col col cols="2"  v-for="p in items" :key="p.id">
      <b-card  :title="p.name" img-src="/img/product.jpg" img-alt="Image" img-top class="mt-2 product_card" @click="detail(p)" >
        <b-card-text>
          {{p.description}} 
        </b-card-text>
      </b-card>
    </b-col>
  </b-row>

  <b-row class="mt-2" v-if="helper.total>10">
     <b-col> <b-pagination  v-model="query.page_num"  :total-rows="helper.total"></b-pagination></b-col>
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
      total:0,
      helper:{
        total:0,
      },
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
          _this.helper.total=res.data.total;
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
.product_card {
  cursor: pointer;
}
</style>