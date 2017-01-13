package io.xys.demo.springboot.groovy;

import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;


/**
 * 消息
 *
 * @author 摇光 [NO.0146]
 * @since 2016年11月25日 14:16
 */
public class Message {

  private Long id;

  @NotEmpty(message = "Message is required.")
  private String text;

  @NotEmpty(message = "Summary is required.")
  private String summary;

  private Date created = new Date();

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getCreated() {
    return this.created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public String getText() {
    return this.text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getSummary() {
    return this.summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }
}
