<template>
  <div class="orgTree">
    <nav aria-label="breadcrumb" class="breadcrumb-two">
      <ol class="breadcrumb">
        <li class="breadcrumb-item "  ><a @click="goRoot" href="javascript:void(0);">根</a></li>
         <li class="breadcrumb-item active"   v-if="parent.id!=null">
           <a href="javascript:void(0);">{{parent.name}}</a>
        </li>
         <li class="breadcrumb-item" aria-current="page" v-if="current.id!=null">
           <a href="javascript:void(0);">
            {{current.name}}({{current.childrenNum||0}})
           </a>
          </li>
      </ol>
    </nav>
    <b-list-group class="mt-2 mb-2">
      <VuePerfectScrollbar class="scroll-area">
        <b-list-group-item v-for="i in list" :key="i.id">
          <a   href="javascript:void(0);" v-on:click="click(i)" >
            {{i.name}}({{i.childrenNum||0}})
          </a>
        </b-list-group-item>
      </VuePerfectScrollbar>
    </b-list-group>
  </div>
</template>
<script>

import {organization} from "../../api/organization.js"
import VuePerfectScrollbar from 'vue-perfect-scrollbar'

export default {
  name:"OrganizationTree",
  components:{
    VuePerfectScrollbar
  },
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
          name:"上级",
      },
      current:{
        id:null,
          id:10000,
          name:"当前",
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


.scroll-area {
  position: relative;
  margin: auto;
  width:100%;
  height: 60vh;
}
</style>