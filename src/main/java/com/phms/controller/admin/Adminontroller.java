package com.phms.controller.admin;

import com.phms.model.ResultMap;
import com.phms.pojo.Page;
import com.phms.pojo.Role;
import com.phms.pojo.User;
import com.phms.pojo.UserParameter;
import com.phms.service.*;
import com.phms.utils.MD5;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * 管理员权限控制类
 */
@Controller("Admin")
@RequestMapping("/admin")
public class Adminontroller {

	@Autowired
	private PageService pageService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PageRoleService pageRoleService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private UserService userService;

	private final Logger logger = LoggerFactory.getLogger(Adminontroller.class);

	/**
	 * Method name: page <BR>
	 * Description: 跳转到页面设置页面 <BR>
	 * 
	 * @param model
	 * @return String<BR>
	 */
	@RequestMapping("/page")
	public String page(Model model) {
		List<Page> pageList = pageService.getAllPage();
		model.addAttribute("pageList", pageList);
		return "sa/page";
	}

	/**
	 * Method name: role <BR>
	 * Description: 跳转到角色设置页面 <BR>
	 * 
	 * @param model
	 * @return String<BR>
	 */
	@RequestMapping("/role")
	public String role(Model model) {
		return "sa/role";
	}

	/**
	 * Method name: getAllRole <BR>
	 * Description: 获取所有权限 <BR>
	 * 
	 * @return List<Role><BR>
	 */
	@RequestMapping("/getAllRole")
	@ResponseBody
	public List<Role> getAllRole() {
		return roleService.getAllRole();
	}

	/**
	 * Method name: getAllPage <BR>
	 * Description: 获取所有页面 <BR>
	 * 
	 * @return List<Page><BR>
	 */
	@RequestMapping("/getAllPage")
	@ResponseBody
	public List<Page> getAllPage() {
		return pageService.getAllPage();
	}

	/**
	 * Method name: getPageByRole <BR>
	 * Description: 获取某个角色的权限页面 <BR>
	 */
	@RequestMapping("/getPageByRole")
	@ResponseBody
	public Object getPageByRole(Integer roleId) {
		return pageService.getAllPageByRoleId(roleId);
	}

	/**
	 * Method name: updatePageById <BR>
	 * Description: 根据页面id更新页面 <BR>
	 * 
	 * @param page
	 * @return ResultMap<BR>
	 */
	@RequestMapping("/updatePageById")
	@ResponseBody
	public ResultMap updatePageById(Page page) {
		return pageService.updatePageById(page);
	}

	/**
	 * Method name: addPage <BR>
	 * Description: 添加页面 <BR>
	 * 
	 * @param page
	 * @return Page<BR>
	 */
	@RequestMapping("/addPage")
	@ResponseBody
	public Page addPage(Page page) {
		return pageService.addPage(page);
	}

	/**
	 * Method name: delPageById <BR>
	 * Description: 根据页面id删除页面 <BR>
	 * 
	 * @param id
	 * @return ResultMap<BR>
	 */
	@RequestMapping("/delPageById")
	@ResponseBody
	public ResultMap delPageById(Integer id) {
		if (null == id) {
			return new ResultMap().fail().message("参数错误");
		}
		return pageService.delPageById(id);
	}

	/**
	 * Method name: addRole <BR>
	 * Description: 增加角色 <BR>
	 * 
	 * @param name
	 * @return String<BR>
	 */
	@RequestMapping("/addRole")
	@ResponseBody
	public String addRole(String name) {
		return roleService.addRole(name);
	}

	/**
	 * Method name: delManageRole <BR>
	 * Description: 根据角色id删除角色 <BR>
	 * 
	 * @param id
	 * @return String<BR>
	 */
	@RequestMapping("/delRole")
	@ResponseBody
	public String delRole(int id) {
		// 删除角色
		boolean flag1 = roleService.delRoleById(id);
		// 删除角色_权限表
		boolean flag2 = pageRoleService.delPageRoleByRoleId(id);
		// 删除某个角色的所有用户
		boolean flag3 = userRoleService.delUserRoleByRoleId(id);

		if (flag1 && flag2 && flag3) {
			return "SUCCESS";
		}
		return "ERROR";
	}

	/**
	 * Method name: updateRole <BR>
	 * Description: 根据权限id修改权限信息 <BR>
	 * 
	 * @param id
	 * @param name
	 * @return String<BR>
	 */
	@RequestMapping("/updateRole")
	@ResponseBody
	public String updateRole(Integer id, String name) {
		int n = roleService.updateRoleById(id, name);
		if (n != 0) {
			return "SUCCESS";
		}
		return "ERROR";
	}

	/**
	 * Method name: addPageRoleByRoleId <BR>
	 * Description: 增加某个角色的权限页面 <BR>
	 * 
	 * @param roleId
	 * @param pageIds
	 * @return String<BR>
	 */
	@RequestMapping("/addPageRoleByRoleId")
	@ResponseBody
	public String addPageRoleByRoleId(Integer roleId, Integer[] pageIds) {

		if (null == roleId) {
			return "ERROR";
		}

		// 先删除老的权限
		boolean flag1 = pageRoleService.delPageRoleByRoleId(roleId);
		boolean flag2 = pageRoleService.addPageRoles(roleId, pageIds);

		if (flag1 && flag2) {
			return "SUCCESS";
		}
		return "ERROR";
	}

