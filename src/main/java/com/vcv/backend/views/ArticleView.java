package com.vcv.backend.views;

import com.vcv.backend.entities.Article;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ArticleView implements Serializable {
    private Long id;
    private Timestamp timestamp;
    private String title;
    private String subtitle;
    private String content;
    private String date;
    private String tags;

    public Long getId() {
        return id;
    }
    public Timestamp getTimestamp() {
        return timestamp;
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

    public ArticleView setId(Long id) {
        this.id = id;
        return this;
    }
    public ArticleView setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
        return this;
    }
    public ArticleView setTitle(String title) {
        this.title = title;
        return this;
    }
    public ArticleView setSubtitle(String subtitle) {
        this.subtitle = subtitle;
        return this;
    }
    public ArticleView setContent(String content) {
        this.content = content;
        return this;
    }
    public ArticleView setDate(String date) {
        this.date = date;
        return this;
    }
    public ArticleView setTags(String tags) {
        this.tags = tags;
        return this;
    }

    public ArticleView build(Article article) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);

        this.id = article.getId();
        this.timestamp = article.getDate();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleView that = (ArticleView) o;
        return id.equals(that.id) &&
                timestamp.equals(that.timestamp) &&
                title.equals(that.title) &&
                Objects.equals(subtitle, that.subtitle) &&
                content.equals(that.content) &&
                date.equals(that.date) &&
                Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timestamp, title, subtitle, content, date, tags);
    }
}
