package io.xys.demo.springboot.event;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 事件监听应用
 *
 * @author 摇光 [NO.0146]
 * @since 2016年11月28日 15:31
 */
@SpringBootApplication
@EnableAsync
public class EventApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(EventApplication.class, args);

        context.publishEvent(new DemoEvent(new EventApplication(), "this is a application event"));
    }

}
