package com.khimii.knowledgebasesearch.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleSearchResponse {

    private Long id;
    private String title;
    private String summary;
    private String content;

    private Set<String> tags;
    private String category;
    private String author;
    private String status;

    private OffsetDateTime createdAt;

    private String highlight;
}
