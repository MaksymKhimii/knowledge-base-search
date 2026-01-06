package com.khimii.knowledgebasesearch.service;

import com.khimii.knowledgebasesearch.model.document.ArticleDocument;
import com.khimii.knowledgebasesearch.model.dto.ArticleSearchRequest;
import com.khimii.knowledgebasesearch.model.dto.ArticleSearchResponse;
import com.khimii.knowledgebasesearch.model.mapper.ArticleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.HighlightQuery;
import org.springframework.data.elasticsearch.core.query.highlight.Highlight;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightField;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.khimii.knowledgebasesearch.util.ApplicationConstants.CONTENT;

@Service
@RequiredArgsConstructor
public class ArticleSearchService {

    private final ElasticsearchOperations elasticsearchOperations;
    private final ArticleMapper mapper;


    public Page<ArticleSearchResponse> searchArticles(ArticleSearchRequest request, Pageable pageable) {

        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q.bool(b -> {
                    if (StringUtils.hasText(request.getQuery())) {
                        b.must(m -> m.multiMatch(mm -> mm
                                .query(request.getQuery())
                                .fields("title^3", "summary^2", CONTENT)
                        ));
                    }
                    return b;
                }))
                .withPageable(pageable)
                .withHighlightQuery(new HighlightQuery(
                        new Highlight(List.of(new HighlightField(CONTENT))),
                        ArticleDocument.class
                ))
                .build();

        SearchHits<ArticleDocument> hits = elasticsearchOperations.search(query, ArticleDocument.class);

        List<ArticleSearchResponse> content = hits.stream().map(hit -> {
            ArticleSearchResponse res = mapper.toResponse(hit.getContent());
            List<String> highlights = hit.getHighlightField(CONTENT);
            if (!highlights.isEmpty()) {
                res.setHighlight(highlights.get(0));
            }
            return res;
        }).toList();

        return PageableExecutionUtils.getPage(content, pageable, hits::getTotalHits);
    }
}
