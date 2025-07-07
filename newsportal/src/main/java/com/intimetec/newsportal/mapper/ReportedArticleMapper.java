package com.intimetec.newsportal.mapper;

import com.intimetec.newsportal.dto.ArticleReportRequestDTO;
import com.intimetec.newsportal.dto.ReportedArticleDTO;
import com.intimetec.newsportal.model.ReportedArticle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReportedArticleMapper {

    public static ReportedArticle toEntity(ArticleReportRequestDTO articleReportRequestDTO) {
        return new ReportedArticle(
                articleReportRequestDTO.getUserId(),
                articleReportRequestDTO.getArticleId(),
                articleReportRequestDTO.getReason()
        );
    }

    public static List<ReportedArticle> toEntityList(List<ArticleReportRequestDTO> articleReportRequestDTOS) {
        return articleReportRequestDTOS.stream()
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
