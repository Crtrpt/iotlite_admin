<template>
   <b-col cols="12" class="mt-2 mb-2">
                  <b-button-toolbar>
                    <b-button-group  class="mr-2">
                      <b-modal id="new" title="新建设备" hide-footer>
                        <New />
                      </b-modal>
                      <b-button size="sm" variant="primary" v-b-modal.new  >新建设备</b-button>

                    </b-button-group>
                    <importFile></importFile>

                    <!-- <b-button-group>
                      <b-dropdown  text="高级" >
                        <b-dropdown-item>全选</b-dropdown-item>
                        <b-dropdown-item>全不选</b-dropdown-item>
                         <b-dropdown-item>分组...</b-dropdown-item>
                      
                        <b-dropdown-divider></b-dropdown-divider>
                        <b-dropdown-item >删除</b-dropdown-item>
                        <b-dropdown-item >禁用</b-dropdown-item>
                      </b-dropdown>
                    </b-button-group> -->
                  </b-button-toolbar>
  </b-col>
</template>
<script>
import importFile from "../../components/export/ImportFile"
import New from "./New"

export default {
  name:"Toolbar",
    components:{New,importFile},
}
</script>