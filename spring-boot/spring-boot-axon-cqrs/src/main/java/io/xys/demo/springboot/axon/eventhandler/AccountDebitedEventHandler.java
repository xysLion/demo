package io.xys.demo.springboot.axon.eventhandler;

import io.xys.demo.springboot.axon.event.AccountDebitedEvent;
import javax.sql.DataSource;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * ${DESCRIPRION}
 *
 * @author 摇光 [NO.0146]
 * @since 2017年01月13日 17:48
 */
@Component
public class AccountDebitedEventHandler {

  @Autowired
  DataSource dataSource;

  /**
   * scribe {@link AccountDebitedEvent} handler.
   *
   * @param event debited event, detail in {@link AccountDebitedEvent}
   */
  @EventHandler
  public void handleAccountDebitedEvent(AccountDebitedEvent event) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

    // Get the current states as reflected in the event
    String accountNo = event.getAccountNo();
    Double balance = event.getBalance();
    Double amountDebited = event.getAmountDebited();
    Double newBalance = balance - amountDebited;

    // Update the view
    String updateQuery = "UPDATE account_view SET balance = ? WHERE account_no = ?";
    jdbcTemplate.update(updateQuery, new Object[]{newBalance, accountNo});
  }

}
