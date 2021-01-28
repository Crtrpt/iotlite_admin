<template>
  <div class="sidebar-wrapper sidebar-theme">
            
            <nav id="sidebar">
                <div class="shadow-bottom"></div>

                <ul class="list-unstyled menu-categories ps ps--active-y" >

                     <li class="menu">
                        <router-link :to='{"name":"dashboard"}'  class="dropdown-toggle">
                            <div class="">
<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-home"><path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path><polyline points="9 22 9 12 15 12 15 22"></polyline></svg>                                <span>首页</span>
                            </div>
                        </router-link>
                    </li>


                     <li class="menu">
                        <a href="javascript:void();" v-b-toggle.accordion-user class="dropdown-toggle">
                            <div class="">
<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-users"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path><circle cx="9" cy="7" r="4"></circle><path d="M23 21v-2a4 4 0 0 0-3-3.87"></path><path d="M16 3.13a4 4 0 0 1 0 7.75"></path></svg>                              
                              <span>用户</span>
                            </div>
                             <div>
                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-chevron-right"><polyline points="9 18 15 12 9 6"></polyline></svg>
                            </div>
                        </a>
                         <b-collapse id="accordion-user" accordion="my-accordion" role="tabpanel">
                          <ul class="collapse submenu list-unstyled show" id="app" data-parent="#accordionExample">
                            <li>
                              <router-link :to='{"name":"organization"}' >组织机构</router-link>
                              <router-link :to='{"name":"user"}' >用户管理</router-link>
                            </li>
                          </ul>
                        </b-collapse>
                    </li>

                

                     <li class="menu">
                        <a href="javascript:void();"  v-b-toggle.accordion-2 class="dropdown-toggle">
                            <div class="">
                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-cpu"><rect x="4" y="4" width="16" height="16" rx="2" ry="2"></rect><rect x="9" y="9" width="6" height="6"></rect><line x1="9" y1="1" x2="9" y2="4"></line><line x1="15" y1="1" x2="15" y2="4"></line><line x1="9" y1="20" x2="9" y2="23"></line><line x1="15" y1="20" x2="15" y2="23"></line><line x1="20" y1="9" x2="23" y2="9"></line><line x1="20" y1="14" x2="23" y2="14"></line><line x1="1" y1="9" x2="4" y2="9"></line><line x1="1" y1="14" x2="4" y2="14"></line></svg>
                             <span>设备</span>
                            </div>
                            <div>
                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-chevron-right"><polyline points="9 18 15 12 9 6"></polyline></svg>
                            </div>
                        </a>
                        <b-collapse id="accordion-2" accordion="my-accordion" role="tabpanel">
                          <ul class="collapse submenu list-unstyled show" id="app" data-parent="#accordionExample">
                            <li>
                              <router-link :to='{"name":"product"}' >产品</router-link>
                              <router-link :to='{"name":"device"}' >设备</router-link>
                            </li>
                          </ul>
                        </b-collapse>
                    </li>

                    <li class="menu">
                        <a href="#product"  v-b-toggle.accordion-7 class="dropdown-toggle">
                            <div class="">
<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-box"><path d="M21 16V8a2 2 0 0 0-1-1.73l-7-4a2 2 0 0 0-2 0l-7 4A2 2 0 0 0 3 8v8a2 2 0 0 0 1 1.73l7 4a2 2 0 0 0 2 0l7-4A2 2 0 0 0 21 16z"></path><polyline points="3.27 6.96 12 12.01 20.73 6.96"></polyline><line x1="12" y1="22.08" x2="12" y2="12"></line></svg>                               
                             <span>产品</span>
                            </div>
                            <div>
                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-chevron-right"><polyline points="9 18 15 12 9 6"></polyline></svg>
                            </div>
                        </a>
                        <b-collapse id="accordion-7" visible accordion="my-accordion" role="tabpanel">
                          <ul class="collapse submenu list-unstyled show" id="app" data-parent="#accordionExample">
                            <li>
                               <router-link :to='{"name":"product"}' >产品</router-link>
                            </li>
                          </ul>
                        </b-collapse>
                    </li>


                    <li class="menu">
                        <a href="#system"  v-b-toggle.accordion-4 class="dropdown-toggle">
                            <div class="">
<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-terminal"><polyline points="4 17 10 11 4 5"></polyline><line x1="12" y1="19" x2="20" y2="19"></line></svg>                                <span></span>
                            <span>系统</span>
                            </div>
                            <div>
                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-chevron-right"><polyline points="9 18 15 12 9 6"></polyline></svg>
                            </div>
                        </a>
                        <b-collapse id="accordion-4"  accordion="my-accordion" role="tabpanel">
                          <ul class="collapse submenu list-unstyled show" id="app" data-parent="#accordionExample">
                             <li>
                               <router-link :to='{"name":"setting"}' >设置</router-link>
                                
                            </li>
                          </ul>
                        </b-collapse>
                    </li>

                    

                </ul>

                
                    

            </nav>

        </div>
</template>

<script>


export default {
  name:"LeftNav",
  mounted(){



  }
}
</script>

<style scoped>

</style>