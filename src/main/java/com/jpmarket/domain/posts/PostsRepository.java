package com.jpmarket.domain.posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
public interface PostsRepository extends JpaRepository<Posts, Long>{

    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findallDesc();

    @Query("SELECT p FROM Posts p ORDER BY p.viewed DESC")
    List<Posts> findPostsByViewed();

    @Modifying
    @Query("UPDATE Posts p SET p.viewed = p.viewed + 1 where p.id = ?1")
    Integer updateView(Long id);


    @Modifying
    @Query("UPDATE Posts p SET p.favoritesNum = p.favoritesNum + 1 where p.id = ?1")
    Integer addFavoritesNum(Long id);

    @Modifying
    @Query("UPDATE Posts p SET p.favoritesNum = p.favoritesNum - 1 where p.id = ?1")
    Integer deleteFavoritesNum(Long id);

    @Query("SELECT p FROM Posts p WHERE UPPER(p.title) LIKE CONCAT('%',UPPER(?1), '%') ORDER BY p.createdDate DESC")
    List<Posts> findSearchPosts(String word);
}
