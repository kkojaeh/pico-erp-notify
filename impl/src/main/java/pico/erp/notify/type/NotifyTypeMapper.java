package pico.erp.notify.type;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper
public abstract class NotifyTypeMapper {

  public NotifyType jpa(NotifyTypeEntity entity) {
    return NotifyType.builder()
      .id(entity.getId())
      .name(entity.getName())
      .markdownTemplate(entity.getMarkdownTemplate())
      .enabled(entity.isEnabled())
      .multipleSend(entity.isMultipleSend())
      .senders(entity.getSenders())
      .build();
  }

  @Mappings({
    @Mapping(target = "createdBy", ignore = true),
    @Mapping(target = "createdDate", ignore = true),
    @Mapping(target = "lastModifiedBy", ignore = true),
    @Mapping(target = "lastModifiedDate", ignore = true)
  })
  public abstract NotifyTypeEntity jpa(NotifyType notifyType);

  public abstract NotifyTypeData map(NotifyType notifyType);

  public abstract NotifyTypeMessages.Update.Request map(
    NotifyTypeRequests.UpdateRequest request);

  public abstract void pass(NotifyTypeEntity from, @MappingTarget NotifyTypeEntity to);


}
