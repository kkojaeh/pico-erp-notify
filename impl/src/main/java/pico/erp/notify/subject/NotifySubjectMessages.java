package pico.erp.notify.subject;

import java.util.Collection;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import pico.erp.shared.event.Event;
import pico.erp.user.UserId;

public interface NotifySubjectMessages {

  interface Create {

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    class Request {

      @Valid
      @NotNull
      NotifySubjectId id;

      @NotNull
      Set<UserId> watchers;

    }

    @Value
    class Response {

      Collection<Event> events;

    }
  }


  interface Update {

    @Data
    class Request {

      @NotNull
      Set<UserId> watchers;

    }

    @Value
    class Response {

      Collection<Event> events;

    }
  }


}
