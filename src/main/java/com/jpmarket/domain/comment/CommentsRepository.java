package com.jpmarket.domain.comment;

import com.jpmarket.domain.posts.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository  extends JpaRepository<Posts, Long> {


}
