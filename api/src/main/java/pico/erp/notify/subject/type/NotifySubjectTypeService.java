package pico.erp.notify.subject.type;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import pico.erp.notify.subject.NotifySubjectId;

public interface NotifySubjectTypeService {

  NotifySubjectId convert(NotifySubjectTypeRequests.ConvertRequest request);

  boolean exists(@Valid @NotNull NotifySubjectTypeId id);

  NotifySubjectTypeData get(@Valid @NotNull NotifySubjectTypeId id);

  List<NotifySubjectTypeData> getAll();

}
