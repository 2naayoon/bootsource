package com.example.movie.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class MovieDto {

    private Long mno;
    private String title;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    // 화면단에서 필요한 데이터
    private double avg; // 평점 평균
    private Long reviewCnt; // 리뷰 개수

    // 영화이미지 리스트
    @Builder.Default
    private List<MovieImageDto> movieImageDtos = new ArrayList<>();
}