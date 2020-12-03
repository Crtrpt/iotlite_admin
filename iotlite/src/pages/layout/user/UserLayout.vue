<template>
  <div class="user_layout">
    <Header />
      <router-view class="content"></router-view>
    <Footer />
  </div>
</template>

<script>
import Header from '../master/Header';

import Footer from '../master/Footer';

export default {
  name: 'UserLayout',
  computed: {
  },
  components: { Header,Footer},
  data(){
    return {
      
    }
  }
};
</script>

<style scoped>
.master_layout {
  display: flex;
  height: 100vh;
}
.logo {
  height: 50px;
  background-color: rgb(79, 233, 151);
  line-height: 50px;
  text-align: center;
  color: white;
}

.left {
  width: 50px;
  display: flex;
  flex-direction: column;
  background-color: rgb(57, 61, 69);
}

.top-header {
  height: 50px;
  padding-bottom: 0px;
  display: flex;
  background-color: rgb(57, 61, 69);
}

.top-tab {
  flex-grow: 1;
}

.content {
  flex-grow: 1;
  width: 0;
  display: flex;
  flex-direction: column;
}
.warp_content {
  display: flex;
  flex-grow: 1;
  height: 0;
  overflow: auto;
}
</style>
