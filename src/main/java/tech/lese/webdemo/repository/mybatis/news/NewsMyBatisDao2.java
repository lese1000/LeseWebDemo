package tech.lese.webdemo.repository.mybatis.news;

import java.util.List;
import java.util.Map;

import tech.lese.webdemo.repository.MyBatisRepository2;

@MyBatisRepository2
public interface NewsMyBatisDao2 {

	public List<Map<String,Object>> getNewsList(Map<String,Object> params);
	public Map<String,Object> getNewsById(Map<String,Object> params);
	public int saveNews(Map<String,Object> params);
	public Map<String,Object> getNewsCount();
}
