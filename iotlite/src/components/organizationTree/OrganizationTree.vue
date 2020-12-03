<template>
  <div>
    <nav aria-label="breadcrumb">
      <ol class="breadcrumb">
          <li class="breadcrumb-item active" aria-current="page">Root</li>
         <li class="breadcrumb-item " aria-current="page"  v-if="current!=null"><a href="#">{{parent.name}}</a></li>
         <li class="breadcrumb-item " aria-current="page" v-if="current!=null">{{current.name}}</li>
      </ol>
    </nav>
    <b-list-group>
      <b-list-group-item v-on:click="click(i)" v-for="i in list" :key="i.id">{{i.id}}</b-list-group-item>
    </b-list-group>
  </div>
</template>
<script>
import {organization} from "../../api/organization.js"
export default {
  name:"OrganizationTree",
  props:{
    id:{
        type:Number,
        default:0
      }
  },
  mounted(){
    organization.tree(this.query).then((res)=>{
      this.list=res.data.list;
    })
  },
  data(){
    return {
      query:{
        id:0,
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
          id:10000,
          name:"xxx",
      },
      current:{
          id:10000,
          name:"xxx",
      },
      list:[
        {
          id:10000,
          name:"aaaa"
        }
      ]
    }
  },
  methods:{
    click(i){
      console.log(i);
      this.$emit("input",i.id)
    }
  }

}
</script>

<style scoped>

</style>