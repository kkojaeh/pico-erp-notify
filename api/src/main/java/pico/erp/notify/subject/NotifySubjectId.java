package pico.erp.notify.subject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import pico.erp.notify.subject.type.NotifySubjectTypeId;
import pico.erp.shared.TypeDefinitions;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "value")
@ToString
public class NotifySubjectId implements Serializable {

  private static final long serialVersionUID = 1L;

  @Getter(onMethod = @__({@JsonValue}))
  @Size(min = 1, max = TypeDefinitions.EXTERNAL_ID_LENGTH)
  @NotNull
  private String value;

  @JsonCreator
  public static NotifySubjectId from(@NonNull String value) {
    return new NotifySubjectId(value);
  }

  public static NotifySubjectId from(@NonNull NotifySubjectTypeId subjectTypeId,
    @NonNull String key) {
    return new NotifySubjectId(subjectTypeId.getValue() + "/" + key);
  }

}
