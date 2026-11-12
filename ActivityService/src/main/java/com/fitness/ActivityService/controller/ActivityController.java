package com.fitness.ActivityService.controller;

import com.fitness.ActivityService.dto.ActivityResonse;
import com.fitness.ActivityService.dto.ActivtiyRequest;
import com.fitness.ActivityService.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    @PostMapping
    public ResponseEntity<ActivityResonse> trackActivity(@RequestBody ActivtiyRequest activtiyRequest){
        return ResponseEntity.ok(activityService.trackActivity(activtiyRequest));
    }
}
