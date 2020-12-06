<template>
    <div class="grid-stack-item">
      <div class="grid-stack-item-content">
          <b-row>
            <b-col><h1>Product</h1></b-col>
            <b-col><h1>4</h1>
            </b-col>
          </b-row>
      </div>
    </div>
</template>

<script>
export default {
  name:"ProductWidget",
  meta:{
    w:2
  },
  data(){
    return{

    }
  },
  methods:{
    test(){
      this.v++
    }
  }
}
</script>