package pico.erp.notify.sender;

import java.util.List;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import pico.erp.notify.sender.NotifySenderRequests.SendRequest;
import pico.erp.shared.Public;

@Service
@Public
@Validated
public class NotifySenderServiceLogic implements NotifySenderService {

  @Autowired
  @Lazy
  private List<NotifySenderDefinition> definitions;

  @Autowired
  private NotifySenderMapper mapper;

  @Override
  public boolean exists(NotifySenderId id) {
    return definitions.stream()
      .anyMatch(d -> d.getId().equals(id));
  }

  @Override
  public NotifySenderData get(NotifySenderId id) {
    return definitions.stream()
      .filter(d -> d.getId().equals(id))
      .findAny()
      .map(mapper::map)
      .orElseThrow(NotifySenderExceptions.NotFoundException::new);
  }

  @Override
  public boolean send(SendRequest request) {
    val definition = definitions.stream()
      .filter(d -> d.getId().equals(request.getId()))
      .findAny()
      .orElseThrow(NotifySenderExceptions.NotFoundException::new);
    return definition.send(request.getMessage(), request.getTargets());
  }
}
