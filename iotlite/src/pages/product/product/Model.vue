<template>
  <div>
       <b-col cols="12" class="mt-2 mb-2">
            <b-button-toolbar>
                      <b-button-group  class="mr-2">
                        <b-button size="sm" variant="primary" @click="saveModel" >更新物模型</b-button>
                      </b-button-group>

                      <!-- <b-button-group  class="mr-2">
                        <b-button size="sm" variant="outline-primary" >增加属性</b-button>
                        <b-button size="sm" variant="outline-primary" >增加事件</b-button>
                        <b-button size="sm" variant="outline-primary" >增加控制</b-button>
                        <b-button size="sm" variant="outline-primary" >增加告警</b-button>
                      </b-button-group> -->

                       <!-- <b-button-group  class="mr-2">
                        <b-button size="sm" variant="primary" @click="saveModel" >文档</b-button>
                      </b-button-group> -->
            </b-button-toolbar>
      </b-col>
      <model-editor ref="editor" :data=d />
  </div>
</template>

<script>
import ModelEditor from '../../../components/modelEditor/ModelEditor.vue'
import {product} from "../../../api/product"
export default {
  components: { ModelEditor },
  props:{
    form:Object
  },
  name:"Model",
  data(){
    return {
        d:{
            type:"codeEditor",
            mode:"javascript",
            value:JSON.stringify(this.form.spec,null,2),
        }
    }
  },
  methods:{
    saveModel(){
      var _this=this;
      console.log(this.$refs.editor.instance.getValue())
      product.saveModel({
           productSn:this.form.sn,
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

<style scoped>

</style>