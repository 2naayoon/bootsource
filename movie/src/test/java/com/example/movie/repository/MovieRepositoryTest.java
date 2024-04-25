package com.example.movie.repository;

import static org.mockito.ArgumentMatchers.isNull;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.movie.constant.MemberRole;
import com.example.movie.entity.Member;
import com.example.movie.entity.Movie;
import com.example.movie.entity.MovieImage;
import com.example.movie.entity.Review;

@SpringBootTest
public class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private MovieImageRepository movieImageRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void movieInsertTest() {
        // 영화/ 영화이미지 샘플 데이터 추가
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Movie movie = Movie.builder()
                    .title("Movie" + i)
                    .build();
            movieRepository.save(movie);

            int count = (int) (Math.random() * 5) + 1;

            for (int j = 0; j < count; j++) {
                MovieImage mImage = MovieImage.builder()
                        // UUID.randomUUID() : 유효아이디, 패스정보.jpg 붙여 사용 (중복파일명 x)
                        .uuid(UUID.randomUUID().toString())
                        .movie(movie)
                        .imgName("img" + j + ".jpg")
                        .build();
                movieImageRepository.save(mImage);
            }
        });
    }

    @Test
    public void memberInsertTest() {
        // 멤버 샘플 데이터 추가
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder()
                    .email("mem" + i + "@naver.com")
                    .password(passwordEncoder.encode("1111"))
                    .nickname("reviewer" + i)
                    .role(MemberRole.MEMBER)
                    .build();
            memberRepository.save(member);
        });
    }

    @Test
    public void reviewInsertTest() {
        // 리뷰 샘플 데이터 추가
        IntStream.rangeClosed(1, 200).forEach(i -> {
            Long mno = (long) (Math.random() * 100) + 1;
            Movie movie = Movie.builder().mno(mno).build();

            Long mid = (long) (Math.random() * 100) + 1;
            Member member = Member.builder().mid(mid).build();

            Review review = Review.builder()
                    .movie(movie)
                    .member(member)
                    .grade((int) (Math.random() * 5) + 1)
                    .text("이 영화에 대한.." + i)
                    .build();
            reviewRepository.save(review);
        });
    }

    @Test
    public void movieListTest() {
        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<Object[]> list = movieRepository.getListPage(pageRequest);

        for (Object[] objects : list) {
            System.out.println(Arrays.toString(objects));
        }
    }

    @Test
    public void movieImageListTest() {
        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<Object[]> list = movieImageRepository.getTotalList(pageRequest);

        for (Object[] objects : list) {
            System.out.println(Arrays.toString(objects));
        }
    }
}