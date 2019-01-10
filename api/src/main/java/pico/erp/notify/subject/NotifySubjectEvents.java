package pico.erp.notify.subject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.shared.event.Event;

public interface NotifySubjectEvents {

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class CreatedEvent implements Event {

    public final static String CHANNEL = "event.notify-subject.created";

    private NotifySubjectId notifySubjectId;

    public String channel() {
      return CHANNEL;
    }

  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class UpdatedEvent implements Event {

    public final static String CHANNEL = "event.notify-subject.updated";

    private NotifySubjectId notifySubjectId;

    public String channel() {
      return CHANNEL;
    }

  }

}
