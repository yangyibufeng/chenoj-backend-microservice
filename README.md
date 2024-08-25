# chenoj-backend-microservice
## 介绍🎊

 基于Spring Cloud 微服务 + MQ + Docker（+ Vue 3 + Arco Design）的编程题目在线判题系统。
在系统前台，管理员可以创建、管理题目；用户可以自由搜索题目、阅读题目、编写并提交代码。
在系统后端，能够根据管理员设定的题目测试用例在 [自主实现的代码沙箱](https://github.com/yangyibufeng/chenoj-code-sandbox) 中对代码进行编译、运行、判断输出是否正确。
其中，代码沙箱可以作为独立服务，提供给其他开发者使用。

## 项目核心亮点 ⭐

1. 权限校验：用户权限校验（JWT）
2. Redisson限流
3. 代码沙箱（安全沙箱）
   - 用户代码藏毒：写个木马文件、修改系统权限
   - 沙箱：隔离的、安全的环境，用户的代码不会影响到沙箱之外的系统的运行
   - 资源分配：限制用户程序的占用资源
4. 判题规则
   - 题目用例的比对，结果的验证
5. 任务调度（消息队列执行判题）
   - 服务器资源有限，用户要排队，按照顺序去依次执行判题
## 软件架构🌟
### 项目架构
![](https://cdn.nlark.com/yuque/0/2024/jpeg/35273292/1724515184588-254372eb-d144-48e8-9425-279e6f38f5f1.jpeg)

### 用例图
![](https://cdn.nlark.com/yuque/0/2024/jpeg/35273292/1724555236372-6b2b3b2a-1960-4741-8c27-34cd133fa202.jpeg)

### 核心业务流程
![](https://cdn.nlark.com/yuque/0/2024/jpeg/35273292/1724571188472-6e26aafc-6c32-4732-828b-4764ca2c0e90.jpeg)

## 服务划分

1. chenoj-backend-common：系统通用模块，比如用户角色权限校验，异常处理，统一返回值，常量，工具类等
2. chenoj-backend-model：系统实体模块，比如用户实体类、题目实体类，VO、枚举等
3. chenoj-backend-service-client：系统内部调用模块，给内部系统提供调用接口
4. chenoj-backend-gateway：系统网关模块：实现了给前端返回统一接口路由，聚合文档（Knife4j），全局跨域配置，权限校验（JWT Token）等
5. chenoj-backend-judge-service：系统判题模块：调用远程代码沙箱接口，实现工厂模式、策略模式、代理模式，验证代码沙箱执行结果是否正确与错误，使用消息队列实现异步处理消息
6. chenoj-backend-question-service：系统题目模块：题目的增删改查、题目提交限流、使用消息队列异步处理消息
7. chenoj-backend-user-service：系统用户模块，管理员对用户的增删改查，用户自己信息查询，修改等。

## OJ项目展示
### 游客
> 仅对游客展示oj首页以及浏览题目界面

![游客首页](https://raw.githubusercontent.com/yangyibufeng/Cloudimg_Typora/master/img/2024/07/16/2024-07-16-16%3A14%3A31.png#errorMessage=unknown%20error&height=691&id=tp7OI&originHeight=929&originWidth=1864&originalType=binary&ratio=1&rotation=0&showTitle=true&status=error&style=none&title=%E6%B8%B8%E5%AE%A2%E9%A6%96%E9%A1%B5&width=1386 "游客首页")

_游客仅能浏览题目，不能提交，不能查看具体内容_
![](https://raw.githubusercontent.com/yangyibufeng/Cloudimg_Typora/master/img/2024/07/16/2024-07-16-16%3A18%3A01.png#errorMessage=unknown%20error&height=682&id=IraSX&originHeight=916&originWidth=1826&originalType=binary&ratio=1&rotation=0&showTitle=false&status=error&style=none&width=1359)

### 普通用户
> 针对普通用户增加可以进行题目内容的浏览以及题目提交的权限，增加展示题目提交页面

![用户注册](https://raw.githubusercontent.com/yangyibufeng/Cloudimg_Typora/master/img/2024/07/14/2024-07-14-16%3A11%3A27.png#errorMessage=unknown%20error&id=SYnj1&originHeight=699&originWidth=1069&originalType=binary&ratio=1&rotation=0&showTitle=true&status=error&style=none&title=%E7%94%A8%E6%88%B7%E6%B3%A8%E5%86%8C "用户注册")
![用户登录](https://raw.githubusercontent.com/yangyibufeng/Cloudimg_Typora/master/img/2024/07/14/2024-07-14-16%3A11%3A52.png#errorMessage=unknown%20error&id=vzvYT&originHeight=545&originWidth=800&originalType=binary&ratio=1&rotation=0&showTitle=true&status=error&style=none&title=%E7%94%A8%E6%88%B7%E7%99%BB%E5%BD%95 "用户登录")


![普通用户首页](https://raw.githubusercontent.com/yangyibufeng/Cloudimg_Typora/master/img/2024/07/16/2024-07-16-16%3A16%3A20.png#errorMessage=unknown%20error&id=Ioksd&originHeight=925&originWidth=1847&originalType=binary&ratio=1&rotation=0&showTitle=true&status=error&style=none&title=%E6%99%AE%E9%80%9A%E7%94%A8%E6%88%B7%E9%A6%96%E9%A1%B5 "普通用户首页")
 
_可以浏览题目具体内容_
![](https://raw.githubusercontent.com/yangyibufeng/Cloudimg_Typora/master/img/2024/07/16/2024-07-16-16%3A19%3A05.png#from=url&id=nZveG&originHeight=950&originWidth=1919&originalType=binary&ratio=1&rotation=0&showTitle=false&status=done&style=none&title=)

_可以浏览题目提交页面_
![](https://raw.githubusercontent.com/yangyibufeng/Cloudimg_Typora/master/img/2024/07/16/2024-07-16-16%3A19%3A35.png#errorMessage=unknown%20error&id=PSpJ0&originHeight=910&originWidth=1832&originalType=binary&ratio=1&rotation=0&showTitle=false&status=error&style=none)

_用户内容展示_
![](https://raw.githubusercontent.com/yangyibufeng/Cloudimg_Typora/master/img/2024/07/16/2024-07-16-16%3A22%3A07.png#errorMessage=unknown%20error&id=jQ5RW&originHeight=950&originWidth=1919&originalType=binary&ratio=1&rotation=0&showTitle=false&status=error&style=none)

_用户信息修改_
![](https://raw.githubusercontent.com/yangyibufeng/Cloudimg_Typora/master/img/2024/07/16/2024-07-16-16%3A22%3A26.png#errorMessage=unknown%20error&id=TT3SL&originHeight=950&originWidth=1919&originalType=binary&ratio=1&rotation=0&showTitle=false&status=error&style=none)


### 管理员
> 针对管理员，开放全部页面的浏览操作权限

![](https://raw.githubusercontent.com/yangyibufeng/Cloudimg_Typora/master/img/2024/07/16/2024-07-16-16%3A17%3A21.png#from=url&id=tGPFx&originHeight=952&originWidth=1890&originalType=binary&ratio=1&rotation=0&showTitle=false&status=done&style=none&title=)

_可以浏览创建题目页面_
![](https://raw.githubusercontent.com/yangyibufeng/Cloudimg_Typora/master/img/2024/07/16/2024-07-16-16%3A20%3A40.png#from=url&id=CYtAF&originHeight=950&originWidth=1919&originalType=binary&ratio=1&rotation=0&showTitle=false&status=done&style=none&title=)
![](https://raw.githubusercontent.com/yangyibufeng/Cloudimg_Typora/master/img/2024/07/14/2024-07-14-14%3A54%3A29.png#errorMessage=unknown%20error&id=w3dhN&originHeight=822&originWidth=1514&originalType=binary&ratio=1&rotation=0&showTitle=false&status=error&style=none)
_可以浏览管理题目页面_
![](https://raw.githubusercontent.com/yangyibufeng/Cloudimg_Typora/master/img/2024/07/16/2024-07-16-16%3A21%3A11.png#from=url&id=OBcUb&originHeight=950&originWidth=1919&originalType=binary&ratio=1&rotation=0&showTitle=false&status=done&style=none&title=)

_可以浏览用户管理页面_
![](https://raw.githubusercontent.com/yangyibufeng/Cloudimg_Typora/master/img/2024/07/16/2024-07-16-16%3A21%3A31.png#from=url&id=hsXK3&originHeight=950&originWidth=1919&originalType=binary&ratio=1&rotation=0&showTitle=false&status=done&style=none&title=)

_修改用户信息_
![2024-07-16-16_23_39.png](https://cdn.nlark.com/yuque/0/2024/png/35273292/1724574497839-36e89853-47f4-4118-a2e4-47d83b0ff132.png#averageHue=%237f8389&clientId=uafec8fe4-fbf9-4&from=drop&id=ud7411d1a&originHeight=900&originWidth=1874&originalType=binary&ratio=1&rotation=0&showTitle=false&size=115398&status=done&style=none&taskId=u3ae227b3-47b1-4b40-80e2-72eacf9822e&title=)




