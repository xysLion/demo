package io.xys.demo.springboot.event;

import org.springframework.context.ApplicationEvent;

/**
 * DEMO事件
 *
 * @author 摇光 [NO.0146]
 * @since 2016年11月28日 15:33
 */
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
