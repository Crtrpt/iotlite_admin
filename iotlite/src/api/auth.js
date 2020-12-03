import {request} from './request.js'

const auth={
    login:(ctx)=>{
        return request.post("login",ctx)
    },
    register:(ctx)=>{
      return request.post("signup",ctx)
    },
    /**
     * 短信验证
     */
    phoneVerif:(ctx)=>{
      return request.post("auth/phoneVerif",ctx)
    },
    logout:(ctx)=>{
        return request.post("auth/logout",ctx)
    }
}

export {
    auth
}
