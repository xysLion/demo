package io.xys.demo.springboot.axon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * use axonframework develop CQRS and ES's project
 *
 * @author 摇光 [NO.0146]
 * @since 2017年01月13日 13:43
 */
@SpringBootApplication
public class AxonAppliton {

  public static void main(String[] args) {
    SpringApplication.run(AxonAppliton.class, args);
  }
}
