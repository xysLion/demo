package io.xys.demo.springboot.axon.commandhandler;

import io.xys.demo.springboot.axon.command.DebitAccountCommand;
import io.xys.demo.springboot.axon.domain.Account;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * debit handler
 *
 * @author 摇光 [NO.0146]
 * @since 2017年01月13日 17:40
 */
@Component
public class DebitAccountHandler {

  private Repository repository;

  @Autowired
  public DebitAccountHandler(Repository repository) {
    this.repository = repository;
  }

  @CommandHandler
  public void handle(DebitAccountCommand debitAccountCommandCommand) {
    Account accountToDebit = (Account) repository.load(debitAccountCommandCommand.getAccount());
    accountToDebit.debit(debitAccountCommandCommand.getAmount());
  }

}