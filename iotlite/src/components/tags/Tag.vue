<template>
  <vue-tags-input
      v-model="tag"
      :tags="tags"
      placeholder="添加标签"
      @tags-changed="newTags => change(newTags)"
    />
</template>

<script>
import VueTagsInput from '@johmun/vue-tags-input/dist/vue-tags-input';
export default {
  name:"Tag",
  components:{
    VueTagsInput
  },
  props:{
    value:Array
  },
  data(){
    return {
      tag: ''
    }
  },
  computed: {
    tags (){
      if(this.value!=null){
        var tags=[];
        this.value.forEach((e)=>{
          tags.push({
            text:e,
            tiClasses:[
              "ti-valid"
            ]
          })
        })
      return tags;
      }else{
        return [];
      }
     
    }
  },
  methods:{
    change(newTags){
       console.log(this.value);
      var payload=[]
      newTags.forEach(t => {
        payload.push(t.text)
      });
      this.$emit("input",payload);
    }
  }
}
</script>

<style lang="css">
  /* style the background and the text color of the input ... */
  .vue-tags-input {
    background: none !important;
  }

  .vue-tags-input .ti-new-tag-input {
    background: transparent;
    color: #b7c4c9;
  }

  .vue-tags-input .ti-input {
    padding: 0px !important;
    border: none!important;
    transition: border-bottom 200ms ease;
  }

  /* we cange the border color if the user focuses the input */
  .vue-tags-input.ti-focus .ti-input {
    border: none !important;;
  }

  /* some stylings for the autocomplete layer */
  .vue-tags-input .ti-autocomplete {
    background: #283944;
    border: 1px solid #8b9396;
    border-top: none;
  }

  /* the selected item in the autocomplete layer, should be highlighted */
  .vue-tags-input .ti-item.ti-selected-item {
    background: white;
    color: #283944;
  }

  /* style the placeholders color across all browser */
  .vue-tags-input ::-webkit-input-placeholder {
    color: #a4b1b6;
  }

  .vue-tags-input ::-moz-placeholder {
    color: #a4b1b6;
  }

  .vue-tags-input :-ms-input-placeholder {
    color: #a4b1b6;
  }

  .vue-tags-input :-moz-placeholder {
    color: #a4b1b6;
  }

  /* default styles for all the tags */
  .vue-tags-input .ti-tag {
    position: relative;
    background: var(--yellow);
    color: #283944;
  }

  /* we defined a custom css class in the data model, now we are using it to style the tag */
  .vue-tags-input .ti-tag.custom-class {
    background: transparent;
    border: 1px solid #ebde6e;
    color: #ebde6e;
    margin-right: 4px;
    border-radius: 0px;
    font-size: 13px;
  }

  /* the styles if a tag is invalid */
  .vue-tags-input .ti-tag.ti-invalid {
    background-color: #e88a74;
  }

  /* if the user input is invalid, the input color should be red */
  .vue-tags-input .ti-new-tag-input.ti-invalid {
    color: #e88a74;
  }

  /* if a tag or the user input is a duplicate, it should be crossed out */
  .vue-tags-input .ti-duplicate span,
  .vue-tags-input .ti-new-tag-input.ti-duplicate {
    text-decoration: line-through;
  }

  /* if the user presses backspace, the complete tag should be crossed out, to mark it for deletion */
  .vue-tags-input .ti-tag:after {
    transition: transform .2s;
    position: absolute;
    content: '';
    height: 2px;
    width: 108%;
    left: -4%;
    top: calc(50% - 1px);
    background-color: #000;
    transform: scaleX(0);
  }

  .vue-tags-input .ti-deletion-mark:after {
    transform: scaleX(1);
  }
</style>