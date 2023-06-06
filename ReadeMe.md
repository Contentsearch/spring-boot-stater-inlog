# Read Me First
- 👋 Hi, I’m @iPhase2  

**This project is designed to assist me in troubleshooting issues through log analysis during interface calls.**

# Getting Started
## 引入依赖
```xml
<dependency>
    <groupId>com.github.iPhase2</groupId>
    <artifactId>spring-boot-stater-inlog</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```
依赖安装后功能默认是关闭的，如需开启需要配置:
```yaml
auto-log:
  enable: true
```
开启后配置需要拦截的包全路径或接口全路径名:
```yaml
auto-log:
  enable: true
  filter:
    - com.iPhase2.user.controller.ReportController
    - com.iPhase.item.controller.*
    - com.demo.order.api.*
```

关闭日志
```yaml
auto-log:
  enable: false
```
