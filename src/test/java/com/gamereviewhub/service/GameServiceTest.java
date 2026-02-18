package com.gamereviewhub.service;

import com.gamereviewhub.model.Game;
import com.gamereviewhub.repository.GameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameService gameService;

    @Test
    public void testGetAllGames() {
        Game g1 = new Game(1L, "G1", "Gen", "Desc", "u1");
        Game g2 = new Game(2L, "G2", "Gen", "Desc", "u1");
        when(gameRepository.findAll()).thenReturn(Arrays.asList(g1, g2));

        List<Game> result = gameService.getAllGames();

        assertEquals(2, result.size());
        verify(gameRepository, times(1)).findAll();
    }

    @Test
    public void testSaveGame() {
        Game game = new Game(null, "New", "Genre", "Desc", "user");
        when(gameRepository.save(any(Game.class))).thenReturn(game);

        gameService.saveGame(game);

        verify(gameRepository, times(1)).save(game);
    }
}
