package sg.com.online.articles.services.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.com.online.articles.repositories.ArticleRepository;
import sg.com.online.articles.model.Article;
import sg.com.online.articles.services.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService{

	private final ArticleRepository articleRepository;

    @Autowired
    ArticleServiceImpl(ArticleRepository articleRepository){
        this.articleRepository = articleRepository;
    }

    public List<Article> findAllArticles() {
        return articleRepository.findAll();
    }
    
    public Article findById(String id) {
    	 Optional<Article> art = articleRepository.findById(id);
    	 if(art.isPresent()) {
    		 return art.get();
    	 }
    	 return null;
    }
    
    public List<Article> findAllUserArticles(List<String> ids) {
        return articleRepository.findByIdIn(ids);
    }
    
    public Article save(Article article) {
        return articleRepository.save(article);
    }
    
    public void delete(String id) {
        articleRepository.deleteById(id);
    }
}
