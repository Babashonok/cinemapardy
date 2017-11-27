/*
 * Copyright 2016: Thomson Reuters. All Rights Reserved. Proprietary
 * and Confidential information of Thomson Reuters. Disclosure, Use or
 * Reproduction without the written authorization of Thomson Reuters is
 * prohibited.
 */
package com.dev.babak.cinemapardy.queue.models

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
class SqsMessage {

    @JsonProperty("Type")
    String type

    @JsonProperty("MessageId")
    String messageId

    @JsonProperty("TopicArn")
    String topicArn

    @JsonProperty("Message")
    String message

    @JsonProperty("Timestamp")
    String timestamp

    @JsonProperty("SignatureVersion")
    String signatureVersion

    @JsonProperty("Signature")
    String signature

    @JsonProperty("SigningCertURL")
    String signingCertUrl

    @JsonProperty("UnsubscribeURL")
    String unsubscribeURL
}
