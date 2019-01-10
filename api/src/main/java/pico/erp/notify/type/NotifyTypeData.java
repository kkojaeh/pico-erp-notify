package pico.erp.notify.type;

import java.util.List;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import pico.erp.notify.sender.NotifySenderId;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotifyTypeData {

  NotifyTypeId id;

  String name;

  String markdownTemplate;

  boolean multipleSend;

  boolean enabled;

  List<NotifySenderId> senders;

}
