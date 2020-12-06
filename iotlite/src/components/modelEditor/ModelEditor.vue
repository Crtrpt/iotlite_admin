<template>
    <div>
        <div ref="editor">
        </div>
    </div>
</template>

<script>
  // 引入全局实例
import CodeMirror from 'codemirror'
  // 核心样式
import 'codemirror/lib/codemirror.css'
import 'codemirror/theme/idea.css'


import 'codemirror/mode/javascript/javascript.js'
import 'codemirror/addon/hint/javascript-hint.js';


export default {
    name:"ModelEditor",
    props:{
        data:Object
    },
    data(){
        return {
            instance:null,
        }
    },
    methods:{
        changeTheme(){
            console.log(this.instance.setOption("theme", "idea"));
        }
    },
    mounted(){
        console.log(this.$refs.editor)
        console.log(this.data);
        this.instance= CodeMirror(this.$refs.editor, {
            value: this.data.value,
            mode:  this.data.mode,
            theme: "monokai",
            lineNumbers: true,
        });
        console.log(this.instance);
    }
}
</script>
<style scoped>

</style>