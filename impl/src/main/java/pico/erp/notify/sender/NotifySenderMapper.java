package pico.erp.notify.sender;

import org.mapstruct.Mapper;

@Mapper
public abstract class NotifySenderMapper {

  public abstract NotifySenderData map(NotifySenderDefinition definition);


}
