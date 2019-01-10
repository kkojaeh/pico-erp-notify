package pico.erp.notify

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.notify.message.NotifyMessage
import pico.erp.notify.sender.NotifySenderDefinition
import pico.erp.notify.sender.NotifySenderId
import pico.erp.notify.subject.NotifySubjectId
import pico.erp.notify.target.NotifyTargetData
import pico.erp.notify.type.NotifyTypeDefinition
import pico.erp.notify.type.NotifyTypeId
import pico.erp.notify.type.NotifyTypeRequests
import pico.erp.notify.type.NotifyTypeService
import pico.erp.shared.IntegrationConfiguration
import pico.erp.user.UserId
import spock.lang.Specification

import java.util.stream.Stream

@SpringBootTest(classes = [IntegrationConfiguration])
@Transactional
@Rollback
@ActiveProfiles("test")
@Configuration
class NotifyServiceSpec extends Specification {

  static def typeId = NotifyTypeId.from("test")

  static def typeName = "테스트 타입"

  static def markdownTemplate = """{{name}} Hello"""

  static def key = "test-key"

  static def userId = UserId.from("kjh")

  static def subjectId = NotifySubjectId.from("unknown")

  static def senderId = NotifySenderId.from("console")

  @Bean
  NotifyTypeDefinition testNotifyTypeDefinition() {
    return new NotifyTypeDefinition.NotifyTypeDefinitionImpl(
      typeId, typeName, {
      k -> [name: "테스트"]
    })
  }

  @Bean
  NotifySenderDefinition testNotifySenderDefinition() {
    return new TestNotifySenderDefinition()
  }

  class TestNotifySenderDefinition implements NotifySenderDefinition {

    @Override
    NotifySenderId getId() {
      return senderId
    }

    @Override
    String getName() {
      return "콘솔"
    }

    @Override
    boolean send(NotifyMessage message, Collection<NotifyTargetData> targets) {
      println(message.asMarkdown())
      return true
    }
  }

  @Lazy
  @Autowired
  TestNotifySenderDefinition testNotifySenderDefinition

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
        subjectId: subjectId,
        key: key,
        userId: userId
      )
    )

    then:
    1 == 1
  }

}
