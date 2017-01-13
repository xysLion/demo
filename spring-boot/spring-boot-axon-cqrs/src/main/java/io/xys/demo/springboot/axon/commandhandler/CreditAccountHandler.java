package io.xys.demo.springboot.axon.commandhandler;

import io.xys.demo.springboot.axon.command.CreditAccountCommand;
import io.xys.demo.springboot.axon.domain.Account;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Credit Handler
 *
 * @author 摇光 [NO.0146]
 * @since 2017年01月13日 17:35
 */
@Component
public class CreditAccountHandler {

  private Repository repository;

  @Autowired
  public CreditAccountHandler(Repository repository) {
    this.repository = repository;
  }

  @CommandHandler
  public void handle(CreditAccountCommand creditAccountCommandCommand) {
    Account accountToCredit = (Account) repository.load(creditAccountCommandCommand.getAccount());
    accountToCredit.credit(creditAccountCommandCommand.getAmount());
  }

}
