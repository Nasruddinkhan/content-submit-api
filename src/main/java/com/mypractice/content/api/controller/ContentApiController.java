package com.mypractice.content.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mypractice.content.api.document.ContentDocument;
import com.mypractice.content.api.dto.ContentDocumentDto;
import com.mypractice.content.api.service.ContentService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/layer/api")
public class ContentApiController {

	private final ContentService service;

	@Autowired
	public ContentApiController(ContentService service) {
		super();
		this.service = service;
	}

	@PostMapping("/save-content")
	public Mono<ContentDocumentDto> createContent(@RequestBody Mono<ContentDocumentDto> contentDocument) {
		return service.createDocument(contentDocument);
	}

	@GetMapping("/find-all-content")
	public Flux<ContentDocumentDto> findAllContent() {
		return service.allContents();
	}

	@GetMapping("/find-content/{id}")
	public Mono<ResponseEntity<ContentDocumentDto>> findContentById(@PathVariable("id") String contentId) {
		return service.findOneDocument(contentId)
				.map((content) -> new ResponseEntity<>(content, HttpStatus.OK))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@DeleteMapping("/delete-content/{id}")
	public Mono<Void> deleteContent(@PathVariable("id") String contentId) {
		return service.deleteDocument(contentId);
	}

	@PutMapping("/update-content/{id}")
	public Mono<ResponseEntity<ContentDocumentDto>> updateContent(@RequestBody  Mono<ContentDocumentDto> contentDocument,
			@PathVariable("id") String contentId) {
		return service.updateDocument(contentId, contentDocument).map(update -> new ResponseEntity<>(update, HttpStatus.OK))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

}
