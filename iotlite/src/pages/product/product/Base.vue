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
                      <b-td> {{form.type?"device":"gateway"}} </b-td>
                      <b-td  variant="light" class="text-right">desc:</b-td>
                      <b-td > {{form.description}} </b-td>
                    </b-tr>
                    <b-tr>

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