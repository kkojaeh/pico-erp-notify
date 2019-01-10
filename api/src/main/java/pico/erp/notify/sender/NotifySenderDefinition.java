package pico.erp.notify.sender;

import java.util.Collection;
import pico.erp.notify.message.NotifyMessage;
import pico.erp.notify.target.NotifyTargetData;

public interface NotifySenderDefinition {

  NotifySenderId getId();

  String getName();

  boolean send(NotifyMessage message, Collection<NotifyTargetData> targets);

}
