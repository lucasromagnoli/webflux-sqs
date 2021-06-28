package br.com.lucasromagnoli.webfluxsqs.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;

/**
 * @author github.com/lucasromagnoli
 * @since 06/2021
 */
@Component
@RequiredArgsConstructor
public class SqsService {
    private final SqsAsyncClient sqsAsyncClient;

    public Mono<ReceiveMessageResponse> receberMensagem(String queueURL) {
        return Mono.fromFuture(() ->
                sqsAsyncClient.receiveMessage(
                        ReceiveMessageRequest.builder()
                                .maxNumberOfMessages(10)
                                .queueUrl(queueURL)
                                .visibilityTimeout(20)
                                .build())
        ).onErrorResume(Mono::error);
    }
}
