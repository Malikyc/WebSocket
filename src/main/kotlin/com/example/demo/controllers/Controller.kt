package com.example.demo.controllers

import com.example.demo.models.Message
import com.fasterxml.jackson.databind.util.JSONPObject
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping


@Controller
class Controller {

    @MessageMapping("/sendDataToUser")
    @SendTo("topic/getData")
    public fun receiveData(message : Message) : Message{
        return  message
    }

}