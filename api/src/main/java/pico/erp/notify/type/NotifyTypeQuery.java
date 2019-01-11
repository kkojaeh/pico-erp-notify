package pico.erp.notify.type;

import javax.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotifyTypeQuery {

  Page<NotifyTypeView> retrieve(@NotNull NotifyTypeView.Filter filter,
    @NotNull Pageable pageable);

}
