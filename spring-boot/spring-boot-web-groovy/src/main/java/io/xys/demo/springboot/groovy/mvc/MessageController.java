package io.xys.demo.springboot.groovy.mvc;

import com.google.common.io.Files;
import io.xys.demo.springboot.groovy.Message;
import io.xys.demo.springboot.groovy.MessageRepository;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 消息mvc处理类
 *
 * @author 摇光 [NO.0146]
 * @since 2016年11月25日 14:25
 */
@Controller
public class MessageController {

  private final MessageRepository messageRepository;

  public MessageController(MessageRepository messageRepository) {
    this.messageRepository = messageRepository;
  }

  @GetMapping
  public ModelAndView list() {
    Iterable<Message> messages = this.messageRepository.findAll();
    return new ModelAndView("messages/list", "messages", messages);
  }

  @GetMapping("{id}")
  public ModelAndView view(@PathVariable("id") Message message) {
    return new ModelAndView("messages/view", "message", message);
  }

  @GetMapping(params = "form")
  public String createForm(@ModelAttribute Message message) {
    return "messages/form";
  }

  /**
   * render to  form page.
   *
   * @param message   message
   * @param result    validating result
   * @param redirect  redirect attribute
   * @return  form page
   */
  @PostMapping
  public ModelAndView create(
      @Valid Message message, BindingResult result, RedirectAttributes redirect) {
    if (result.hasErrors()) {
      ModelAndView mav = new ModelAndView("messages/form");
      mav.addObject("formErrors", result.getAllErrors());
      mav.addObject("fieldErrors", getFieldErrors(result));
      return mav;
    }
    message = this.messageRepository.save(message);
    redirect.addFlashAttribute("globalMessage", "Successfully created a new message");
    return new ModelAndView("redirect:/{message.id}", "message.id", message.getId());
  }

  private Map<String, ObjectError> getFieldErrors(BindingResult result) {
    Map<String, ObjectError> map = new HashMap<String, ObjectError>();
    for (FieldError error : result.getFieldErrors()) {
      map.put(error.getField(), error);
    }
    return map;
  }

  @RequestMapping("foo")
  public String foo() {
    throw new RuntimeException("Expected exception in controller");
  }

  /**
   * download something.
   *
   * @return  stream
   */
  @RequestMapping("/download")
  public StreamingResponseBody download() {
    final String filePath = ClassLoader.getSystemResource("application.yml").getPath();
    return new StreamingResponseBody() {
      public void writeTo(OutputStream outputStream) throws IOException {
        outputStream.write(Files.toByteArray(new File(filePath)));
      }
    };
  }
}
