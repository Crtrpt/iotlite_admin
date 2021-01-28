<template>
    <b-container fluid>
    
     <h5>组织</h5>
    <b-row>
      <b-col cols="12" class="mt-2 mb-2">
        <b-button-toolbar key-nav aria-label="Toolbar with button groups">

          <b-button-group class="mr-2" >
            <b-button variant="primary" @click="displayOrganizationTree=!displayOrganizationTree">
              <b-icon icon="bar-chart-steps"></b-icon>
            </b-button>
          </b-button-group>

          <b-button-group >
            <b-modal id="new" title="组织">
              <New :data="query"/>
            </b-modal>
            
            <b-button variant="primary" v-b-modal.new>新建</b-button>
          </b-button-group>
          <b-button-group class="ml-1">
              <b-input-group  class="mb-2 mr-sm-2 mb-sm-0">
                <template #append>
                   <b-input-group-text>
                      <b-icon icon="search" @click="getList" />
                    </b-input-group-text>
                </template>
                <b-form-input id="inline-form-input-username" v-model="query.words" placeholder="Search"></b-form-input>
              </b-input-group>
          </b-button-group> 
        </b-button-toolbar>
      </b-col>
    </b-row>

    <b-row>
    <b-col cols="2">
     <OrganizationTree v-model="query.organizationId"></OrganizationTree>
    </b-col>
    <b-col cols="10">
      <b-table striped hover :items="items">
        <template #cell(action)="row">
         <b-dropdown right split text="Action">
          <b-dropdown-item>详情</b-dropdown-item>
          <b-dropdown-divider></b-dropdown-divider>
          <b-dropdown-item>删除</b-dropdown-item>
        </b-dropdown>
        </template>
      </b-table>
       <b-pagination  v-model ="query.page_num"  :total-rows="helper.total"></b-pagination>
    </b-col>
    </b-row>
  </b-container>

</template>

<script>
import New from "./New"
import OrganizationTree from "../../components/organizationTree/OrganizationTree"
import {organization} from "../../api/organization"
export default {
  name:"Organization",
  components:{New,OrganizationTree},
  mounted(){
    this.getList();
  },
  watch:{
    "query":{
      deep:true,
      handler:function(){
        this.getList()
      }
    }
  },
  methods:{
    getList(){
      var _this=this;
      organization.list(_this.query).then((res)=>{
          _this.items=res.data.list;
          _this.total=res.total;
      })
    },
  },
  data(){
    return {
      displayOrganizationTree:false,
      helper:{
          total:0,
      },
      query:{
        organizationId: 0,
        words:"",
        pageNum:1,
        pageSize:10,
      },
      items:[
      ]
    }
  }
}
</script>

<style scoped>

</style>