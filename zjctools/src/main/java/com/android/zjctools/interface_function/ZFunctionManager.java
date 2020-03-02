package com.android.zjctools.interface_function;

import android.text.TextUtils;

import java.util.HashMap;

public class ZFunctionManager {
    private HashMap<String, ZFunctionOnlyParam> mOnlyPramMap ;
    private HashMap<String, ZFunctionNoParamNoResult> mNoneMap ;
    private HashMap<String, ZFunctionOnlyResult> mOlyResultMap ;
    private HashMap<String, ZFunctionBoth> mBothMap;
    static ZFunctionManager functionManager;

    public ZFunctionManager() {
        mOnlyPramMap = new HashMap<>();
        mNoneMap=new HashMap<>();
        mOlyResultMap=new HashMap<>();
        mBothMap=new HashMap<>();
    }

    public static ZFunctionManager getInstance() {
        if (functionManager == null) {
            functionManager = new ZFunctionManager();
        }
        return functionManager;
    }


    public ZFunctionManager addFunction(ZFunctionNoParamNoResult funtionNone){
        mNoneMap.put(funtionNone.mFunctionName,funtionNone);
        return  this;
    }

    public ZFunctionManager addFunction(ZFunctionOnlyParam funtionOnlyParam){
        mOnlyPramMap.put(funtionOnlyParam.mFunctionName,funtionOnlyParam);
        return  this;
    }

    public ZFunctionManager addFunction(ZFunctionOnlyResult functionOnlyResult){
        mOlyResultMap.put(functionOnlyResult.mFunctionName,functionOnlyResult);
        return  this;
    }


    public ZFunctionManager addFunction(ZFunctionBoth functionBoth) {
        mBothMap.put(functionBoth.mFunctionName, functionBoth);
        return this;
    }

    public void  invokeNoneFunc(String funName){
        if(TextUtils.isEmpty(funName)||mNoneMap==null||mNoneMap.size()==0){
            return;
        }
        ZFunctionNoParamNoResult f= mNoneMap.get(funName);
        if(f==null){
            return;
        }
        f.function();
    }
    public <Param> void  invokeOnlyPramFunc(String funName, Param param){
        if(TextUtils.isEmpty(funName)||mOnlyPramMap==null||mOnlyPramMap.size()==0){
            return;
        }
        ZFunctionOnlyParam f= mOnlyPramMap.get(funName);
        if(f==null){
            return;
        }
        f.function(param);
    }
    public <Result> Result invokeOlyResultFunc(String funName) {
        Result result;
        if (TextUtils.isEmpty(funName) || mOlyResultMap == null || mOlyResultMap.size() == 0) {
            return null;
        }
        ZFunctionOnlyResult f = mOlyResultMap.get(funName);
        if (f == null) {
            return null;
        }
        result = f.function();
        return result;
    }
    public  <Result,Param> Result invokeBothFunc(String funName, Param param) {
        Result result;
        if (TextUtils.isEmpty(funName) || mBothMap == null || mBothMap.size() == 0) {
            return null;
        }
        ZFunctionBoth f = mBothMap.get(funName);
        if (f == null) {
            return null;
        }
        result = (Result) f.function(param);
        return result;
    }

}