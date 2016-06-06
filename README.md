# BaseApp_MVP
android 项目基础架构，采用mvp架构，fragmentActivity进行控制，fragment进行显示

目录结构介绍：
base.brian.com.baseapp_mvp

  -constant-常量
  
  -db-数据库相关，数据库采用xutils
  
  -event-数据响应/事件结构体
  
  -model-模型
  
      -buiness-业务类
      
      -entity-实体
      
      -param-参数对象
      
  -network-网络请求－retrofit2+okhttp
  
  -ui
  
      -activity控制活动
      
      -adapter
      
      -fragment
      
      -presetner
      
      -presetnerimpl
      
      -widget自定义控件
      
  util
  
  
  
  

第三方包说明
1.使用xutils 3进行日志，数据库，视图注解
2.eventbus 组建件通信
3.rxandroid 异步数据流处理
4.retrofit2 网络请求

compile 'com.squareup.retrofit2:retrofit:2.0.0'//REST 客户端

compile 'com.squareup.retrofit2:converter-gson:2.0.0'//retrofit 用到的gson格式
compile 'com.squareup.retrofit2:adapter-rxjava:2.0.0'

compile 'com.squareup.okhttp3:logging-interceptor:3.2.0'//网络请求日志

compile 'io.reactivex:rxandroid:1.1.0'//响应式编程组件

compile 'org.greenrobot:eventbus:3.0.0'//事件处理

compile 'org.xutils:xutils:3.3.25'//日志、数据库操作、视图注解
    
    
