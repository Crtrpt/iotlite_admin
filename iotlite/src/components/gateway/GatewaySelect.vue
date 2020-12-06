<template>
    <treeselect v-model="v" :multiple="false" :options="options"  @input="change"  />
</template>

<script>

  import Treeselect from '@riophae/vue-treeselect'
  // import the styles
  import '@riophae/vue-treeselect/dist/vue-treeselect.css'

import { gateway } from '../../api/gateway.js'

export default {
    name:"Gateway",
    components: { Treeselect },
    props:{
        value:Number,
    },
    data(){
        return {
            v:0,
            options:[
                {
                    id:"1",
                    label:"111"
                }
            ]
        }
    },
    mounted(){
        var _this=this;
        this.v=this.value
        gateway.all({}).then(res=>{
            _this.options=res.data;
        })
    },
    methods:{
        change(v){
            this.$emit("input",v);
        }
    }
}
</script>