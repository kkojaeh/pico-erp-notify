package pico.erp.notify;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface NotifyExceptions {

  @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "notify.not.found.exception")
  class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

  }

  @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "notify.subject.type.not.found.exception")
  class SubjectTypeNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

  }
}
