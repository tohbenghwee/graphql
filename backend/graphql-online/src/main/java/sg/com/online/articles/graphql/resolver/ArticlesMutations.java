package sg.com.online.articles.graphql.resolver;

import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import sg.com.online.articles.model.Article;
import sg.com.online.articles.services.implementation.ArticleServiceImpl;

@Component
public class ArticlesMutations implements GraphQLMutationResolver{

	private static final Logger LOGGER = LoggerFactory.getLogger(ArticleQueries.class);
	
	@Autowired
	private ArticleServiceImpl articleService;
	
	public Article createArticle(Article article) {
		article.setId(null);
		article.setVersion("1");
		// update time stamp no matter new or update article
		article.setModifiedTime(new Date());
		// save
		return articleService.save(article);
	}
	
	public Article updateArticle(Article article) {
		
		Article current = articleService.findById(article.getId());
		article.setVersion(String.format("%s", Integer.parseInt(current.getVersion()) + 1) );
		// update time stamp no matter new or update article
		article.setModifiedTime(new Date());
		// update
		return articleService.save(article);
	}
	
	public void deleteArticle(String id) {
		articleService.delete(id);
	}
}
