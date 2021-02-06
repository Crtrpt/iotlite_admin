<template>
  <b-row class="mt-2">
      <b-col cols="3" v-for="(ctr,i) in  form.product.spec.metric" :key="i">
        <b-card :title="ctr.name" >
          <b-card-text>{{ctr.desc}}</b-card-text>
            <LineChart ref="cav"  :chart-data="datacollection" :styles="{
              position: 'relative',
              height:'200px'
            }" ></LineChart>
        </b-card>
      </b-col>
  </b-row>
</template>

<script>
import LineChart from "./LineChart.js";

export default {
  name:"Metric",
  components:{
    LineChart
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
      fillData () {
        this.datacollection = {
          labels: [
             this.getRandomInt(),
             this.getRandomInt(),
             this.getRandomInt(),
             this.getRandomInt(),
             this.getRandomInt(),
             this.getRandomInt(),
             this.getRandomInt(),
             this.getRandomInt(),
             this.getRandomInt(),
             this.getRandomInt(),
                          this.getRandomInt(),
             this.getRandomInt(),
             this.getRandomInt(),
             this.getRandomInt(),
             this.getRandomInt(),
             this.getRandomInt(),
             this.getRandomInt(),
             this.getRandomInt(),
             this.getRandomInt(),
             this.getRandomInt()
             ],
          datasets: [
            {
              label: '温度',
              backgroundColor: "",
               pointStrokeColor : "#fff",
              data: [
                this.getRandomInt(),
                this.getRandomInt(),
                this.getRandomInt(),
                this.getRandomInt(),
                this.getRandomInt(),
                this.getRandomInt(),
                this.getRandomInt(),
                this.getRandomInt(),
                this.getRandomInt(),
                this.getRandomInt(),
                this.getRandomInt(),
                this.getRandomInt(),
                this.getRandomInt(),
                this.getRandomInt(),
                this.getRandomInt(),
                this.getRandomInt(),
                this.getRandomInt(),
                this.getRandomInt(),
                this.getRandomInt(),
                this.getRandomInt()
              ]
            }
          ]
        }
      },
      getRandomInt () {
        return Math.floor(Math.random() * (20 - 5 + 1)) + 5
      }
  },
  mounted(){
     this.fillData()

     setInterval(() => {
       var d=this.getRandomInt()
      // this.datacollection.labels.shift()
      //  this.datacollection.datasets[0].data.shift();
      
      this.datacollection.labels.push(d);
       this.datacollection.datasets[0].data.push(d)

     }, 2000);
  }
}
</script>

<style scoped>

</style>