	/**
	 * Method name: getAllUserByMap <BR>
	 * Description: 根据角色查询下面所有的人员/非角色下所有人员 <BR>
	 */
	@RequestMapping("/getAllUserByRoleId")
	@ResponseBody
	public Object getAllUserByRoleId(Integer roleId, String roleNot, Integer page, Integer limit) {
		if (null == roleNot) {
			return userService.getAllUserByRoleId(roleId, page, limit);
		}
		return userService.getAllUserByNotRoleId(roleId, page, limit);
	}

	/**
	 * Method name: delUserRoleByUserIdAndRoleId <BR>
	 * Description: 根据用户id权限id删除用户权限表 <BR>
	 * 
	 * @param userId
	 * @param roleId
	 * @return ResultMap<BR>
	 */
	@RequestMapping("/delUserRoleByUserIdAndRoleId")
	@ResponseBody
	public ResultMap delUserRoleByUserIdAndRoleId(String userId, Integer roleId) {
		return userRoleService.delUserRoleByUserIdAndRoleId(userId, roleId);
	}

	/**
	 * Method name: selectUserRole <BR>
	 * Description: 跳转到选择用户角色页面 <BR>
	 * 
	 * @return String<BR>
	 */
	@RequestMapping("/selectUserRole")
	public String selectUserRole() {
		return "sa/selectUserRole";
	}

	/**
	 * Method name: addUserRole <BR>
	 * Description: 增加用户的角色 <BR>
	 * 
	 * @param roleId
	 * @param userIds
	 * @return String<BR>
	 */
	@RequestMapping("/addUserRole")
	@ResponseBody
	public String addUserRole(Integer roleId, String[] userIds) {
		return userRoleService.addUserRole(roleId, userIds);
	}

	/**
	 * Method name: userAddPage <BR>
	 * Description: 用户添加页面 <BR>
	 * 
	 * @return String<BR>
	 */
	@RequestMapping(value = "/userAddPage")
	public String userAddPage() {
		return "sa/userAdd";
	}

	/**
	 * Method name: userPage <BR>
	 * Description: 用户管理页面 <BR>
	 * 
	 * @return String<BR>
	 */
	@RequestMapping(value = "/userPage")
	public String userPage() {
		return "sa/userList";
	}

	/**
	 * Method name: getAllUserByLimit <BR>
	 * Description: 根据条件获取所有用户 <BR>
	 * 
	 * @param userParameter
	 * @return Object<BR>
	 */
	@RequestMapping("/getAllUserByLimit")
	@ResponseBody
	public Object getAllUserByLimit(UserParameter userParameter) {
		return userService.getAllUserByLimit(userParameter);
	}

	/**
	 * Method name: getAllDelUserByLimit <BR>
	 * Description: 获取所有删除用户 <BR>
	 * 
	 * @param userParameter
	 * @return Object<BR>
	 */
	@RequestMapping("/getAllDelUserByLimit")
	@ResponseBody
	public Object getAllDelUserByLimit(UserParameter userParameter) {
		return userService.getAllDelUserByLimit(userParameter);
	}

	/**
	 * Method name: delUser <BR>
	 * Description: 批量删除用户 <BR>
	 * 
	 * @param ids
	 * @return String<BR>
	 */
	@RequestMapping(value = "delUser")
	@ResponseBody
	@Transactional
	public String delUser(Long[] ids) {
		Subject subject = SecurityUtils.getSubject();
		User user = (User) subject.getPrincipal();
		try {
			for (Long id : ids) {
				if (id.equals(user.getId())) {
					return "DontOP";
				}
				userService.delUserById(id);
			}
			return "SUCCESS";
		} catch (Exception e) {
			logger.error("根据用户id更新用户异常", e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "ERROR";
		}
	}

	/**
	 * Method name: addUserPage <BR>
	 * Description: 增加用户界面 <BR>
	 * 
	 * @return String<BR>
	 */
	@RequestMapping(value = "/addUserPage")
	public String addUserPage(Long userId, Model model) {
		model.addAttribute("manageUser", userId);
		if (null != userId) {
			User user = userService.selectByPrimaryKey(userId);
			model.addAttribute("manageUser", user);
		}
		return "sa/userAdd";
	}

	/**
	 * Method name: checkUserId <BR>
	 * Description: 检测用户账号是否存在 <BR>
	 * 
	 * @param userId
	 * @return User<BR>
	 */
	@ResponseBody
	@RequestMapping("/checkUserId")
	public User checkUserId(Long userId) {
		return userService.selectByPrimaryKey(userId);
	}

	/**
	 * Method name: addUser <BR>
	 * Description: 用户添加 <BR>
	 * 
	 * @param user
	 * @return String<BR>
	 */
	@ResponseBody
	@RequestMapping("/addUser")
	public String addUser(User user) {
		try {
			user.setPassword(MD5.md5(user.getPassword()));
			user.setCreateTime(new Date());
			userService.addUser(user);

			User u = userService.getUserByPhoneAndName(user.getPhone(), user.getName());

			String[] ids = new String[1];
			ids[0] = u.getId()+"";
			// 医生角色
			userRoleService.addUserRole(3, ids);
			return "SUCCESS";
		} catch (Exception e) {
			return "ERR";
		}
	}

	/**
	 * Method name: updateUser <BR>
	 * Description: 更新用户 <BR>
	 * 
	 * @param user
	 * @return String<BR>
	 */
	@ResponseBody
	@RequestMapping("/updateUser")
	public String updateUser(User user, Long oldId) {
		return userService.updateUser(oldId, user);
	}

	/**
	 * Method name: delUserPage <BR>
	 * Description: 已删除用户列表 <BR>
	 * 
	 * @return String<BR>
	 */
	@RequestMapping("/delUserPage")
	public String delUserPage() {
		return "sa/userDelPage";
	}
}
