package io.xys.demo.springboot.axon.configuration;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.monitoring.NoOpMessageMonitor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * A configuration for axonframework. Axonframework is used to support eventsourcing and CQRS.
 *
 * @author 摇光 [NO.0146]
 * @since 2017年01月13日 13:31
 */
@Configuration
public class AxonConfiguration {

  @Bean
  CommandBus commandBus(TransactionManager transactionManager) {
    SimpleCommandBus commandBus =
        new SimpleCommandBus(transactionManager, NoOpMessageMonitor.INSTANCE);
    //        commandBus.registerDispatchInterceptor(new BeanValidationInterceptor());
    return commandBus;
  }
}
