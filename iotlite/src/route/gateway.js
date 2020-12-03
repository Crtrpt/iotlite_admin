const gateway =[
  {
    path: 'gateway',
    component: require('../pages/gateway/Index').default,
    name: 'gateway',
    meta:{
      name:"gateway"
    }
  },
  {
    path: 'gateway/:id',
    component: require('../pages/gateway/Detail').default,
    name: 'gatewayDetail',
    meta:{
      name:"gatewayDetail"
    }
  },
]
export default gateway
