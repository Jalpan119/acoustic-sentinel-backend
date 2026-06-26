package com.jalpan.acoustic_sentinel_backend.repository;

import com.jalpan.acoustic_sentinel_backend.model.AlertLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AlertRepository extends MongoRepository<AlertLog, String> {
}
