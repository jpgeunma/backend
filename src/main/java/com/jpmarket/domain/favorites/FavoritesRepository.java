package com.jpmarket.domain.favorites;

import com.jpmarket.domain.posts.Posts;
import com.jpmarket.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FavoritesRepository extends JpaRepository<Favorites, Long> {

    @Query("SELECT f FROM Favorites f WHERE f.user.id = ?1 ORDER BY f.user.id DESC")
    List<Favorites> findAllByUserId(Long userId);

    //FIXME not added to Controller Yet
    @Query("SELECT COUNT(f) FROM Favorites f WHERE f.user.id=?1")
    Long findAllLengthByUserId(Long userId);

    Optional<Favorites> findByUserAndPosts(User user, Posts Posts);

    Long deleteFavoritesByUserAndPosts(User user, Posts posts);
}
