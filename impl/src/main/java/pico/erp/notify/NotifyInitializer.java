package pico.erp.notify;

import kkojaeh.spring.boot.component.SpringBootComponentReadyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import pico.erp.notify.subject.type.NotifySubjectTypeServiceLogic;
import pico.erp.notify.type.NotifyTypeService;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Configuration
public class NotifyInitializer implements ApplicationListener<SpringBootComponentReadyEvent> {

  @Autowired
  private NotifyTypeService notifyTypeService;

  @Autowired
  private NotifySubjectTypeServiceLogic notifySubjectTypeService;

  @Override
  public void onApplicationEvent(SpringBootComponentReadyEvent event) {
    /*notifySubjectTypeService.initialize();
    notifyTypeService.initialize();*/
  }
}
