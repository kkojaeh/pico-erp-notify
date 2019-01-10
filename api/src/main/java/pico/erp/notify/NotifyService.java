package pico.erp.notify;

public interface NotifyService {

  void notify(NotifyRequests.NotifyUserRequest request);

  void notify(NotifyRequests.NotifyGroupRequest request);

}
