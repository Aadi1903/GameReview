package com.gamereviewhub.controller;

import com.gamereviewhub.model.Game;
import com.gamereviewhub.model.Review;
import com.gamereviewhub.service.GameService;
import com.gamereviewhub.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final GameService gameService;

    public ReviewController(ReviewService reviewService, GameService gameService) {
        this.reviewService = reviewService;
        this.gameService = gameService;
    }

    @GetMapping("/add/{gameId}")
    public String addReviewForm(@PathVariable Long gameId, Model model) {
        Game game = gameService.getGameById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found with id: " + gameId));
        model.addAttribute("game", game);
        model.addAttribute("review", new Review());
        return "reviews/add";
    }

    @PostMapping("/add/{gameId}")
    public String saveReview(@PathVariable Long gameId,
                             @Valid @ModelAttribute("review") Review review,
                             BindingResult result,
                             @AuthenticationPrincipal UserDetails userDetails,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            Game game = gameService.getGameById(gameId)
                    .orElseThrow(() -> new RuntimeException("Game not found with id: " + gameId));
            model.addAttribute("game", game);
            return "reviews/add";
        }
        review.setGameId(gameId);
        review.setCreatedBy(userDetails.getUsername());
        reviewService.saveReview(review);
        redirectAttributes.addFlashAttribute("successMessage", "Review added successfully!");
        return "redirect:/games/" + gameId;
    }

    @GetMapping("/edit/{id}")
    public String editReviewForm(@PathVariable Long id, Model model) {
        Review review = reviewService.getReviewById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));
        Game game = gameService.getGameById(review.getGameId())
                .orElseThrow(() -> new RuntimeException("Game not found"));
        model.addAttribute("review", review);
        model.addAttribute("game", game);
        return "reviews/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateReview(@PathVariable Long id,
                               @Valid @ModelAttribute("review") Review updatedReview,
                               BindingResult result,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        Review existing = reviewService.getReviewById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));

        if (result.hasErrors()) {
            Game game = gameService.getGameById(existing.getGameId())
                    .orElseThrow(() -> new RuntimeException("Game not found"));
            model.addAttribute("game", game);
            return "reviews/edit";
        }

        existing.setRating(updatedReview.getRating());
        existing.setComment(updatedReview.getComment());
        reviewService.saveReview(existing);
        redirectAttributes.addFlashAttribute("successMessage", "Review updated successfully!");
        return "redirect:/games/" + existing.getGameId();
    }

    @PostMapping("/delete/{id}")
    public String deleteReview(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Review review = reviewService.getReviewById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));
        Long gameId = review.getGameId();
        reviewService.deleteReview(id);
        redirectAttributes.addFlashAttribute("successMessage", "Review deleted successfully!");
        return "redirect:/games/" + gameId;
    }
}
