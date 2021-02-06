<template>
  <div>
      <Toolbar :query=query />
      <b-table hover :items="items" :fields="fields"  @row-dblclicked="gotoDevice">

      </b-table>

      <b-row class="mt-2" v-if="helper.total>10">
          <b-col> <b-pagination 
            v-model="query.pageNum"
            :per-page="query.pageSize"
            :total-rows="helper.total"
            ></b-pagination></b-col>
      </b-row>
  </div>
</template>

<script>
import {product} from "../../../api/product"
import Toolbar from "../../device/ToolBar"
export default {
  name:"Device",
  components:{Toolbar},
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

       fields: [
          {
            key: 'sn',
            label: '设备序号',
            sortable: true
          },
          {
            key: 'name',
            label: '设备名称',
            sortable: true
          },
          {
            key: 'description',
            label: '设备描述',
            sortable: true
          },
          {
            key: 'location',
            label: '设备位置',
            sortable: true
          },
          {
            key: 'createdAt',
            label: '创建时间',
            sortable: true
          },
           {
            key: 'action',
            label: '操作',
            sortable: true
          },
        ],
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
    gotoDevice(item,idx,e){
      console.log(item);
      this.$router.push({name: 'deviceDetail',params: { id: item.id }})
    },
    getList(){
      var _this=this;
       product.deviceList(Object.assign(
       {
          productSn:this.form.sn,
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