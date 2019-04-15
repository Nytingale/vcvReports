package com.vcv.backend.views;

import com.vcv.backend.entities.Article;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ArticleView implements Serializable {
    private Long id;
    private String title;
    private String subtitle;
    private String content;
    private String date;
    private String tags;

    public Long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getSubtitle() {
        return subtitle;
    }
    public String getContent() {
        return content;
    }
    public String getDate() {
        return date;
    }
    public String getTags() {
        return tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleView that = (ArticleView) o;
        return id.equals(that.id) &&
                title.equals(that.title) &&
                Objects.equals(subtitle, that.subtitle) &&
                content.equals(that.content) &&
                date.equals(that.date) &&
                Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, subtitle, content, date, tags);
    }
    
    public ArticleView build(Article article) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);

        this.id = article.getId();
        this.title = article.getTitle();
        this.subtitle = article.getSubtitle();
        this.content = article.getContent();
        this.date = LocalDate.ofInstant(article.getDate().toInstant(), ZoneId.systemDefault()).format(dateFormatter);
        this.tags = article.getTags();

        return this;
    }
    public List<ArticleView> build(List<Article> articles) {
        List<ArticleView> views = new ArrayList<>();

        for(Article article: articles) {
            ArticleView view = new ArticleView().build(article);
            views.add(view);
        }

        return views;
    }
}
