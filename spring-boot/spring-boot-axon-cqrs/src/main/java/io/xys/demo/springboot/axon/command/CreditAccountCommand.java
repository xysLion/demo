package io.xys.demo.springboot.axon.command;

import lombok.Value;

/**
 * credit command
 *
 * @author 摇光 [NO.0146]
 * @since 2017年01月13日 17:36
 */
@Value
public class CreditAccountCommand {

  private String account;
  private Double amount;

}
