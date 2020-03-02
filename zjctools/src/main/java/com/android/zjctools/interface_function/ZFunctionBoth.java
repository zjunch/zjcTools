package com.android.zjctools.interface_function;

public abstract class ZFunctionBoth<Param>extends ZFunction {
    public ZFunctionBoth(String functionName) {
        super(functionName);
    }

    public abstract <Result> Result function(Param param);
}