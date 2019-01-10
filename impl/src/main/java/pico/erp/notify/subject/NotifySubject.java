package pico.erp.notify.subject;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import pico.erp.user.UserId;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotifySubject implements Serializable {

  private static final long serialVersionUID = 1L;

  NotifySubjectId id;

  Set<UserId> watchers;

  public NotifySubjectMessages.Create.Response apply(
    NotifySubjectMessages.Create.Request request) {
    this.id = request.getId();
    this.watchers = request.getWatchers();
    return new NotifySubjectMessages.Create.Response(
      Arrays.asList(new NotifySubjectEvents.CreatedEvent(id))
    );
  }

  public NotifySubjectMessages.Update.Response apply(
    NotifySubjectMessages.Update.Request request) {
    this.watchers = request.getWatchers();
    return new NotifySubjectMessages.Update.Response(
      Arrays.asList(new NotifySubjectEvents.UpdatedEvent(id))
    );
  }

}
