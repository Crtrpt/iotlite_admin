<template>
  <b-container fluid class="dashboard">
    <b-row>
        <b-col col cols="3"  v-for="p in items" :key="p.id" @click="priview()">
            <b-card  :title="p.name" img-src="/img/product.jpg" img-alt="Image" img-top>
                <router-link :to="{name: 'dashboardPreview',params: { id: p.id }}"> <b-icon icon="gear-fill"/></router-link>
                <router-link :to="{name: 'dashboardEdit',params: { id: p.id }}"> <b-icon icon="gear-fill"/></router-link>
            </b-card>
        </b-col>
    </b-row>
  </b-container>
</template>
<script>

import 'gridstack/dist/gridstack.min.css'
import Vue from 'vue'
import GridStack from "../../../node_modules/gridstack/dist/gridstack-h5"

import ProductWidget from "../../widget/test/ProductWidget" 
import DeviceWidget from "../../widget/test/DeviceWidget" 

export default {
  name: 'Dashboard',
  data(){
        return  {
           items:[
               {
                   id:1,
                   name:"设备大屏"
               },
           ],
          }
        },
        methods: {

        },
  components: {  },
};
</script>

<style >
  .dashboard {
    padding: 0;
    margin: 0;
  }
  .grid-stack { background: #FAFAD2; }
  .grid-stack-item-content { background-color: #18BC9C; }
</style>
