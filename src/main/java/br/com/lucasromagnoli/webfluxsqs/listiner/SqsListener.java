package br.com.lucasromagnoli.webfluxsqs.listiner;

import br.com.lucasromagnoli.webfluxsqs.service.SqsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;

import javax.annotation.PostConstruct;
import java.util.function.BooleanSupplier;

/**
 * @author github.com/lucasromagnoli
 * @since 06/2021
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SqsListener {
    private final SqsService sqsService;
    private final String queueUrl;

    @PostConstruct
    private void init() {
        continuousListener(() -> true).subscribe();
    }

    private Flux<Message> continuousListener(BooleanSupplier booleanSupplier) {
        return sqsService.receberMensagem(queueUrl)
                .map(ReceiveMessageResponse::messages)
                .flatMapMany(Flux::fromIterable)
                .doOnNext((message) -> log.info("Mensagem recebida com sucesso. ID: [{}] - Body: [{}]", message.messageId(), message.body()))
                .repeat(() -> true)
                .retry(5);
    }
}
