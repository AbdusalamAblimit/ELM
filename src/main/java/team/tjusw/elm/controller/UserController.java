package team.tjusw.elm.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import team.tjusw.elm.po.CommonResult;
import team.tjusw.elm.po.User;
import team.tjusw.elm.service.UserService;
import team.tjusw.elm.util.BasicAuth;

//@CrossOrigin("*") 
@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping("/{userId}")
	public CommonResult<User> getUserByIdByPass(
			@PathVariable("userId") String userId,String password,
			HttpServletRequest request

			) throws Exception {
		User param = new User();
		param.setUserId(userId);

//	    String header = request.getHeader("Authorization");
//	    if (header == null || !header.startsWith("Basic ")) {
//	        return new CommonResult<User>(401,"需要Base64加密.",null);
//	    }
//	    String[] tokens = BasicAuth.decodeFromHeader(header);
//	    String password = tokens[1];
		if(password==null)
			return new CommonResult<User>(400,"请求中不包含密码.",null);
	    param.setPassword(password);

		User ret = userService.getUserByIdByPass(param);
		if(ret == null)
		{
			if(userService.getUserById(userId)!=0)
				return new CommonResult<User>(403,"密码错误.",null);
			else
				return new CommonResult<User>(404,"用户名或密码错误.",null);
		}
		else
			return new CommonResult<User>(200,"成功获取用户信息.",(User) ret);
		
	}
	
	
	

	@GetMapping("/exists/{userId}")
	public CommonResult<Integer> getUserById(
			@PathVariable("userId") String userId
			) throws Exception {
		int ret = userService.getUserById(userId);
		if(ret==0)
			return new CommonResult<Integer> (404,String.format("不存在ID为%s的用户",userId,ret),ret);
		else
			return new CommonResult<Integer> (200,String.format("存在ID为%s的用户",userId,ret),ret);
	}

	
	@PostMapping("/{userId}")
	public CommonResult<Integer> saveUser(
			@PathVariable("userId") String userId,
			HttpServletRequest request
			) throws Exception {
		Map<String,String[]> formData = request.getParameterMap();
		User param = new User();
		param.setUserId(userId);
//		String header = request.getHeader("Authorization");
//	    if (header == null || !header.startsWith("Basic ")) {
//	    	return new CommonResult<Integer>(401,"需要Base64加密.",null);
//	    }
//	    String[] tokens = BasicAuth.decodeFromHeader(header);
//	    String password = tokens[1];
//	    param.setPassword(password);
		param.setPassword(formData.get("password")[0]);
	    param.setUserName(formData.get("userName")[0]);
	    
	    param.setUserSex(Integer.valueOf(formData.get("userSex")[0]));
		
	    int ret = userService.saveUser(param);
	    if(ret == 0)
	    	return new CommonResult<Integer>(409, String.format("已经存在ID为%s的用户.",param.getUserId()),ret);
	    else
	    	return new CommonResult<Integer> (200,"成功注册用户.",ret);
	}

}
