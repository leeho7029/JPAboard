package com.edutech.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@NoArgsConstructor, @AllArgsConstructor는 Lombok 어노테이션으로 각각 파라미터가 없는 기본생성자(예시-Board())와 모든 필드를 파라미터로 받는 생성자를 자동으로 생성한다.
// ㄴ> 이것의 장점은 Board()와 Board()
@EntityListeners(value = { AuditingEntityListener.class })
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bno;

    @Column(length = 200, nullable = false)
    private String title;

    @Column(length = 2000, nullable = false)
    private String content;

    @Column(length = 50, nullable = false)
    private String writer;

    @CreationTimestamp  //@CreatedDate = LocalDate
    @Column(name = "regdate", updatable = false)
    private LocalDateTime regDate;

    @UpdateTimestamp    //@LastModifiedDate = LocalDate
    @Column(name = "moddate")
    private LocalDateTime modDate;

    public void change(String title, String content){
        this.title = title; // 매개변수로 받아온 title값으로 title정보를 갱신
        this.content = content; // 매개변수로 받아온 content값으로 content 정보를 갱신
    }
}
