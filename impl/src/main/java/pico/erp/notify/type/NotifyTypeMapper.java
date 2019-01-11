package pico.erp.notify.type;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import pico.erp.notify.subject.type.NotifySubjectTypeData;
import pico.erp.notify.subject.type.NotifySubjectTypeId;
import pico.erp.notify.subject.type.NotifySubjectTypeService;

@Mapper
public abstract class NotifyTypeMapper {

  @Autowired
  private NotifySubjectTypeService subjectTypeService;

  public NotifyType jpa(NotifyTypeEntity entity) {
    return NotifyType.builder()
      .id(entity.getId())
      .subjectType(map(entity.getSubjectTypeId()))
      .name(entity.getName())
      .markdownTemplate(entity.getMarkdownTemplate())
      .enabled(entity.isEnabled())
      .multipleSend(entity.isMultipleSend())
      .senders(entity.getSenders())
      .build();
  }

  @Mappings({
    @Mapping(target = "subjectTypeId", source = "subjectType.id"),
    @Mapping(target = "createdBy", ignore = true),
    @Mapping(target = "createdDate", ignore = true),
    @Mapping(target = "lastModifiedBy", ignore = true),
    @Mapping(target = "lastModifiedDate", ignore = true)
  })
  public abstract NotifyTypeEntity jpa(NotifyType notifyType);

  @Mappings({
    @Mapping(target = "subjectTypeId", source = "subjectType.id")
  })
  public abstract NotifyTypeData map(NotifyType notifyType);

  public abstract NotifyTypeMessages.Update.Request map(
    NotifyTypeRequests.UpdateRequest request);

  public abstract void pass(NotifyTypeEntity from, @MappingTarget NotifyTypeEntity to);

  protected NotifySubjectTypeData map(NotifySubjectTypeId id) {
    return subjectTypeService.get(id);
  }


}
