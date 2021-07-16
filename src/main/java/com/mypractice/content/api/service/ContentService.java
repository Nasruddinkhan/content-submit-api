package com.mypractice.content.api.service;

import com.mypractice.content.api.dto.ContentDocumentDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ContentService {

	Flux<ContentDocumentDto> allContents();
	Mono<ContentDocumentDto> findOneDocument(String contentId);
	Mono<ContentDocumentDto> createDocument(Mono<ContentDocumentDto> contentDocument);
	Mono<ContentDocumentDto> updateDocument(String contenId, Mono<ContentDocumentDto> conentDocumentDto);
	Mono<Void> deleteDocument(String contentId);


}
