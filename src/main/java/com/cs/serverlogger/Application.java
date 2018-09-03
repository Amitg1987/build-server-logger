package com.cs.serverlogger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.cs.serverlogger.dto.EventDTO;
import com.cs.serverlogger.service.LogProcessingService;

//CHECKSTYLE:OFF
@SpringBootApplication
@EnableJpaRepositories

public class Application {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = SpringApplication.run(Application.class, args);
        LogProcessingService logProcessingService = context.getBean(LogProcessingService.class);
        logProcessingService.process("input.txt");
    }

    @Bean(name = "queue")
    public BlockingQueue<EventDTO> eventQueue() {
        return new LinkedBlockingQueue<>();
    }
}
// CHECKSTYLE:ON
