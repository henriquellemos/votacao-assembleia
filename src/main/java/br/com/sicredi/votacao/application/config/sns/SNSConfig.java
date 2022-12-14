package br.com.sicredi.votacao.application.config.sns;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SNSConfig {

    @Bean
    public AmazonSNS amazonSNS(){
        return AmazonSNSClient.builder()
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration("http://localhost:4566", "us-east-1")
                )
                .build();
    }
}
