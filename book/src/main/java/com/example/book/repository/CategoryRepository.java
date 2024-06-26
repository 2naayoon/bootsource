package com.example.book.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.book.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // 분류명으로 찾기
    Optional<Category> findByName(String name);
}
