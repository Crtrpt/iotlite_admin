<template>
  <div class="header">

      <b-sidebar id="sidebar-1" title="Menu" shadow>
      <div class="px-3 py-2">
            <b-nav vertical class="w-25">
              <b-nav-item active>产品</b-nav-item>
              <b-nav-item>设备</b-nav-item>
              <b-nav-item>网关</b-nav-item>
              <b-nav-item>组织</b-nav-item>
              <b-nav-item>用户</b-nav-item>
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