package pico.erp.notify.type;

import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pico.erp.notify.subject.type.NotifySubjectTypeId;

public interface NotifyTypeDefinition<K, C> {

  C createContext(K key);

  K createKey(String key);

  NotifyTypeId getId();

  NotifySubjectTypeId getSubjectTypeId();

  String getName();

  @Getter
  @Builder
  @AllArgsConstructor
  class Impl<K, C> implements NotifyTypeDefinition<K, C> {

    NotifyTypeId id;

    NotifySubjectTypeId subjectTypeId;

    String name;

    Function<K, C> contextCreator;

    Function<String, K> keyCreator;

    @Override
    public C createContext(K key) {
      return contextCreator.apply(key);
    }

    @Override
    public K createKey(String key) {
      return keyCreator.apply(key);
    }
  }

}
