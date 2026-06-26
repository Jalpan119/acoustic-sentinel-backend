package com.jalpan.acoustic_sentinel_backend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;

@Data
@Document(collection = "alerts")
public class AlertLog {
    @Id
    private String id;
    private String sensorId;
    private String eventType;
    private int confidenceScore;
    private Instant timestamp;
}
