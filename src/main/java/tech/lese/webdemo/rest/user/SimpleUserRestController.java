package tech.lese.webdemo.rest.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tech.lese.webdemo.entity.SimpleUser;
import tech.lese.webdemo.service.user.SimpleUserService;
import tech.lese.webdemo.utils.JsonDto;

@RestController
@RequestMapping("/api/v1/user")
public class SimpleUserRestController {
	@Autowired
	SimpleUserService simpleUserService;
	
	@RequestMapping("/getAllUsers")
	public JsonDto getAllUsers(){
		JsonDto json =new JsonDto();
		List<SimpleUser> list = simpleUserService.getAllUsers();
		json.setList(list);
		json.setSuccess();
		return json;
	}

}
