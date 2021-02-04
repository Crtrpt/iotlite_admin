<template>
    <b-row class="mt-2">
      <b-col cols="6">
       <b-row>
          <b-col>
            <b-row>
              <b-col><h5>设备信息</h5></b-col>
            </b-row>
            <b-row>
              <b-col>
              <b-table-simple  responsive :bordered="true" :fixed=true>
                  <b-tbody>
                    <b-tr>
                      <b-td  variant="light" class="text-right">设备名称:</b-td>
                      <b-td>{{form.name}}</b-td>
                      <b-td  variant="light" class="text-right">设备序号:</b-td>
                      <b-td>{{form.sn}}</b-td>
                    </b-tr>
                     <b-tr>
                      <b-td  variant="light" class="text-right">创建时间:</b-td>
                      <b-td>2021年2月4日14:40:36</b-td>
                      <b-td  variant="light" class="text-right">维护时间:</b-td>
                      <b-td>2021年2月4日14:40:42</b-td>
                    </b-tr>
                  </b-tbody>
              </b-table-simple>
              </b-col>
            </b-row>

            <b-row>
              <b-col><h5>产品信息</h5></b-col>
            </b-row>
            <b-row>
              <b-col>
              <b-table-simple  responsive :bordered="true" :fixed=true>
                  <b-tbody>
                    <b-tr>
                      <b-td  variant="light" class="text-right">产品:</b-td>
                      <b-td>{{form.product.name}}</b-td>
                       <b-td  variant="light" class="text-right">产品序号:</b-td>
                      <b-td>{{form.product.sn}}</b-td>
                    </b-tr>
                  </b-tbody>
              </b-table-simple>
              </b-col>
        
            </b-row>

              <b-row>
              <b-col><h5>基本属性</h5></b-col>
            </b-row>
            <b-row>
              <b-col>
              <b-table-simple  responsive :bordered="true" :fixed=true>
                  <b-tbody>
                    <b-tr>
                      <b-td  variant="light" class="text-right">度数:</b-td>
                      <b-td>65</b-td>
                       <b-td  variant="light" class="text-right">开机时长:</b-td>
                      <b-td>21h</b-td>
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