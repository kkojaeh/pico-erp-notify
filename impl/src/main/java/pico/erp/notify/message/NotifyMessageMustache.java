package pico.erp.notify.message;

import com.github.mustachejava.Mustache;
import java.io.StringWriter;
import lombok.SneakyThrows;

public class NotifyMessageMustache implements NotifyMessage {

  private final Mustache markdown;

  private final Object context;

  private String markdownResult;

  public NotifyMessageMustache(Mustache markdown, Object context) {
    this.markdown = markdown;
    this.context = context;
  }

  @SneakyThrows
  @Override
  public String asMarkdown() {
    if (markdownResult != null) {
      return markdownResult;
    }
    StringWriter writer = new StringWriter();
    markdown.execute(writer, context).flush();
    return writer.toString();
  }
}
