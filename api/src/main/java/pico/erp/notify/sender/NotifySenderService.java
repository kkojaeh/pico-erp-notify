package pico.erp.notify.sender;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface NotifySenderService {

  boolean exists(@Valid @NotNull NotifySenderId id);

  NotifySenderData get(@Valid @NotNull NotifySenderId id);

  boolean send(NotifySenderRequests.SendTargetRequest request);

  boolean send(NotifySenderRequests.SendGroupRequest request);


}
