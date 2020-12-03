<template>
  <div class="master_layout">
    <Header />
      <router-view class="content"></router-view>
    <Footer />
  </div>
</template>

<script>
import Header from './Header';
import Footer from './Footer';

export default {
  name: 'MasterLayout',
  computed: {
  },
  components: { Header,Footer},
  data(){
    return {
      showLogin:false
    }
  }
};
</script>

<style scoped>
.master_layout {
  display: flex;
  height: 100vh;
  flex-direction: column;
}



</style>
