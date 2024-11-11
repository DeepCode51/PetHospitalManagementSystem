package com.phms.config;

import com.phms.shiro.CustomRealm;
import com.phms.shiro.CustomRolesAuthorizationFilter;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 权限控制
 */
@Configuration
public class ShiroConfig {

	@Bean
	public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	/**
	 * Method name: getDefaultAdvisorAutoProxyCreator <BR>
	 * Description: 使用户信息放入Subject里面 <BR>
	 * Remark: <BR>
	 * 
	 * @return DefaultAdvisorAutoProxyCreator<BR>
	 */
	@Bean
	public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		defaultAdvisorAutoProxyCreator.setUsePrefix(true);
		return defaultAdvisorAutoProxyCreator;
	}

	/**
	 * 过滤器默认权限表 {anon=anon, authc=authc, authcBasic=authcBasic, logout=logout,
	 * noSessionCreation=noSessionCreation, perms=perms, port=port, rest=rest,
	 * roles=roles, ssl=ssl, user=user}
	 * <p>
	 * anon, authc, authcBasic, user 是第一组认证过滤器 perms, port, rest, roles, ssl
	 * 是第二组授权过滤器
	 * <p>
	 * user 和 authc 的不同：当应用开启了rememberMe时, 用户下次访问时可以是一个user, 但绝不会是authc,
	 * 因为authc是需要重新认证的, user表示用户不一定已通过认证, 只要曾被Shiro记住过登录状态的用户就可以正常发起请求,比如rememberMe
	 * 以前的一个用户登录时开启了rememberMe, 然后他关闭浏览器, 下次再访问时他就是一个user, 而不会authc
	 *
	 * @param securityManager 初始化 ShiroFilterFactoryBean 的时候需要注入 SecurityManager
	 */
	/**
	 * Method name: shirFilter <BR>
	 * Description: 核心Shiro拦截器 <BR>
	 * Remark: <BR>
	 * 
	 * @param securityManager
	 * @return ShiroFilterFactoryBean<BR>
	 */
	@Bean
	public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		// 自定义拦截器
		Map<String, Filter> filtersMap = new LinkedHashMap<>();
		filtersMap.put("roleOrFilter", new CustomRolesAuthorizationFilter());
		shiroFilterFactoryBean.setFilters(filtersMap);

		// 必须设置 SecurityManager
		// setLoginUrl 如果不设置值，默认会自动寻找Web工程根目录下的"/login.jsp"页面 或 "/login" 映射
		shiroFilterFactoryBean.setLoginUrl("/");

		// 设置无权限时跳转的 url;
		shiroFilterFactoryBean.setUnauthorizedUrl("/notRole");

		/*
		 * anon 表示匿名访问，不需要认证以及授权 authc表示需要认证 没有进行身份认证是不能进行访问的
		 * authc，roles[admin]表示是admin角色的用户才能访问
		 */

		// 设置拦截器
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
		// 用户，需要角色权限 “user”
		//filterChainDefinitionMap.put("/user/**", "roleOrFilter[家长|老师|超级管理员]");
		// 这样写必须同时拥有两个角色才能访问
		// filterChainDefinitionMap.put("/user/**", "roles[\"组员, 管理员\"]");
		// 管理员，需要角色权限 “admin”
		//filterChainDefinitionMap.put("/sa/**", "roles[超级管理员]");
		//filterChainDefinitionMap.put("/jz/**", "roleOrFilter[家长|教师|超级管理员]");
		//filterChainDefinitionMap.put("/ls/**", "roleOrFilter[教师|超级管理员]");
		// 开放登陆接口
		filterChainDefinitionMap.put("/login", "anon");
		filterChainDefinitionMap.put("/index", "anon");
		filterChainDefinitionMap.put("/home", "anon");
		filterChainDefinitionMap.put("/open/**", "anon");
		filterChainDefinitionMap.put("/upload/**", "anon");
		filterChainDefinitionMap.put("/file/**", "anon");
		filterChainDefinitionMap.put("/regist", "anon");
		filterChainDefinitionMap.put("/doRegist", "anon");
		// 静态资源设置为可访问
		filterChainDefinitionMap.put("/css/**", "anon");
		filterChainDefinitionMap.put("/imgs/**", "anon");
		filterChainDefinitionMap.put("/js/**", "anon");
		filterChainDefinitionMap.put("/plug/**", "anon");
		filterChainDefinitionMap.put("/samples/**", "anon");
		// 其余接口一律拦截
		// 主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截
		filterChainDefinitionMap.put("/**", "anon");

		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	/**
	 * Method name: securityManager <BR>
	 * Description: 注入 securityManager <BR>
	 * Remark: <BR>
	 * 
	 * @param customRealm
	 * @return SecurityManager<BR>
	 */
	@Bean
	public DefaultWebSecurityManager securityManager(CustomRealm customRealm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 设置realm.
		securityManager.setRealm(customRealm);
		// 控制多个浏览器只能登陆一个用户
		securityManager.setSessionManager(sessionManager());
		// 控制多个浏览器只能登陆一个用户
		securityManager.setCacheManager(new MemoryConstrainedCacheManager());
		return securityManager;
	}

	/**
	 * Method name: sessionManager <BR>
	 * Description: 设置Session <BR>
	 * 
	 * @return SessionManager<BR>
	 */
	@Bean
	public SessionManager sessionManager() {
		DefaultWebSessionManager shiroSessionManager = new DefaultWebSessionManager();
		shiroSessionManager.setSessionDAO(new MemorySessionDAO());
		shiroSessionManager.setSessionValidationSchedulerEnabled(false);
		return shiroSessionManager;
	}

}