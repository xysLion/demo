package io.xys.demo.springboot.configured.dao;

import org.springframework.stereotype.Repository;

/**
 * 注入到spring中的bean
 *
 * @author 摇光 [NO.0146]
 * @since 2016年11月28日 13:45
 */
@Repository
public class AccountDao {

    public String sayHello(String threadName){
        return "Hello " + threadName;
    }

}
