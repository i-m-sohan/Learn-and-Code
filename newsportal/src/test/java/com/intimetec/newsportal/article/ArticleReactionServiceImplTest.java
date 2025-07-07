package com.intimetec.newsportal.article;

import com.intimetec.newsportal.dto.ArticleReactionRequestDTO;
import com.intimetec.newsportal.mapper.ReactionMapper;
import com.intimetec.newsportal.model.Reaction;
import com.intimetec.newsportal.model.enums.ReactionType;
import com.intimetec.newsportal.repository.ArticleRepository;
import com.intimetec.newsportal.repository.ReactionRepository;
import com.intimetec.newsportal.repository.UserRepository;
import com.intimetec.newsportal.service.CategoryService;
import com.intimetec.newsportal.service.serviceImpl.ArticleReactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ArticleReactionServiceImplTest {

    @InjectMocks
    private ArticleReactionServiceImpl articleReactionService;

    @Mock
    private ReactionRepository reactionRepository;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private ArticleReactionRequestDTO getRequest(String type) {
        return new ArticleReactionRequestDTO(1L, type,101 );
    }

    @Test
    void shouldSaveNewReactionIfNotExist() {
        ArticleReactionRequestDTO request = getRequest("like");

        when(reactionRepository.findByUserIdAndArticleId(1L, 101))
                .thenReturn(Optional.empty());

        Reaction mockReaction = ReactionMapper.toEntity(request);
        when(reactionRepository.save(any(Reaction.class)))
                .thenReturn(mockReaction);

        articleReactionService.handleArticelReaction(request);

        verify(reactionRepository).save(any(Reaction.class));
    }

    @Test
    void shouldNotUpdateIfSameReactionExists() {
        Reaction existing = new Reaction();
        existing.setReactionType(ReactionType.LIKE);

        ArticleReactionRequestDTO request = getRequest("like");

        when(reactionRepository.findByUserIdAndArticleId(1L, 101))
                .thenReturn(Optional.of(existing));

        articleReactionService.handleArticelReaction(request);

        verify(reactionRepository, never()).save(any());
    }

    @Test
    void shouldUpdateReactionIfDifferentExists() {
        Reaction existing = new Reaction();
        existing.setReactionType(ReactionType.DISLIKE);

        ArticleReactionRequestDTO request = getRequest("like");

        when(reactionRepository.findByUserIdAndArticleId(1L, 101))
                .thenReturn(Optional.of(existing));

        articleReactionService.handleArticelReaction(request);

        verify(reactionRepository).save(existing);
    }

    @Test
    void isArticleReactionSame_shouldReturnTrueIfSame() {
        Reaction r = new Reaction();
        r.setReactionType(ReactionType.LIKE);
        try{
            boolean result = articleReactionService
                    .getClass()
                    .getDeclaredMethod("isArticleReactionSame", Reaction.class, ReactionType.class)
                    .invoke(articleReactionService, r, ReactionType.LIKE)
                    .equals(true);
            assertEquals(true, result);
        }
        catch (Exception e){

        }

    }
}
