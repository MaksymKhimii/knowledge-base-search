package com.khimii.knowledgebasesearch.model.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.OffsetDateTime;
import java.util.Set;

import static com.khimii.knowledgebasesearch.util.ApplicationConstants.ANALYSER_STANDARD;
import static com.khimii.knowledgebasesearch.util.ApplicationConstants.ARTICLES;

@Document(indexName = ARTICLES, createIndex = false)
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDocument {

    @Id
    private String id;

    @Field(type = FieldType.Text, analyzer = ANALYSER_STANDARD)
    private String title;

    @Field(type = FieldType.Text, analyzer = ANALYSER_STANDARD)
    private String summary;

    @Field(type = FieldType.Text, analyzer = ANALYSER_STANDARD)
    private String content;

    @Field(type = FieldType.Keyword)
    private Set<String> tags;

    @Field(type = FieldType.Keyword)
    private String category;

    @Field(type = FieldType.Keyword)
    private String author;

    @Field(type = FieldType.Keyword)
    private String status;

    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private OffsetDateTime createdAt;
}
