package com.mypractice.content.api.service;

import com.mypractice.content.api.dto.ContentDocumentDto;
import reactor.core.publisher.Mono;

public interface ContentApiNotification {
    Mono<Void> notificatorSubmitContentApi(ContentDocumentDto documentDto);
}
