
const product  =[
  {
    path: 'product',
    component: require('../pages/product/Index').default,
    name: 'product',
    meta:{
      name:"product"
    }
  },
  {
  path: 'product/:id',
  component: require('../pages/product/Detail').default,
  name: 'productDetail',
  redirect: { name: 'productDetailBase' },
  meta:{
    name:"productDetail"
  },
  children:[
    {
      path: 'base',
      component: require('../pages/product/product/Base').default,
      name: 'productDetailBase',
      meta:{
        name:"productDetailBase"
      }
    },
    {
      path: 'model',
      component: require('../pages/product/product/Model').default,
      name: 'productDetailModel',
      meta:{
        name:"productDetailModel"
      }
    },
    {
      path: 'version',
      component: require('../pages/product/product/Version').default,
      name: 'productDetailVersion',
      meta:{
        name:"productDetailVersion"
      }
    }
  ]
}
]
export default product