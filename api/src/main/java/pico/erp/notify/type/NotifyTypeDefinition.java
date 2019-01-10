package pico.erp.notify.type;

import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.Getter;

public interface NotifyTypeDefinition<K, C> {

  C createContext(K key);

  NotifyTypeId getId();

  String getName();

  @Getter
  @AllArgsConstructor
  class NotifyTypeDefinitionImpl<K, C> implements NotifyTypeDefinition<K, C> {

    NotifyTypeId id;

    String name;

    Function<K, C> creator;

    @Override
    public C createContext(K key) {
      return creator.apply(key);
    }
  }

}
