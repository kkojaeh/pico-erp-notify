package pico.erp.notify.target;

import java.util.Collection;
import javax.validation.constraints.NotNull;
import pico.erp.user.UserId;
import pico.erp.user.group.GroupId;

public interface NotifyTargetService {

  NotifyTargetData get(@NotNull UserId userId);

  NotifyGroupData get(@NotNull GroupId groupId);

  Collection<NotifyTargetData> getAll(@NotNull GroupId groupId);


}
