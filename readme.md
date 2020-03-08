# Apt 注解生成 java 代码

## 1. 定义注解

1. 新建一个 **java library** module `apt-annotation`
2. 编写注解类
```
@Retention(RetentionPolicy.CLASS) // 存在于源码以及.class 文件
@Target(ElementType.TYPE) // 作用于类
public @interface Adapter {
  String source(); // 参数
}
```

## 2. 编写接口
编写接口的目的是为了面向接口编程，方便统一处理
1. 新建一个 **java library** module `apt-api`
2. 编写接口代码
```
public interface AdAdapter {

  void fetchAd();

  String getAd();
}
```

## 3. 编写注解处理器
1. 新建一个 **java library** module `apt-compiler`
2. 编写注解处理器，注解处理器需要继承 `AbstractProcessor`，重写几个固定的方法，如下

```java
public class AdAdapterProcessor extends AbstractProcessor {

  // 一般返回最新的版本
  @Override public SourceVersion getSupportedSourceVersion() {
      return SourceVersion.latestSupported();
    }

    // 返回要处理的注解全名
  @Override public Set<String> getSupportedAnnotationTypes() {
    return Collections.singleton(Adapter.class.getCanonicalName());
  }

  // 这是主要的逻辑，一般就是生成代码
  @Override
  public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
    if (null == set || set.isEmpty()) return false;
    return genFactory(roundEnvironment);
  }
}

3. 注册处理器
这里主要讲手动注册，google 的自动注册在这个项目没成功，不知道什么原因。手动注册其实也不麻烦，相对于自动注册更稳一点
- 在 main 目录下新建 `resources/META-INF/services/` 目录
- 在 `services` 目录下新建文件 `javax.annotation.processing.Processor`
- 在文件 `javax.annotation.processing.Processor` 填写注解处理器，有多个的话，每行写一个，如下
```
com.lanayru.apt.compiler.AdAdapterProcessor
com.lanayru.apt.compiler.ViewBinderProcessor
```

## 4. 使用注解
1. 在 module 的 `build.gradle` 中添加依赖 (注意，哪个模块用到注解就在哪个模块添加)
```
annotationProcessor project(':apt-compiler')
implementation project(':apt-api')
```
2. 给相应的类加上注解

```java
@Adapter(source = Const.SOURCE_GP)
public class GPAdAdapter implements AdAdapter {
  @Override public void fetchAd() {
  }
  @Override public String getAd() {
    return "google ad";
  }
}
```

## 5. 编译代码，生成 java 类
可以使用 `make project` 或者 `build`

在 3.6 版本的 AS 上，生成的代码位于 `build/generated/ap_generated_sources` 下
其他版本略有差异，不过基本都在 `build/generated/`下

## 6. 其他
详细请看源码，代码不算复杂，后面有时间再补点注解和其他的文档

## todo
- [] AutoService 注解不生效，不知道为啥
- [] 补充注解文档
- [] 补充生成代码文档