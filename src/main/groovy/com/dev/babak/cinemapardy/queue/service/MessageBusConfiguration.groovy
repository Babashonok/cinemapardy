/*
 * Copyright 2016: Thomson Reuters. All Rights Reserved. Proprietary
 * and Confidential information of Thomson Reuters. Disclosure, Use or
 * Reproduction without the written authorization of Thomson Reuters is
 * prohibited.
 */
package com.dev.babak.cinemapardy.queue.service

import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.regions.Regions
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MessageBusConfiguration {

    @Bean(name = "queueName")
    public String getAwsQueueName() {
        return "https://sqs.us-east-1.amazonaws.com/877645874613/OrganizationQueue"
    }

    @Bean(name = "serviceRegion")
    public Regions getRegion() {
        //return Regions.fromName(EC2MetadataUtils.getEC2InstanceRegion())
        return Regions.US_EAST_1
    }
}
