package com.dev.babak.cinemapardy.queue.service

/*
 * Copyright 2016: Thomson Reuters. All Rights Reserved. Proprietary
 * and Confidential information of Thomson Reuters. Disclosure, Use or
 * Reproduction without the written authorization of Thomson Reuters is
 * prohibited.
 */

interface SnsService {

    public String getArnForTopicName(String topicName)

    public void pushNotification(String topicName, Object message)
}