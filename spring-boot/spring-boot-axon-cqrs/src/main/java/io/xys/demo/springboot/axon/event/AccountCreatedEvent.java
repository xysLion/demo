package io.xys.demo.springboot.axon.event;

import lombok.RequiredArgsConstructor;
import lombok.Value;

/**
 * account Created Event
 *
 * @author 摇光 [NO.0146]
 * @since 2017年01月13日 14:09
 */
@Value
@RequiredArgsConstructor(staticName = "of")
public class AccountCreatedEvent {

  private String accountNo;
}
