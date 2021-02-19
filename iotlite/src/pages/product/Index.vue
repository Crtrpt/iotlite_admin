<template>

    <b-col cols="12">
      <div class="widget box box-shadow">
          <div class="widget-header">
            <b-row>
              <b-col>
                <h4>产品 ({{helper.total||0}})</h4>
              </b-col>
              <Toolbar :query=query />
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
                    <div class="card_sub_script">{{p.ver}}<b-icon   icon="bookmark"></b-icon></div>
                    <Tag v-model="p.tags" @input="payload=>changeTags(payload,p)" />
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
import {product} from "../../api/product"


import New from "./New"
import Tag from "../../components/tags/Tag"
export default {
  name:"Product",
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
     changeTags(payload,p){
      var _this=this;
      product.changeTags({
        productSn:p.sn,
        tags:payload
      }).then(res=>{
      })
    },
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