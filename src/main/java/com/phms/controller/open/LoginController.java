package com.phms.controller.open;


import com.phms.model.ResultMap;
import com.phms.pojo.Page;
import com.phms.pojo.User;
import com.phms.service.PageService;
import com.phms.service.UserRoleService;
import com.phms.service.UserService;
import com.phms.utils.MD5;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * 登录控制类
 */
@Controller("OpenLogin")
@RequestMapping()
public class LoginController {
	@Autowired
	private ResultMap resultMap;
	@Autowired
	private UserService userService;
	@Autowired
	private PageService pageService;
	@Autowired
	private UserRoleService userRoleService;

	private final Logger logger = LoggerFactory.getLogger(LoginController.class);

	/**
	 * 返回 尚未登陆信息
	 */
	@RequestMapping(value = "/notLogin", method = RequestMethod.GET)
	@ResponseBody
	public ResultMap notLogin() {
		logger.warn("尚未登陆！");
		return resultMap.success().message("您尚未登陆！");
	}

	/**
	 * 返回 没有权限
	 */
	@RequestMapping(value = "/notRole", method = RequestMethod.GET)
	@ResponseBody
	public ResultMap notRole() {
		Subject subject = SecurityUtils.getSubject();
		User user = (User) subject.getPrincipal();
		if (user != null) {
			logger.info("{}---没有权限！", user.getName());
		}
		return resultMap.success().message("您没有权限！");
	}
/**演示页面**/
	@RequestMapping(value = "/demo/table", method = RequestMethod.GET)
	public String demoTable() {
		return "table";
	}

	@RequestMapping(value = "/demo/tu", method = RequestMethod.GET)
	public String demoTu() {
		return "tu";
	}

	@RequestMapping(value = "/demo/tu1", method = RequestMethod.GET)
	public String tu1() {
		return "tu1";
	}

	@RequestMapping(value = "/demo/tu2", method = RequestMethod.GET)
	public String tu2() {
		return "tu2";
	}

	@RequestMapping(value = "/demo/tu3", method = RequestMethod.GET)
	public String tu3() {
		return "tu3";
	}
/**演示页面**/
	/**
	 * Method name: logout <BR>
	 * Description: 退出登录 <BR>
	 * @return String<BR>
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout() {
		Subject subject = SecurityUtils.getSubject();
		User user = (User) subject.getPrincipal();
		if (null != user) {
			logger.info("{}---退出登录！", user.getName());
		}
		subject.logout();
		return "login";
	}

	/**
	 * Method name: login <BR>
	 * Description: 登录验证 <BR>
	 * Remark: <BR>
	 * 
	 * @param username 用户名
	 * @param password 密码
	 * @return ResultMap<BR>
	 */
	@RequestMapping(value = "/login")
	@ResponseBody
	public ResultMap login(String username, String password) {
		return userService.login(username, password);
	}

	/**
	 * Method name: login <BR>
	 * Description: 登录页面 <BR>
	 * 
	 * @return String login.html<BR>
	 */
	@RequestMapping(value = "/index")
	public String login() {
		return "login";
	}

	/**
	 * 注册页面 regist.html
	 */
	@RequestMapping(value = "/regist")
	public String regist() {
		return "regist";
	}

	/**
	 * 注册
	 */
	@RequestMapping(value = "/doRegist")
	@ResponseBody
	public ResultMap doRegist(User user) {
		System.out.println(user);
		User u = userService.getUserByPhoneAndName(user.getPhone(), null);
		if (u != null){
			return resultMap.success().message("该手机号已注册!");
		}
		try {
			user.setPassword(MD5.md5(user.getPassword()));
			user.setCreateTime(new Date());
			userService.save(user);
			String[] ids = new String[1];
			ids[0] = user.getId()+"";
			// 普通用户
			userRoleService.addUserRole(2, ids);
			return resultMap.success().message("注册成功");
		}catch (Exception e){
			e.printStackTrace();
			return resultMap.fail().message("注册失败");
		}
	}

	/**
	 * Method name: index <BR>
	 * Description: 登录页面 <BR>
	 * 
	 * @return String login.html<BR>
	 */
	@RequestMapping(value = "/")
	public String index(Model model) {
		Subject subject = SecurityUtils.getSubject();
		User user = (User) subject.getPrincipal();

		if (null != user) {
			model.addAttribute("user", user);

			List<Page> pageList = pageService.getAllRolePageByUserId(user.getId()+"");

			model.addAttribute("pageList", pageList);
			return "index";
		} else {
			return "login";
		}
	}

	/**
	 * Method name: main <BR>
	 * Description: 进入主页面 <BR>
	 * index.html
	 */
	@RequestMapping(value = "/main")
	public String main(Model model) {
		Subject subject = SecurityUtils.getSubject();
		User user = (User) subject.getPrincipal();
		if (null != user) {
			model.addAttribute("user", user);
		} else {
			return "login";
		}

		List<Page> pageList = pageService.getAllRolePageByUserId(user.getId()+"");

		model.addAttribute("pageList", pageList);
		return "index";
	}

	/**
	 * Method name: checkUserPassword <BR>
	 * Description: 检测旧密码是否正确 <BR>
	 * 
	 * @param password 旧密码
	 * @return boolean 是否正确<BR>
	 */
	@RequestMapping(value = "/user/checkUserPassword")
	@ResponseBody
	public boolean checkUserPassword(String password) {
		return userService.checkUserPassword(password);
	}

	/**
	 * Method name: updatePassword <BR>
	 * Description: 更新密码 <BR>
	 * 
	 * @param password 旧密码
	 * @return String 是否成功<BR>
	 */
	@RequestMapping(value = "/user/updatePassword")
	@ResponseBody
	public String updatePassword(String password) {
		return userService.updatePassword(password);
	}
}
