package com.intimetec.newsportal.mapper;

import com.intimetec.newsportal.dto.ArticleFlagRequestDTO;
import com.intimetec.newsportal.dto.ReportedArticleDTO;
import com.intimetec.newsportal.model.ReportedArticle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReportedArticleMapper {

    public static ReportedArticle toEntity(ArticleFlagRequestDTO articleFlagRequestDTO) {
        return new ReportedArticle(
                articleFlagRequestDTO.getUserId(),
                articleFlagRequestDTO.getArticleId(),
                articleFlagRequestDTO.getReason()
        );
    }

    public static List<ReportedArticle> toEntityList(List<ArticleFlagRequestDTO> articleFlagRequestDTOS) {
        return articleFlagRequestDTOS.stream()
                .map(ReportedArticleMapper::toEntity)
                .collect(Collectors.toList());
    }
    public static ReportedArticleDTO toDTO(ReportedArticle reportedArticle) {
        if (reportedArticle == null) return null;

        return new ReportedArticleDTO(
                reportedArticle.getReportId(),
                reportedArticle.getUserId(),
                reportedArticle.getArticleId(),
                reportedArticle.getReason(),
                reportedArticle.getReportedAt()
        );
    }

    public static List<ReportedArticleDTO> toDTOList(List<ReportedArticle> reportedArticles) {
        List<ReportedArticleDTO> dtoList = new ArrayList<>();
        if (reportedArticles != null) {
            for (ReportedArticle entity : reportedArticles) {
                dtoList.add(toDTO(entity));
            }
        }
        return dtoList;
    }

}
