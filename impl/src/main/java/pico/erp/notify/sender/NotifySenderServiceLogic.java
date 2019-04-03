package pico.erp.notify.sender;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import kkojaeh.spring.boot.component.ComponentAutowired;
import kkojaeh.spring.boot.component.ComponentBean;
import kkojaeh.spring.boot.component.SpringBootComponentReadyEvent;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import pico.erp.notify.sender.NotifySenderRequests.SendGroupRequest;

@Service
@ComponentBean
@Validated
public class NotifySenderServiceLogic implements NotifySenderService,
  ApplicationListener<SpringBootComponentReadyEvent> {

  private final Map<NotifySenderId, NotifySenderDefinition> mapping = new HashMap<>();

  @ComponentAutowired(required = false)
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
  public List<NotifySenderData> getAll() {
    return definitions.stream()
      .map(mapper::map)
      .collect(Collectors.toList());
  }

  @Override
  public void onApplicationEvent(SpringBootComponentReadyEvent event) {
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
