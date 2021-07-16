package com.mypractice.content.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mypractice.content.api.document.ContentDocument;
import com.mypractice.content.api.dto.ContentDocumentDto;
import com.mypractice.content.api.repository.ContentRepository;
import com.mypractice.content.api.service.ContentService;
import com.mypractice.content.api.util.DocumentToDtoUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Transactional("reactiveTransactionManager")
@Service
public class ContentServiceImpl implements ContentService {

	private final Sinks.Many<ContentDocumentDto> sink;
	private final ContentRepository repository;

	@Autowired
	public ContentServiceImpl(ContentRepository repository, Sinks.Many<ContentDocumentDto> sink) {
		super();
		this.repository = repository;
		this.sink = sink;
	}

	@Override
	public Flux<ContentDocumentDto> allContents() {
		return repository.findAll().map(m -> DocumentToDtoUtil.map(m, ContentDocumentDto.class)).log()
				.doOnNext(this.sink::tryEmitNext);
	}

	@Override
	public Mono<ContentDocumentDto> findOneDocument(String contentId) {
		return repository.findById(contentId).map(m -> DocumentToDtoUtil.map(m, ContentDocumentDto.class));
	}

	@Override
	public Mono<ContentDocumentDto> updateDocument(String contenId, Mono<ContentDocumentDto> conentDocumentDto) {
		return repository.findById(contenId).log()
				.flatMap(m -> conentDocumentDto.map(d -> DocumentToDtoUtil.map(m, ContentDocument.class)).log()
						.doOnNext(e -> e.setContenId(contenId)).log().flatMap(repository::save).log()
						.map(s -> DocumentToDtoUtil.map(s, ContentDocumentDto.class)))
				.log();
	}

	@Override
	public Mono<Void> deleteDocument(String id) {
		return this.repository.deleteById(id);
	}

	@Override
	public Mono<ContentDocumentDto> createDocument(Mono<ContentDocumentDto> contentDocument) {
		// TODO Auto-generated method stub
		return contentDocument.map(m -> DocumentToDtoUtil.map(m, ContentDocument.class)).log().flatMap(repository::save)
				.map(m -> DocumentToDtoUtil.map(m, ContentDocumentDto.class)).doOnNext(sink::tryEmitNext);
	}

}
