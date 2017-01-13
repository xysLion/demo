package io.xys.demo.springboot.axon.eventhandler;

import io.xys.demo.springboot.axon.event.AccountCreditedEvent;
import java.time.LocalDateTime;
import javax.sql.DataSource;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.axonframework.messaging.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * credited event handler
 *
 * @author 摇光 [NO.0146]
 * @since 2017年01月13日 17:43
 */
@Component
public class AccountCreditedEventHandler {

  @Autowired
  DataSource dataSource;

  /**
   * scribe {@link AccountCreditedEvent} handler.
   *
   * @param event         credited event, detail in {@link AccountCreditedEvent}
   * @param eventMessage  message
   * @param moment        timestamp
   */
  @EventHandler
  public void handleAccountCreditedEvent(AccountCreditedEvent event, Message eventMessage,
      @Timestamp LocalDateTime moment) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

    // Get the current states as reflected in the event
    String accountNo = event.getAccountNo();
    Double balance = event.getBalance();
    Double amountCredited = event.getAmountCredited();
    Double newBalance = balance + amountCredited;

    // Update the view
    String updateQuery = "UPDATE account_view SET balance = ? WHERE account_no = ?";
    jdbcTemplate.update(updateQuery, new Object[]{newBalance, accountNo});

    System.out.println(
        "Events Handled With EventMessage " + eventMessage.toString() + " at " + moment.toString());
  }

}
