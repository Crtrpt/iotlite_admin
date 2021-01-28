<template>
    <div class="grid-stack-item">
      <div class="grid-stack-item-content">
          <b-row>
            <b-col><h5>Product</h5></b-col>
            <b-col><h5>4</h5>
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