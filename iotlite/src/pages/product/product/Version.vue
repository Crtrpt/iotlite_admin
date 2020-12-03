<template>
  <div class="mt-1">
       <b-table sticky-header :items="list" head-variant="light"></b-table>
  </div>
</template>

<script>
export default {
  name:"Version",
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