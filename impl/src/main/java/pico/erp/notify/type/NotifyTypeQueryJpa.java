package pico.erp.notify.type;

import static org.springframework.util.StringUtils.isEmpty;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import kkojaeh.spring.boot.component.ComponentBean;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.notify.type.NotifyTypeView.Filter;
import pico.erp.shared.jpa.QueryDslJpaSupport;

@Service
@ComponentBean
@Transactional(readOnly = true)
@Validated
public class NotifyTypeQueryJpa implements NotifyTypeQuery {

  private final QNotifyTypeEntity notifyType = QNotifyTypeEntity.notifyTypeEntity;

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  private QueryDslJpaSupport queryDslJpaSupport;

  @Override
  public Page<NotifyTypeView> retrieve(Filter filter, Pageable pageable) {
    val query = new JPAQuery<NotifyTypeView>(entityManager);
    val select = Projections.bean(NotifyTypeView.class,
      notifyType.id,
      notifyType.subjectTypeId,
      notifyType.name,
      notifyType.multipleSend,
      notifyType.enabled,
      notifyType.createdDate
    );

    query.select(select);
    query.from(notifyType);

    val builder = new BooleanBuilder();

    if (!isEmpty(filter.getName())) {
      builder.and(notifyType.name
        .likeIgnoreCase(queryDslJpaSupport.toLikeKeyword("%", filter.getName(), "%")));
    }

    if (filter.getSubjectTypeId() != null) {
      builder.and(notifyType.subjectTypeId.eq(filter.getSubjectTypeId()));
    }

    query.where(builder);
    return queryDslJpaSupport.paging(query, pageable, select);
  }
}
