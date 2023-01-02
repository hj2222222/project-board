package com.koreait.projectboard.domain;

import com.koreait.projectboard.config.JpaConfig;
import com.koreait.projectboard.repository.ArticleCommentRepository;
import com.koreait.projectboard.repository.ArticleRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@Disabled("Jpa Rest 테스트는 불필요하므로 제외시킴")
@Import(JpaConfig.class)
@DisplayName("JPA연결 테스트")
@DataJpaTest
class JpaRepositoryTest {
    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    public JpaRepositoryTest(
            @Autowired ArticleRepository articleRepository,
            @Autowired ArticleCommentRepository articleCommentRepository){
        this.articleCommentRepository = articleCommentRepository;
        this.articleRepository = articleRepository;
    }

    @DisplayName("select test")
    @Test
    void select(){
        List<Article> articles = articleRepository.findAll();
        assertThat(articles).isNotNull().hasSize(1000);
    }

    @DisplayName("insert test")
    @Test
    void insert(){
        long prevCount = articleRepository.count();
        Article saveArticle = articleRepository.save(Article.of("새로운글","새로운글을 등록합니다","new"));
        assertThat(articleRepository.count()).isEqualTo(prevCount+1);
    }

    @DisplayName("update test")
    @Test
    void update(){
        Article article = articleRepository.findById(1L).orElseThrow();
        String updateHashTag = "#Spring";
        article.setHashtag(updateHashTag);

        Article saveArticle = articleRepository.saveAndFlush(article);
        assertThat(saveArticle).hasFieldOrPropertyWithValue("hashtag",updateHashTag);

    }

    @DisplayName("delete test")
    @Test
    void delete(){
        Article article = articleRepository.findById(1L).orElseThrow();
        long preArticleCount = articleRepository.count();
        long preArticleCommentCount = articleCommentRepository.count();
        int deletedCommentSize = article.getArticleComments().size();

        articleRepository.delete(article);

        assertThat(articleRepository.count()).isEqualTo(preArticleCount-1);
        assertThat(articleCommentRepository.count()).isEqualTo(preArticleCommentCount-deletedCommentSize);

    }


}