/*
 * Copyright 2016: Thomson Reuters. All Rights Reserved. Proprietary
 * and Confidential information of Thomson Reuters. Disclosure, Use or
 * Reproduction without the written authorization of Thomson Reuters is
 * prohibited.
 */
package com.dev.babak.cinemapardy.queue.messages

import com.dev.babak.cinemapardy.queue.handlers.MessageHandler


class MessageContainer {

    Class<?> messageType

    Class<? extends MessageHandler<?>> handlerType
}
