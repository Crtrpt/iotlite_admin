<template>

    <b-col cols="12">
      <div class="widget box box-shadow">
          <div class="widget-header">
        <b-row>
          <b-col>
            <h4>产品 ({{helper.total||0}})</h4>
          </b-col>
          <b-col cols="12" class="mt-2 mb-2">
            <b-button-toolbar>
                    <b-button-group  class="mr-2">
                      <b-modal id="new" title="新建产品" hide-footer>
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
                <b-card  :title="p.name" class="mt-2 product_card" @click="detail(p)" >
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
import {product} from "../../api/product"
import importFile from "../../components/export/ImportFile"
import exportFile from "../../components/export/ExportFile"
import New from "./New"
export default {
  name:"Product",
  components:{New,importFile,exportFile},
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