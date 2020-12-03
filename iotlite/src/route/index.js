
import  MasterLayout  from "../pages/layout/master/MasterLayout";
import  UserLayout  from "../pages/layout/user/UserLayout";
var routes = [
  {
    path: '/',
    component: MasterLayout,
    name: 'home',
    redirect: { name: 'dashboard' },
    children: [
      {
        path: 'dashboard',
        component: require('../pages/Dashboard').default,
        name: 'dashboard',
        meta:{
          name:"首页"
        }
      }

    ],
  },
  {
    path: '/user',
    component: UserLayout,
    name: 'user',
  },
  {
    path: '/passport',
    component: require('../pages/auth/Auth').default,
    name: 'login',
  },
  {
    path: '/register',
    component: require('../pages/auth/Register').default,
    name: 'register',
  },
  { path: '*', component:  require('../pages/NotFound').default }
]

export default routes
