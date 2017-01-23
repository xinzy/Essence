# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/xinzy/Android/android-sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-optimizationpasses 5                                                           # 指定代码的压缩级别
-dontusemixedcaseclassnames                                                     # 是否使用大小写混合
-dontskipnonpubliclibraryclasses                                                # 是否混淆第三方jar
-dontpreverify                                                                  # 混淆时是否做预校验
-verbose                                                                        # 混淆时是否记录日志
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*        # 混淆时所采用的算法

-keep class * extends java.lang.annotation.Annotation { *; }                    # 注解类
-keep interface * extends java.lang.annotation.Annotation { *; }                # 注解类

-keepclasseswithmembernames class * {                                           # 保持 native 方法不被混淆
    native <methods>;
}
-keepclasseswithmembers class * {                                               # 保持自定义控件类不被混淆
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers enum * {                                                      # 保持枚举 enum 类不被混淆
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable {                                # 保持 Parcelable 不被混淆
  public static final android.os.Parcelable$Creator *;
}

-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn retrofit2.**
-dontwarn com.squareup.okhttp.**

# RxAndroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
 long producerIndex;
 long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
 rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
 rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

# Model 类
-keep class com.xinzy.essence.model.** {
    *;
}
