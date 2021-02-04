<template>
  <div>
      <b-table hover :items="items"></b-table>

      <b-row class="mt-2">
          <b-col> <b-pagination 
            v-model="query.pageNum"
            :per-page="query.pageSize"
            :total-rows="helper.total"></b-pagination></b-col>
      </b-row>
  </div>
</template>

<script>
import {device} from "../../../api/device"
export default {
  name:"Log",
  props:{
    form:Object
  },
  data(){
    return {
      helper:{total:0},
      query:{
        words:"",
        pageNum:1,
        pageSize:10,
       },
      items: [
         
      ]
    }
  },
  watch:{
    "query":{
      handler(){
        this.getList()
      },
      deep:true
    }
  },
  mounted(){
    this.getList();
  },
  methods:{
    getList(){
      var _this=this;
       device.logList(Object.assign(
         {
        prductSn:this.form.product.sn,
        deviceSn:this.form.sn,
       },this.query
       )).then((res)=>{
          _this.items=res.data.list;
          _this.helper.total=res.data.total;
      })
    },
  }
}
</script>

<style scoped>

</style>