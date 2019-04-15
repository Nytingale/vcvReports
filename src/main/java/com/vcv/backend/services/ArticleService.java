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

import java.util.List;
import java.util.Optional;

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
    public MessageView.ArticleReport publish(User vcv,
                                             Article article) throws ArticleServiceException {
        // First, Confirm that the Admin is VCV Staff
        if(!Utils.isValidStaff(vcv)) throw new ArticleServiceException("Error 920: publish(vcv, article) has failed you for VCV Staff Authentication");

        try {
            // Second, Add the New Article to the Database
            articleRepository.save(article);
            return new MessageView.ArticleReport().build(article,"Successfully Published the Article");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ArticleServiceException("Error 915: publish(vcv, article) failed to publish the Article");
        }
    }

    public MessageView.ArticleReport remove(User vcv,
                                            Long id) throws ArticleServiceException {
        // First, Confirm that the Admin is VCV Staff
        if(!Utils.isValidStaff(vcv)) throw new ArticleServiceException("Error 920: remove(vcv, id) has failed you for VCV Staff Authentication");

        // Second, Confirm that the Article Exists
        Optional<Article> article = articleRepository.findById(id);
        if(article.isEmpty()) throw new ArticleServiceException("Error 905: remove(vcv, id) cannot find such an Article that Exists");

        try {
            // Third, Add the New Article to the Database
            article.ifPresent(a -> articleRepository.save(a));
            return new MessageView.ArticleReport().build(article.get(),"Successfully Removed the Article");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ArticleServiceException("Error 915: remove(vcv, id) failed to remove the Article");
        }
    }

    public MessageView.ArticleReport edit(User vcv,
                                          Article article) throws ArticleServiceException {
        // First, Confirm that the Admin is VCV Staff
        if(!Utils.isValidStaff(vcv)) throw new ArticleServiceException("Error 920: edit(vcv, article) has failed you for VCV Staff Authentication");

        // Second, Confirm that the Article Exists
        Optional<Article> articleDB = articleRepository.findById(article.getId());
        if(articleDB.isEmpty()) throw new ArticleServiceException("Error 905: edit(vcv, article) cannot find such an Article that Exists");

        try {
            // Third, Add the New Article to the Database
            articleRepository.save(article);
            return new MessageView.ArticleReport().build(article,"Successfully Edited the Article");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ArticleServiceException("Error 915: edit(vcv, article) failed to edit the Article");
        }
    }
}
