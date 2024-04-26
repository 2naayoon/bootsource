package com.example.movie.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.movie.dto.MovieDto;
import com.example.movie.dto.PageRequestDto;
import com.example.movie.dto.PageResultDto;
import com.example.movie.entity.Movie;
import com.example.movie.entity.MovieImage;
import com.example.movie.repository.MovieImageRepository;
import com.example.movie.repository.MovieRepository;
import com.example.movie.repository.ReviewRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieImageRepository movieImageRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public PageResultDto<MovieDto, Object[]> getList(PageRequestDto pageRequestDto) {

        Pageable pageable = pageRequestDto.getPageable(Sort.by("mno").descending());

        Page<Object[]> result = movieImageRepository.getTotalList(pageable);

        Function<Object[], MovieDto> function = (en -> entityToDto((Movie) en[0],
                (List<MovieImage>) Arrays.asList((MovieImage) en[1]),
                (Long) en[2], (Double) en[3]));

        return new PageResultDto<>(result, function);
    }

    // [Movie(mno=3, title=Movie3), MovieImage(inum=9,
    // uuid=03b48866-92d3-4d1a-b5ff-e3ebef9944f2, imgName=img1.jpg, path=null), 1,
    // 3.0]
    // [Movie(mno=3, title=Movie3), MovieImage(inum=8,
    // uuid=0a459902-b250-4e31-b300-2d0cdf4e37cf, imgName=img0.jpg, path=null), 1,
    // 3.0]
    @Override
    public MovieDto getRow(Long mno) {
        List<Object[]> result = movieImageRepository.getMovieRow(mno);

        Movie movie = (Movie) result.get(0)[0];

        // 두가지 방식
        // result 길이만큼 반복
        List<MovieImage> movieImages = new ArrayList<>();
        result.forEach(arr -> {
            MovieImage movieImage = (MovieImage) arr[1];
            movieImages.add(movieImage);
        });

        // List<MovieImage> movieImages = result.stream().map(en ->
        // (MovieImage)en[1]).collect(Collectors.toList());

        Long reviewCnt = (Long) result.get(0)[2];
        Double avg = (Double) result.get(0)[3];

        return entityToDto(movie, movieImages, reviewCnt, avg);
    }

    @Transactional
    @Override
    public void movieRemove(Long mno) {
        Movie movie = Movie.builder().mno(mno).build();

        movieImageRepository.deleteByMovie(movie);

        reviewRepository.deleteByMovie(movie);

        movieRepository.delete(movie);
    }

}
