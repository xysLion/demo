package io.xys.demo.springboot.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * Demo事件监听器
 *
 * @author 摇光 [NO.0146]
 * @since 2016年11月28日 15:36
 */
@Component
public class Demo3Listener implements ApplicationListener<DemoEvent>, Ordered{

    private static final Logger LOGGER = LoggerFactory.getLogger(Demo3Listener.class);

//    @Async
    public void onApplicationEvent(DemoEvent demoEvent) {

        String msg = demoEvent.getMsg();

        LOGGER.info("Demo3Listener接收到消息：{}", msg);
    }

    public int getOrder() {
        return 1;
    }
}
