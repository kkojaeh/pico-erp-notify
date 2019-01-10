package pico.erp.notify.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.shared.event.Event;

public interface NotifyTypeEvents {

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class CreatedEvent implements Event {

    public final static String CHANNEL = "event.notify-type.created";

    private NotifyTypeId notifyTypeId;

    public String channel() {
      return CHANNEL;
    }

  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class UpdatedEvent implements Event {

    public final static String CHANNEL = "event.notify-type.updated";

    private NotifyTypeId notifyTypeId;

    public String channel() {
      return CHANNEL;
    }

  }

}
