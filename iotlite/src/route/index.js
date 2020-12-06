import  MasterLayout  from "../pages/layout/master/MasterLayout";
import  UserLayout  from "../pages/layout/user/UserLayout";

import product from "./product"
import device from "./device"
import organization from "./organization"
import user from "./user"
import gateway from "./gateway"
import dashboard from "./dashboard"

var routes = [
  {
    path: '/',
    component: MasterLayout,
    name: 'root',
    redirect: { name: 'home' },
    children: [
      {
        path: 'home',
        component: require('../pages/Home').default,
        name: 'home',
        meta:{
          name:"home"
        }
      },
      ...dashboard,
      ...device,
      ...product,
      ...gateway,
      ...organization,
      ...user,
      {
        path: 'gateway',
        component: require('../pages/gateway/Index').default,
        name: 'gateway',
        meta:{
          name:"setting"
        }
      }
    ],
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
