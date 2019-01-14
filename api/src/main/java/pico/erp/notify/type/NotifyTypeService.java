package pico.erp.notify.type;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import pico.erp.notify.message.NotifyMessage;

public interface NotifyTypeService {

  NotifyMessage compile(@Valid @NotNull NotifyTypeRequests.CompileRequest request);

  NotifyMessage testCompile(@Valid @NotNull NotifyTypeRequests.TestCompileRequest request);

  boolean exists(@Valid @NotNull NotifyTypeId id);

  NotifyTypeData get(@Valid @NotNull NotifyTypeId id);

  void update(
    @Valid @NotNull NotifyTypeRequests.UpdateRequest request);

}
