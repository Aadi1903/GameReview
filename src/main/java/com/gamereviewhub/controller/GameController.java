package com.gamereviewhub.controller;

import com.gamereviewhub.model.Game;
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
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;
    private final ReviewService reviewService;

    public GameController(GameService gameService, ReviewService reviewService) {
        this.gameService = gameService;
        this.reviewService = reviewService;
    }

    @GetMapping
    public String listGames(Model model) {
        model.addAttribute("games", gameService.getAllGames());
        return "games/list";
    }

    @GetMapping("/{id}")
    public String gameDetail(@PathVariable Long id, Model model) {
        Game game = gameService.getGameById(id)
                .orElseThrow(() -> new RuntimeException("Game not found with id: " + id));
        model.addAttribute("game", game);
        model.addAttribute("reviews", reviewService.getReviewsByGameId(id));
        return "games/detail";
    }

    @GetMapping("/new")
    public String newGameForm(Model model) {
        model.addAttribute("game", new Game());
        return "games/new";
    }

    @PostMapping("/new")
    public String saveGame(@Valid @ModelAttribute("game") Game game,
                           BindingResult result,
                           @AuthenticationPrincipal UserDetails userDetails,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "games/new";
        }
        game.setCreatedBy(userDetails.getUsername());
        gameService.saveGame(game);
        redirectAttributes.addFlashAttribute("successMessage", "Game added successfully!");
        return "redirect:/games";
    }
}
