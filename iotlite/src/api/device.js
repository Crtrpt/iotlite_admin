import {request} from './request.js'

const device={
    remove:(ctx)=>{
      return request.post("device/remove",ctx)
    },
    info:(ctx)=>{
      return request.get("device/info",ctx)
    },
    list:(ctx)=>{
      return request.get("device/list",ctx)
    },
    logList:(ctx)=>{
      return request.get("device/log",ctx)
    },
    save:(ctx)=>{
      return request.post("device/save",ctx)
    },
    action:(ctx)=>{
      return request.post("device/action",ctx)
    }
}

export {
  device
}
