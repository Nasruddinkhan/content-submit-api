package com.mypractice.content.api.controller;

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
import com.mypractice.content.api.repository.ContentRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class ContentController {

	private final ContentRepository repository;

	public ContentController(ContentRepository repository) {
		super();
		this.repository = repository;
	}

	@PostMapping("/save-content")
	public Mono<ContentDocument> createContent(@RequestBody ContentDocument contentDocument) {
		return repository.save(contentDocument);
	}

	@GetMapping("/find-all-content")
	public Flux<ContentDocument> findAllContent() {
		return repository.findAll();
	}

	@GetMapping("/find-content/{id}")
	public Mono<ResponseEntity<ContentDocument>> findContentById(@PathVariable("id") String contentId) {
		return repository.findById(contentId).map((content) -> new ResponseEntity<>(content, HttpStatus.OK))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@DeleteMapping("/delete-content/{id}")
	public Mono<Void> deleteContent(@PathVariable("id") String contentId) {
		return repository.deleteById(contentId);
	}

	@PutMapping("/update-content/{id}")
	public Mono<ResponseEntity<ContentDocument>> updateContent(@RequestBody ContentDocument contentDocument,
			@PathVariable("id") String contentId) {
		return repository.findById(contentId).flatMap(content -> {
			content.setDescription(contentDocument.getDescription());
			return repository.save(content);
		}).map(update -> new ResponseEntity<>(update, HttpStatus.OK))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

}
