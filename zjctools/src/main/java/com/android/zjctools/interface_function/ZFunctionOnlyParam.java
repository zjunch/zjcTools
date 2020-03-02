package com.android.zjctools.interface_function;

public abstract  class ZFunctionOnlyParam<Param> extends ZFunction {
    public ZFunctionOnlyParam(String functionName) {
        super(functionName);
    }
    public  abstract   void function(Param param);
}
