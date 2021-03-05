
const group  =[
  {
    path: 'group',
    component: require('../pages/group/Index').default,
    name: 'group',
    meta:{
      name:"group"
    }
  },
  {
  path: 'group/:id',
  component: require('../pages/group/Detail').default,
  name: 'groupDetail',
  redirect: { name: 'groupDeviceList' },
  meta:{
    name:"groupDetail"
  },
  children:[
  //   {
  //     path: 'base',
  //     component: require('../pages/group/group/Base').default,
  //     name: 'groupDetailBase',
  //     meta:{
  //       name:"groupDetailBase"
  //     }
  //   },
  //   {
  //     path: 'model',
  //     component: require('../pages/group/group/Model').default,
  //     name: 'groupDetailModel',
  //     meta:{
  //       name:"groupDetailModel"
  //     }
  //   },
    {
      path: 'playground',
      component: require('../pages/group/group/Playground').default,
      name: 'groupPlayground',
      meta:{
        name:"groupPlayground"
      }
    },
    {
      path: 'map',
      component: require('../pages/group/group/Map').default,
      name: 'groupDetailMap',
      meta:{
        name:"groupDetailMap"
      }
    },
    {
      path: 'log',
      component: require('../pages/group/group/Log').default,
      name: 'groupDetailLog',
      meta:{
        name:"groupDetailLog"
      }
    },
    {
      path: 'device',
      component: require('../pages/group/group/Device').default,
      name: 'groupDeviceList',
      meta:{
        name:"groupDeviceList"
      }
    },
  //   {
  //     path: '3d',
  //     component: require('../pages/group/group/3D').default,
  //     name: 'group3D',
  //     meta:{
  //       name:"group3D"
  //     }
  //   }
  ]
}
]
export default group