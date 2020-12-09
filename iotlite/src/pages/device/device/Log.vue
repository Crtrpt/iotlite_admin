<template>
  <div>
      <b-table hover :items="items"></b-table>
  </div>
</template>

<script>
export default {
  name:"Log",
  data(){
    return {
      items: [
          { id: 40, createTime: "23点42分", source: '127.0.0.1',target:"127.0.0.1",direction:"↑",description:"开机自检" },
          { id: 40, createTime: "23点42分", source: '127.0.0.1',target:"127.0.0.1",direction:"↑",description:"开机自检" },
          { id: 40, createTime: "23点42分", source: '127.0.0.1',target:"127.0.0.1",direction:"↑",description:"开机自检" },
          { id: 40, createTime: "23点42分", source: '127.0.0.1',target:"127.0.0.1",direction:"↑",description:"开机自检" },
          { id: 40, createTime: "23点42分", source: '127.0.0.1',target:"127.0.0.1",direction:"↑",description:"开机自检" },
          { id: 40, createTime: "23点42分", source: '127.0.0.1',target:"127.0.0.1",direction:"↑",description:"开机自检" },
          { id: 40, createTime: "23点42分", source: '127.0.0.1',target:"127.0.0.1",direction:"↑",description:"开机自检" },
          { id: 40, createTime: "23点42分", source: '127.0.0.1',target:"127.0.0.1",direction:"↑",description:"开机自检" },
          { id: 40, createTime: "23点42分", source: '127.0.0.1',target:"127.0.0.1",direction:"↑",description:"开机自检" },
          { id: 40, createTime: "23点42分", source: '127.0.0.1',target:"127.0.0.1",direction:"↑",description:"开机自检" },
      ]
    }
  }
}
</script>

<style scoped>

</style>