<template>
  <div>
    <nav aria-label="breadcrumb">
      <ol class="breadcrumb">
          <li class="breadcrumb-item active" aria-current="page" ><a @click="goRoot" href="javascript:void(0);">Root</a></li>
         <li class="breadcrumb-item " aria-current="page"  v-if="parent.id!=null"><a href="javascript:void(0);">{{parent.name}}({{current.childrenNum||0}})</a></li>
         <li class="breadcrumb-item " aria-current="page" v-if="current.id!=null">{{current.name}}({{current.childrenNum||0}})</li>
      </ol>
    </nav>
    <b-list-group>
      <b-list-group-item v-for="i in list" :key="i.id">
        <a   href="javascript:void(0);" v-on:click="click(i)" >
          {{i.name}}({{i.childrenNum||0}})
        </a>
      </b-list-group-item>
    </b-list-group>
  </div>
</template>
<script>

import {organization} from "../../api/organization.js"
export default {
  name:"OrganizationTree",
  props:{
    value:{
        type:Number,
        default:0
      }
  },
  mounted(){
    this.refresh();
  },
  watch:{
    "value":{
      deep:true,
      handler(){
        this.refresh();
      }
    }
  },
  data(){
    return {
      query:{
        id:"",
      },
      tree:[
         {
            text: 'Root',
            href: '#'
          },
          {
            text: 'aaa',
            href: '#'
          },
          {
            text: 'Library',
          },
           {
            text: 'Add',
          }
      ],
      parent:{
        id:null,
          id:10000,
          name:"xxx",
      },
      current:{
        id:null,
          id:10000,
          name:"xxx",
      },
      list:[
      ]
    }
  },
  methods:{
    goRoot(){
      console.log("更新")
      this.$emit("input",0)
    },
    refresh(){
    var _this=this;
    organization.tree({id:this.value}).then((res)=>{
      _this.list=res.data.list;
      _this.current=res.data.current;
      _this.parent=res.data.parent;
    })
    },
    click(i){
      this.query.id=i.id;
      this.$emit("input",i.id)
    }
  }

}
</script>

<style scoped>

</style>