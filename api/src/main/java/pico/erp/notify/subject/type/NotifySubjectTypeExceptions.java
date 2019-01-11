package pico.erp.notify.subject.type;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface NotifySubjectTypeExceptions {

  @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "notify-subject-type.not.found.exception")
  class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

  }
}
