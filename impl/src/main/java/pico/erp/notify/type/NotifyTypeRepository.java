package pico.erp.notify.type;

import java.util.Optional;
import java.util.stream.Stream;
import javax.validation.constraints.NotNull;

public interface NotifyTypeRepository {

  NotifyType create(NotifyType notifyType);

  void deleteBy(NotifyTypeId id);

  boolean exists(NotifyTypeId id);

  Stream<NotifyType> findAll();

  Optional<NotifyType> findBy(@NotNull NotifyTypeId id);

  void update(@NotNull NotifyType notifyType);

}
