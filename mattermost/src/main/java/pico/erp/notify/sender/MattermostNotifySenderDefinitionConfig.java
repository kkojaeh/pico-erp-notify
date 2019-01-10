package pico.erp.notify.sender;

import java.util.Map;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MattermostNotifySenderDefinitionConfig {

  @NotNull
  String url;

  @NotNull
  String accessToken;

  @NotNull
  String teamName;

  @NotNull
  String senderEmail;

  @NotNull
  Map<String, String> groupChannelNames;

}
