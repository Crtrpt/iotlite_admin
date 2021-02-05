import {request} from './request.js'

const product={
    remove:(ctx)=>{
      return request.post("product/remove",ctx)
    },
    info:(ctx)=>{
      return request.get("product/info",ctx)
    },
    list:(ctx)=>{
      return request.get("product/list",ctx)
    },
    all:(ctx)=>{
      return request.get("product/all",ctx)
    },
    save:(ctx)=>{
      return request.post("product/save",ctx)
    },
    deviceList:(ctx)=>{
      return request.get("product/deviceList",ctx)
    },
    mapDeviceList:(ctx)=>{
      return request.get("product/mapDeviceList",ctx)
    },
    getSetting:(ctx)=>{
      return request.get("product/getSetting",ctx)
    },
    saveSetting:(ctx)=>{
      return request.post("product/saveSetting",ctx)
    }
}

export {
  product
}
