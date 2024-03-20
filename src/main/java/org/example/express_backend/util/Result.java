package org.example.express_backend.util;

import lombok.Data;

@Data
public class Result<T>{
    private Integer code;
    private String message;
    private T data;
    private long total;

    public Result(){}

    protected static <T> Result<T> build(T data){
        Result<T> result = new Result<>();
        if(data != null){
            result.setData(data);
        }
        return result;
    }

    public static <T> Result<T> build(T body, ResultCodeEnum resultCodeEnum) {
        Result<T> result = build(body);
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());
        return result;
    }

    public static <T> Result<T> ok(T body){
        return Result.build(body, ResultCodeEnum.SUCCESS);
    }

    public static <T> Result<T> ok(){
        return Result.ok(null);
    }

    public static<T> Result<T> fail(T data){
        return Result.build(data, ResultCodeEnum.FAIL);
    }

    public static<T> Result<T> fail(){
        return Result.fail(null);
    }

    public Result<T> message(String msg){
        this.setMessage(msg);
        return this;
    }

    /**
     * 设置返回数据
     * @param total 返回数据
     * @return Result
     */
    public Result<T> total(long total){
        this.setTotal(total);
        return this;
    }
}
