package net.oussama.chatboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;

@SpringBootApplication
public class ChatBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatBootApplication.class, args);
    }


}
