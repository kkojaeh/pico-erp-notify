package pico.erp.notify

import kkojaeh.spring.boot.component.SpringBootTestComponent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.notify.type.NotifyTypeExceptions
import pico.erp.notify.type.NotifyTypeId
import pico.erp.notify.type.NotifyTypeRequests
import pico.erp.notify.type.NotifyTypeService
import pico.erp.shared.ComponentDefinitionServiceLoaderTestComponentSiblingsSupplier
import pico.erp.shared.TestParentApplication
import spock.lang.Specification

@SpringBootTest(classes = [NotifyApplication, TestConfiguration])
@SpringBootTestComponent(parent = TestParentApplication, siblingsSupplier = ComponentDefinitionServiceLoaderTestComponentSiblingsSupplier.class)
@ComponentScan(useDefaultFilters = false)
@Transactional
@Rollback
@ActiveProfiles("test")
class NotifyTypeServiceSpec extends Specification {


  static def id = NotifyTypeId.from("test")

  static def name = "테스트 타입"

  static def markdownTemplate = """{{name}} Hello"""

  static def key = "test-key"

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
