package pico.erp.notify.sender;

import java.util.Collection;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.notify.message.NotifyMessage;
import pico.erp.notify.target.NotifyTargetData;

public interface NotifySenderRequests {

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class SendRequest {

    @Valid
    @NotNull
    NotifySenderId id;

    @Valid
    @NotNull
    NotifyMessage message;

    @NotNull
    Collection<NotifyTargetData> targets;

  }


}
