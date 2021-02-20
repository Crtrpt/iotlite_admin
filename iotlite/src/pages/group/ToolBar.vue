<template>
   <b-col cols="12" class="mt-2 mb-2">
                  <b-button-toolbar>
                    <b-button-group  class="mr-2">
                      <b-modal id="new" title="新建分组" hide-footer>
                        <New />
                      </b-modal>
                      <b-button size="sm" variant="primary" v-b-modal.new  >新建分组</b-button>
                    </b-button-group>
                    <!-- <importFile></importFile> -->

                    <b-button-group  class="mr-2">
                      <b-form-input class="tool_input" v-model="query.words" placeholder="搜索"></b-form-input>
                    </b-button-group>

                    <b-button-group  class="mr-2 ">
                        <DateTimePicker  v-model="query.date" />
                    </b-button-group>

                     <b-button-group class="mr-2">
                      <b-button size="sm" variant="primary" @click="$emit('refresh',{})" >刷新</b-button>
                    </b-button-group>

                  </b-button-toolbar>
  </b-col>
</template>
<script>
import importFile from "../../components/export/ImportFile"
import DateTimePicker from "../../components/date/DateTimePicker"
import New from "./New"

export default {
  props:{
    query:Object
  },
  name:"Toolbar",
    components:{New,importFile,DateTimePicker},
}
</script>