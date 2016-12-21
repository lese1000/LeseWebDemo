package tech.lese.webdemo.service.news;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import tech.lese.webdemo.repository.mybatis.news.NewsMyBatisDao;
import tech.lese.webdemo.repository.mybatis.news.NewsMyBatisDao2;

@Component
@Transactional
public class NewsService {
	@Autowired
	NewsMyBatisDao newsMyBatisDao;
	@Autowired
	NewsMyBatisDao2 newsMyBatisDao2;
	
	public List<Map<String,Object>> getServerNewsList(Map<String,Object> params){
		return newsMyBatisDao2.getNewsList(params);
	}
	public List<Map<String,Object>> getNewsList(Map<String,Object> params){
		return newsMyBatisDao.getNewsList(params);
	}
	public Map<String,Object> getNewsById(Map<String,Object> params){
		return newsMyBatisDao.getNewsById(params);
	}
	public Map<String,Object> getNewsCount(){
		return newsMyBatisDao.getNewsCount();
	}
	public int saveNews(Map<String,Object> params){
		return newsMyBatisDao.saveNews(params);
	}
}
