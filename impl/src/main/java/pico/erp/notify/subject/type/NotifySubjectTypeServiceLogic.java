package pico.erp.notify.subject.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import kkojaeh.spring.boot.component.Give;
import kkojaeh.spring.boot.component.SpringBootComponentReadyEvent;
import kkojaeh.spring.boot.component.Take;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import pico.erp.notify.subject.NotifySubjectId;
import pico.erp.notify.subject.type.NotifySubjectTypeRequests.ConvertRequest;

@Service
@Give
@Validated
public class NotifySubjectTypeServiceLogic implements NotifySubjectTypeService,
  ApplicationListener<SpringBootComponentReadyEvent> {

  private final Map<NotifySubjectTypeId, NotifySubjectTypeDefinition> mapping = new HashMap<>();

  @Take(required = false)
  private List<NotifySubjectTypeDefinition> definitions;

  @Autowired
  private NotifySubjectTypeMapper mapper;

  @Override
  public NotifySubjectId convert(ConvertRequest request) {
    val id = request.getId();
    if (mapping.containsKey(id)) {
      return mapping.get(id).convert(request.getKey());
    } else {
      throw new NotifySubjectTypeExceptions.NotFoundException();
    }
  }

  @Override
  public boolean exists(NotifySubjectTypeId id) {
    return mapping.containsKey(id);
  }

  @Override
  public NotifySubjectTypeData get(NotifySubjectTypeId id) {
    if (mapping.containsKey(id)) {
      return mapper.map(mapping.get(id));
    } else {
      throw new NotifySubjectTypeExceptions.NotFoundException();
    }
  }

  @Override
  public List<NotifySubjectTypeData> getAll() {
    return definitions.stream()
      .map(mapper::map)
      .collect(Collectors.toList());
  }

  /*public void initialize() {
    mapping.putAll(
      definitions.stream().collect(Collectors.toMap(d -> d.getId(), d -> d))
    );
  }*/

  @Override
  public void onApplicationEvent(SpringBootComponentReadyEvent event) {
    mapping.putAll(
      definitions.stream().collect(Collectors.toMap(d -> d.getId(), d -> d))
    );
  }
}
