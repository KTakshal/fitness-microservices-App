package com.fitness.AiService.controller;

import com.fitness.AiService.model.Recommendation;
import com.fitness.AiService.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommendations")
public class RecommendationController {
    private final RecommendationService recommendationService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Recommendation>> getUserRecommendation(@PathVariable String userId){
        return recommendationService.getUserRecommendation(userId);
    }

    @GetMapping("/activity/{activityId}")
    public Recommendation getActivityRecommendation(@PathVariable String activityId){
        return recommendationService.getActivityRecommendation(activityId);
    }
}
