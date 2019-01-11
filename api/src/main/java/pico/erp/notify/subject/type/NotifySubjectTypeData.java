package pico.erp.notify.subject.type;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotifySubjectTypeData {

  NotifySubjectTypeId id;

  String name;

}
