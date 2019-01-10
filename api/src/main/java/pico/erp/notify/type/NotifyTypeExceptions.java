package pico.erp.notify.type;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface NotifyTypeExceptions {

  @ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "notify-type.cannot.compile.exception")
  class CannotCompileException extends RuntimeException {

    private static final long serialVersionUID = 1L;
  }

  @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "notify-type.not.found.exception")
  class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

  }
}
