<template>
      <b-button-group class="mr-2" >
          <b-modal id="import" title="导入" size="lg" cancel-title="取消" ok-title="确认">
              <b-row>
                 <b-col>
                   <b-form inline>
                      <b-form-group class="mr-2" label="上传文件:" label-cols-sm="3">
                        <b-form-file size="sm" placeholder="拖放上传" browse-text="选择" ></b-form-file>
                      </b-form-group>
                     <b-button size="sm" variant="primary">下载导入模板</b-button>
                  </b-form>
                 </b-col>
              </b-row>

              <b-row>
                  <b-col>
                    <b-table class="mt-5" striped hover :items="items"></b-table>
                 </b-col>
              </b-row>
          </b-modal>

           <b-modal id="export" title="导出"  size="lg" cancel-title="取消" ok-title="确认">
                <b-overlay :show="true"  :opacity="0" rounded="sm" class="col col-12">
                    <b-col cols="12">
                      正在检索数据... <br>
                      处理进度 99%...<br>
                      完成...<br>
                    </b-col>
                </b-overlay>
          </b-modal>
          <b-button size="sm" variant="outline-primary"  v-b-modal.import >导入</b-button>
          <b-button size="sm" variant="outline-primary" v-b-modal.export  >导出</b-button>
      </b-button-group>

</template>

<script>
export default {
  name:"Import",
  data:()=>{
    return {
      items:[
        { 
          age: 40, 
          first_name: 'Dickerson', 
          last_name: 'Macdonald',
          x:'1',
          y:'2',
          x1:'3',
          x2:'4',
           },
      ],
    }
  }
}
</script>