<template>
  <div class="mt-1">
      <b-row>
        <b-col cols="4">
          <b-form @submit="onSubmit" >
            <b-form-group label="经度:" description="地图默认经纬度" >
              <b-form-input
                v-model="form.name" type="text" required placeholder="经纬度"
              ></b-form-input>
            </b-form-group>

            <b-button type="submit" variant="primary">保存</b-button>
          </b-form>
        </b-col>
      </b-row>
  </div>
</template>

<script>
import {product} from "../../../api/product"
export default {
  name:"Setting",
  data(){
    return {
      form:{

      }
    }
  },
  methods:{
    onSubmit(){
      product.saveSetting().then((res)=>{
            if(res.code==0){
              _this.$bvModal.msgBoxOk("保存成功")
            }else{
              _this.$bvModal.msgBoxOk(res.msg)
            }
      })
    }
  }
}
</script>

<style scoped>

</style>