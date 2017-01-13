package io.xys.demo.springboot.axon.domain;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

import io.xys.demo.springboot.axon.event.AccountCreatedEvent;
import io.xys.demo.springboot.axon.event.AccountCreditedEvent;
import io.xys.demo.springboot.axon.event.AccountDebitedEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateRoot;
import org.axonframework.eventsourcing.EventSourcingHandler;

/**
 * a account aggregate root.
 *
 * @author 摇光 [NO.0146]
 * @since 2017年01月13日 13:50
 */
@AggregateRoot
@NoArgsConstructor
public class Account {

  @AggregateIdentifier
  @Getter
  private String accountNo;

  @Getter
  private Double balance;

  public Account(String accountNo) {
    apply(AccountCreatedEvent.of(accountNo));
  }

  @EventSourcingHandler
  public void applyAccountCreation(AccountCreatedEvent event) {
    this.accountNo = event.getAccountNo();
    this.balance = 0.0d;
  }

  /**
   * Business Logic: Cannot debit with a negative amount Cannot debit with amount that leaves the
   * account balance in a negative state.
   *
   * @param debitAmount debit money
   */
  public void debit(Double debitAmount) {

    if (Double.compare(debitAmount, 0.0d) > 0 && this.balance - debitAmount > -1) {
      /**
       * Instead of changing the state directly we apply an event that conveys what happened.
       *
       * <p>The event thus applied is stored.
       */
      apply(AccountDebitedEvent.of(this.accountNo, debitAmount, this.balance));
    } else {
      throw new IllegalArgumentException("Cannot debit with the amount");
    }
  }

  /**
   * This method is called as a reflection of applying events stored in the event store. Consequent
   * application of all the events in the event store will bring the Account to the most recent
   * state.
   */
  @EventSourcingHandler
  private void applyDebit(AccountDebitedEvent event) {

    this.balance -= event.getAmountDebited();
  }

  /**
   * Business Logic: Cannot credit with a negative amount Cannot credit with more than a million
   * amount (You laundering money?).
   *
   * @param creditAmount credit money
   */
  public void credit(Double creditAmount) {
    if (Double.compare(creditAmount, 0.0d) > 0 && Double.compare(creditAmount, 1000000) < 0) {
      /**
       * Instead of changing the state directly we apply an event that conveys what happened.
       *
       * <p>The event thus applied is stored.
       */
      apply(AccountCreditedEvent.of(this.accountNo, creditAmount, this.balance));
    } else {
      throw new IllegalArgumentException("Cannot credit with the amount");
    }
  }

  /**
   * This method is called as a reflection of applying events stored in the event store.
   * Consequent application of all the events in the event store will bring the Account to the
   * most recent state.
   */
  @EventSourcingHandler
  private void applyCredit(AccountCreditedEvent event) {
    this.balance += event.getAmountCredited();
  }
}