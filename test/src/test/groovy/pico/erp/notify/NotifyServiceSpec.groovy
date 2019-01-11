package pico.erp.notify

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.notify.message.NotifyMessage
import pico.erp.notify.sender.NotifySenderDefinition
import pico.erp.notify.sender.NotifySenderId
import pico.erp.notify.subject.NotifySubjectId
import pico.erp.notify.subject.type.NotifySubjectTypeDefinition
import pico.erp.notify.subject.type.NotifySubjectTypeId
import pico.erp.notify.target.NotifyGroupData
import pico.erp.notify.target.NotifyTargetData
import pico.erp.notify.type.NotifyTypeDefinition
import pico.erp.notify.type.NotifyTypeId
import pico.erp.notify.type.NotifyTypeRequests
import pico.erp.notify.type.NotifyTypeService
import pico.erp.shared.IntegrationConfiguration
import pico.erp.user.UserId
import spock.lang.Specification

@SpringBootTest(classes = [IntegrationConfiguration])
@Transactional
@Rollback
@ActiveProfiles("test")
@Configuration
class NotifyServiceSpec extends Specification {

  static def typeId = NotifyTypeId.from("test")

  static def subjectTypeId = NotifySubjectTypeId.from("test")

  static def typeName = "테스트 타입"

  static def markdownTemplate = """{{name}} Hello"""

  static def key = "test-key"

  static def userId = UserId.from("kjh")

  static def subjectId = NotifySubjectId.from("unknown")

  static def senderId = NotifySenderId.from("console")

  @Bean
  NotifyTypeDefinition testNotifyTypeDefinition() {

    return NotifyTypeDefinition.Impl.builder()
      .id(typeId)
      .subjectTypeId(subjectTypeId)
      .name(typeName)
      .creator({
      k -> [name: "테스트"]
    })
      .build()
  }

  @Bean
  NotifySubjectTypeDefinition testNotifySubjectTypeDefinition() {
    return NotifySubjectTypeDefinition.Impl.builder()
      .id(subjectTypeId)
      .name("테스트")
      .converter({
      k -> NotifySubjectId.from(subjectTypeId, k)
    })
      .build()
  }

  @Bean
  NotifySenderDefinition testNotifySenderDefinition() {
    return new TestNotifySenderDefinition()
  }

  class TestNotifySenderDefinition implements NotifySenderDefinition {

    boolean targetSent = false;

    @Override
    NotifySenderId getId() {
      return senderId
    }

    @Override
    String getName() {
      return "콘솔"
    }

    @Override
    boolean send(NotifyMessage message, NotifyGroupData group) {
      println(message.asMarkdown())
      return false
    }

    @Override
    boolean send(NotifyMessage message, NotifyTargetData target) {
      targetSent = true;
      println(message.asMarkdown())
      return true
    }
  }

  @Autowired
  NotifyTypeService notifyTypeService

  @Autowired
  NotifyService notifyService

  def setup() {
    notifyTypeService.update(
      new NotifyTypeRequests.UpdateRequest(
        id: typeId,
        name: typeName,
        markdownTemplate: markdownTemplate,
        senders: [senderId],
        enabled: true
      )
    )
  }

  def "전송 - 사용자에게 전송"() {
    when:

    notifyService.notify(
      new NotifyRequests.NotifyUserRequest(
        typeId: typeId,
        key: key,
        userId: userId
      )
    )

    then:
    1 == 1
  }

}
