package br.com.lucasromagnoli.webfluxsqs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;

import java.net.URI;
import java.util.concurrent.ExecutionException;

/**
 * @author github.com/lucasromagnoli
 * @since 06/2021
 */
@Configuration
public class AwsConfig {
    private final String queueName;

    public AwsConfig() {
        this.queueName = "webflux";
    }

    @Bean
    public SqsAsyncClient sqsAsyncClient() {
        return SqsAsyncClient.builder()
                .endpointOverride(URI.create("http://localhost:4566"))
                .region(Region.AP_SOUTHEAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(new AwsCredentials() {
                    @Override
                    public String accessKeyId() {
                        return "localstack";
                    }

                    @Override
                    public String secretAccessKey() {
                        return "localstack";
                    }
                }))
                .build();
    }

    @Bean
    public String queueTesteUrl() throws ExecutionException, InterruptedException {
        return sqsAsyncClient().getQueueUrl(GetQueueUrlRequest.builder().queueName(queueName).build()).get().queueUrl();
    }
}
