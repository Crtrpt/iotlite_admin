<template>
  <b-row class="mt-2">
      <b-col cols="6">
       <b-row>
          <b-col>
            <b-row>
              <b-col><h5>产品信息</h5></b-col>
            </b-row>
            <b-row>
              <b-col>
              <b-table-simple  responsive :bordered="true" :fixed=true>
                  <b-tbody>
                    <b-tr>
                      <b-td  variant="light" class="text-right">设备名称:</b-td>
                      <b-td>{{form.name}}</b-td>
                       <b-td  variant="light" class="text-right">序号:</b-td>
                      <b-td>{{form.sn}}</b-td>
                    </b-tr>
                     <b-tr>
                      <b-td  variant="light" class="text-right">认证Key:</b-td>
                      <b-td>{{form.secKey|***}}</b-td>
                       <b-td ></b-td>
                      <b-td></b-td>
                    </b-tr>
                     <b-tr>
                      <b-td  variant="light" class="text-right">设备数量:</b-td>
                      <b-td>{{form.deviceCount||0}}</b-td>
                       <b-td  variant="light" class="text-right">维保数量:</b-td>
                      <b-td>{{form.maintainCount||0}}</b-td>
                    </b-tr>
                  </b-tbody>
                 
              </b-table-simple>
              </b-col>
        
          </b-row>
          </b-col> 
          
        </b-row>
      
      </b-col>
  </b-row>
</template>

<script>
export default {
  name:"Base",
  props:{
    form:Object
  },
}
</script>

<style scoped>

</style>