package com.jpmarket.domain.posts;
import com.jpmarket.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

    @Column(columnDefinition = "integer default 0")
    private Long status;

    @Column(nullable = false)
    private Long location;

    @Column
    private Long category;

    @Column
    private Long buyerId;

//    @Column
//    private Location
//    //TODO 구글맵 장소 추가

    @Builder
    public Posts(String title, String content, String author,
                 Long location, Long category, Long buyerId) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.location = location;
        this.category = category;
        this.buyerId = buyerId;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
