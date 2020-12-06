<template>
    <b-container fluid>
    
    <h1>User</h1>
    <b-row align-h="end">
      <b-col cols="12" class="mt-2 mb-2"  >
        <b-button-toolbar key-nav aria-label="Toolbar with button groups" >
          <b-button-group >
            <b-modal id="new" title="New User">
              <New :data="query"/>
            </b-modal>
            <b-button variant="primary" v-b-modal.new>New</b-button>
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
          <b-dropdown-item @click="detail(row)">详情</b-dropdown-item>
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
import {user} from "../../api/user"
export default {
  name:"User",
  components:{New,OrganizationTree},
  data(){
    return {
      helper:{total:0},
      query:{
        organizationId:0,
        words:"",
        pageNum:1,
        pageSize:10,
      },
      items:[
      ]
    }
  },
  mounted(){
    this.getList();
  },
  methods:{
    getList(){
      var _this=this;
      user.list(this.query).then((res)=>{
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