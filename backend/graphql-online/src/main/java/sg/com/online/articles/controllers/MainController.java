package sg.com.online.articles.controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import sg.com.online.articles.model.Article;
import sg.com.online.articles.repositories.ArticleRepository;

@RestController
public class MainController {

	@Autowired
    private ArticleRepository articleRepository;
	
	@PostMapping(value = "/article/save") 
	public void saveArticles(@RequestBody Article article){
		if(article.getId() != null && !article.getId().isEmpty()) {
			Optional<Article> current =articleRepository.findById(article.getId());
			article.setVersion(String.format("%s", Integer.parseInt(current.get().getVersion()) + 1) );
		}
		else {
			article.setVersion("1");
		}
		// update time stamp no matter new or update article
		article.setModifiedTime(new Date());
		
		// save to mongo
		articleRepository.save(article);
	}
	
	@GetMapping(value = "/articles") 
	public List<Article> getArticles(){
		List<Article> articles = articleRepository.findAll();
		return articles; 
	}
}
