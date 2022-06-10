package com.jpmarket.domain.chatroom;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("SELECT p FROM ChatRoom p ORDER BY p.id DESC")
    List<ChatRoom> findAllById();
}
