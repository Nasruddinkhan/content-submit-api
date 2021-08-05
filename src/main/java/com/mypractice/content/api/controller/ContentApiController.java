package com.mypractice.content.api.controller;

import com.mypractice.content.api.dto.ContentDocumentDto;
import com.mypractice.content.api.exception.NotFoundException;
import com.mypractice.content.api.service.ContentApiNotification;
import com.mypractice.content.api.service.ContentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "contentapi")
public class ContentApiController {

	private final ContentService service;
    private  final ContentApiNotification apiNotification;
	@Autowired
	public ContentApiController(ContentService service, ContentApiNotification apiNotification) {
		super();
		this.service = service;
		this.apiNotification = apiNotification;
	}

	@PostMapping("/content")
	public Mono<Void> createContent(@RequestBody @Valid Mono<ContentDocumentDto> contentDocument,
												  ServerHttpResponse response,
												  UriComponentsBuilder uriComponentsBuilder) {
		return service.createDocument(contentDocument)
				.doOnNext(contentDocumentDto -> {
					apiNotification.notificatorSubmitContentApi(contentDocumentDto);
					setLocationHeader(response, uriComponentsBuilder, contentDocumentDto.getContenId());
				})
				.then();
	}

	@GetMapping("/content")
	public Flux<ContentDocumentDto> findAllContent() {
		return service.allContents();
	}

	@GetMapping("/content/{id}")
	public Mono<ResponseEntity<ContentDocumentDto>> findContentById(@PathVariable("id") String contentId) {
		return service.findOneDocument(contentId)
				.map(content -> new ResponseEntity<>(content, HttpStatus.OK))
				.switchIfEmpty(Mono.error(new NotFoundException( contentId +" is not found " )));
	}

	@DeleteMapping("/content/{id}")
	public Mono<Void> deleteContent(@PathVariable("id") String contentId) {
		return service.deleteDocument(contentId);
	}


	@PutMapping("/content/{id}")
	public Mono<ResponseEntity<ContentDocumentDto>> updateContent(@RequestBody  Mono<ContentDocumentDto> contentDocument,
			@PathVariable("id") String contentId) {
		return service.updateDocument(contentId, contentDocument).map(update -> new ResponseEntity<>(update, HttpStatus.OK))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	private void setLocationHeader(ServerHttpResponse response,
								   UriComponentsBuilder uriComponentsBuilder,
								   String id) {
		response.getHeaders().setLocation(uriComponentsBuilder
				.path("/content/{id}")
				.buildAndExpand(id)
				.toUri());
	}
}
