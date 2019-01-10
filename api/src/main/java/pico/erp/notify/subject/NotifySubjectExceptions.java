package pico.erp.notify.subject;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface NotifySubjectExceptions {

  @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "notify-subject.not.found.exception")
  class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

  }
}
