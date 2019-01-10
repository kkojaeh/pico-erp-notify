package pico.erp.notify.subject;

import java.util.Optional;
import javax.validation.constraints.NotNull;

public interface NotifySubjectRepository {

  NotifySubject create(NotifySubject notifySubject);

  void deleteBy(NotifySubjectId id);

  boolean exists(NotifySubjectId id);

  Optional<NotifySubject> findBy(@NotNull NotifySubjectId id);

  void update(@NotNull NotifySubject notifySubject);

}
