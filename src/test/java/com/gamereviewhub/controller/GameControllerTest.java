package com.gamereviewhub.controller;

import com.gamereviewhub.config.SecurityConfig;
import com.gamereviewhub.config.TestSecurityConfig;
import com.gamereviewhub.model.Game;
import com.gamereviewhub.security.CustomUserDetailsService;
import com.gamereviewhub.service.GameService;
import com.gamereviewhub.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = GameController.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class))
@Import(TestSecurityConfig.class)
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private CustomUserDetailsService userDetailsService;

    @Test
    @WithMockUser
    public void testListGames() throws Exception {
        when(gameService.getAllGames()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/games"))
                .andExpect(status().isOk())
                .andExpect(view().name("games/list"))
                .andExpect(model().attributeExists("games"));
    }

    @Test
    @WithMockUser
    public void testGameDetail() throws Exception {
        Game game = new Game(1L, "Test Game", "Action", "Desc", "admin");
        when(gameService.getGameById(anyLong())).thenReturn(Optional.of(game));
        when(reviewService.getReviewsByGameId(anyLong())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/games/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("games/detail"))
                .andExpect(model().attributeExists("game"))
                .andExpect(model().attributeExists("reviews"));
    }

    @Test
    public void testNewGameFormIsAccessibleWithoutLoginInTest() throws Exception {
        // With TestSecurityConfig permitting all, this should now return 200 OK
        mockMvc.perform(get("/games/new"))
                .andExpect(status().isOk());
    }
}
