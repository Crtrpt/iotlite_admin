<template>
  <b-container fluid>
  <b-row>
    <b-col>
      <h1>user Detail</h1>
      <p>desc</p>
    </b-col>
  </b-row>
  <!-- <b-row>
    <b-col col cols="12" class="mt-2 mb-2">
      <b-button-toolbar key-nav aria-label="Toolbar with button groups">
        <b-button-group >
          <b-button variant="secondary">Edit</b-button>
        </b-button-group>

        <b-button-group  class="ml-1">
          <b-button variant="secondary">Export</b-button>
        </b-button-group>
      </b-button-toolbar>
    </b-col>
  </b-row> -->
  <b-row>
    <b-col>
      <b-nav tabs>
        <b-nav-item to="base" active>Base</b-nav-item>
        <b-nav-item to="log"  >Log</b-nav-item>
      </b-nav>
      <router-view class="content"></router-view>
    </b-col>
  </b-row>
</b-container>
</template>

<script>
export default {
  name:"userDetail",
  data(){
    return {
      list:[
        {
          id:"",
          name:"燃气",
          description:"CQ2010"
        },
        {
          id:"",
          name:"烟感",
          description:"CQ2010"
        },
        
      ],
    }
  }
}
</script>

<style scoped>

</style>