package sg.com.online.articles.graphql.resolver;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import sg.com.online.articles.model.Article;
import sg.com.online.articles.services.implementation.ArticleServiceImpl;

@Component
public class ArticleQueries implements GraphQLQueryResolver {

	private static final Logger LOGGER = LoggerFactory.getLogger(ArticleQueries.class);
	
	@Autowired
	private ArticleServiceImpl articleService;
	
	public List<Article> articles() {
		return articleService.findAllArticles();
	}	
}
