package tech.lese.webdemo.repository.mybatis.news;

import java.util.List;
import java.util.Map;

import tech.lese.webdemo.repository.MyBatisRepository;

@MyBatisRepository
public interface NewsMyBatisDao {

	public List<Map<String,Object>> getNewsList(Map<String,Object> params);
	public Map<String,Object> getNewsById(Map<String,Object> params);
	public int saveNews(Map<String,Object> params);
	public Map<String,Object> getNewsCount();
}
