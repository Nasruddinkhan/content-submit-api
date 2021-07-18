package com.mypractice.content.api.kafka;

import com.mypractice.content.api.dto.ContentDocumentDto;
import com.mypractice.content.api.service.ContentApiNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
@Service
@RequiredArgsConstructor
public class KafkaContentNotification implements ContentApiNotification {
    private final KafkaTemplate<String, ContentDocumentDto> kafkaTemplate;

    @Override
    public Mono<Void> notificatorSubmitContentApi(ContentDocumentDto documentDto) {
        return Mono.fromFuture(kafkaTemplate
                .send("upload-content-events-v1", documentDto)
                .completable())
                .then();
    }
}
