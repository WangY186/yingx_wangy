package com.baizhi.wy.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResult {
    private String status;
    private String message;
    private Object data;

    public CommonResult success(String message,Object data){

        CommonResult commonResult=new CommonResult();
        commonResult.setData(data);
        commonResult.setMessage(message);
        commonResult.setStatus("100");
        return commonResult;
    }
    public CommonResult success(Object data){
        CommonResult commonResult=new CommonResult();
        commonResult.setData(data);
        commonResult.setMessage("查询成功");
        commonResult.setStatus("100");
        return commonResult;
    }
    public CommonResult failed(String status,String message,Object data){

        CommonResult commonResult=new CommonResult();
        commonResult.setData(data);
        commonResult.setMessage(message);
        commonResult.setStatus(status);
        return commonResult;
    }
    public CommonResult failed(String message,Object data){

        CommonResult commonResult=new CommonResult();
        commonResult.setData(data);
        commonResult.setMessage(message);
        commonResult.setStatus("104");
        return commonResult;
    }
    public CommonResult failed(Object data){

        CommonResult commonResult=new CommonResult();
        commonResult.setData(data);
        commonResult.setMessage("查询失败");
        commonResult.setStatus("104");
        return commonResult;
    }
}
