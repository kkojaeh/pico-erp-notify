package pico.erp.notify.target;

import org.mapstruct.Mapper;
import pico.erp.user.UserData;
import pico.erp.user.UserId;

@Mapper
public abstract class NotifyTargetMapper {

  public abstract NotifyTargetData map(UserData user);

  protected NotifyTargetId map(UserId id) {
    if (id == null) {
      return null;
    } else {
      return NotifyTargetId.from(id.getValue());
    }
  }


}
