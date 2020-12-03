const user =[
  {
    path: 'user',
    component: require('../pages/user/Index').default,
    name: 'user',
    meta:{
      name:"user"
    }
  },
  {
    path: 'user/:id',
    component: require('../pages/user/Detail').default,
    name: 'userDetail',
    redirect: { name: 'userDetailBase' },
    meta:{
      name:"userDetail"
    },
    children:[
      {
        path: 'base',
        component: require('../pages/user/user/Base').default,
        name: 'userDetailBase',
        meta:{
          name:"userDetailBase"
        }
      },
      {
        path: 'log',
        component: require('../pages/user/user/Log').default,
        name: 'userDetailLog',
        meta:{
          name:"userDetailLog"
        }
      },
    ]
  },
]
export default user
