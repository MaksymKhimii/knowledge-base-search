package com.khimii.knowledgebasesearch.repository;


import com.khimii.knowledgebasesearch.model.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleJpaRepository extends JpaRepository<ArticleEntity, Long> {
}
