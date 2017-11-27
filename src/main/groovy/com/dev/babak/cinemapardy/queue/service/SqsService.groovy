/*
 * Copyright 2016: Thomson Reuters. All Rights Reserved. Proprietary
 * and Confidential information of Thomson Reuters. Disclosure, Use or
 * Reproduction without the written authorization of Thomson Reuters is
 * prohibited.
 */
package com.dev.babak.cinemapardy.queue.service

import com.amazonaws.services.sqs.model.Message

interface SqsService {
    public void processMessages(List<Message> messages)

    public void executeMessageHandler(Message message)

    public void deleteMessage(Message message)

    public List<Message> getMessage()

    public List<Message> getMessages(int count)

    public List<Message> getMessages(int numberOfMessages, int visibilityTimeoutSeconds, int waitTimeSeconds)
}
