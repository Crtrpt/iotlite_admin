import {request} from './request.js'

const dashboard={
    info:(ctx)=>{
      return request.get("dashboard/info",ctx)
    }
}

export {
  dashboard
}
