package com.example.jpa.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "membertbl", uniqueConstraints = {
        @UniqueConstraint(name = "NAME_AGE_UNIQUE", columnNames = { "name", "age" }) })
@Entity
public class Member {
    // id, name, age, roleType(ADMIN, USER), create_date, last_modified_datae,
    // description
    @Id
    private String id;

    @Column(name = "name")
    private String userName;

    private Integer age;

    // RoleType enum에서 제한한 값이 들어옴
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    // insert 시 시간 자동 저장
    @CreatedDate
    private LocalDateTime createDate;

    // 마지막으로 변경된 시간 자동 저장
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    // 대용량 데이터 담을 때
    @Lob
    private String description;
}
