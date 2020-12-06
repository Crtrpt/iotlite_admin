<template>
  <div>
      <model-editor :data=d />
  </div>
</template>

<script>
import ModelEditor from '../../../components/modelEditor/ModelEditor.vue'
export default {
  components: { ModelEditor },
  props:{
    form:Object
  },
  name:"Model",
  data(){
    return {
        d:  {
            type:"codeEditor",
            mode:"javascript",
            value:JSON.stringify(this.form.spec,null,2),
          }
    }
  },
  mounted(){
    console.log(this.form);
    this.d.value=JSON.stringify(this.form.spec,null,2);
    console.log(this.d.value);
  }
}
</script>

<style scoped>

</style>