package com.dev.babak.cinemapardy.queue.service

import com.dev.babak.cinemapardy.queue.handlers.MessageHandler


interface MessageBusFactory {

    public <T extends Class<T>> T getMessageType(String eventName);

    public Class<? extends MessageHandler<?>> getMessageHandlerType(String eventName);

}