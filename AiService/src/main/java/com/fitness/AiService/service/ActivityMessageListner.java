package com.fitness.AiService.service;

import com.fitness.AiService.model.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListner {

    private final ActivityAiService activityAiService;

    @KafkaListener(topics ="${kafka.topic.name}",groupId = "activity-processor-group")
    public void processActivity(Activity activity){
        log.info("Recieved Activity for processing : {}"+activity.getUserId());
        activityAiService.generateRecommendation(activity);
    }


}
