package pico.erp.notify.target;

import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import pico.erp.shared.Public;
import pico.erp.user.UserExceptions;
import pico.erp.user.UserId;
import pico.erp.user.UserService;
import pico.erp.user.group.GroupId;
import pico.erp.user.group.GroupQuery;

@Service
@Public
@Validated
public class NotifyTargetServiceLogic implements NotifyTargetService {

  @Lazy
  @Autowired
  private UserService userService;

  @Lazy
  @Autowired
  private GroupQuery groupQuery;

  @Autowired
  private NotifyTargetMapper mapper;

  @Override
  public NotifyTargetData get(UserId userId) {
    try {
      return mapper.map(userService.get(userId));
    } catch (UserExceptions.NotFoundException e) {
      return null;
    }
  }

  @Override
  public Collection<NotifyTargetData> getAll(GroupId groupId) {
    return groupQuery.findAllGroupJoinedUser(groupId)
      .stream()
      .map(view -> get(view.getUserId()))
      .collect(Collectors.toList());
  }
}
