const organization =[
  {
    path: 'organization',
    component: require('../pages/organization/Index').default,
    name: 'organization',
    meta:{
      name:"organization"
    }
  },
  {
    path: 'organization/:id',
    component: require('../pages/organization/Detail').default,
    name: 'organizationDetail',
    meta:{
      name:"organizationDetail"
    }
  },
]
export default organization
