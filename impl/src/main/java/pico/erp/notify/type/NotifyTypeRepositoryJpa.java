package pico.erp.notify.type;

import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
interface NotifyTypeEntityRepository extends CrudRepository<NotifyTypeEntity, NotifyTypeId> {

}

@Repository
@Transactional
public class NotifyTypeRepositoryJpa implements NotifyTypeRepository {

  @Autowired
  private NotifyTypeEntityRepository repository;

  @Autowired
  private NotifyTypeMapper mapper;

  @Override
  public NotifyType create(NotifyType notifyType) {
    val entity = mapper.jpa(notifyType);
    val created = repository.save(entity);
    return mapper.jpa(created);
  }

  @Override
  public void deleteBy(NotifyTypeId id) {
    repository.delete(id);
  }

  @Override
  public boolean exists(NotifyTypeId id) {
    return repository.exists(id);
  }

  @Override
  public Stream<NotifyType> findAll() {
    return StreamSupport.stream(repository.findAll().spliterator(), false)
      .map(mapper::jpa);
  }

  @Override
  public Optional<NotifyType> findBy(NotifyTypeId id) {
    return Optional.ofNullable(repository.findOne(id))
      .map(mapper::jpa);
  }

  @Override
  public void update(NotifyType notifyType) {
    val entity = repository.findOne(notifyType.getId());
    mapper.pass(mapper.jpa(notifyType), entity);
    repository.save(entity);
  }

}
