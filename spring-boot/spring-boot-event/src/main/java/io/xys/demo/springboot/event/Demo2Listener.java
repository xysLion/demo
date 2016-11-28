package io.xys.demo.springboot.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Demo事件监听器
 *
 * @author 摇光 [NO.0146]
 * @since 2016年11月28日 15:36
 */
@Component
@Order(2)
public class Demo2Listener implements ApplicationListener<DemoEvent>{

    private static final Logger LOGGER = LoggerFactory.getLogger(Demo2Listener.class);

//    @Async
    public void onApplicationEvent(DemoEvent demoEvent) {

        String msg = demoEvent.getMsg();

        LOGGER.info("Demo2Listener接收到消息：{}", msg);
    }
}
