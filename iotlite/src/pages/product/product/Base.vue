<template>
  <b-row class="mt-2">
      <b-col cols="6">
       <b-row>
          <b-col>
            <b-row>
              <b-col><h4>Info</h4></b-col>
            </b-row>
            <b-row>
              <b-col>
              <b-table-simple  responsive :bordered="true" :fixed=true>
                  <b-tbody>
                    <b-tr>
                      <b-td  variant="light" class="text-right">name:</b-td>
                      <b-td>{{form.name}}</b-td>
                      <b-td  variant="light" class="text-right">key:</b-td>
                      <b-td>*** <b-icon icon="eye"/></b-td>
                    </b-tr>
                    <b-tr>
                      <b-td  variant="light" class="text-right">type:</b-td>
                      <b-td>gas </b-td>
                      <b-td  variant="light" class="text-right">network:</b-td>
                      <b-td>wifi </b-td>
                    </b-tr>
                    <b-tr>
                      <b-td  variant="light" class="text-right">gateway:</b-td>
                      <b-td>self-host </b-td>
                      <b-td  variant="light" class="text-right">protocol:</b-td>
                      <b-td> http </b-td>
                    </b-tr>
                    <b-tr>
                    <b-td  variant="light" class="text-right">desc:</b-td>
                      <b-td colspan="3"> {{form.description}} </b-td>
                    </b-tr>
                  </b-tbody>
              </b-table-simple>
              </b-col>
        
          </b-row>
          <b-row>
              <b-col><h4>Statistics</h4></b-col>
              
          </b-row>
           <b-row>
            <b-col> <b-table-simple  responsive :bordered="true" :fixed=true>
                <b-tbody>
                  <b-tr>
                    <b-td  variant="light" class="text-right">device:</b-td>
                    <b-td>920/100</b-td>
                    <b-td  variant="light" class="text-right">online:</b-td>
                    <b-td>98%</b-td>
                  </b-tr>
                 
                </b-tbody>
            </b-table-simple></b-col>
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