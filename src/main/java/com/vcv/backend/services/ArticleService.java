package com.vcv.backend.services;

import com.vcv.backend.entities.Article;
import com.vcv.backend.entities.User;
import com.vcv.backend.exceptions.ArticleServiceException;
import com.vcv.backend.repositories.ArticleRepository;
import com.vcv.backend.views.ArticleView;
import com.vcv.backend.views.MessageView;
import com.vcv.utilities.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;

@Service
public class ArticleService {
    @Autowired private ArticleRepository articleRepository;

    /* General */
    public List<ArticleView> getArticles() throws ArticleServiceException {
        List<Article> articles = (List<Article>) articleRepository.findAll();
        if(!articles.isEmpty()) return new ArticleView().build(articles);
        else throw new ArticleServiceException("Error 900: getArticles() returned null");
    }

    /* Portal (VCV) */
    public MessageView addArticle(User vcv, Article article) throws ArticleServiceException {
        // First, Confirm that the Admin is VCV Staff
        if(!Utils.isValidStaff(vcv)) throw new ArticleServiceException("Error 920: addArticle(vcv, article) has failed you for VCV Staff Authentication");

        try {
            // Second, Add the New Article to the Database
            articleRepository.save(article);
            return new MessageView().build("Successfully Saved the Article");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ArticleServiceException("Error 915: addArticle(vcv, article) failed to add the Article");
        }
    }
}
