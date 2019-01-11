package pico.erp.notify.subject.type;

import org.mapstruct.Mapper;

@Mapper
public abstract class NotifySubjectTypeMapper {

  public abstract NotifySubjectTypeData map(NotifySubjectTypeDefinition definition);


}
