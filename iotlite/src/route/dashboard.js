
const dashboard  = [
  {
    path: 'dashboard',
    component: require('../pages/dashboard/List').default,
    name: 'dashboard',
    meta:{
      name:"dashboard"
    }
  },
  {
    path: 'dashboard/:id',
    component: require('../pages/dashboard/Edit').default,
    name: 'dashboardEdit',
    meta:{
      name:"dashboardEdit"
    },
  },
  {
    path: 'dashboard/:id/preview',
    component: require('../pages/dashboard/Preview').default,
    name: 'dashboardPreview',
    meta:{
      name:"dashboardPreview"
    },
  }
]
export default dashboard