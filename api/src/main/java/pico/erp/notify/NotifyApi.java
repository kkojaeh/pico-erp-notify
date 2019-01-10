package pico.erp.notify;

import javax.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pico.erp.shared.ApplicationId;
import pico.erp.shared.data.Role;

public final class NotifyApi {

  public static ApplicationId ID = ApplicationId.from("notify");

  @RequiredArgsConstructor
  public enum Roles implements Role {

    NOTIFY_MANAGER;

    @Id
    @Getter
    private final String id = name();

  }
}
