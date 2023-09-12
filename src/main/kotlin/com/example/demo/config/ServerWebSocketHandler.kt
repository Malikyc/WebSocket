package com.example.demo.config


import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

import org.springframework.web.util.HtmlUtils
import java.io.IOException
import java.time.LocalTime


class ServerWebSocketHandler : TextWebSocketHandler(){
    companion object{
        val sessions: MutableList<WebSocketSession> = ArrayList()
    }


    override fun afterConnectionEstablished(session: WebSocketSession) {
        sessions.add(session)
        while (true){
            for (session : WebSocketSession in sessions) {
                Thread.sleep(1000)
                if (session.isOpen) {
                    val broadcast = "server periodic message " + LocalTime.now()
                    session.sendMessage(TextMessage(broadcast))
                }
            }
        }
    }
    @Throws(Exception::class)
    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val request = message.payload
        val response = String.format("response from server to '%s'", HtmlUtils.htmlEscape(request))
        session.sendMessage(TextMessage(response))
    }




}
