package com.khimii.knowledgebasesearch.model.mapper;

import com.khimii.knowledgebasesearch.model.document.ArticleDocument;
import com.khimii.knowledgebasesearch.model.dto.ArticleSearchResponse;
import com.khimii.knowledgebasesearch.model.entity.ArticleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static com.khimii.knowledgebasesearch.util.ApplicationConstants.ID;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    @Mapping(target = ID, expression = "java(entity.getId().toString())")
    ArticleDocument toDocument(ArticleEntity entity);

    ArticleSearchResponse toResponse(ArticleEntity entity);

    @Mapping(target = ID, expression = "java(Long.valueOf(doc.getId()))")
    ArticleSearchResponse toResponse(ArticleDocument doc);
}
