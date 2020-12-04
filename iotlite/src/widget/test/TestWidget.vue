<template>
    <div class="grid-stack-item">
      <div class="grid-stack-item-content">
       

         <b-overlay >
            <b-card title="用户信息" >
              <b-card-text>{{v}}111111111111111

                1
                1
                1
                1
                1
                1
                1
                
              </b-card-text>
              <b-button variant="primary"  @click="test">
                click
              </b-button>
            </b-card>
          </b-overlay>
      </div>
    </div>
</template>

<script>
export default {
  name:"TestWidget",
  data(){
    return{
      v:0
    }
  },
  methods:{
    test(){
      this.v++
    }
  }
}
</script>