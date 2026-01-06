package com.khimii.knowledgebasesearch.api;

import com.khimii.knowledgebasesearch.model.dto.ArticleSearchRequest;
import com.khimii.knowledgebasesearch.model.dto.ArticleSearchResponse;
import com.khimii.knowledgebasesearch.service.ArticleSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor
public class ArticleSearchController {

    private final ArticleSearchService searchService;

    @GetMapping("/search")
    public ResponseEntity<PagedModel<ArticleSearchResponse>> search(
            ArticleSearchRequest request,
            @PageableDefault(size = 10) Pageable pageable) {
        log.info("Searching for: {}", request.getQuery());
        Page<ArticleSearchResponse> page = searchService.searchArticles(request, pageable);

        return ResponseEntity.ok(new PagedModel<>(page));
    }
}
