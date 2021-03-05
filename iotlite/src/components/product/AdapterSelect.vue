<template>
    <treeselect v-model="v" :multiple="false" :options="options"  @input="change"  />
</template>

<script>

  import Treeselect from '@riophae/vue-treeselect'
  // import the styles
  import '@riophae/vue-treeselect/dist/vue-treeselect.css'

import { product } from '../../api/product.js'

export default {
    name:"ProductAdapterSelect",
    components: { Treeselect },
    props:{
        value:Number,
    },
    data(){
        return {
            v:0,
            options:[
            ]
        }
    },
    mounted(){
        var _this=this;
        this.v=this.value
        product.allAdapter({}).then(res=>{
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