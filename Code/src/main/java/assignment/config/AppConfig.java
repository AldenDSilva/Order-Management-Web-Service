package assignment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    // RestTemplate bean for making external web service calls (wholesalers web service)
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}