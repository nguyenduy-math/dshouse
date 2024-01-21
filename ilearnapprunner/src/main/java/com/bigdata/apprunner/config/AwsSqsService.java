package com.bigdata.apprunner.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class AwsSqsService {
    @Value("${aws.sqs.queueName}")
    private String queueName;

    @SqsListener(value="${aws.sqs.queueName}")
    public void receiveMsg(String msg, @Header("SenderId") String senderId){
        System.out.println(msg);
    }
}
