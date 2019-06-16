package com.vcv.backend.entities;

import com.vcv.backend.views.ArticleView;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "article")
public class Article {
    @Id
    private Long id;
    private String title;
    private String subtitle;
    private String content;
    private Timestamp date;
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
    public Timestamp getDate() {
        return date;
    }
    public String getTags() {
        return tags;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setDate(Timestamp date) {
        this.date = date;
    }
    public void setTags(String tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id.equals(article.id) &&
                title.equals(article.title) &&
                Objects.equals(subtitle, article.subtitle) &&
                content.equals(article.content) &&
                date.equals(article.date) &&
                Objects.equals(tags, article.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, subtitle, content, date, tags);
    }

    public static class Builder{
        private Long id;
        private String title;
        private String subtitle;
        private String content;
        private Timestamp date;
        private String tags;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }
        public Builder setSubtitle(String subtitle) {
            this.subtitle = subtitle;
            return this;
        }
        public Builder setContent(String content) {
            this.content = content;
            return this;
        }
        public Builder setDate(Timestamp date) {
            this.date = date;
            return this;
        }
        public Builder setTags(String tags) {
            this.tags = tags;
            return this;
        }

        public Article build() {
            Article article = new Article();

            article.setId(this.id);
            article.setTitle(this.title);
            article.setSubtitle(this.subtitle);
            article.setContent(this.content);
            article.setDate(this.date);
            article.setTags(this.tags);

            return article;
        }
        public Article build(ArticleView view) {
            Article article = new Article();

            article.setId(view.getId());
            article.setTitle(view.getTitle());
            article.setSubtitle(view.getSubtitle());
            article.setContent(view.getContent());
            article.setDate(view.getTimestamp());
            article.setTags(view.getTags());

            return article;
        }
    }
}
