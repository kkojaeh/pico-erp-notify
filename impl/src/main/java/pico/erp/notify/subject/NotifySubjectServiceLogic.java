package pico.erp.notify.subject;

import kkojaeh.spring.boot.component.Give;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.notify.subject.NotifySubjectRequests.CreateRequest;
import pico.erp.notify.subject.NotifySubjectRequests.UpdateRequest;
import pico.erp.shared.event.EventPublisher;

@SuppressWarnings("Duplicates")
@Service
@Give
@Transactional
@Validated
public class NotifySubjectServiceLogic implements NotifySubjectService {


  @Autowired
  private NotifySubjectRepository notifySubjectRepository;

  @Autowired
  private EventPublisher eventPublisher;

  @Autowired
  private NotifySubjectMapper mapper;

  @Override
  public NotifySubjectData create(CreateRequest request) {
    val notifySubject = new NotifySubject();
    val response = notifySubject.apply(mapper.map(request));
    val created = notifySubjectRepository.create(notifySubject);
    eventPublisher.publishEvents(response.getEvents());
    return mapper.map(created);
  }

  @Override
  public boolean exists(NotifySubjectId id) {
    return notifySubjectRepository.exists(id);
  }

  @Override
  public NotifySubjectData get(NotifySubjectId id) {
    return notifySubjectRepository.findBy(id)
      .map(mapper::map)
      .orElseThrow(NotifySubjectExceptions.NotFoundException::new);
  }

  @Override
  public void update(UpdateRequest request) {
    val notifyType = notifySubjectRepository.findBy(request.getId())
      .orElseThrow(NotifySubjectExceptions.NotFoundException::new);
    val response = notifyType.apply(mapper.map(request));
    notifySubjectRepository.update(notifyType);
    eventPublisher.publishEvents(response.getEvents());
  }
}
