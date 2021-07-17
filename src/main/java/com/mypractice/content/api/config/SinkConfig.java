package com.mypractice.content.api.config;

import com.mypractice.content.api.dto.ContentDocumentDto;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Configuration
public class SinkConfig {
	@Bean
	public Sinks.Many<ContentDocumentDto> sink() {
		return Sinks.many().replay().limit(1);
	}

	@Bean
	public Flux<ContentDocumentDto> productBroadcast(Sinks.Many<ContentDocumentDto> sink) {
		return sink.asFlux();
	}

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setBasename("messages");
		return source;
	}
	@Bean
	public LocalValidatorFactoryBean getValidator(MessageSource messageSource) {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource);
		return bean;
	}
}
