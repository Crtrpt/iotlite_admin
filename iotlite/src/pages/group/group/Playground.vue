<template>
  <div class="mt-1 mx-1">
       <b-row>
         <b-col cols="12" class="mt-2 mb-2">
           <b-button-toolbar>
                      <b-button-group  class="mr-2">
                        <b-button size="sm" variant="primary" @click="saveModel" >更新编排策略</b-button>
                      </b-button-group>
            </b-button-toolbar>
         </b-col>
        <b-col cols="12" >
           <model-editor ref="editor" :data=d />
        </b-col>
      </b-row>
  </div>
</template>


<script>
import ModelEditor from '../../../components/modelEditor/ModelEditor.vue'
import {device} from "../../../api/device"
export default {
  components: { ModelEditor },
  props:{
    form:Object
  },
  name:"Playground",
  data(){
    return {
        d:{
            type:"codeEditor",
            mode:"groovy",
            value:this.form.spec,
        }
    }
  },
  methods:{
    saveModel(){
      var _this=this;
      device.saveGroupPlayground({
           id:this.form.id,
           spec:this.$refs.editor.instance.getValue()
      }).then((res)=>{
          if(res.code==0){
              _this.$bvModal.msgBoxOk("保存成功")
          }else{
              _this.$bvModal.msgBoxOk(res.msg)
          }
      })
    }
  },
  mounted(){
    this.d.value=JSON.stringify(this.form.spec,null,2);
  }

}
</script>