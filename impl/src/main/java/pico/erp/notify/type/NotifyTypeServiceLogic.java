package pico.erp.notify.type;

import com.github.mustachejava.MustacheFactory;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.notify.message.NotifyMessage;
import pico.erp.notify.message.NotifyMessageMustache;
import pico.erp.notify.type.NotifyTypeRequests.CompileRequest;
import pico.erp.notify.type.NotifyTypeRequests.UpdateRequest;
import pico.erp.shared.ApplicationInitializer;
import pico.erp.shared.Public;
import pico.erp.shared.event.EventPublisher;

@SuppressWarnings("Duplicates")
@Service
@Public
@Transactional
@Validated
public class NotifyTypeServiceLogic implements NotifyTypeService, ApplicationInitializer {

  private final Map<NotifyTypeId, Function> contexts = new HashMap<>();

  @Autowired
  private NotifyTypeRepository notifyTypeRepository;

  @Autowired
  private EventPublisher eventPublisher;

  @Autowired
  private NotifyTypeMapper mapper;

  @Lazy
  @Autowired
  private List<NotifyTypeDefinition> definitions;

  @Autowired
  private MustacheFactory mustacheFactory;

  @SneakyThrows
  @Override
  public NotifyMessage compile(CompileRequest request) {
    val notifyType = notifyTypeRepository.findBy(request.getId())
      .orElseThrow(NotifyTypeExceptions.NotFoundException::new);
    if (!notifyType.isEnabled()) {
      throw new NotifyTypeExceptions.CannotCompileException();
    }
    String markdownTemplate = notifyType.getMarkdownTemplate();
    val context = contexts.get(request.getId()).apply(request.getKey());
    val markdownMustache = mustacheFactory
      .compile(new StringReader(markdownTemplate), notifyType.getId().getValue());
    return new NotifyMessageMustache(markdownMustache, context);
  }

  @Override
  public boolean exists(NotifyTypeId id) {
    return notifyTypeRepository.exists(id);
  }

  @Override
  public NotifyTypeData get(NotifyTypeId id) {
    return notifyTypeRepository.findBy(id)
      .map(mapper::map)
      .orElseThrow(NotifyTypeExceptions.NotFoundException::new);
  }

  @Override
  public void initialize() {
    val targets = definitions.stream().collect(Collectors.toMap(d -> d.getId(), d -> d));
    contexts.putAll(
      definitions.stream()
        .collect(Collectors.toMap(d -> d.getId(), d -> (k) -> d.createContext(k)))
    );
    notifyTypeRepository.findAll().forEach(notifyType -> targets.remove(notifyType.getId()));
    targets.values().forEach(definition -> {
      val notifyType = new NotifyType();
      val request = NotifyTypeMessages.Create.Request.builder()
        .id(definition.getId())
        .name(definition.getName())
        .build();
      val response = notifyType.apply(request);
      notifyTypeRepository.create(notifyType);
      eventPublisher.publishEvents(response.getEvents());
    });

  }

  @Override
  public void update(UpdateRequest request) {
    val notifyType = notifyTypeRepository.findBy(request.getId())
      .orElseThrow(NotifyTypeExceptions.NotFoundException::new);
    val response = notifyType.apply(mapper.map(request));
    notifyTypeRepository.update(notifyType);
    eventPublisher.publishEvents(response.getEvents());
  }
}
