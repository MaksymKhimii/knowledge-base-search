package com.khimii.knowledgebasesearch.model.dto;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.Set;

@Data
public class ArticleSearchRequest {

    private String query;

    private Set<String> tags;
    private String category;
    private String author;
    private String status;

    private OffsetDateTime createdFrom;
    private OffsetDateTime createdTo;

    private int page = 0;
    private int size = 10;

    private String sortBy = "createdAt";
    private SortDirection sortDirection = SortDirection.DESC;

    public enum SortDirection {
        ASC, DESC
    }
}
