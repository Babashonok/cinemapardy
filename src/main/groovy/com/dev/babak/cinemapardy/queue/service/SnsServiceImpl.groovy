/*
 * Copyright 2016: Thomson Reuters. All Rights Reserved. Proprietary
 * and Confidential information of Thomson Reuters. Disclosure, Use or
 * Reproduction without the written authorization of Thomson Reuters is
 * prohibited.
 */
package com.dev.babak.cinemapardy.queue.service

import com.amazonaws.ClientConfiguration
import com.amazonaws.Protocol
import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.sns.AmazonSNS
import com.amazonaws.services.sns.AmazonSNSClient
import com.amazonaws.services.sns.model.ListTopicsResult
import com.amazonaws.services.sns.model.PublishRequest
import com.amazonaws.services.sns.model.Topic
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class SnsServiceImpl implements SnsService {

    Map<String, String> topicToArnMapping

    @Value('${docker.sns.endpoint}')
    private String endpoint

    @Autowired
    Regions region

    ObjectMapper mapper = new ObjectMapper()

    public SnsServiceImpl() {
        topicToArnMapping = new HashMap<>()
    }

    public void pushNotification(String topicName, Object message) {

        AmazonSNSClient snsClient = getSnsClient()

        String json = mapper.writeValueAsString(message)

        PublishRequest request = new PublishRequest().withTopicArn(this.getArnForTopicName(topicName)).withMessage(json)

        snsClient.publish(request)
    }

    private AmazonSNSClient getSnsClient() {
        ClientConfiguration clientConfiguration = new ClientConfiguration()
        clientConfiguration.setProtocol(Protocol.HTTP)
        Region snsClientRegion = Region.getRegion(this.region)

        AmazonSNS snsClient = new AmazonSNSClient(new DefaultAWSCredentialsProviderChain(), clientConfiguration)
        snsClient.setRegion(snsClientRegion)
        if (endpoint) {
            snsClient.endpoint = endpoint
        }
        return snsClient
    }

    @Override
    String getArnForTopicName(String topicName) {
        if (this.topicToArnMapping.containsKey(topicName)) {
            return this.topicToArnMapping.get(topicName)
        }

        AmazonSNSClient snsClient = getSnsClient()
        ListTopicsResult result = snsClient.listTopics()
        Topic topic = result.topics.find({
                    it -> getTopicName(it.topicArn).equals(topicName)
                })

        while (!topic && result.nextToken) {
            result = snsClient.listTopics(result.nextToken)
            topic = result.topics.find({
                it -> getTopicName(it.topicArn).equals(topicName)
            })
        }

        return topic.topicArn
    }


    public String getTopicName(String topicArn) {

        String[] arnParts = topicArn.split(":")
        String topicName = arnParts[arnParts.length - 1]

        return topicName
    }
}
