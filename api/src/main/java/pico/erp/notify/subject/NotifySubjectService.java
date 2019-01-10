package pico.erp.notify.subject;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface NotifySubjectService {

  NotifySubjectData create(@Valid @NotNull NotifySubjectRequests.CreateRequest request);

  boolean exists(@Valid @NotNull NotifySubjectId id);

  NotifySubjectData get(@Valid @NotNull NotifySubjectId id);

  void initialize();

  void update(@Valid @NotNull NotifySubjectRequests.UpdateRequest request);


}
