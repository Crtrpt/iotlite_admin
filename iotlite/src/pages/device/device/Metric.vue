<template>
  <b-row class="mt-2">
      <b-col cols="3" v-for="(ctr,i) in  form.product.spec.metric" :key="i">
        <b-card :title="ctr.name" >
          <b-card-text>{{ctr.desc}}</b-card-text>
            <p>TODO 仪表盘 使用echart.js 重构</p>
        </b-card>
      </b-col>
  </b-row>
</template>

<script>


export default {
  name:"Metric",
  components:{
  },
  data:()=>{
    return {
      datacollection: null,
    }
  },
  props:{
    form:Object
  },
  methods:{
  },
  mounted(){
    
  }
}
</script>

<style scoped>

</style>