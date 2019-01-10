package pico.erp.notify.subject;

import java.io.Serializable;
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

}
