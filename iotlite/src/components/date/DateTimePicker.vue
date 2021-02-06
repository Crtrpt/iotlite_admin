<template>
    <VueCtkDateTimePicker color="#1a1c2d" 
    button-now-translation="现在" 
    :range='true'
    :noLabel="true"
    label="选择时间范围" v-model="value.date" />
</template>

<script>
import VueCtkDateTimePicker from 'vue-ctk-date-time-picker';
import './date.time.picker.css';
export default {
  name:"DateTimePicker",
  components:{
    VueCtkDateTimePicker
  },
  props:{
    value:Object
  }
}
</script>