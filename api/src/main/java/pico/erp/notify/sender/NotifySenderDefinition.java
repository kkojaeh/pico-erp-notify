package pico.erp.notify.sender;

import pico.erp.notify.message.NotifyMessage;
import pico.erp.notify.target.NotifyGroupData;
import pico.erp.notify.target.NotifyTargetData;

public interface NotifySenderDefinition {

  NotifySenderId getId();

  String getName();

  boolean send(NotifyMessage message, NotifyGroupData group);

  boolean send(NotifyMessage message, NotifyTargetData target);

}
