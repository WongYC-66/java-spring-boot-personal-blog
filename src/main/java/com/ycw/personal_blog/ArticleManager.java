package com.ycw.personal_blog;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

@Service // singleton
public class ArticleManager {
    List<Article> allArticles = new ArrayList<>();
    static Integer lastId = 0;

    public ArticleManager() {
        // todo: read the article from JSON;
        // populateFakeArticles();
        allArticles = readFromJson();
        lastId = getLastId();
        System.out.println(lastId);
    }

    public Article getArticleById(Integer id) {
        return allArticles
                .stream()
                .filter(art -> art.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Article> getAllArticles() {
        return allArticles;
    }

    public void addArticle(Article article) {
        if (article.getPublishedAt() == null) {
            article.setPublishedAt(LocalDateTime.now());
        }
        allArticles.add(article);
        saveToJson();
    }

    public void updateArticle(Integer targetId, Article updatedArticle) {
        Integer idx = getIndexOfArticle(targetId);
        if (idx == null)
            return;

        allArticles.set(idx, updatedArticle);
        saveToJson();
    }

    public Integer getIndexOfArticle(Integer targetId) {
        List<Integer> idList = allArticles
                .stream()
                .map(Article::getId)
                .collect(Collectors.toList());
        return idList.indexOf(targetId);
    }

    public void deleteArticle(Integer targetId) {
        allArticles.removeIf(art -> art.getId() == targetId);
        saveToJson();
    }

    public void populateFakeArticles() {
        for (Integer i = 0; i < 10; i++) {
            Article newArticle = new Article(i, "title-" + i.toString(), LocalDateTime.now(), "im content");
            addArticle(newArticle);
        }
    }

    public Integer getLastId() {
        return allArticles.getLast().getId();
    }

    public void saveToJson() {
        // Create an ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Define the file path
        File file = new File("output.json");

        try {
            // Write the object as JSON to the file
            objectMapper.writeValue(file, allArticles);
            System.out.println("JSON written to output.json successfully!");
        } catch (IOException e) {
            System.out.println("Failed to write!");
        }
    }

    public List<Article> readFromJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        JavaTimeModule module = new JavaTimeModule();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS");
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));

        File file = new File("output.json");

        if (!file.exists())
            return new ArrayList<>();

        try {
            return objectMapper.readValue(file, new TypeReference<List<Article>>() {
            });
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}
