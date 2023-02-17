package com.tivizado.tivizado.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tivizado.tivizado.model.Show;

public interface ShowRepository extends JpaRepository<Show, Long> {
    Optional<Show> findByShowid(Long showid);
}