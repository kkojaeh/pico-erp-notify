package pico.erp.notify.target;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Data
@EqualsAndHashCode(of = "id")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotifyTargetData {

  NotifyTargetId id;

  String name;

  String mobilePhoneNumber;

  String email;

}
