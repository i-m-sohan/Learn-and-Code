package com.intimetec.newsportal.service.serviceImpl;

import com.intimetec.newsportal.dto.ArticleDTO;
import com.intimetec.newsportal.model.*;
import com.intimetec.newsportal.model.enums.ReactionType;
import com.intimetec.newsportal.repository.*;
import com.intimetec.newsportal.service.PersonalizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PersonalizationServiceImpl implements PersonalizationService {

    private static final long WEIGHT_LIKE = 2L;
    private static final long WEIGHT_DISLIKE  = 3L;
    private static final long WEIGHT_REPORT = 5L;
    private static final long WEIGHT_SEARCH = 2L;
    private static final long WEIGHT_NOTIFY = 2L;
    private static final long WEIGHT_KEYWORD = 1L;

    @Autowired
    UserRepository userRepository;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    ReactionRepository reactionRepository;
    @Autowired
    ArticleCategoryRepository articleCategoryRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ReportedArticleRepository reportedArticleRepository;

    @Override
    public List<ArticleDTO> getPersonalizedArticles(Long userId, List<ArticleDTO> articleDTOList){
        computeRelevance(userId,articleDTOList);
        return null;
    }

    private Map<ArticleDTO,Long> computeRelevance(Long userId, List<ArticleDTO> articleDTOList){
        Map<ArticleDTO,Long> articleToRelevanceScoreMap = new HashMap<>();

//        Map<Integer,Long> articleIdToLikeRelevanceMap = computeLikeContribution(userId,articleDTOList);
//
//        for(Integer articleId : articleIdToLikeRelevanceMap.keySet()){
//            System.out.println("Articel Id : " + articleId);
//            System.out.println("Like Relevance :" + articleIdToLikeRelevanceMap.get(articleId));
//        }
//
//        Map<Integer,Long> articleIdToDisikeRelevanceMap = computeDislikeContribution(userId,articleDTOList);
//        for(Integer articleId : articleIdToDisikeRelevanceMap.keySet()){
//            System.out.println("Articel Id : " + articleId);
//            System.out.println("Dislike Relevance :" + articleIdToDisikeRelevanceMap.get(articleId));
//        }

        Map<Integer,Long> articleIdToReportRelevanceMap = computeReportContribution(userId,articleDTOList);
        for(Integer articleId : articleIdToReportRelevanceMap.keySet()){
            System.out.println("Articel Id : " + articleId);
            System.out.println("Report Relevance :" + articleIdToReportRelevanceMap.get(articleId));
        }

        return null;
    }

    Map<Integer,Long> computeLikeContribution(Long userId, List<ArticleDTO> articleDTOList){
        Map<Integer,Long> articelIdToLikeRelevanceMap = new HashMap<>();
        List<Reaction> likedReactions = reactionRepository.findByUserIdAndReactionType(userId, ReactionType.LIKE);

        List<Integer> articleIds = new ArrayList<>();

        for(Reaction reaction : likedReactions){
            System.out.println(reaction.toString());
            if(!articleIds.contains(reaction.getArticleId())){
                articleIds.add(reaction.getArticleId());
            }
        }

        List<ArticleCategory> articleCategoryList = articleCategoryRepository.findByArticleIdIn(articleIds);

        List<Integer> catgoryIds = new ArrayList<>();

        for(ArticleCategory articleCategory : articleCategoryList){
            if(!catgoryIds.contains(articleCategory.getCategoryId())){
                catgoryIds.add(articleCategory.getCategoryId());
            }
        }

        List<Category> categoryList = categoryRepository.findAllById(catgoryIds);
        Map<Integer,String> idToCategoryMap = new HashMap<>();
        for(Category category : categoryList){
            System.out.println(category.getCategoryName());
            if(!idToCategoryMap.containsKey(category.getCategoryName())){
                idToCategoryMap.put(category.getCategoryId(),category.getCategoryName());
            }
        }

        Map<Integer,List<String>> articleIdToCategoriesMap = new HashMap();

        for(ArticleCategory articleCategory : articleCategoryList){
            Integer articleId = articleCategory.getArticleId();
            Integer categoryId = articleCategory.getCategoryId();

            if(!articleIdToCategoriesMap.containsKey(articleId)){
                articleIdToCategoriesMap.put(articleId, new ArrayList<>());
            }
            List<String> categories = articleIdToCategoriesMap.get(articleId);
            String categoryName = idToCategoryMap.get(categoryId);

            if(categories != null && categoryName != null && !categories.contains(categoryName)){
                categories.add(categoryName);
            }
        }

        Map<String,Long> categoryNameToLikeCountMap = new HashMap<>();
        Map<Integer,Long> articleIdToCategoryLikeCountMap = new HashMap<>();

        for(Reaction reaction : likedReactions){
            Integer articleId = reaction.getArticleId();
            List<String> categories = articleIdToCategoriesMap.get(articleId);
            System.out.println("for Article " + articleId + "Categories : [" + categories+"]");
            if(categories != null) {
                for (String categoryName : categories) {
                    if (categoryName != null && !categoryName.isEmpty()) {
                        categoryNameToLikeCountMap.put(categoryName, categoryNameToLikeCountMap.getOrDefault(categoryName, 0L)+1);
                    }
                }
            }
        }

        System.out.println("Now final : ");
        for(ArticleDTO articleDTO : articleDTOList){
            Integer articleId = articleDTO.getArticleId();
            List<String> categoryNames = articleDTO.getCategories();
            System.out.println("for Article " + articleId + "Categories : [" + categoryNames+"]");
            for(String categoryName : categoryNames){
                Long likeCountForCategoryName = categoryNameToLikeCountMap.getOrDefault(categoryName,0L);
                articleIdToCategoryLikeCountMap.put(articleId,articleIdToCategoryLikeCountMap.getOrDefault(articleId,0L)+likeCountForCategoryName);
            }
            articelIdToLikeRelevanceMap.put(articleId,articleIdToCategoryLikeCountMap.getOrDefault(articleId,0L) * WEIGHT_LIKE);
        }
        return articelIdToLikeRelevanceMap;
    }

    Map<Integer,Long> computeDislikeContribution(Long userId, List<ArticleDTO> articleDTOList){
        Map<Integer,Long> articelIdToDislikeRelevanceMap = new HashMap<>();
        List<Reaction> dislikedReactions = reactionRepository.findByUserIdAndReactionType(userId, ReactionType.DISLIKE);

        List<Integer> articleIds = new ArrayList<>();

        for(Reaction reaction : dislikedReactions){
            System.out.println(reaction.toString());
            if(!articleIds.contains(reaction.getArticleId())){
                articleIds.add(reaction.getArticleId());
            }
        }

        List<ArticleCategory> articleCategoryList = articleCategoryRepository.findByArticleIdIn(articleIds);

        List<Integer> catgoryIds = new ArrayList<>();

        for(ArticleCategory articleCategory : articleCategoryList){
            if(!catgoryIds.contains(articleCategory.getCategoryId())){
                catgoryIds.add(articleCategory.getCategoryId());
            }
        }

        List<Category> categoryList = categoryRepository.findAllById(catgoryIds);
        Map<Integer,String> idToCategoryMap = new HashMap<>();
        for(Category category : categoryList){
            System.out.println(category.getCategoryName());
            if(!idToCategoryMap.containsKey(category.getCategoryName())){
                idToCategoryMap.put(category.getCategoryId(),category.getCategoryName());
            }
        }

        Map<Integer,List<String>> articleIdToCategoriesMap = new HashMap();

        for(ArticleCategory articleCategory : articleCategoryList){
            Integer articleId = articleCategory.getArticleId();
            Integer categoryId = articleCategory.getCategoryId();

            if(!articleIdToCategoriesMap.containsKey(articleId)){
                articleIdToCategoriesMap.put(articleId, new ArrayList<>());
            }
            List<String> categories = articleIdToCategoriesMap.get(articleId);
            String categoryName = idToCategoryMap.get(categoryId);

            if(categories != null && categoryName != null && !categories.contains(categoryName)){
                categories.add(categoryName);
            }
        }

        Map<String,Long> categoryNameToDislikeCountMap = new HashMap<>();
        Map<Integer,Long> articleIdToCategoryDislikeCountMap = new HashMap<>();

        for(Reaction reaction : dislikedReactions){
            Integer articleId = reaction.getArticleId();
            List<String> categories = articleIdToCategoriesMap.get(articleId);
            System.out.println("for Article " + articleId + "Categories : [" + categories+"]");
            if(categories != null) {
                for (String categoryName : categories) {
                    if (categoryName != null && !categoryName.isEmpty()) {
                        categoryNameToDislikeCountMap.put(categoryName, categoryNameToDislikeCountMap.getOrDefault(categoryName, 0L));
                    }
                }
            }
        }

        System.out.println("Now final : ");
        for(ArticleDTO articleDTO : articleDTOList){
            Integer articleId = articleDTO.getArticleId();
            List<String> categoryNames = articleDTO.getCategories();
            System.out.println("for Article " + articleId + "Categories : [" + categoryNames+"]");

            for(String categoryName : categoryNames){
                Long dislikeCountForCategoryName = categoryNameToDislikeCountMap.getOrDefault(categoryName,0L);
                articleIdToCategoryDislikeCountMap.put(articleId,articleIdToCategoryDislikeCountMap.getOrDefault(articleId,0L)+dislikeCountForCategoryName);
            }
            articelIdToDislikeRelevanceMap.put(articleId,articleIdToCategoryDislikeCountMap.getOrDefault(articleId,0L) * WEIGHT_DISLIKE);
        }
        return articelIdToDislikeRelevanceMap;
    }

    Map<Integer,Long> computeReportContribution(Long userId, List<ArticleDTO> articleDTOList){
        Map<Integer,Long> articelIdToReportRelevanceMap = new HashMap<>();
        List<ReportedArticle> reportedArticles = reportedArticleRepository.findByUserId(userId);

        List<Integer> articleIds = new ArrayList<>();

        for(ReportedArticle reportedArticle : reportedArticles){
            System.out.println(reportedArticle.toString());
            if(!articleIds.contains(reportedArticle.getArticleId())){
                articleIds.add(reportedArticle.getArticleId());
            }
        }

        List<ArticleCategory> articleCategoryList = articleCategoryRepository.findByArticleIdIn(articleIds);

        List<Integer> catgoryIds = new ArrayList<>();

        for(ArticleCategory articleCategory : articleCategoryList){
            if(!catgoryIds.contains(articleCategory.getCategoryId())){
                catgoryIds.add(articleCategory.getCategoryId());
            }
        }

        List<Category> categoryList = categoryRepository.findAllById(catgoryIds);
        Map<Integer,String> idToCategoryMap = new HashMap<>();
        for(Category category : categoryList){
            System.out.println(category.getCategoryName());
            if(!idToCategoryMap.containsKey(category.getCategoryName())){
                idToCategoryMap.put(category.getCategoryId(),category.getCategoryName());
            }
        }

        Map<Integer,List<String>> articleIdToCategoriesMap = new HashMap();

        for(ArticleCategory articleCategory : articleCategoryList){
            Integer articleId = articleCategory.getArticleId();
            Integer categoryId = articleCategory.getCategoryId();

            if(!articleIdToCategoriesMap.containsKey(articleId)){
                articleIdToCategoriesMap.put(articleId, new ArrayList<>());
            }
            List<String> categories = articleIdToCategoriesMap.get(articleId);
            String categoryName = idToCategoryMap.get(categoryId);

            if(categories != null && categoryName != null && !categories.contains(categoryName)){
                categories.add(categoryName);
            }
        }

        Map<String,Long> categoryNameToReportCountMap = new HashMap<>();
        Map<Integer,Long> articleIdToCategoryReportCountMap = new HashMap<>();

        for(ReportedArticle reportedArticle : reportedArticles){
            Integer articleId = reportedArticle.getArticleId();
            List<String> categories = articleIdToCategoriesMap.get(articleId);
            System.out.println("for Article " + articleId + "Categories : [" + categories+"]");
            if(categories != null) {
                for (String categoryName : categories) {
                    if (categoryName != null && !categoryName.isEmpty()) {
                        categoryNameToReportCountMap.put(categoryName, categoryNameToReportCountMap.getOrDefault(categoryName, 0L)+1);
                    }
                }
            }
        }

        System.out.println("Now final : ");
        for(ArticleDTO articleDTO : articleDTOList){
            Integer articleId = articleDTO.getArticleId();
            List<String> categoryNames = articleDTO.getCategories();
            System.out.println("for Article " + articleId + "Categories : [" + categoryNames+"]");

            for(String categoryName : categoryNames){
                Long reportCountForCategoryName = categoryNameToReportCountMap.getOrDefault(categoryName,0L);
                articleIdToCategoryReportCountMap.put(articleId,articleIdToCategoryReportCountMap.getOrDefault(articleId,0L)+reportCountForCategoryName);
            }
            articelIdToReportRelevanceMap.put(articleId,articleIdToCategoryReportCountMap.getOrDefault(articleId,0L) * WEIGHT_REPORT);
        }
        return articelIdToReportRelevanceMap;
    }
}
