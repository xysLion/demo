package io.xys.demo.springboot.configured;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import io.xys.demo.springboot.configured.pojo.Account;

/**
 * spring configured DEMO
 * 启动时要加入执行以下参数
 * -javaagent:/path/to/spring-instrument.jar
 * 注：[/path/to]要替换为实际路径
 *
 * @author 摇光 [NO.0146]
 * @since 2016年11月28日 10:19
 */
@SpringBootApplication
@EnableSpringConfigured
@EnableScheduling
@EnableLoadTimeWeaving
public class ConfiguredApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfiguredApplication.class, args);
    }

    @Scheduled(fixedDelay = 1000)
    public void sayHello(){
        Account account = new Account();
        System.out.println(account.toString());
    }

}
