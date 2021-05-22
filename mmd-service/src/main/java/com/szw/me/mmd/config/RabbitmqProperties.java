package com.szw.me.mmd.config;

import lombok.Data;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("spring.rabbitmq")
public class RabbitmqProperties {
    private String host = "8.140.115.114";
    private Integer port = 5672;
    private  String username = "admin";
    private String password = "admin";
    private String virtualHost = "my_vhost";
    private Integer connectionTimeout = 120000;
    private CachingConnectionFactory.ConfirmType confirmType = CachingConnectionFactory.ConfirmType.CORRELATED;
}
