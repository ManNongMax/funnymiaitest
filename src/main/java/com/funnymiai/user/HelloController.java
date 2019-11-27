package com.funnymiai.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器
 *
 * @author: @我没有三颗心脏
 * @create: 2018-05-08-下午 16:46
 */
@RestController
public class HelloController {

    @RequestMapping(value="/hello",method=RequestMethod.POST)
    public String hello(@RequestBody String str, HttpServletRequest request) {
    	 HttpSession session = request.getSession();
    	    if (session.getAttribute("user") == null) {
    	      session.setAttribute("user", "zhangsan");
    	      System.out.println("不存在session");
    	    } else {
    	      System.out.println("存在session");
    	   }
    	 
    	  return session.getId()+"  :  "+session.getAttribute("user");
    }
}