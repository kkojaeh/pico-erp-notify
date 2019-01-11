package pico.erp.notify

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Configuration
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.notify.subject.type.NotifySubjectTypeId
import pico.erp.notify.type.NotifyTypeExceptions
import pico.erp.notify.type.NotifyTypeId
import pico.erp.notify.type.NotifyTypeRequests
import pico.erp.notify.type.NotifyTypeService
import pico.erp.shared.IntegrationConfiguration
import spock.lang.Specification

@SpringBootTest(classes = [IntegrationConfiguration])
@Transactional
@Rollback
@ActiveProfiles("test")
@Configuration
class NotifyTypeServiceSpec extends Specification {


  static def id = NotifyTypeId.from("test")

  static def subjectTypeId = NotifySubjectTypeId.from("test")

  static def name = "테스트 타입"

  static def markdownTemplate = """{{name}} Hello"""

  static def key = "test-key"

  /*@Bean
  NotifyTypeDefinition testNotifyTypeDefinition() {
    return NotifyTypeDefinition.Impl.builder()
    .id(id)
    .subjectTypeId(subjectTypeId)
    .name(name)
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
  }*/

  @Autowired
  NotifyTypeService notifyTypeService


  def template() {
    return notifyTypeService.update(
      new NotifyTypeRequests.UpdateRequest(
        id: id,
        name: name,
        markdownTemplate: markdownTemplate,
        senders: [],
        multipleSend: true,
        enabled: true
      )
    )
  }

  def compile() {
    return notifyTypeService.compile(
      new NotifyTypeRequests.CompileRequest(
        id: id,
        key: key
      )
    )
  }

  def setup() {

  }

  def "조회 - 정의된 타입 확인"() {
    when:
    template()
    def type = notifyTypeService.get(id)

    then:
    type.id == id
    type.name == name
    type.markdownTemplate == markdownTemplate
    type.multipleSend == true
    type.enabled == true
  }

  def "컴파일 - 템플릿 지정 전"() {
    when:
    compile()

    then:
    thrown(NotifyTypeExceptions.CannotCompileException)
  }

  def "컴파일 - 템플릿 지정 후"() {
    when:
    template()
    def message = compile()

    then:
    message.asMarkdown() == "테스트 Hello"
  }

}
