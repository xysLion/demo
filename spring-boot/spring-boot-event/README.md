自定义Spring 事件使用
============================================================================

在程序开发过程中，时常需要一个观察者模式实现。如果你是使用Spring框架来开发，并且又用到观察者模式，那么，你非常幸运，因为Spring框架中已经集成了该模式。

## 栗子

* 首先，你需要创建一个类继承`ApplicationEvent`：
```java
public class DemoEvent extends ApplicationEvent {

    private final String msg;

    public DemoEvent(Object source, String msg) {
        super(source);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
```

* 接着，你需要监听该事件并做出处理，同样你需要创建一个类，不过是集成`ApplicationListener<DemoEvent>`:
```java
@Component
public class DemoListener implements ApplicationListener<DemoEvent>{

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoListener.class);

    public void onApplicationEvent(DemoEvent demoEvent) {

        String msg = demoEvent.getMsg();

        LOGGER.info("DemoListener接收到消息：{}", msg);
    }
}
```

注：`DemoEvent`为你要监听的事件类型，必须放在`ApplicationListener`泛型中。

* 最后，我们只要在要发布事件的地方，使用`ApplicationContext`实例发布该事件就可以了：
```batch
context.publishEvent(new DemoEvent(new EventApplication(), "this is a application event"));
```

## 高级应用

* 定义好监听器的顺序后，在接收到事件时，可以顺序执行监听该事件的监听器。如何定义监听器顺序：
   * 在注册为bean时使用`Order`注解来定义顺序:
   ```batch
   @Component
   @Order(3)
   public class DemoListener implements ApplicationListener<DemoEvent>
   ```
   `Order`注解的value值就是该监听器在监听器链中的位置
   * 监听器实现`Ordered`接口，然后在`getOrder`方法中返回在监听器链中的位置信息：
   ```java
   @Component
   public class Demo3Listener implements ApplicationListener<DemoEvent>, Ordered{
   
       public int getOrder() {
           return 1;
       }
   }
   ```
注：顺序执行只允许使用同步方法执行捕捉到事件后的操作

* 在执行方法上加`Async`注解实现异步执行捕捉到事件后的操作：
```java
@Component
public class DemoListener implements ApplicationListener<DemoEvent>{

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoListener.class);

    @Async
    public void onApplicationEvent(DemoEvent demoEvent) {

        String msg = demoEvent.getMsg();

        LOGGER.info("DemoListener接收到消息：{}", msg);
    }
}
```