package io.xys.demo.springboot.groovy;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 消息操作，内存实现
 *
 * @author 摇光 [NO.0146]
 * @since 2016年11月25日 14:17
 */
public class InMemoryMessageRepository implements MessageRepository {

  private static AtomicLong counter = new AtomicLong();

  private final ConcurrentMap<Long, Message> messages = new ConcurrentHashMap<Long, Message>();

  @Override
  public Iterable<Message> findAll() {
    return this.messages.values();
  }

  @Override
  public Message save(Message message) {
    Long id = message.getId();
    if (id == null) {
      id = counter.incrementAndGet();
      message.setId(id);
    }
    this.messages.put(id, message);
    return message;
  }

  @Override
  public Message findMessage(Long id) {
    return this.messages.get(id);
  }
}
