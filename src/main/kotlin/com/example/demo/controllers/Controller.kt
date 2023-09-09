package com.example.messagingstompwebsocket

import com.example.demo.models.Message
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import org.springframework.web.util.HtmlUtils

@Controller
class Controller {
    @MessageMapping("/hello")
    @SendTo("/topic/getData")
    @Throws(Exception::class)
    fun getData(message: Message): Message {
        Thread.sleep(1000) // simulated delay
        return Message(message.id,HtmlUtils.htmlEscape(message.data))
    }
}