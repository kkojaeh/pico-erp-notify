package pico.erp.notify;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("unused")
@Component
@Transactional
public class NotifyEventListener {

  private static final String LISTENER_NAME = "listener.notify-event-listener";

/*

  @EventListener
  @JmsListener(destination = LISTENER_NAME + "." + NotifyEvents.AddedEvent.CHANNEL)
  public void onCommentAdded(NotifyEvents.AddedEvent event) {
    Notify notify = notifyRepository.findBy(event.getNotifyId())
      .orElseThrow(NotFoundException::new);
    NotifyData notifyData = notifyMapper.map(notify);
  }
*/

}
