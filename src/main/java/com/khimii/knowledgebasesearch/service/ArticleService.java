package com.khimii.knowledgebasesearch.service;

import com.khimii.knowledgebasesearch.model.document.ArticleDocument;
import com.khimii.knowledgebasesearch.model.dto.ArticleSearchResponse;
import com.khimii.knowledgebasesearch.model.entity.ArticleEntity;
import com.khimii.knowledgebasesearch.model.mapper.ArticleMapper;
import com.khimii.knowledgebasesearch.repository.ArticleJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleService {

    private final ArticleJpaRepository jpaRepository;
    private final ElasticsearchOperations elasticsearchOperations;
    private final ArticleMapper articleMapper;

    @Transactional
    public ArticleSearchResponse createArticle(ArticleEntity article) {
        log.info("Saving article to database: {}", article.getTitle());

        ArticleEntity savedEntity = jpaRepository.save(article);

        ArticleDocument document = articleMapper.toDocument(savedEntity);
        elasticsearchOperations.save(document);
        log.info("Article indexed in Elasticsearch with ID: {}", document.getId());

        return articleMapper.toResponse(savedEntity);
    }

    @Transactional(readOnly = true)
    public ArticleSearchResponse getById(Long id) {
        ArticleEntity entity = jpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Article not found with id: " + id));
        return articleMapper.toResponse(entity);
    }

    @Transactional
    public void deleteArticle(Long id) {
        jpaRepository.deleteById(id);
        elasticsearchOperations.delete(id.toString(), ArticleDocument.class);
        log.info("Article with ID {} deleted from DB and ES", id);
    }

    @Transactional(readOnly = true)
    public Page<ArticleSearchResponse> getAllArticles(Pageable pageable) {
        log.info("Fetching a page of articles from database: {}", pageable);

        Page<ArticleEntity> entitiesPage = jpaRepository.findAll(pageable);

        return entitiesPage.map(articleMapper::toResponse);
    }

    @Transactional
    public List<ArticleSearchResponse> createBulk(List<ArticleEntity> articles) {
        log.info("Bulk saving {} articles", articles.size());

        List<ArticleEntity> savedArticles = jpaRepository.saveAll(articles);

        List<ArticleDocument> documents = savedArticles.stream()
                .map(articleMapper::toDocument)
                .toList();

        elasticsearchOperations.save(documents);
        log.info("Bulk indexing completed.");

        return savedArticles.stream()
                .map(articleMapper::toResponse)
                .toList();
    }
}
