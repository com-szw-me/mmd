package com.szw.me.mmd.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {
    @Bean
    public Exchange directExchange() {
        return new DirectExchange("directExchange");
    }

    @Bean
    public Exchange topicExchange() {
        return new TopicExchange("topicExchange");
    }

    @Bean
    public Queue messageQueue() {
        return new Queue("messageQueue", true);
    }

    @Bean
    public Queue bjQueue() {
        return new Queue("bjQueue", true);
    }

    @Bean
    public Queue njQueue() {
        return new Queue("njQueue", true);
    }

    @Bean
    public Queue newsQueue() {
        return new Queue("newsQueue", true);
    }

    @Bean
    public Binding bjBinding(@Qualifier("bjQueue") Queue queue, @Qualifier("topicExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("bj.#").noargs();
    }

    @Bean
    public Binding njBinding(@Qualifier("njQueue") Queue queue, @Qualifier("topicExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("nj.#").noargs();
    }

    @Bean
    public Binding newsBinding(@Qualifier("newsQueue") Queue queue, @Qualifier("topicExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("#.news").noargs();
    }

    @Bean
    public Binding messageBinding(@Qualifier("messageQueue") Queue queue, @Qualifier("directExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("message").noargs();
    }
}
