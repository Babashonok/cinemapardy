/*
 * Copyright 2016: Thomson Reuters. All Rights Reserved. Proprietary
 * and Confidential information of Thomson Reuters. Disclosure, Use or
 * Reproduction without the written authorization of Thomson Reuters is
 * prohibited.
 */
package com.dev.babak.cinemapardy.queue.service

import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.sqs.AmazonSQS
import com.amazonaws.services.sqs.AmazonSQSClient
import com.amazonaws.services.sqs.model.DeleteMessageRequest
import com.amazonaws.services.sqs.model.Message
import com.amazonaws.services.sqs.model.ReceiveMessageRequest
import com.amazonaws.services.sqs.model.ReceiveMessageResult
import com.dev.babak.cinemapardy.queue.handlers.MessageHandler
import com.dev.babak.cinemapardy.queue.models.SqsMessage
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Service

@Service
class SqsServiceImpl implements SqsService {

    @Autowired
    AWSCredentialsProvider credentialsProvider

    @Autowired
    MessageBusFactory messageBusFactory

    @Autowired
    ApplicationContext context

    @Autowired
    Regions region

    @Autowired @Qualifier("queueName")
    String queueUrl

    private ObjectMapper mapper = new ObjectMapper()

    public String getTopicName(SqsMessage sqsMessage) {

        String[] arnParts = sqsMessage.topicArn.split(":")
        String topicName = arnParts[arnParts.length - 1]

        return topicName
    }

    /**
     * Creates a strongly typed object for the body of the message to work with
     * @param message the message that has come from SQS
     * @return the message body as a strongly typed SQSMessage model
     */
    private SqsMessage convertToSqsMessage(Message message) {
        SqsMessage messageModel = mapper.readValue(message.body, SqsMessage)
        return messageModel
    }

    /**
     * Iterates over a list of messages and processes each one, deleting them after they have been processed
     * @param messages
     */
    public void processMessages(List<Message> messages) {
        for (Message message in messages) {

            this.executeMessageHandler(message)

            this.deleteMessage(message)
        }
    }

    /**
     * Takes a message and executes the corresponding handler for it
     * @param message the message that has come from SQS
     */
    public void executeMessageHandler(Message message) {
        SqsMessage sqsMessage = this.convertToSqsMessage(message)
        String eventName = this.getTopicName(sqsMessage)
        Class<?> eventType = this.messageBusFactory.getMessageType(eventName)
        Class<? extends MessageHandler<?>> eventCallback = this.messageBusFactory.getMessageHandlerType(eventName)
        MessageHandler<?> callbackObject = context.getBean(eventCallback)
        callbackObject.processMessage(mapper.readValue(sqsMessage.message, eventType))
    }

    public void deleteMessage(Message message) {

        AmazonSQSClient sqsClient = getAmazonSqsClient()

        DeleteMessageRequest request = new DeleteMessageRequest()
                .withQueueUrl(this.queueUrl)
                .withReceiptHandle(message.receiptHandle)

        sqsClient.deleteMessage(request)
    }

    /**
     * Gets a single message using the default settings to retrieve multiple messages
     * @return
     */
    public List<Message> getMessage() {
        return this.getMessages(1)
    }

    /**
     * Gets a number of messages that you ask for
     * @param numberOfMessages the number of messages you wish to receive. This can be no less than 1 and no more than 10
     * @return
     */
    public List<Message> getMessages(int numberOfMessages) {
        return this.getMessages(numberOfMessages, 30, 20)
    }

    /**
     * Gets a number of messages that you ask for with customizable settings for polling
     * @param numberOfMessages The number of messages you wish to receive. This can be no less than 1 and no more than 10
     * @param visibilityTimeoutSeconds
     * @param waitTimeSeconds
     * @return
     */
    public List<Message> getMessages(int numberOfMessages, int visibilityTimeoutSeconds, int waitTimeSeconds) {
        if (numberOfMessages > 10 || numberOfMessages < 1) {
            throw new IllegalArgumentException("Can only retrieve a number of messages between 0 and 1")
        }

        ReceiveMessageRequest request = new ReceiveMessageRequest()
                .withQueueUrl(this.getQueueUrl())
                .withMaxNumberOfMessages(numberOfMessages)
                .withVisibilityTimeout(visibilityTimeoutSeconds)
                .withWaitTimeSeconds(waitTimeSeconds)

        ReceiveMessageResult result = getAmazonSqsClient().receiveMessage(request)

        return result.getMessages()
    }

    AmazonSQS getAmazonSqsClient() {
        return new AmazonSQSClient(credentialsProvider)
    }
}
