使用Spring AOP向领域模型注入依赖
========================================================================
使用`new`关键字或由JPA创建的对象是不受Spring托管的，也就无法进行依赖注入。解决这个问题的方法有很多，这里是阐述使用面向切面编程来实现。

## 面向切面编程

面向切面编程，或`AOP`，是一种编程范式，和面向对象编程（`OOP`）互为补充。简单来说，AOP可以在不修改既有代码的情况下改变代码的行为。开发者通过定义一组规则，在特定的类方法前后增加逻辑，如记录日志、性能监控、事务管理等。这些逻辑称为切面（Aspect），规则称为切点（Pointcut），在调用前还是调用后执行称为通知（Before advice, After advice）。最后，我们可以选择在编译期将这些逻辑写入类文件，或是在运行时动态加载这些逻辑，这是两种不同的织入方式（`Compile-time weaving`, `Load-time weaving`）。

对于使用`new`关键字或有JPA创建的对象的依赖注入，我们要做的就是使用`AOP`在对象创建后调用Spring框架来注入依赖。`Spring AOP`已经提供了`@Configurable`注解来实现这一需求。

## Configurable注解

Spring应用程序会定义一个上下文容器，在该容器内创建的对象会由Spring负责注入依赖。对于容器外创建的对象，可以使用`@Configurable`来修饰类，告知Spring对这些类的实例也进行依赖注入。

假设我有一个`Account`类，需要引用dao层的`AccountDao`类实例来进行一些操作，可以使用`@Configurable`将容器内的bean[`accountDao`]注入到类的实例中：

```java
@Configurable(autowire = Autowire.BY_TYPE)
public class Account {

    public static int index = 0;

    public Account() {
        index = index + 1;
    }

    private AccountDao accountDao;

    @Autowired
    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public String toString(){
        return accountDao.sayHello("" + index);
    }

}
```

* `autowire`参数默认是NO，因此需要显式打开，否则只能使用XML定义依赖。`@Autowired`是目前比较推荐的注入方式。
* 项目依赖中需要包含`spring-aspects`。
* 应用程序配置中需要加入`@EnableSpringConfigured`：

```java
@SpringBootApplication
@EnableSpringConfigured
@EnableScheduling
@EnableLoadTimeWeaving
public class ConfiguredApplication {

    @Autowired
    private Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(ConfiguredApplication.class, args);
    }

    @Scheduled(fixedDelay = 1000)
    public void sayHello(){
        Account account = new Account();
        System.out.println(account.toString());
        System.out.println(environment.getProperty("user.home"));
        System.out.println(environment.getProperty("user.dir"));
    }

}
```

## 运行时织入(Load-Time Weaving, LTW)

除了项目依赖和应用程序配置，还需要选择一种织入方式来使AOP生效。Spring AOP推荐的方式是运行时织入，并提供了一个专用的jar包。运行时织入的原理是：当类加载器在读取类文件时，动态修改类的字节码。这一机制是从`JDK1.5`开始提供的，需要使用--javaagent参数开启，如：

```bash
$ java -javaagent:/path/to/spring-instrument.jar -jar app.jar
```

网上有爱人士在测试时发现，Spring AOP提供的这一Jar包对普通的类是有效果的，但对于使用@Entity修饰的类就没有作用了。因此，我们改用AspectJ提供的Jar包（可到Maven中央仓库下载）：

```bash
$ java -javaagent:/path/to/aspectjweaver.jar -jar app.jar
```

对于Spring Boot应用程序，可以在Maven命令中加入以下参数：

```bash
$ mvn spring-boot:run -Drun.agent=/path/to/aspectjweaver.jar
```

此外，该有爱人士在使用AspectJ作为LTW的提供方后，会影响到Spring的事务管理，因此需要在应用程序配置中加入：

```batch
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
```

## AnnotationBeanConfigurerAspect

这背后都是Spring中的AnnotationBeanConfigurerAspect在做工作。我们不妨浏览一下精简后的源码：

[AnnotationBeanConfigurerAspect.aj](https://github.com/spring-projects/spring-framework/blob/master/spring-aspects/src/main/java/org/springframework/beans/factory/aspectj/AnnotationBeanConfigurerAspect.aj)

```batch
public aspect AnnotationBeanConfigurerAspect implements BeanFactoryAware {
	private BeanConfigurerSupport beanConfigurerSupport = new BeanConfigurerSupport();
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanConfigurerSupport.setBeanFactory(beanFactory);
	}
	public void configureBean(Object bean) {
		this.beanConfigurerSupport.configureBean(bean);
	}
	public pointcut inConfigurableBean() : @this(Configurable);
	declare parents: @Configurable * implements ConfigurableObject;
	
	public pointcut beanConstruction(Object bean) :
			initialization(ConfigurableObject+.new(..)) && this(bean);
	after(Object bean) returning :
		beanConstruction(bean) && inConfigurableBean() {
		configureBean(bean);
	}
}
```

* `.aj`文件是AspectJ定义的语言，增加了pointcut、after等关键字，用来定义切点、通知等；
* `inConfigurationBean`切点用于匹配使用`Configurable`修饰的类型；
* `declare parents`将这些类型声明为`ConfigurableObject`接口，从而匹配`beanConstruction`切点；
* `ConfigurableObject+.new(..)`表示匹配该类型所有的构造函数；
* `after`定义一个通知，表示对象创建完成后执行`configureBean`方法；
* 该方法会调用`BeanConfigurerSupport`来对新实例进行依赖注入。

## 其他方案

1. 将依赖作为参数传入。比如上文中的`setAccountDao`方法中传入Spring 容器中的bean[`accountDao`]。
2. 将`ApplicationContext`作为某个类的静态成员，领域模型通过这个引用来获取依赖。
3. 编写一个工厂方法，所有新建对象都要通过这个方法生成，进行依赖注入。

## 参考资料

* http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#aop-atconfigurable
* http://shzhangji.com/blog/2015/09/12/model-dependency-injection-with-spring-aop/
* http://blog.igorstoyanov.com/2005/12/dependency-injection-or-service.html
* http://jblewitt.com/blog/?p=129