package com.phms.service.impl;

import com.phms.mapper.RolePageMapper;
import com.phms.pojo.RolePage;
import com.phms.pojo.RolePageExample;
import com.phms.service.PageRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
public class PageRoleServiceImpl implements PageRoleService {
	@Autowired
	private RolePageMapper rolePageMapper;
	/** logback日志记录 */
	private static final Logger logger = LoggerFactory.getLogger(PageRoleServiceImpl.class);

	/**
	 *      Description: 根据权限id删除权限页面表 <BR>
	 *      Remark: <BR>
	 * @param id
	 * @return <BR>
	 */
	@Override
	@Transactional
	public boolean delPageRoleByRoleId(Integer id) {
		RolePageExample excExample = new RolePageExample();
		excExample.createCriteria().andRoleIdEqualTo(id).andRpIdGreaterThan(4);
		try {
			rolePageMapper.deleteByExample(excExample);
			return true;
		} catch (Exception e) {
			logger.error("删除页面-权限出错!", e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
	}

	/**
	 *      Description: 根据角色id添加页面角色表 <BR>
	 *      Remark: <BR>
	 * @param pageIds
	 * @return <BR>
	 */
	@Override
	@Transactional
	public boolean addPageRoles(Integer roleId, Integer[] pageIds) {
		try {
			if (pageIds != null && pageIds.length > 0) {
				for (int pageId : pageIds) {
					RolePage rolePage = new RolePage();
					rolePage.setRoleId(roleId);
					rolePage.setPageId(pageId);
					rolePageMapper.insert(rolePage);
				}
			}
			return true;
		} catch (Exception e) {
			logger.error("添加页面-权限出错!", e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
	}
}
