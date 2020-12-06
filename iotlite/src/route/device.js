const device  =[
  {
    path: 'device',
    component: require('../pages/device/Index').default,
    name: 'device',
    meta:{
      name:"device"
    }
  },
  {
  path: 'device/:id',
  component: require('../pages/device/Detail').default,
  name: 'deviceDetail',
  redirect: { name: 'deviceDetailBase' },
  meta:{
    name:"deviceDetail"
  },
  children:[
    {
      path: 'base',
      component: require('../pages/device/device/Base').default,
      name: 'deviceDetailBase',
      meta:{
        name:"deviceDetailBase"
      }
    },
    {
      path: 'control',
      component: require('../pages/device/device/Control').default,
      name: 'deviceControl',
      meta:{
        name:"deviceControl"
      }
    },
    {
      path: 'metric',
      component: require('../pages/device/device/Metric').default,
      name: 'deviceMetric',
      meta:{
        name:"deviceMetric"
      }
    },
    {
      path: 'log',
      component: require('../pages/device/device/Log').default,
      name: 'deviceLog',
      meta:{
        name:"deviceLog"
      }
    }
  ]
}
]
export default device