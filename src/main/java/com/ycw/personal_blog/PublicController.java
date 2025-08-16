package com.ycw.personal_blog;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class PublicController {

    private ArticleManager articleManager;

    public PublicController(ArticleManager articleManager) {
        this.articleManager = articleManager;
    }

    @GetMapping("/")
    public String Index() {
        return "check /hello or /home";
    }

    @GetMapping("/home")
    public ModelAndView getHome() {
        ModelAndView mav = new ModelAndView("home");
        mav.addObject("allArticles", articleManager.getAllArticles());
        return mav;
    }

    @GetMapping("/article/{article_id}")
    public ModelAndView GetOneArticle(@PathVariable("article_id") String article_id) {
        ModelAndView mav = new ModelAndView("article");
        mav.addObject("article", articleManager.getArticleById(Integer.valueOf(article_id)));
        return mav;
    }

}
