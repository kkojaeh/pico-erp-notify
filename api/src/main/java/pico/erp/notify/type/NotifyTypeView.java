package pico.erp.notify.type;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.notify.subject.type.NotifySubjectTypeId;

@Data
public class NotifyTypeView {

  NotifyTypeId id;

  NotifySubjectTypeId subjectTypeId;

  String name;

  boolean multipleSend;

  boolean enabled;

  OffsetDateTime createdDate;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Filter {

    String name;

    NotifySubjectTypeId subjectTypeId;

  }

}
