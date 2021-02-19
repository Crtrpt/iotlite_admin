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
    },
    location:(ctx)=>{
      return request.get("device/location",ctx)
    },
    refreshDeviceKey:(ctx)=>{
      return request.post("device/refreshDeviceKey",ctx)
    },
    changeTags:(ctx)=>{
      return request.post("device/changeTags",ctx)
    }
}

export {
  device
}
