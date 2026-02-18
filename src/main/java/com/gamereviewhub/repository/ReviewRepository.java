package com.gamereviewhub.repository;

import com.gamereviewhub.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByGameIdOrderByCreatedDateDesc(Long gameId);
}
