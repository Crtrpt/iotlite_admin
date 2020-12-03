import {request} from './request.js'

const user={
    /**
     * 运行
     */
    ban:(ctx)=>{
        return request.post("user/ban",ctx)
    },
    /**
     * 停止
     */
    remove:(ctx)=>{
      return request.post("user/remove",ctx)
    },
    /**
     * 流水线详情
     */
    info:(ctx)=>{
      return request.get("user/info",ctx)
    },
    /**
     * 用户列表 / 搜索
     */
    list:(ctx)=>{
      return request.get("user/list",ctx)
    },
    /**
     * 列表头部统计
     */
    header:(ctx)=>{
      return request.get("user/header",ctx)
    },
    save:(ctx)=>{
      return request.post("user/save",ctx)
    },
    /**
     * 删除全部
     */
    removeAll:(ctx)=>{
      return request.post("user/removeAll",ctx)
    },
    /**
     * 删除全部
     */
    disableAll:(ctx)=>{
      return request.post("user/disableAll",ctx)
    },
    /**
     * 删除全部
     */
    enableAll:(ctx)=>{
      return request.post("user/enableAll",ctx)
    },
    
}

export {
  user
}
