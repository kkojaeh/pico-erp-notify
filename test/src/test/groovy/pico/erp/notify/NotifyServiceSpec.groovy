package pico.erp.notify

import kkojaeh.spring.boot.component.SpringBootTestComponent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.notify.sender.NotifySenderId
import pico.erp.notify.subject.NotifySubjectId
import pico.erp.notify.subject.type.NotifySubjectTypeId
import pico.erp.notify.type.NotifyTypeId
import pico.erp.notify.type.NotifyTypeRequests
import pico.erp.notify.type.NotifyTypeService
import pico.erp.shared.TestParentApplication
import pico.erp.user.UserApplication
import pico.erp.user.UserId
import spock.lang.Specification

@SpringBootTest(classes = [NotifyApplication, TestConfiguration])
@SpringBootTestComponent(parent = TestParentApplication, siblings = [UserApplication])
@ComponentScan(useDefaultFilters = false)
@Transactional
@Rollback
@ActiveProfiles("test")
class NotifyServiceSpec extends Specification {

  static def typeId = NotifyTypeId.from("test")

  static def subjectTypeId = NotifySubjectTypeId.from("test")

  static def typeName = "테스트 타입"

  static def markdownTemplate = """{{name}} Hello"""

  static def key = "test-key"

  static def userId = UserId.from("kjh")

  static def subjectId = NotifySubjectId.from("unknown")

  static def senderId = NotifySenderId.from("console")



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
