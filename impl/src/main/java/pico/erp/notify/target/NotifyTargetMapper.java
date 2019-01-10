package pico.erp.notify.target;

import org.mapstruct.Mapper;
import pico.erp.user.UserData;
import pico.erp.user.UserId;
import pico.erp.user.group.GroupData;
import pico.erp.user.group.GroupId;

@Mapper
public abstract class NotifyTargetMapper {

  public abstract NotifyTargetData map(UserData user);

  public abstract NotifyGroupData map(GroupData group);

  protected NotifyTargetId map(UserId id) {
    if (id == null) {
      return null;
    } else {
      return NotifyTargetId.from(id.getValue());
    }
  }

  protected NotifyTargetId map(GroupId id) {
    if (id == null) {
      return null;
    } else {
      return NotifyTargetId.from(id.getValue());
    }
  }


}
