package io.xys.demo.springboot.groovy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;

/**
 * grovvy模板web应用
 *
 * @author 摇光 [NO.0146]
 * @since 2016年11月25日 14:11
 */
@SpringBootApplication
public class GroovyTemplateApplication {

  @Bean
  public MessageRepository messageRepository() {
    return new InMemoryMessageRepository();
  }

  /**
   * Message converter bean.
   *
   * @return  converter bean
   */
  @Bean
  public Converter<String, Message> messageConverter() {
    return new Converter<String, Message>() {
      @Override
      public Message convert(String id) {
        return messageRepository().findMessage(Long.valueOf(id));
      }
    };
  }

  public static void main(String[] args) throws Exception {
    SpringApplication.run(GroovyTemplateApplication.class, args);
  }
}
