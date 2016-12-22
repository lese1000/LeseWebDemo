package tech.lese.webdemo.repository.mybatis.user;

import java.util.List;

import tech.lese.webdemo.entity.SimpleUser;
import tech.lese.webdemo.repository.MyBatisRepository;

@MyBatisRepository
public interface SimpleUserMybatisDao {
	public List<SimpleUser> getAllUsers();
}
