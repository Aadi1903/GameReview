package com.gamereviewhub.service;

import com.gamereviewhub.model.Review;
import com.gamereviewhub.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> getReviewsByGameId(Long gameId) {
        return reviewRepository.findByGameIdOrderByCreatedDateDesc(gameId);
    }

    public Review saveReview(Review review) {
        if (review.getCreatedDate() == null) {
            review.setCreatedDate(LocalDateTime.now());
        }
        return reviewRepository.save(review);
    }

    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}
