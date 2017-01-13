package io.xys.demo.springboot.axon.event;

import java.time.LocalDateTime;
import java.time.ZoneId;

import lombok.Value;

/**
 * a account debited event
 *
 * @author 摇光 [NO.0146]
 * @since 2017年01月13日 14:53
 */
@Value
public class AccountDebitedEvent {

  private String accountNo;

  private Double amountDebited;

  private Double balance;

  private Long timeStamp;

  protected AccountDebitedEvent(String accountNo, Double amountDebited, Double balance) {
    this.accountNo = accountNo;
    this.amountDebited = amountDebited;
    this.balance = balance;
    ZoneId zoneId = ZoneId.systemDefault();
    this.timeStamp = LocalDateTime.now().atZone(zoneId).toEpochSecond();
  }

  public static AccountDebitedEvent of(String accountNo, Double amountDebited, Double balance) {
    return new AccountDebitedEvent(accountNo, amountDebited, balance);
  }
}
