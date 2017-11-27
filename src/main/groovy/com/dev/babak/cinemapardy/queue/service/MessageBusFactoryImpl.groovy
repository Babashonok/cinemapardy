/*
 * Copyright 2016: Thomson Reuters. All Rights Reserved. Proprietary
 * and Confidential information of Thomson Reuters. Disclosure, Use or
 * Reproduction without the written authorization of Thomson Reuters is
 * prohibited.
 */
package com.dev.babak.cinemapardy.queue.service

import com.dev.babak.cinemapardy.queue.handlers.MessageHandler
import com.dev.babak.cinemapardy.queue.messages.MessageContainer
import org.springframework.stereotype.Service

@Service
class MessageBusFactoryImpl implements MessageBusFactory {

    private Map<String, MessageContainer> messageContainer = [
            "FMS_OFFICE" : new MessageContainer(messageType: null, handlerType: null)
    ]

    @Override
    def <T extends Class<T>> T getMessageType(String eventName) {
        if (messageContainer.containsKey(eventName)) {
            return messageContainer.get(eventName).messageType
        }
        return null
    }

    @Override
    Class<? extends MessageHandler<?>> getMessageHandlerType(String eventName) {
        if (messageContainer.containsKey(eventName)) {
            return messageContainer.get(eventName).getHandlerType()
        }
        return null
    }
}
