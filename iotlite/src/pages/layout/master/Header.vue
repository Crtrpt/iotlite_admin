<template>
  <div class="header">

      <b-sidebar id="sidebar-1" title="Menu" shadow>
      <div class="px-3 py-2">
            <b-nav vertical class="w-25">
              <b-nav-item :to="{name:'home'}">Home</b-nav-item>
              <b-nav-item :to="{name:'dashboard'}">Dashboard</b-nav-item>
              <b-nav-item :to="{name:'product'}">Product</b-nav-item>
              <b-nav-item :to="{name:'device'}">Device</b-nav-item>
              <b-nav-item :to="{name:'gateway'}">Gateway</b-nav-item>
              <b-nav-item :to="{name:'organization'}">Organization</b-nav-item>
              <b-nav-item :to="{name:'user'}">User</b-nav-item>
            </b-nav>
      </div>
    </b-sidebar>
       <div>
          <b-nav>
            <b-nav-item v-b-toggle.sidebar-1>
                <b-icon icon="text-left" aria-hidden="true"></b-icon>
            </b-nav-item>
          </b-nav>
        </div>
  </div>
</template>

<script>
export default {
  name:"Header"
}
</script>

<style scoped>

</style>