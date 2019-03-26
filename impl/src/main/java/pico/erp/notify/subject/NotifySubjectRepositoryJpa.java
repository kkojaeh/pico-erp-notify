package pico.erp.notify.subject;

import java.util.Optional;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
interface NotifySubjectEntityRepository extends
  CrudRepository<NotifySubjectEntity, NotifySubjectId> {

}

@Repository
@Transactional
public class NotifySubjectRepositoryJpa implements NotifySubjectRepository {

  @Autowired
  private NotifySubjectEntityRepository repository;

  @Autowired
  private NotifySubjectMapper mapper;

  @Override
  public NotifySubject create(NotifySubject notifySubject) {
    val entity = mapper.jpa(notifySubject);
    val created = repository.save(entity);
    return mapper.jpa(created);
  }

  @Override
  public void deleteBy(NotifySubjectId id) {
    repository.deleteById(id);
  }

  @Override
  public boolean exists(NotifySubjectId id) {
    return repository.existsById(id);
  }


  @Override
  public Optional<NotifySubject> findBy(NotifySubjectId id) {
    return repository.findById(id)
      .map(mapper::jpa);
  }

  @Override
  public void update(NotifySubject notifySubject) {
    val entity = repository.findById(notifySubject.getId()).get();
    mapper.pass(mapper.jpa(notifySubject), entity);
    repository.save(entity);
  }

}
