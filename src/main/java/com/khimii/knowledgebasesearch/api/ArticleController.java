package com.khimii.knowledgebasesearch.api;

import com.khimii.knowledgebasesearch.model.dto.ArticleSearchResponse;
import com.khimii.knowledgebasesearch.model.entity.ArticleEntity;
import com.khimii.knowledgebasesearch.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public ResponseEntity<ArticleSearchResponse> create(@RequestBody ArticleEntity article) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(articleService.createArticle(article));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleSearchResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<ArticleSearchResponse>> getAll(
            @PageableDefault(size = 100, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<ArticleSearchResponse> page = articleService.getAllArticles(pageable);

        if (page.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(page);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<ArticleSearchResponse>> createBulk(@RequestBody List<ArticleEntity> articles) {
        return ResponseEntity.ok(articleService.createBulk(articles));
    }
}
