package pico.erp.notify.subject;

import java.util.Set;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import pico.erp.user.UserId;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotifySubjectData {

  NotifySubjectId id;

  Set<UserId> watchers;

}
