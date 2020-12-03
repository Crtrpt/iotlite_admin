<template>
  <b-container fluid class="dashboard">
    <section class="grid-stack"></section>
  </b-container>
</template>

<script>

import 'gridstack/dist/gridstack.min.css'
import GridStack from "../../node_modules/gridstack/dist/gridstack-h5"
export default {
  name: 'Dashboard',
  data(){
        return  {
          grid: undefined,
          count: 0,
          info: "",
          timerId: undefined,
        items: [
            { w:2, content:  '' },
            { w:8, content:   ''},
            { w:2, content:   ''},
            { w:2, content:   '' },
            { w:8,h:3, content: '' },
            { w:2, content: '' },
            { w:2, content: ''},
            { w:2, content: '' },
            { w:2, content: '' },
            { w:2, content: '' },
            { w:2, content: '' },
            { w:2, content: ''},
            { w:2, content: '' },
            { w:2, content: '' },
            { w:2, content: '' },
            { w:2, content: '' },
          ],
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
        },
        methods: {
          addNewWidget: function () {
            const node = this.$options.items[this.count] || {
              x: Math.round(12 * Math.random()),
              y: Math.round(5 * Math.random()),
              w: Math.round(1 + 3 * Math.random()),
              h: Math.round(1 + 3 * Math.random()),
            };
            node.id = node.content = String(this.count++);
            this.grid.addWidget(node);
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
  /* .grid-stack { background: #FAFAD2; }
  .grid-stack-item-content { background-color: #18BC9C; } */
</style>
