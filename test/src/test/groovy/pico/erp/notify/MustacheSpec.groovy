package pico.erp.notify

import com.github.mustachejava.DefaultMustacheFactory
import spock.lang.Specification

class MustacheSpec extends Specification {

  def template = """{{name}} hello"""

  def factory

  def setup() {
    factory = new DefaultMustacheFactory()
  }

  def "댓글 생성"() {
    when:
    def mustache = factory.compile(new StringReader(template), "테스트")
    StringWriter writer = new StringWriter()
    mustache.execute(writer, [name: "테스트"]).flush()
    def result = writer.toString()
    println result
    then:
    result == "테스트 hello"
  }

}
