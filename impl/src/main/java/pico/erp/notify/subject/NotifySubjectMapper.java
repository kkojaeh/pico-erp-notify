package pico.erp.notify.subject;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper
public abstract class NotifySubjectMapper {

  public NotifySubject jpa(NotifySubjectEntity entity) {
    return NotifySubject.builder()
      .id(entity.getId())
      .watchers(entity.getWatchers())
      .build();
  }

  @Mappings({
    @Mapping(target = "createdBy", ignore = true),
    @Mapping(target = "createdDate", ignore = true),
    @Mapping(target = "lastModifiedBy", ignore = true),
    @Mapping(target = "lastModifiedDate", ignore = true)
  })
  public abstract NotifySubjectEntity jpa(NotifySubject notifySubject);

  public abstract NotifySubjectData map(NotifySubject notifySubject);

  public abstract NotifySubjectMessages.Update.Request map(
    NotifySubjectRequests.UpdateRequest request);

  public abstract NotifySubjectMessages.Create.Request map(
    NotifySubjectRequests.CreateRequest request);

  public abstract void pass(NotifySubjectEntity from, @MappingTarget NotifySubjectEntity to);


}
