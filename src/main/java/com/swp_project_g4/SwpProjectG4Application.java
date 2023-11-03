package com.swp_project_g4;

import com.swp_project_g4.Service.storage.StorageProperties;
import com.swp_project_g4.Service.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class SwpProjectG4Application {

    public static void main(String[] args) {
        SpringApplication.run(SwpProjectG4Application.class, args);
//        Runtime rt = Runtime.getRuntime();
//        try {
//            rt.exec("cmd /c start chrome.exe http://localhost:8080");
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//        }
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.init();
        };
    }

}
