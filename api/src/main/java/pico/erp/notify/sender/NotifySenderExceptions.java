package pico.erp.notify.sender;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface NotifySenderExceptions {

  @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "notify-sender.not.found.exception")
  class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

  }
}
