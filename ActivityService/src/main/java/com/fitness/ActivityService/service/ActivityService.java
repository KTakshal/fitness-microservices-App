package com.fitness.ActivityService.service;

import com.fitness.ActivityService.dto.ActivityResonse;
import com.fitness.ActivityService.dto.ActivtiyRequest;
import com.fitness.ActivityService.model.Activity;
import com.fitness.ActivityService.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserValidationService userValidationService;
    private final KafkaTemplate<String,Activity> kafkaTemplate;

    @Value("${kafka.topic.name}")
    private String topicName;

    public ActivityResonse trackActivity(ActivtiyRequest activtiyRequest) {
        boolean isValidUser = userValidationService.validateUser(activtiyRequest.getUserId());

        if(!isValidUser){
            throw new RuntimeException("Invalid User: "+activtiyRequest.getUserId());
        }

        Activity activity = Activity.builder()
                .userId(activtiyRequest.getUserId())
                .type(activtiyRequest.getType())
                .duration(activtiyRequest.getDuration())
                .caloriesBurned(activtiyRequest.getCaloriesBurned())
                .additionalMetrics(activtiyRequest.getAdditionalMetrics())
                .startTime(activtiyRequest.getStartTime())
                .build();
        Activity savedActivity = activityRepository.save(activity);

        try{
            kafkaTemplate.send(topicName,savedActivity.getUserId(),savedActivity);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return mapToResponse(savedActivity);
    }

    private  ActivityResonse mapToResponse(Activity savedActivity) {
        return ActivityResonse.builder()
                .id(savedActivity.getId())
                .userId(savedActivity.getUserId())
                .type(savedActivity.getType())
                .duration(savedActivity.getDuration())
                .caloriesBurned(savedActivity.getCaloriesBurned())
                .startTime(savedActivity.getStartTime())
                .additionalMetrics(savedActivity.getAdditionalMetrics())
                .createdAt(savedActivity.getCreatedAt())
                .updatedAt(savedActivity.getUpdatedAt())
                .build();
    }
}
