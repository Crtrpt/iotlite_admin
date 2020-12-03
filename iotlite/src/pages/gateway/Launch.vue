<template>
  <div>
    <b-form @submit="onSubmit" >
       <b-form-group
        id="input-group-1"
        label="Type:"
        description="gateway type gas smoke and so on"
      >
        <b-form-input
          v-model="form.type"
          type="text"
          required
          list="type-list"
          placeholder="enter gateway type"
        ></b-form-input>
        <b-form-datalist id="type-list" :options="gatewayType"></b-form-datalist>
      </b-form-group>
      <b-form-group
        id="input-group-1"
        label="Name:"
        description="gateway name"
      >
        <b-form-input
          v-model="form.name"
          type="text"
          required
          placeholder="Enter gateway Name"
        ></b-form-input>
      </b-form-group>
      <b-form-group
        id="input-group-1"
        label="Desc:"
        description="gateway desc"
      >
        <b-form-input
          v-model="form.description"
          type="text"
          required
          placeholder="enter gateway description"
        ></b-form-input>
      </b-form-group>
     
       <b-button type="submit" variant="primary">Submit</b-button>
    </b-form>
  </div>
</template>

<script>
export default {
  name:"Launch",
  data(){
    return {
      gatewayType:[
          "Self Host","Amazon","Ali","Onenet","Ctwing"
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