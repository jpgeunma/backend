package com.jpmarket.domain.posts;
import com.jpmarket.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity     // Entity class에서는 Setter를 만들지 않는다!!!!! 값 변경이 필요하면 메소드를 추가
public class Posts extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private String author;

    private Long userId;

    @Column
    private Long price;

    @Column(columnDefinition = "integer default 0")
    private Long status;

    @Column(nullable = true) // TODO 위치 정보 획득 방법 확인
    private Long location;

    @Column
    private Long category;

    @Column
    private Long buyerId;

//    @Column
//    private Location
//    //TODO 구글맵 장소 추가
    @ColumnDefault("0")
    private Long viewed;

    @ColumnDefault("0")
    private Long commentsNum;

    @ColumnDefault("0")
    private Long favoritesNum;

    @Builder
    public Posts(Long id, String title, String content, String author, Long userId,
                 Long location, Long price, Long category, Long buyerId, Long status,
                 Long viewd, Long commentsNum, Long favoritesNum) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.userId = userId;
        this.price = price;
        this.location = location;
        this.category = category;
        this.buyerId = buyerId;
        this.status = status;
        this.viewed = viewd;
        this.commentsNum = commentsNum;
        this.favoritesNum = favoritesNum;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
