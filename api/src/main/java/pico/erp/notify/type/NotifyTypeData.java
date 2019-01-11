package pico.erp.notify.type;

import java.util.List;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import pico.erp.notify.sender.NotifySenderId;
import pico.erp.notify.subject.type.NotifySubjectTypeId;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotifyTypeData {

  NotifyTypeId id;

  NotifySubjectTypeId subjectTypeId;

  String name;

  String markdownTemplate;

  boolean multipleSend;

  boolean enabled;

  List<NotifySenderId> senders;

}
