package com.mypractice.content.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mypractice.content.api.dto.ContentDocumentDto;

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
}
