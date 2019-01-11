package pico.erp.notify;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.notify.type.NotifyTypeId;
import pico.erp.user.UserId;
import pico.erp.user.group.GroupId;

public interface NotifyRequests {

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class NotifyUserRequest<K> {

    @Valid
    @NotNull
    NotifyTypeId typeId;

    @Valid
    @NotNull
    K key;

    @Valid
    @NotNull
    UserId userId;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class NotifyGroupRequest<K> {

    @Valid
    @NotNull
    NotifyTypeId typeId;

    @Valid
    @NotNull
    K key;

    @Valid
    @NotNull
    GroupId groupId;

  }
}
