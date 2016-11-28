package io.xys.demo.springboot.configured.pojo;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import io.xys.demo.springboot.configured.dao.AccountDao;

/**
 * 普通pojo类
 *
 * @author 摇光 [NO.0146]
 * @since 2016年11月28日 13:44
 */
@Configurable(autowire = Autowire.BY_TYPE)
public class Account {

    public static int index = 0;

    public Account() {
        index = index + 1;
    }

    private AccountDao accountDao;

    @Autowired
    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public String toString(){
        return accountDao.sayHello("" + index);
    }

}
