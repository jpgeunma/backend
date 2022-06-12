package com.jpmarket.domain.favorites;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FavoritesRepository extends JpaRepository<Favorites, Long> {

    @Query("SELECT f FROM Favorites f WHERE f.userId = ?1 ORDER BY f.userId DESC")
    List<Favorites> findAllByUserId(Long userId);

    //FIXME not added to Controller Yet
    @Query("SELECT COUNT(f) FROM Favorites f WHERE f.userId=?1")
    Long findAllLengthByUserId(Long userId);
}
