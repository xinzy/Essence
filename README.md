# Essence

一个自学习的Android app，借助Gank IO的开放API，实现一个Material Design风格的App

### 技术要点

#### 架构方面

使用流行的MVP架构，使其解耦更彻底。

#### 网络请求

网络方面使用OKHttp，通过接口上层使用Retrofit和RxJava两重实现方式，在使用过程中，只需要修改一处接口实现类即能改变其实现方式，解耦更彻底
Picasso作为图片加载库，使用OKHttp作为网络实现

