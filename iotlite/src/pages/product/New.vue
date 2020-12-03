<template>
  <div>
    <b-form @submit="onSubmit" >
      <b-form-group
        id="input-group-1"
        label="Name:"
        description="product name"
      >
        <b-form-input
          v-model="form.name"
          type="text"
          required
          placeholder="Enter Product Name"
        ></b-form-input>
      </b-form-group>
      <b-form-group
        id="input-group-1"
        label="Desc:"
        description="product desc"
      >
        <b-form-input
          v-model="form.description"
          type="text"
          required
          placeholder="enter product description"
        ></b-form-input>
      </b-form-group>
      <b-form-group
        id="input-group-1"
        label="Type:"
        description="product type gas smoke and so on"
      >
        <b-form-input
          v-model="form.type"
          type="text"
          required
          list="type-list"
          placeholder="enter product type"
        ></b-form-input>
        <b-form-datalist id="type-list" :options="productType"></b-form-datalist>
      </b-form-group>
       <b-button type="submit" variant="primary">Submit</b-button>
    </b-form>
  </div>
</template>

<script>
export default {
  name:"New",
  data(){
    return {
      productType:[
          "烟感","燃气"
      ],
      form:{

      }
    }
  },
  methods:{
    onSubmit(){

    }
  }
}
</script>

<style scoped>

</style>