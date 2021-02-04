<template>
  <div class="mt-1">
      <b-row>
        <b-col>
          设备位置 以及周边设备的位置
        </b-col>
      </b-row>
  </div>
</template>

<script>
export default {
  name:"Map",
  data(){
    return {
      list:[
        { version: 'table cell', desc: 'table cell', deviceCount: 'table cell' },
      ]
    }
  }
}
</script>

<style scoped>

</style>