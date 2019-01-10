package pico.erp.notify;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.notify.NotifyRequests.NotifyGroupRequest;
import pico.erp.notify.NotifyRequests.NotifyUserRequest;
import pico.erp.notify.message.NotifyMessage;
import pico.erp.notify.sender.NotifySenderId;
import pico.erp.notify.sender.NotifySenderRequests;
import pico.erp.notify.sender.NotifySenderService;
import pico.erp.notify.subject.NotifySubjectId;
import pico.erp.notify.subject.NotifySubjectService;
import pico.erp.notify.target.NotifyGroupData;
import pico.erp.notify.target.NotifyTargetData;
import pico.erp.notify.target.NotifyTargetService;
import pico.erp.notify.type.NotifyTypeData;
import pico.erp.notify.type.NotifyTypeId;
import pico.erp.notify.type.NotifyTypeRequests;
import pico.erp.notify.type.NotifyTypeService;
import pico.erp.shared.Public;
import pico.erp.user.UserId;
import pico.erp.user.group.GroupId;

@Service
@Public
@Transactional
@Validated
public class NotifyServiceLogic implements NotifyService {

  @Autowired
  private NotifyTypeService notifyTypeService;

  @Autowired
  private NotifySubjectService notifySubjectService;

  @Autowired
  private NotifyTargetService targetService;

  @Autowired
  private NotifySenderService senderService;

  private NotifyGroupData group(GroupId groupId) {
    return targetService.get(groupId);
  }

  private <K> NotifyMessage message(NotifyTypeId typeId, K key) {
    return notifyTypeService.compile(
      NotifyTypeRequests.CompileRequest.builder()
        .id(typeId)
        .key(key)
        .build()
    );
  }

  @Override
  public void notify(NotifyUserRequest request) {
    val type = notifyTypeService.get(request.getTypeId());
    val message = message(request.getTypeId(), request.getKey());
    val targets = targets(request.getUserId(), request.getSubjectId());
    val senders = type.getSenders();
    send(type, targets, senders, message);

  }

  @Override
  public void notify(NotifyGroupRequest request) {
    val type = notifyTypeService.get(request.getTypeId());
    val message = message(request.getTypeId(), request.getKey());
    val group = group(request.getGroupId());
    val groupSenders = new LinkedList<NotifySenderId>(type.getSenders());
    val targetSenders = new LinkedList<NotifySenderId>();

    while (!groupSenders.isEmpty()) {
      val senderId = groupSenders.poll();
      if (!senderService.exists(senderId)) {
        continue;
      }
      val done = senderService.send(
        NotifySenderRequests.SendGroupRequest.builder()
          .id(senderId)
          .message(message)
          .group(group)
          .build()
      );
      if (done) {
        if (type.isMultipleSend()) {
          continue;
        } else {
          break;
        }
      } else {
        targetSenders.add(senderId);
      }
    }
    val targets = new LinkedList<NotifyTargetData>(
      targets(request.getGroupId(), request.getSubjectId()));

    send(type, targets, targetSenders, message);
  }

  private void send(NotifyTypeData type, Collection<NotifyTargetData> targets,
    List<NotifySenderId> senders, NotifyMessage message) {
    val failures = new LinkedList<NotifyTargetData>();
    for (val target : targets) {
      for (val senderId : senders) {
        val done = senderService.send(
          NotifySenderRequests.SendTargetRequest.builder()
            .id(senderId)
            .message(message)
            .target(target)
            .build()
        );
        if (done) {
          if (failures.contains(target)) {
            failures.remove(target);
          }
          if (type.isMultipleSend()) {
            continue;
          } else {
            break;
          }
        } else {
          if (!failures.contains(target)) {
            failures.add(target);
          }
        }
      }
    }
  }

  private Collection<NotifyTargetData> targets(UserId userId, NotifySubjectId notifySubjectId) {
    val exists = notifySubjectService.exists(notifySubjectId);
    val watchers =
      exists ? notifySubjectService.get(notifySubjectId).getWatchers() : new HashSet<UserId>();
    return Stream.concat(
      Stream.of(
        targetService.get(userId)
      ),
      watchers.stream()
        .map(watcher -> targetService.get(watcher))
    )
      .filter(t -> t != null)
      .distinct()
      .collect(Collectors.toList());
  }

  private Collection<NotifyTargetData> targets(GroupId groupId, NotifySubjectId notifySubjectId) {
    val exists = notifySubjectService.exists(notifySubjectId);
    val watchers =
      exists ? notifySubjectService.get(notifySubjectId).getWatchers() : new HashSet<UserId>();
    return Stream.concat(
      targetService.getAll(groupId).stream(),
      watchers.stream()
        .map(watcher -> targetService.get(watcher))
    )
      .filter(t -> t != null)
      .distinct()
      .collect(Collectors.toList());
  }
}
