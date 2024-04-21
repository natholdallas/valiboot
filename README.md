# ValiBoot

## 介绍

> 此框架为轻量化校验框架, 为去除 Service 层而生  
> 受到 django.py 的启发

## 安装

由于是通过 `jitpack` 打包的而不是直接在 `maven` 仓库中, 因此需要配置 `jitpack` 的远程仓库

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

```xml
<dependency>
    <groupId>com.github.NatholDallas</groupId>
    <artifactId>valiboot</artifactId>
    <version>xxxxxxxxxx</version>
</dependency>
```

## 使用指南 🧭

```java
public static void main(String[] args) {
    List<InvalidMsg> msgs = new StringValidator()
            .required(true)
            .minLength(10)
            .maxLength(20)
            .notEmpty()
            .notBlank()
            .regex("12")
            .enumration("123445678909876543")
            .validate("112803612893891547812523445678909876543");
    System.out.println(msgs);
    // output
    /* [
        InvalidMsg[title=MaxLength, message=value 不能大于 20],
        InvalidMsg[title=Regex, message=value 与正则表达式不匹配 12],
        InvalidMsg[title=Enumration, message=value 必须在 [123445678909876543] 中]
       ]
    */
}
```

不止是 `validate()` 方法, 内置有多种不同的产生结果, 详情可查看 `com.github.natholdallas.Validator`

你可以使用 Record 类来简化校验过程, 面向异常处理, 面向数据处理, 面向

```java
public record Test(String content) {
    public Test {
        List<InvalidMsg> msgs = new StringValidator()
            .required(true)
            .minLength(10)
            .maxLength(20)
            .notEmpty()
            .notBlank()
            .regex("12")
            .enumration("123445678909876543")
            .validate(content);
        System.out.println(msgs);
    }
}
```

## 已知问题

### 当校验的参数为 `null` 时

`required` 如果设置为 `true` 且 `value` 为 `null` 的情况下, 不会执行接下来的条件, `InvalidMsg` 产生的错误信息只会有一条

- 好处: 减少了性能开销
- 坏处: 无法实现需要全部错误信息的需求

### 国际化

目前框架仅支持中文, 连最基本的英文也不支持, 并且信息是内联代码的, 没有抽离出来, 后续会解决

- 好处: 耦合减少性能开销
- 坏处: 不支持其他语种, 也不能自定义错误信息

## 后续内容

此框架不止可以应用在 SpringBoot 中, 因此我不会添加 Spring 的包在项目中, 它完全独立
