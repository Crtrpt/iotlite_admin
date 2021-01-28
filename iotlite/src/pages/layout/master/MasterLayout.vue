<template>
  <div class="master_layout">
    <Header />
    <SubHeader />
      <div class="main-container" id="container">
          <div class="overlay"></div>
          <div class="search-overlay"></div>
         <LeftNav  />
          <div id="content" class="main-content">
              <div class="layout-px-spacing">
                  <div class="row layout-top-spacing">
                        <router-view  ></router-view>
                  </div>
              </div>
          </div>
          
          
      </div>
    <Footer />
  </div>
</template>

<script>
import Header from './Header';
import LeftNav from './LeftNav';
import Footer from './Footer';
import SubHeader from './SubHeader.vue';

export default {
  name: 'MasterLayout',
  computed: {
  },
  components: { Header,Footer,LeftNav,SubHeader},
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
