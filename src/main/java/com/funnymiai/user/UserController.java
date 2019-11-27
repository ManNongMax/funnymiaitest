package com.funnymiai.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.funnymiai.model.user.User;
import com.funnymiai.service.user.UserService;

@RestController
@RequestMapping(value="/user")
public class UserController {
	@Autowired
	private UserService userService;
	@RequestMapping(value="/0002",method=RequestMethod.POST)
    public String mng0002(@RequestBody String str) {
		User user = new User();
		user.setId(str);
    	User u=userService.getNameById(user);
    	return JSONObject.toJSONString(u);    
    }
	@RequestMapping(value="/0001")
    public String mng0001(@RequestBody String str) {
    	User u=userService.getNameById(null);

    	return JSONObject.toJSONString(u);    
    }
}
