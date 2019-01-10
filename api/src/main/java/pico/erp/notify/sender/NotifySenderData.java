package pico.erp.notify.sender;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotifySenderData {

  NotifySenderId id;

  String name;

}
