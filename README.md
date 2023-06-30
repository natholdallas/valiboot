# ValiBoot

> 此框架极为简单, 为了去除 Service 层而生  
> 受到 django.py 的启发

## 介绍

本框架为轻量化的校验框架, 多数为了校验前端传入过来的参数, 并且弱化 `Service` 的作用, 以解决冗余类过多
并且此框架比较与 SpringBoot / Spring MVC 贴合, 后续还会增加 **Mybatis** 的支持

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

// DTO
public class UserPutRequest {
    private final Integer id;
    private final String username;
    private final String password;

    public UserPutRequest(Integer id, String username, String password) {
        // 拿到 Wrapper 的实例, 以此来校验
        // 注: 此框架没有提供此方法, 因此你需要用自己的方式拿到这个校验类的实例
        // 比如使用 IOC Container 或者使用 @PostStruct 注解来获得实例
        YourProjectWrapper wrapper = ...;
        this.id = Validate.of(id)
                .wrapper(value -> wrapper.userCheck(value));
        this.username = Validate.of(username)
                .range(6, 12)
                .notEmpty()
                .notBlank();
        this.password = Validate.of(password)
                .regex("正则表达式...(我不会🤣)")
                .notEmpty()
                .notBlank();
    }

    // getter...
}

// Wrapper 把此类交给 IOC Container, 就可以自动实例化, 拿到实例化后直接使用去校验
@Component
public class YourProjectWrapper {
    private final UserMapper userMapper;

    public YourProjectWrapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public boolean userCheck(Integer id) {
        // 如果用户存在就返回 true, 不存在就返回 false
        return this.userMapper.selectById(id) != null;
    }
}
```

## 后续内容

由于此框架不止可以应用在 SpringBoot 中, 因此我不会添加 Spring 的包在项目中, 它完全独立  
因此如上述所说的 `Wrapper` 的问题, 你需要自行解决, 或者我再写一个包.......
