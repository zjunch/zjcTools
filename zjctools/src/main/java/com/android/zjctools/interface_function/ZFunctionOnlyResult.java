package com.android.zjctools.interface_function;


public abstract class ZFunctionOnlyResult extends ZFunction {
    public ZFunctionOnlyResult(String functionName) {
        super(functionName);
    }

    public abstract <Result> Result function();
}