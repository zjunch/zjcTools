# zjcTools常用工具库，
包含用基类，activity,fragment,app
toast,bitmap，file,date，router
图片选择，权限申请

简单view，设置类型view（ZSettingView），九宫格图片（ZNinePicturesView），点赞头像堆叠（ZPileLayout），recycleview分割线（ZItemDecoration）等


引用方式：


一  、项目gradle ：allprojects {
    repositories {
        maven { url 'https://dl.bintray.com/zjun/maven' }
    }
}

二 、module: implementation 'com.zjun:zjcTools:0.0.7'


三 、初始化工具  ZTools.init(this);



