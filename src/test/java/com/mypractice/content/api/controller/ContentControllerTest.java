package com.mypractice.content.api.controller;

import com.mypractice.content.api.document.ContentDocument;
import com.mypractice.content.api.repository.ContentRepository;
import com.mypractice.content.api.service.ContentApiNotification;
import com.mypractice.content.api.service.ContentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient

public class ContentControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    ContentRepository contentRepository;



    @Test
    public void createContent() {
        ContentDocument document = new ContentDocument();
        document.setContenId("1010");
        document.setDescription("Test");
        Mockito.when(contentRepository.save(document))
                .thenReturn(Mono.just(document));
        webTestClient.post().uri("/api/save-content")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(document), ContentDocument.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody();
    }

    @Test
    public void findAllContent() {
        ContentDocument document = new ContentDocument();
        document.setDescription("ABC");
        Mockito.when(contentRepository.findAll())
                .thenReturn(Flux.just(document
                ));
        webTestClient.get().uri("/api/find-all-content")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("[0].description").isEqualTo("ABC");
    }

    @Test
    public void findContentById() {
        ContentDocument document = new ContentDocument();
        document.setContenId("12345");
        document.setDescription("ABC");
        Mockito.when(contentRepository.findById("12345"))
                .thenReturn(Mono.just(document));
        webTestClient.get().uri("/api/find-content/{id}","12345" )
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.description").isEqualTo("ABC");
    }

    @Test
    public void deleteContent() {
        Mockito.when(contentRepository.deleteById("610a3d674dc2926fb9af407c"))
                .thenReturn(Mono.empty());
        webTestClient.delete().uri("/api/delete-content/{id}", "610a3d674dc2926fb9af407c")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Void.class);
    }

    @Test
    public void updateContent() {

        ContentDocument document = new ContentDocument();
        document.setContenId("12345");
        document.setDescription("ABC");

        Mockito.when(contentRepository.findById("12345"))
                .thenReturn(Mono.just(document));
        Mockito.when(contentRepository.save(document))
                .thenReturn(Mono.just(document));
        webTestClient.put().uri("/api/update-content/{id}", "12345")
                .bodyValue(document)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(ContentDocument.class);
    }
}