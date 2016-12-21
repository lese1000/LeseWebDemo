package tech.lese.webdemo.rest.site;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tech.lese.webdemo.service.news.NewsService;
import tech.lese.webdemo.utils.JsonDto;
import tech.lese.webdemo.utils.JsonDto.PageDto;
@RestController
@RequestMapping(value="/api/v1/news")
public class NewsRestController {
	@Autowired
	private NewsService newsService;
	
	@RequestMapping(value="/getNewsList")
	public JsonDto getNewsList(@RequestParam(value = "pageNum", defaultValue = "1")int pageNum,@RequestParam(value = "pageSize", defaultValue = "20")int pageSize){
		JsonDto json = new JsonDto();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("pageNum", (pageNum-1)*pageSize);
			params.put("pageSize", pageSize);
			 List<Map<String, Object>> newsList = newsService.getNewsList(params);
			 Map<String, Object> newsCountMap = newsService.getNewsCount();
			 Long totalElements=0l;
			 if(null!=newsCountMap){
				 totalElements=(Long) newsCountMap.get("counts");
			 }
			 json.setList(newsList);
			 PageDto page =new PageDto();
			 page.setPageNumber(pageNum);
			 page.setPageSize(pageSize);
			 page.setTotalElements(totalElements);
			 int totalPages=0;
			 if(totalElements%pageSize==0){
				 totalPages = (int) (totalElements/pageSize);
			 }else{
				 totalPages = (int) (totalElements/pageSize+1);
			 }
			
			page.setTotalPages(totalPages);
			json.setPage(page);
			 json.setSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json ;
		
	}
	@RequestMapping(value="/getServerNewsList")
	public JsonDto getServerNewsList(@RequestParam(value = "pageNum", defaultValue = "1")int pageNum,@RequestParam(value = "pageSize", defaultValue = "20")int pageSize){
		JsonDto json = new JsonDto();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("pageNum", (pageNum-1)*pageSize);
			params.put("pageSize", pageSize);
			 List<Map<String, Object>> newsList = newsService.getServerNewsList(params);
			 Map<String, Object> newsCountMap = newsService.getNewsCount();
			 Long totalElements=0l;
			 if(null!=newsCountMap){
				 totalElements=(Long) newsCountMap.get("counts");
			 }
			 json.setList(newsList);
			 PageDto page =new PageDto();
			 page.setPageNumber(pageNum);
			 page.setPageSize(pageSize);
			 page.setTotalElements(totalElements);
			 int totalPages=0;
			 if(totalElements%pageSize==0){
				 totalPages = (int) (totalElements/pageSize);
			 }else{
				 totalPages = (int) (totalElements/pageSize+1);
			 }
			
			page.setTotalPages(totalPages);
			json.setPage(page);
			 json.setSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json ;
		
	}
	@RequestMapping(value="/getNewsById")
	public JsonDto  getNewsById(String newsId){
		JsonDto json = new JsonDto();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("newsId", newsId);
			 Map<String, Object> newsMap = newsService.getNewsById(params);
			 json.setObj(newsMap);
			 json.setSuccess();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json ;
	}
//	public int saveNews(Map<String,Object> params);

}
