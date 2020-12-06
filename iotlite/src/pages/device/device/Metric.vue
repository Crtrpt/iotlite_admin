<template>
  <b-row class="mt-2">
      <b-col cols="2" v-for="(ctr,i) in  form.product.spec.property" :key="i">
        <b-card :title="ctr.name" >
          <b-card-text>{{ctr.desc}}</b-card-text>
        </b-card>
      </b-col>
  </b-row>
</template>

<script>
export default {
  name:"Metric",
  props:{
    form:Object
  },
  mounted(){
    console.log(this.form);
  }
}
</script>

<style scoped>

</style>