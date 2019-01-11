package pico.erp.notify.sender;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import pico.erp.notify.sender.NotifySenderRequests.SendGroupRequest;
import pico.erp.shared.ApplicationInitializer;
import pico.erp.shared.Public;

@Service
@Public
@Validated
public class NotifySenderServiceLogic implements NotifySenderService, ApplicationInitializer {

  private final Map<NotifySenderId, NotifySenderDefinition> mapping = new HashMap<>();

  @Autowired
  @Lazy
  private List<NotifySenderDefinition> definitions;

  @Autowired
  private NotifySenderMapper mapper;

  @Override
  public boolean exists(NotifySenderId id) {
    return mapping.containsKey(id);
  }

  @Override
  public NotifySenderData get(NotifySenderId id) {
    if (mapping.containsKey(id)) {
      return mapper.map(mapping.get(id));
    } else {
      throw new NotifySenderExceptions.NotFoundException();
    }
  }

  @Override
  public void initialize() {
    mapping.putAll(
      definitions.stream().collect(Collectors.toMap(d -> d.getId(), d -> d))
    );
  }

  @Override
  public boolean send(NotifySenderRequests.SendTargetRequest request) {
    val id = request.getId();
    if (mapping.containsKey(id)) {
      return mapping.get(id).send(request.getMessage(), request.getTarget());
    } else {
      throw new NotifySenderExceptions.NotFoundException();
    }
  }

  @Override
  public boolean send(SendGroupRequest request) {
    val id = request.getId();
    if (mapping.containsKey(id)) {
      return mapping.get(id).send(request.getMessage(), request.getGroup());
    } else {
      throw new NotifySenderExceptions.NotFoundException();
    }
  }
}
