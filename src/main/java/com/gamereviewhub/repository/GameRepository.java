package com.gamereviewhub.repository;

import com.gamereviewhub.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
