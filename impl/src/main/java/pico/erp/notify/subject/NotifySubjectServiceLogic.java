package pico.erp.notify.subject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.notify.subject.NotifySubjectRequests.CreateRequest;
import pico.erp.notify.subject.NotifySubjectRequests.UpdateRequest;
import pico.erp.shared.Public;

@SuppressWarnings("Duplicates")
@Service
@Public
@Transactional
@Validated
public class NotifySubjectServiceLogic implements NotifySubjectService {

  @Override
  public NotifySubjectData create(CreateRequest request) {
    return null;
  }

  @Override
  public boolean exists(NotifySubjectId id) {
    return false;
  }

  @Override
  public NotifySubjectData get(NotifySubjectId id) {
    return null;
  }

  @Override
  public void initialize() {

  }

  @Override
  public void update(UpdateRequest request) {

  }
}
