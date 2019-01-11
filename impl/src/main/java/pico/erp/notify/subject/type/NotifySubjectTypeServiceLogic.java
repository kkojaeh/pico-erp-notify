package pico.erp.notify.subject.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import pico.erp.notify.sender.NotifySenderExceptions;
import pico.erp.notify.subject.NotifySubjectId;
import pico.erp.notify.subject.type.NotifySubjectTypeRequests.ConvertRequest;
import pico.erp.shared.ApplicationInitializer;
import pico.erp.shared.Public;

@Service
@Public
@Validated
public class NotifySubjectTypeServiceLogic implements NotifySubjectTypeService,
  ApplicationInitializer {

  private final Map<NotifySubjectTypeId, NotifySubjectTypeDefinition> mapping = new HashMap<>();

  @Lazy
  @Autowired
  private List<NotifySubjectTypeDefinition> definitions;

  @Autowired
  private NotifySubjectTypeMapper mapper;

  @Override
  public NotifySubjectId convert(ConvertRequest request) {
    val id = request.getId();
    if (mapping.containsKey(id)) {
      return mapping.get(id).convert(request.getKey());
    } else {
      throw new NotifySenderExceptions.NotFoundException();
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
      throw new NotifySenderExceptions.NotFoundException();
    }
  }

  @Override
  public void initialize() {
    mapping.putAll(
      definitions.stream().collect(Collectors.toMap(d -> d.getId(), d -> d))
    );
  }
}
