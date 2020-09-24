package custq;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/** 
 * Externalised configuration properties
 * @author regen
 *
 */
@Configuration
@ConfigurationProperties(prefix="qservice")
public class ClientConfig {
	private static String URL;

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	public static String getUrl() {
		return URL;
	}

	public void setUrl(String url) {
		URL = url;
	}

}
