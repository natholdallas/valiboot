# ValiBoot

## 前言

> 此框架极为简单, 为了去除 Service 层而生  
> 受到 django.py 的启发  
> 此文档具有非常强的上下文关联,请详细配合上下文查看文档

## 介绍

本框架为轻量化的校验框架, 多数为了校验前端传入过来的参数, 并且弱化 `Service` 的作用, 以解决冗余类过多  
并且此框架比较与 SpringBoot / Spring MVC 贴合, 后续还会增加 **Mybatis** 的支持

## 安装

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    <repository>
</repositories>

<dependency>
    <groupId>com.github.NatholDallas</groupId>
    <artifactId>ValiBoot</artifactId>
    <version>2.0.0</version>
</dependency>

<!-- 这个 version 标签中的版本号永远代表着最新版本号, 详细你应该查看以往的版本发布号 -->
```

## ValiBoot - DTO/Controller

简单阐述一下问题:  
传统的写法需要 `DTO`/`Controller`/`Service` 三层传递, 非常麻烦, 让 DTO 只作为一个 record, 不方便编写数据检查  
因此校验的活交给了 `Controller` 层, 然后校验完之后交给 `Service` 返回值, 非常繁琐  
并且按 SSM 框架中, 校验有时不止时数据上的格式, 还要有鉴权性, 比如通过 Mybatis, 使用 `Mapper` 从数据库中  
查询数据, 以此来完成校验, 示例:

```java
// Controller
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;

    // Spring IOC Container 中现阶段不推荐使用 @Autowired, 而是推荐构造方法注入
    public UserController(UserMapper userMapper, UserService userService) {
        this.userMapper = userMapper;
        this.userSerivce = userService;
    }

    @PutMapping
    public String put(UserPutRequest request) {
        if (this.userMapper.selectById(request.getId())) {
            return "error, user not found";
        }
        // request.getUsername() 校验字符长度....之类的
        // request.getPassword() 校验字符合法....(正则表达式什么的)
        return this.userService.putService(request);
    }

}

// DTO (映射到 UserController 中的 PutMapping(put方法))
public class UserPutRequest {

    private final Integer id;
    private final String username;
    private final String password;

    public UserPutRequest(Integer id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    // getter...

}

// Service
@Service
public class UserService {

    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public String putService(UserPutRequest request) {
        // UserMapper update...
        return "successful";
    }

}
```

可以看到, 如果要符合规范的编写, 非常繁琐  
因此我们可以何不尝换一种方式: 在 `DTO` 中的构造方法顺带直接校验...  
于是, 敲了一个 ValiBoot...

## ValiBoot 使用说明

因此, 你可以直接使用此 ValiBoot 中的校验, 直接在 `DTO` 中校验参数:

```java
@SpringBootApplication
public class Application {

    public static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(Application.class);
    }

}

// Controller
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserMapper userMapper;

    public UserController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public String put(UserPutRequest request) {
        // UserMapper update...
        return "successful";
    }

}

// UserMapper 注: 使用了 Mybatis, 因此无需写实现
@Mapper
public interface UserMapper {

    boolean isExists(Long id); // 用于查询这个 id 是否存在与用户表, 以此检查数据库中是否存在此用户

}

// DTO
public class UserPutRequest {

    private final Long id;
    private final String username;
    private final String password;

    public UserPutRequest(Integer id, String username, String password) {
        // 通过 SpringApplication 返回的 context (详情见 ConfigurableApplicationContext 对象) 拿到 UserMapper 的实例
        Spt
        this.id = Validate.of(id)
                .wrapper(value -> {
                    UserMapper userMapper = Application.context.getBean("userMapper", UserMapper.class);
                    return userMapper.isExists(value);
                });
        this.username = Validate.of(username)
                .range(6, 12)
                .regex("正则超人!(我不会🤣)")
                .notEmpty()
                .notBlank();
        this.password = Validate.of(password)
                .regex("正则表达式...(我不会🤣)")
                .notEmpty()
                .notBlank();
    }

    // getter...

}
```

## 后续内容

此框架不止可以应用在 SpringBoot 中, 因此我不会添加 Spring 的包在项目中, 它完全独立  
