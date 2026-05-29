package dev.rivasjf.expensemanager.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;

@RestController
public class MainController {

    @GetMapping()
    public String helloWorld() {
        return "Hello World!";
    }

    @GetMapping("/now")
    public String now() {
        return OffsetDateTime.now().toString();
    }
}