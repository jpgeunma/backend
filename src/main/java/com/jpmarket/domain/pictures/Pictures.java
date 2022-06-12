package com.jpmarket.domain.pictures;

import com.jpmarket.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pictures extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long boardId;

    @Column(nullable = false)
    private String originalFileName;

    @Column(nullable = false)
    private String storedFolderPath;

    @Column
    private String type;

    @Builder
    public Pictures (Long boardId, String originalFileName, String storedFolderPath) {
        this.boardId = boardId;
        this.originalFileName = originalFileName;
        this.storedFolderPath = storedFolderPath;
    }

}
