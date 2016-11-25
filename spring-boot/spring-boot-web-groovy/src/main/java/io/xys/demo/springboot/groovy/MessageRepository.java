package io.xys.demo.springboot.groovy;

/**
 * 消息数据操作相关
 *
 * @author 摇光 [NO.0146]
 * @since 2016年11月25日 14:15
 */
public interface MessageRepository {

    Iterable<Message> findAll();

    Message save(Message message);

    Message findMessage(Long id);

}
