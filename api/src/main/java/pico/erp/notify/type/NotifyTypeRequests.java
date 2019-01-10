package pico.erp.notify.type;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.notify.sender.NotifySenderId;
import pico.erp.shared.TypeDefinitions;

public interface NotifyTypeRequests {

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class UpdateRequest {

    @Valid
    @NotNull
    NotifyTypeId id;

    @NotNull
    @Size(max = TypeDefinitions.NAME_LENGTH)
    String name;

    @NotNull
    @Size(max = TypeDefinitions.CLOB_LENGTH)
    String markdownTemplate;

    @NotNull
    List<NotifySenderId> senders;

    boolean enabled;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class CompileRequest<K> {

    @Valid
    @NotNull
    NotifyTypeId id;

    @Valid
    @NotNull
    K key;

  }

}
