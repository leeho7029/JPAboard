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
//@Builder는 Lombok에서 제공하는 어노테이션으로 빌더 패턴 적용. 빌더패턴은 매개변수의 순서를 신경쓰지 않고도 객체를 생성할수 있다.
@NoArgsConstructor
@AllArgsConstructor
//@NoArgsConstructor, @AllArgsConstructor는 Lombok 어노테이션으로 각각 파라미터가 없는 기본생성자(예시-Board())와 모든 필드를 파라미터로 받는 생성자를 자동으로 생성한다.
// ㄴ> 이것의 장점은 Board()와 Board()
@EntityListeners(value = { AuditingEntityListener.class })
//@EntityListeners(value={AuditingEntiryListener.class})는 JPA AUditing을 활성화하기 위한 어노테이션.
// Auditing은 엔터티의 생성 및 수정 시간을 자동으로 관리하는 기능 제공 @Id는 해당 필드를 엔터티의 primarykey로 지정
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@GeneratedValue(strategy=GenerationType.IDENTITY)는 주키의 생성전략을 지정.
    // 데이터베이스의 identity 컬럼을 사용하여 자동으로 생성되도록 설정함.
    private Integer bno;

    @Column(length = 200, nullable = false)
    private String title;

    @Column(length = 2000, nullable = false)
    private String content;

    @Column(length = 50, nullable = false)
    private String writer;

    @CreationTimestamp  //@CreatedDate = LocalDate
    //@CreationTimestamp:엔터티가 생성될때 자동으로 타임스탬프를 설정한다.
    @Column(name = "regdate", updatable = false)
    private LocalDateTime regDate;

    @UpdateTimestamp    //@LastModifiedDate = LocalDate
    //@UpdateTimestamp는 엔터티가 수정될때 자동으로 타임스탬프를 갱신한다.
    @Column(name = "moddate")
    private LocalDateTime modDate;

    public void change(String title, String content){
        this.title = title; // 매개변수로 받아온 title값으로 title정보를 갱신
        this.content = content; // 매개변수로 받아온 content값으로 content 정보를 갱신
    }
}
