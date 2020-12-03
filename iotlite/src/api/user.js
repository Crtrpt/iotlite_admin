import {request} from './request.js'

const user={
    remove:(ctx)=>{
      return request.post("user/remove",ctx)
    },
    info:(ctx)=>{
      return request.get("user/info",ctx)
    },
    list:(ctx)=>{
      return request.get("user/list",ctx)
    },
    save:(ctx)=>{
      return request.post("user/save",ctx)
    },
}

export {
  user
}
