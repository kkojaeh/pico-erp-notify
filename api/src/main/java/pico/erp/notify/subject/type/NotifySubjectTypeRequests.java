package pico.erp.notify.subject.type;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public interface NotifySubjectTypeRequests {

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class ConvertRequest<K> {

    @Valid
    @NotNull
    NotifySubjectTypeId id;

    @Valid
    @NotNull
    K key;

  }

}
