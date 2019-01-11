package pico.erp.notify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import pico.erp.notify.subject.type.NotifySubjectTypeServiceLogic;
import pico.erp.notify.type.NotifyTypeServiceLogic;
import pico.erp.shared.ApplicationInitializer;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Configuration
public class NotifyInitializer implements ApplicationInitializer {

  @Lazy
  @Autowired
  private NotifyTypeServiceLogic notifyTypeService;

  @Lazy
  @Autowired
  private NotifySubjectTypeServiceLogic notifySubjectTypeService;

  @Override
  public void initialize() {
    notifySubjectTypeService.initialize();
    notifyTypeService.initialize();
  }
}
