package com.tivizado.tivizado.repository;

import com.tivizado.tivizado.model.Favourite;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FavouriteRepository extends JpaRepository<Favourite, Integer> {
    Optional<Favourite> findByShowidAndUserid(Long showid, Integer userid);
    List<Favourite> findByUserid(Integer userid);
}