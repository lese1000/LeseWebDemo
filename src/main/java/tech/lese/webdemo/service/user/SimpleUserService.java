package tech.lese.webdemo.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import tech.lese.webdemo.entity.SimpleUser;
import tech.lese.webdemo.repository.mybatis.user.SimpleUserMybatisDao;

@Component
@Transactional
public class SimpleUserService {
	@Autowired
	private SimpleUserMybatisDao simpleUserMybatisDao;
	
	public List<SimpleUser> getAllUsers(){
		return simpleUserMybatisDao.getAllUsers();
	}
	
	public static void main(String[] args){
		 ApplicationContext ctx = new FileSystemXmlApplicationContext( "classpath:applicationContext.xml");  
		 SimpleUserService simpleUserService = (SimpleUserService) ctx.getBean("simpleUserService");  
		 simpleUserService.getAllUsers();
	}

}
