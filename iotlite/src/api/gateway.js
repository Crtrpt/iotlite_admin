import {request} from './request.js'

const gateway={
    remove:(ctx)=>{
      return request.post("gateway/remove",ctx)
    },
    info:(ctx)=>{
      return request.get("gateway/info",ctx)
    },
    list:(ctx)=>{
      return request.get("gateway/list",ctx)
    },
    save:(ctx)=>{
      return request.post("gateway/save",ctx)
    },
}

export {
  gateway
}
