<template>
      <b-button-group class="mr-2" >
          <b-modal id="import" title="导入"  size="lg">
              <b-row>
                 <b-col>
                    <b-form-file
                    v-model="file1"
                    :state="Boolean(file1)"
                    placeholder="选择导入文件"
                    drop-placeholder="Drop file here..."
                  ></b-form-file>
                 </b-col>
              </b-row>
               
              <b-row>
                  <b-col>
                      导入预览
                 </b-col>
              </b-row>
          </b-modal>

           <b-modal id="export" title="导出"  size="lg">
               


                <b-overlay :show="true"  :opacity="0" rounded="sm" class="col col-12">
                    <b-col cols="12">
                      正在检索数据... <br>
                      正在预处理...<br>
                      正在预处理...<br>
                      正在预处理...<br>
                      正在预处理...<br>
                      正在预处理...<br>
                      正在预处理...<br>
                      正在预处理...<br>
                      正在预处理...<br>
                      处理进度 99%...<br>
                      完成...<br>
                    </b-col>
                  </b-overlay>



          </b-modal>
          <b-button size="sm" variant="outline-primary"  v-b-modal.import >导入</b-button>
          <b-button size="sm" variant="outline-primary" v-b-modal.export  >导出</b-button>
      </b-button-group>
   

</template>