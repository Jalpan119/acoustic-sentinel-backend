package com.jalpan.acoustic_sentinel_backend.controller;

import com.jalpan.acoustic_sentinel_backend.model.AlertLog;
import com.jalpan.acoustic_sentinel_backend.repository.AlertRepository;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.time.Instant;

@RestController
@RequestMapping("/api/v1/alerts")
@CrossOrigin(origins = "*") // Allows your mobile browser to talk to Tomcat
public class AlertController {

    private final AlertRepository alertRepository;
    private final RestTemplate restTemplate;

    public AlertController(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
        this.restTemplate = new RestTemplate();
    }

    @PostMapping
    public ResponseEntity<AlertLog> triggerAlert(@RequestBody AlertLog alert) {
        // 1. Persist the record cleanly to your local MongoDB instance
        alert.setTimestamp(Instant.now());
        AlertLog savedAlert = alertRepository.save(alert);
        System.out.println("🚨 Saved to MongoDB: Doorbell signature detected.");

        // 2. Fire the native OS push notification to your phone client
        try {
            // REPLACE THIS with the exact secret topic string you typed into the app
            String topicName = "jalpan-sentinel-6a-to-10-xyz";
            String ntfyUrl = "https://ntfy.sh/" + topicName;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            headers.set("Title", "🔔 Doorbell Ring Detected!");
            headers.set("Priority", "5"); // High priority forces Android to bypass battery saver/Doze
            headers.set("Tags", "warning,loud_sound"); // Adds clean UI icons to your notification banner

            String message = "Acoustic pattern matched with " + alert.getConfidenceScore() + "% confidence score.";
            HttpEntity<String> request = new HttpEntity<>(message, headers);

            restTemplate.postForObject(ntfyUrl, request, String.class);
            System.out.println("📱 Distributed system message pushed to ntfy gateway successfully.");

        } catch (Exception e) {
            System.err.println("⚠️ Notification gateway dispatch failed: " + e.getMessage());
        }

        return ResponseEntity.ok(savedAlert);
    }
}