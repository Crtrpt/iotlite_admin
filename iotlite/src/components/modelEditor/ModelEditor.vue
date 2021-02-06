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
import 'codemirror/theme/monokai.css'


import 'codemirror/mode/javascript/javascript.js'
import 'codemirror/addon/hint/javascript-hint.js';

import 'codemirror/addon/search/search.js';
import 'codemirror/addon/search/searchcursor.js';
import 'codemirror/addon/search/jump-to-line.js'; 
import 'codemirror/addon/dialog/dialog.js'; 
import 'codemirror/addon/dialog/dialog.css'; 
import 'codemirror/addon/fold/foldgutter.css'; 
import 'codemirror/addon/fold/foldcode.js';
import 'codemirror/addon/fold/foldgutter.js';  
import 'codemirror/addon/fold/brace-fold.js';  


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
            lineWrapping: true,
            foldGutter: true,
            gutters: ["CodeMirror-linenumbers", "CodeMirror-foldgutter"]
        });
        console.log(this.instance);
    }
}
</script>
<style scoped>

</style>