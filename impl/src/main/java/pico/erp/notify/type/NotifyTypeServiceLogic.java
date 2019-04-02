package pico.erp.notify.type;

import com.github.mustachejava.MustacheFactory;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import kkojaeh.spring.boot.component.Give;
import kkojaeh.spring.boot.component.SpringBootComponentReadyEvent;
import kkojaeh.spring.boot.component.Take;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.notify.message.NotifyMessage;
import pico.erp.notify.message.NotifyMessageMustache;
import pico.erp.notify.subject.type.NotifySubjectTypeService;
import pico.erp.notify.type.NotifyTypeRequests.CompileRequest;
import pico.erp.notify.type.NotifyTypeRequests.TestCompileRequest;
import pico.erp.notify.type.NotifyTypeRequests.UpdateRequest;
import pico.erp.shared.event.EventPublisher;

@SuppressWarnings("Duplicates")
@Service
@Give
@Transactional
@Validated
public class NotifyTypeServiceLogic implements NotifyTypeService,
  ApplicationListener<SpringBootComponentReadyEvent> {

  private final Map<NotifyTypeId, NotifyTypeDefinition> mapping = new HashMap<>();

  @Autowired
  private NotifyTypeRepository notifyTypeRepository;

  @Autowired
  private EventPublisher eventPublisher;

  @Autowired
  private NotifyTypeMapper mapper;

  @Take(required = false)
  private List<NotifyTypeDefinition> definitions;

  @Autowired
  private MustacheFactory mustacheFactory;

  @Autowired
  private NotifySubjectTypeService subjectTypeService;

  @SneakyThrows
  @Override
  public NotifyMessage compile(CompileRequest request) {
    val notifyType = notifyTypeRepository.findBy(request.getId())
      .orElseThrow(NotifyTypeExceptions.NotFoundException::new);
    if (!notifyType.isEnabled()) {
      throw new NotifyTypeExceptions.CannotCompileException();
    }
    String markdownTemplate = notifyType.getMarkdownTemplate();
    val context = mapping.get(request.getId()).createContext(request.getKey());
    val markdownMustache = mustacheFactory
      .compile(new StringReader(markdownTemplate), notifyType.getId().getValue());
    return new NotifyMessageMustache(markdownMustache, context);
  }

  /*public void initialize() {
    val targets = definitions.stream().collect(Collectors.toMap(d -> d.getId(), d -> d));
    mapping.putAll(
      definitions.stream()
        .collect(Collectors.toMap(d -> d.getId(), d -> d))
    );
    notifyTypeRepository.findAll().forEach(notifyType -> targets.remove(notifyType.getId()));
    targets.values().forEach(definition -> {
      val notifyType = new NotifyType();
      val request = NotifyTypeMessages.Create.Request.builder()
        .id(definition.getId())
        .subjectType(subjectTypeService.get(definition.getSubjectTypeId()))
        .name(definition.getName())
        .build();
      val response = notifyType.apply(request);
      notifyTypeRepository.create(notifyType);
      eventPublisher.publishEvents(response.getEvents());
    });

  }*/

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
  public void onApplicationEvent(SpringBootComponentReadyEvent event) {
    val targets = definitions.stream().collect(Collectors.toMap(d -> d.getId(), d -> d));
    mapping.putAll(
      definitions.stream()
        .collect(Collectors.toMap(d -> d.getId(), d -> d))
    );
    notifyTypeRepository.findAll().forEach(notifyType -> targets.remove(notifyType.getId()));
    targets.values().forEach(definition -> {
      val notifyType = new NotifyType();
      val request = NotifyTypeMessages.Create.Request.builder()
        .id(definition.getId())
        .subjectType(subjectTypeService.get(definition.getSubjectTypeId()))
        .name(definition.getName())
        .build();
      val response = notifyType.apply(request);
      notifyTypeRepository.create(notifyType);
      eventPublisher.publishEvents(response.getEvents());
    });
  }

  @Override
  public NotifyMessage testCompile(TestCompileRequest request) {
    val notifyType = notifyTypeRepository.findBy(request.getId())
      .orElseThrow(NotifyTypeExceptions.NotFoundException::new);
    val markdownTemplate = Optional.ofNullable(request.getMarkdownTemplate())
      .orElse("");
    val definition = mapping.get(request.getId());
    val key = definition.createKey(request.getKey());
    val context = definition.createContext(key);
    val markdownMustache = mustacheFactory
      .compile(new StringReader(markdownTemplate), notifyType.getId().getValue());
    return new NotifyMessageMustache(markdownMustache, context);
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
