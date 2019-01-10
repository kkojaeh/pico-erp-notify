package pico.erp.notify.sender;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;
import net.bis5.mattermost.jersey.provider.MattermostModelMapperProvider;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

public class MattermostClient extends net.bis5.mattermost.client4.MattermostClient {

  public MattermostClient(String url) {
    super(url);
  }

  @Override
  protected Client buildClient() {
    ClientBuilder builder = ClientBuilder.newBuilder()
      .register(MattermostModelMapperProvider.class)
      .register(JacksonFeature.class)
      .register(MultiPartFeature.class)
      .register(NoFailOnUnknownPropertiesJacksonJaxbJsonProvider.class)
      // needs for PUT request with null entity
      // (/commands/{command_id}/regen_token)
      .property(ClientProperties.SUPPRESS_HTTP_COMPLIANCE_VALIDATION, true);
    return builder.build();
  }

  @Provider
  @Produces(MediaType.APPLICATION_JSON)
  public static class NoFailOnUnknownPropertiesJacksonJaxbJsonProvider extends
    JacksonJaxbJsonProvider {

    private static ObjectMapper mapper = new ObjectMapper();

    static {
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
      mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
      mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public NoFailOnUnknownPropertiesJacksonJaxbJsonProvider() {
      super();
      setMapper(mapper);
    }
  }
}
