package com.jpmarket.domain.pictures;

import com.jpmarket.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pictures extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long boardId;

    private Long boardCategory;

    @Column(nullable = false)
    private String originalFileName;

    private String saltedFileName;

    private String savedFileName;

    @Column(nullable = false)
    private String storedFolderPath;


    @Builder
    public Pictures (Long boardId, String originalFileName, String storedFolderPath) {
        this.boardId = boardId;
        this.originalFileName = originalFileName;
        this.storedFolderPath = storedFolderPath;
    }

    public void updateCreatedTime(LocalDateTime createdTime)
    {
        //this.setCreatedDate(createdTime);
        return;
    }

}
