<template>
  <div>
    <b-form @submit="onSubmit" >
      <b-form-group
        id="input-group-1"
        label="Name:"
        description="User name"
      >
        <b-form-input
          v-model="form.name"
          type="text"
          required
          placeholder="Enter User Name"
        ></b-form-input>
      </b-form-group>
      <b-form-group
        id="input-group-1"
        label="Desc:"
        description="User desc"
      >
        <b-form-input
          v-model="form.description"
          type="text"
          required
          placeholder="enter User description"
        ></b-form-input>
      </b-form-group>
      <b-form-group
        id="input-group-1"
        label="Type:"
        description="User type gas smoke and so on"
      >
        <b-form-input
          v-model="form.type"
          type="text"
          required
          list="type-list"
          placeholder="enter User type"
        ></b-form-input>
        <b-form-datalist id="type-list" :options="UserType"></b-form-datalist>
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
      UserType:[
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