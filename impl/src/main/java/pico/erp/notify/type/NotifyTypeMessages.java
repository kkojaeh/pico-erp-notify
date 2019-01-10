package pico.erp.notify.type;

import java.util.Collection;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import pico.erp.notify.sender.NotifySenderId;
import pico.erp.shared.TypeDefinitions;
import pico.erp.shared.event.Event;

public interface NotifyTypeMessages {

  interface Create {

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    class Request {

      @Valid
      @NotNull
      NotifyTypeId id;

      @NotNull
      @Size(max = TypeDefinitions.NAME_LENGTH)
      String name;

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
      @Size(max = TypeDefinitions.NAME_LENGTH)
      String name;

      @NotNull
      @Size(max = TypeDefinitions.CLOB_LENGTH)
      String markdownTemplate;

      @NotNull
      List<NotifySenderId> senders;

      boolean enabled;

      boolean multipleSend;

    }

    @Value
    class Response {

      Collection<Event> events;

    }
  }


}
