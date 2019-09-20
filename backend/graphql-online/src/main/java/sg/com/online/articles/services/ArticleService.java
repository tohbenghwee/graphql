package sg.com.online.articles.services;

import java.util.List;

import sg.com.online.articles.model.Article;

public interface ArticleService {

	List<Article> findAllUserArticles(List<String> userId);
}
