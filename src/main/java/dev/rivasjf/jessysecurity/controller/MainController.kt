package dev.rivasjf.jessysecurity.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.time.Clock

@RestController
class MainController {

    @GetMapping()
    fun home(): String {
        return "Hello, World!"
    }

    @GetMapping("/now")
    fun now(): String {
        return Clock.System.now().toString()
    }
}