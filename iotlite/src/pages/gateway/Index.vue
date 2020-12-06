<template>
  <b-container fluid>
  <b-row>
    <b-col>
      <h1>Gateway</h1>
    </b-col>
    <b-col cols="12" class="mt-2 mb-2">
      <b-button-toolbar key-nav aria-label="Toolbar with button groups">
        <b-button-group >
          <b-modal id="launch" title="Launch Gateway">
            <Launch />
          </b-modal>
          <b-button variant="primary" v-b-modal.launch>Launch</b-button>
        </b-button-group>

        <b-button-group  class="ml-1">
          <b-modal id="new" title="Create Gateway">
            <New />
          </b-modal>
          <b-button variant="info" v-b-modal.new>Create</b-button>
        </b-button-group>
      </b-button-toolbar>
    </b-col>
  </b-row>

  <b-row>
    <b-col col cols="2"  v-for="p in list" :key="p.id">
      <b-card  :title="p.name" img-src="/img/product.jpg" img-alt="Image" img-top>
        <b-card-text>
          {{p.description}} 
        </b-card-text>
        <b-row align-h="end">
          <b-col cols="2"><b-form-checkbox v-model="p.isStart" name="check-button" switch></b-form-checkbox></b-col>
          <b-col cols="2"><router-link :to="{name: 'gatewayDetail',params: { id: p.id }}"> <b-icon icon="gear-fill"/></router-link></b-col>
        </b-row>
      </b-card>
    </b-col>
  </b-row>
</b-container>
</template>

<script>
import New from "./New"
import Launch from "./Launch"
export default {
  name:"Gateway",
  components:{New,Launch},
  data(){
    return {
      list:[

        // {
        //   id:"1",
        //   name:"Ctwing",
        //   isStart:false,
        //   description:"中国电信NBiot ctwing"
        // },
        // {
        //   id:"2",
        //   name:"Onenet",
        //    isStart:false,
        //   description:"中国移动ONENET onenet"
        // },
        // {
        //   id:"3",
        //   name:"自己开发的网关",
        //    isStart:false,
        //   description:"4G网关"
        // },
        {
          id:"4",
          name:"MQTT",
           isStart:false,
          description:"mqtt网关"
        }
      ],
    }
  }
}
</script>

<style scoped>

</style>