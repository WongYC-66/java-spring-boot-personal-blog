package com.ycw.personal_blog;

import java.time.LocalDateTime;

public class Article {
    private Integer id;
    private String title;
    private LocalDateTime publishedAt;
    private String content;

    public Article() {
        // Spring needs this for form binding
    }

    public Article(Integer id, String title, LocalDateTime time, String content) {
        this.id = id;
        this.title = title;
        this.publishedAt = time;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public String getContent() {
        return content;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
