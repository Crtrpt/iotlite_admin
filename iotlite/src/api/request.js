import { sha256 } from 'js-sha256';
import {server} from './server.js'

const sign=function (params) {
    params=params+"&t="+((new Date()).valueOf()*1000-1605367991000)+"&";
    const signStr=  sha256(params).substring(0,10)
    params=params+"sign="+signStr;
    return params
}

const serialize = function(obj) {
    var str = [];
    for (var p in obj)
        // eslint-disable-next-line no-prototype-builtins
        if (obj.hasOwnProperty(p)) {
            str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
        }
        
    return sign(str.join("&"));
}

const catchError = function (err) {
    let msg = err ? err.message : null
    if (msg) {
        // 处理缓存
        let obj = JSON.parse(msg)
        if (obj.type === 'CANCELTTYPE.CACHE') {
            return obj.data
        }
    }
}

const request={
    get:(url,queryString, options)=>{
        queryString=serialize(queryString);
        if(queryString==""){
            queryString=""
        }else{
            queryString="?"+queryString
        }

        return server.get(url+queryString, options).catch(err => {
            let ret = catchError(err)
            if (ret) {
                return ret
            }
        })
    },
    post:(url,data, options)=>{
        return server.post(url,data, options).catch(err => {
            let ret = catchError(err)
            if (ret) {
                return ret
            }
        })
    },
    upload:(url,data, options)=>{
        return server.post(url,data, options).catch(err => {
            console.log(err)
        })
    }
}


export {
    request
}
