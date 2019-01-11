package pico.erp.notify.subject.type;

import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pico.erp.notify.subject.NotifySubjectId;

public interface NotifySubjectTypeDefinition<K> {

  NotifySubjectId convert(K key);

  NotifySubjectTypeId getId();

  String getName();

  @Getter
  @Builder
  @AllArgsConstructor
  class Impl<K> implements NotifySubjectTypeDefinition<K> {

    NotifySubjectTypeId id;

    String name;

    Function<K, NotifySubjectId> converter;

    @Override
    public NotifySubjectId convert(K key) {
      return converter.apply(key);
    }
  }

}
