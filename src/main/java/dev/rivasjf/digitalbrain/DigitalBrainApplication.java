package dev.rivasjf.digitalbrain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication
public class DigitalBrainApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalBrainApplication.class, args);
    }

}
