package kr.ac.univ.config;

import kr.ac.univ.common.converter.StringToActiveStatus;
import kr.ac.univ.common.converter.StringToPublicationSearchType;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        // PublicationSearchType
        registry.addConverter(new StringToPublicationSearchType());
        // ActiveStatus
        registry.addConverter(new StringToActiveStatus());
    }
}
