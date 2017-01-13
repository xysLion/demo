package io.xys.demo.springboot.axon.command;

import lombok.Value;

/**
 * debit command
 *
 * @author 摇光 [NO.0146]
 * @since 2017年01月13日 17:37
 */
@Value
public class DebitAccountCommand {

  private String account;
  private Double amount;

}
