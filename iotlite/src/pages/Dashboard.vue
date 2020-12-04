<template>
  <b-container fluid class="dashboard">
    <b-row>

      <b-col cols="1">
        <b-button @click="addNewWidget">Add</b-button>
      </b-col>
      <b-col>
        <section class="grid-stack"></section>
      </b-col>
    </b-row>
    
  </b-container>
</template>

<script>

import 'gridstack/dist/gridstack.min.css'
import Vue from 'vue'
import GridStack from "../../node_modules/gridstack/dist/gridstack-h5"

import TestWidget from "../widget/test/TestWidget" 

export default {
  name: 'Dashboard',
  data(){
        return  {
          grid: undefined,
           items:[],
          // items: [
          //     { w:2, content:  '' },
          //     { w:8, content:   ''},
          //     { w:2, content:   ''},
          //     { w:2, content:   '' },
          //     { w:8,h:3, content: '' },
          //     { w:2, content: '' },
          //     { w:2, content: ''},
          //     { w:2, content: '' },
          //     { w:2, content: '' },
          //     { w:2, content: '' },
          //     { w:2, content: '' },
          //     { w:2, content: ''},
          //     { w:2, content: '' },
          //     { w:2, content: '' },
          //     { w:2, content: '' },
          //     { w:2, content: '' },
          //   ],
          }
        },
          watch: {
            /**
             * Clear the info text after a two second timeout. Clears previous timeout first.
             */
            info: function (newVal, oldVal) {
              if (newVal.length === 0) return;

              window.clearTimeout(this.timerId);
              this.timerId = window.setTimeout(() => {
                this.info = "";
              }, 2000);
            },
          },
          mounted: function () {
          this.grid = GridStack.init({
          });
          this.grid.load(this.items)
          this.grid.on("dragstop", (event, element) => {
              console.log("更新")
          });
          this.grid.on('change', function(e,i) {
            console.log("更新存储")
          });
        },
        methods: {
          addNewWidget: function () {
            const node = this.items[this.count] || {};
            console.log(node);
            node.id = node.content = "<p>111</p>";
            //  this.grid.addWidget( node);
            var w=   Vue.extend(TestWidget);
         
            var   a=new w().$mount().$el
            this.grid.addWidget(a);
          },
        },
  components: {  },
};
</script>

<style >
  .dashboard {
    padding: 0;
    margin: 0;
  }
  .grid-stack { background: #FAFAD2; }
  .grid-stack-item-content { background-color: #18BC9C; }
</style>
