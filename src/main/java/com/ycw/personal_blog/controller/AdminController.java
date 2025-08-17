package com.ycw.personal_blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ycw.personal_blog.service.ArticleManager;
import com.ycw.personal_blog.data.Article;

@Controller
public class AdminController {

    private ArticleManager articleManager;

    public AdminController(ArticleManager articleManager) {
        this.articleManager = articleManager;
    }

    // index
    @GetMapping("/admin")
    public ModelAndView getAdmin() {
        ModelAndView mav = new ModelAndView("admin");
        mav.addObject("allArticles", articleManager.getAllArticles());
        return mav;
    }

    // new query
    @GetMapping("/new")
    public ModelAndView getNew() {
        ModelAndView mav = new ModelAndView("new");
        Integer nextId = articleManager.getLastId() + 1;
        mav.addObject("article", new Article(nextId, "", null, ""));
        return mav;
    }

    // new submit
    @PostMapping("/new")
    public String postNew(@ModelAttribute Article article,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "new";
        }

        // Proceed with normal processing
        this.articleManager.addArticle(article);

        return "redirect:/admin";
    }

    // Edit query
    @GetMapping("/edit/{article_id}")
    public ModelAndView getEdit(@PathVariable("article_id") String article_id) {
        ModelAndView mav = new ModelAndView("edit");
        Article article = articleManager.getArticleById(Integer.valueOf(article_id));
        mav.addObject("article", article);
        return mav;
    }

    // Edit submit
    @PostMapping("/edit/{article_id}")
    public String postEdit(@PathVariable("article_id") String article_id, @ModelAttribute Article updatedArticle,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("article", updatedArticle);
            return "edit";
        }
        articleManager.updateArticle(Integer.valueOf(article_id), updatedArticle);

        return "redirect:/admin";
    }

    // Delete
    @PostMapping("/delete/{article_id}")
    public String deleteArticle(@PathVariable("article_id") String article_id) {
        articleManager.deleteArticle(Integer.valueOf(article_id));
        return "redirect:/admin";
    }

}
