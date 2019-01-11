package pico.erp.notify.type;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import pico.erp.notify.sender.NotifySenderId;
import pico.erp.notify.subject.type.NotifySubjectTypeData;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotifyType implements Serializable {

  private static final long serialVersionUID = 1L;

  NotifyTypeId id;

  NotifySubjectTypeData subjectType;

  String name;

  String markdownTemplate;

  boolean enabled;

  boolean multipleSend;

  List<NotifySenderId> senders;

  public NotifyTypeMessages.Create.Response apply(
    NotifyTypeMessages.Create.Request request) {
    this.id = request.getId();
    this.subjectType = request.getSubjectType();
    this.name = request.getName();
    this.enabled = false;
    this.multipleSend = false;
    this.senders = Collections.emptyList();
    return new NotifyTypeMessages.Create.Response(
      Arrays.asList(new NotifyTypeEvents.CreatedEvent(id))
    );
  }

  public NotifyTypeMessages.Update.Response apply(
    NotifyTypeMessages.Update.Request request) {
    this.name = request.getName();
    this.markdownTemplate = request.getMarkdownTemplate();
    this.enabled = request.isEnabled();
    this.multipleSend = request.isMultipleSend();
    this.senders = request.getSenders();
    return new NotifyTypeMessages.Update.Response(
      Arrays.asList(new NotifyTypeEvents.UpdatedEvent(id))
    );
  }

}
