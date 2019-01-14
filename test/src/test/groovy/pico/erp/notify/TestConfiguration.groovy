package pico.erp.notify

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
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

@Configuration
class TestConfiguration {

  static def typeId = NotifyTypeId.from("test")

  static def subjectTypeId = NotifySubjectTypeId.from("test")

  static def typeName = "테스트 타입"

  static def senderId = NotifySenderId.from("console")

  @Bean
  NotifyTypeDefinition testNotifyTypeDefinition() {

    return NotifyTypeDefinition.Impl.builder()
      .id(typeId)
      .subjectTypeId(subjectTypeId)
      .name(typeName)
      .contextCreator({
      k -> [name: "테스트"]
    })
      .keyCreator({
      k -> k
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
      println(message.asMarkdown())
      return true
    }
  }

}
