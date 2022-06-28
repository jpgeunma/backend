package com.jpmarket.domain.pictures;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface PicturesRepository extends JpaRepository<Pictures, Long> {
    @Query("SELECT p FROM Pictures p WHERE p.boardId = ?1")
    List<Pictures> findallByBoardId(Long boardId);

    @Query("SELECT p FROM Pictures p WHERE p.originalFileName = ?1")
    Pictures findByOriginalFileName(String originalFileName);
}
