<template>
  <b-container fluid>
  <b-row class="mb-2">
    <b-col cols="12">
      <h3>设备信息# {{form.name}} </h3>
      <p class="small">{{form.ver}} 
        <b-icon v-if="form.ver!=form.product.ver" icon="arrow-up-square"></b-icon>
      </p>
      <p>{{form.description}} 
        <b-link  class="link" href="javascript:void();" v-b-toggle.more> {{"更多"}}</b-link>
        </p>
    </b-col>
    <b-col cols="12">
        <Tag v-model="form.tags"/>
    </b-col>
    <b-col cols="12">
      <DeviceGroup v-model="form.deviceGroup" />
    </b-col>
  </b-row>
   <b-collapse id="more" class="mt-2">
  <b-row>
    <b-col cols="6">
       <b-table-simple  responsive :bordered="true" :fixed=true>
                  <b-tbody>
                    <b-tr>
                      <b-td  variant="light" class="text-right">设备名称:</b-td>
                      <b-td>{{form.name}}</b-td>
                      <b-td  variant="light" class="text-right">设备序号:</b-td>
                      <b-td>{{form.sn}}</b-td>
                    </b-tr>
                    <b-tr>
                      <b-td  variant="light" class="text-right">产品序号:</b-td>
                      <b-td>{{form.product.sn}}</b-td>
                      <b-td  variant="light" class="text-right">产品名称:</b-td>
                      <b-td>{{form.product.name}}</b-td>
                    </b-tr>
                     <b-tr>
                      <b-td  variant="light" class="text-right">创建时间:</b-td>
                      <b-td>{{form.createdAt}}</b-td>
                      <b-td  variant="light" class="text-right">维护时间:</b-td>
                      <b-td>2021年2月4日14:40:42</b-td>
                    </b-tr>
                  </b-tbody>
       </b-table-simple>
    </b-col>
  </b-row>
   </b-collapse>
  <b-row>
    <b-col>
      <b-nav tabs>
        <b-nav-item to="base"  active-class="active" >基础信息</b-nav-item>
        <b-nav-item to="model"  active-class="active" >物模型</b-nav-item>
        <b-nav-item to="control"  active-class="active" >设备控制</b-nav-item>
        <b-nav-item to="metric"  active-class="active" >数据指标</b-nav-item>
        <b-nav-item to="log"   active-class="active" >设备日志</b-nav-item>
        <b-nav-item to="map"   active-class="active" >设备位置</b-nav-item>
      </b-nav>
      <router-view class="content" :form=form></router-view>
    </b-col>
  </b-row>
</b-container>
</template>

<script>
import {device} from "../../api/device"
import Tag from "../../components/tags/Tag"
import DeviceGroup from "../../components/tags/DeviceGroup";
import mqttClient from "../../api/mqttClient"
import eventHub from "../../api/eventhub"
export default {
  name:"deviceDetail",
  components:{
    Tag,DeviceGroup
  },
  destroyed(){
      var _this=this;
      mqttClient.unsubscribe(_this.topic,(err)=>{
          console.log("取消订阅成功: "+_this.topic)
      })
      //取消事件订阅
      eventHub.off(_this.eventSource,(e)=>{
            console.log("mitt收到"+e)
      })
  },
  mounted(){
    this.getInfo()
  },
  methods:{
    getInfo(){
      var _this=this;
      console.log(this.$route.params.id)
      device.info({
        id:this.form.id
      }).then(res=>{
        _this.form=res.data
        
        mqttClient.subscribe(_this.topic,(err)=>{
          console.log("订阅成功: "+_this.topic)

          eventHub.on(_this.eventSource,(e)=>{
            console.log("mitt收到"+e)
          })
        })

     
      })
    }
  },
  computed:{
    topic(){
        return "/device/"+this.form.product.sn+"/"+this.form.sn+"/"+"#"
    },
    eventSource(){
        return "/device/"+this.form.product.sn+"/"+this.form.sn+"/"
    }
  },
  data(){
    return {
      form:{
        id:0||this.$route.params.id,
      },
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