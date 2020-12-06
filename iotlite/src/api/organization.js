import {request} from './request.js'

const organization={
    remove:(ctx)=>{
      return request.post("organization/remove",ctx)
    },
    info:(ctx)=>{
      return request.get("organization/info",ctx)
    },
    list:(ctx)=>{
      return request.get("organization/list",ctx)
    },
    save:(ctx)=>{
      return request.post("organization/save",ctx)
    },
    tree:(ctx)=>{
      return request.get("organization/tree",ctx)
    }
}

export {
  organization
}
