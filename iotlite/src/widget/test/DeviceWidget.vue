<template>
    <div class="grid-stack-item">
      <div class="grid-stack-item-content">
          Product Count 01
      </div>
    </div>
</template>

<script>
export default {
  name:"DeviceWidget",
  data(){
    return{
      meta:{
        w:2
      },
      v:0
    }
  },
  methods:{
    test(){
      this.v++
    }
  }
}
</script>