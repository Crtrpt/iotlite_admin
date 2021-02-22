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
    },
    groupSave:(ctx)=>{
      return request.post("device/saveGroup",ctx)
    },
    groupList:(ctx)=>{
      return request.get("device/groupList",ctx)
    },
    groupInfo:(ctx)=>{
      return request.get("device/groupInfo",ctx)
    },
    groupInfoByName:(ctx)=>{
      return request.get("device/groupInfoByName",ctx)
    },
    groupDeviceList:(ctx)=>{
      return request.get("device/groupDeviceList",ctx)
    },
    groupSaveFence:(ctx)=>{
      return request.post("device/saveGroupFence",ctx)
    },
    setDeviceMeta:(ctx)=>{
      return request.post("device/setDeviceMeta",ctx)
    }
}

export {
  device
}
