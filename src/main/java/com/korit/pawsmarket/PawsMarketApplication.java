package com.korit.pawsmarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PawsMarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(PawsMarketApplication.class, args);
    }

}
