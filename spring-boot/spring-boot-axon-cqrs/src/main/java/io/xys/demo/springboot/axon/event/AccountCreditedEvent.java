package io.xys.demo.springboot.axon.event;

import java.time.LocalDateTime;
import java.time.ZoneId;

import lombok.Value;

/**
 * a acount credited event
 *
 * @author 摇光 [NO.0146]
 * @since 2017年01月13日 14:14
 */
@Value
public class AccountCreditedEvent {

  private String accountNo;

  private Double amountCredited;

  private Double balance;

  private Long timeStamp;

  protected AccountCreditedEvent(String accountNo, Double amountCredited, Double balance) {
    this.accountNo = accountNo;
    this.amountCredited = amountCredited;
    this.balance = balance;
    ZoneId zoneId = ZoneId.systemDefault();
    this.timeStamp = LocalDateTime.now().atZone(zoneId).toEpochSecond();
  }

  public static AccountCreditedEvent of(String accountNo, Double amountCredited, Double balance) {
    return new AccountCreditedEvent(accountNo, amountCredited, balance);
  }
}
