<template>
  <div>
      <Toolbar :query=query />

      <b-table hover :items="items" :fields="fields"  @row-contextmenu="(item, index, event)=>{event.preventDefault();$refs.menu.open(event,item)}" @row-dblclicked="gotoDevice"  selectable>
          <template v-slot:cell(tags)="data">
                     <Tag v-model="data.item.tags" @input="payload=>changeTags(payload,data.item)" />
        </template>
      </b-table>
        <div style="box-sizing: border-box;">
        <vue-context ref="menu">
              <li class="v-context__sub">
                  <a>控制</a>
                  <ul class="v-context">
                      <li>
                          <a>重置</a>
                      </li>
                  </ul>
              </li>
              <li>  <a href="javascript:void(0);" >删除</a> </li>
        </vue-context>
      </div>
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
import VueContext from 'vue-context';
import {product} from "../../../api/product"
import {device} from "../../../api/device"
import Tag from '../../../components/tags/Tag.vue'
import Toolbar from "../../device/ToolBar"

import 'vue-context/dist/css/vue-context.css'

export default {
  name:"Device",
  components:{Toolbar,Tag,VueContext},
  props:{
    form:Object
  },
  data(){
    return {
      helper:{total:0},
      query:{
        date:{},
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
            key: 'ver',
            label: '产品版本',
            sortable: true
          },
           {
            key: 'tags',
            label: '标签',
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
    changeTags(payload,d){
      device.changeTags({
        sn:d.sn,
        productSn:this.form.sn,
        tags:payload
      }).then(res=>{
      })
    },
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