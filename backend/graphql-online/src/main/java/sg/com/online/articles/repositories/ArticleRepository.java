package sg.com.online.articles.repositories;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.repository.PagingAndSortingRepository;

import sg.com.online.articles.model.Article;

public interface ArticleRepository extends PagingAndSortingRepository<Article, String>{

	List<Article> findAll();
	
	List<Article> findByIdIn(List<String> ids);
}
