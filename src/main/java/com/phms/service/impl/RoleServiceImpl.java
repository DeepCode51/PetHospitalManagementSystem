package com.phms.service.impl;


import com.phms.mapper.RoleMapper;
import com.phms.pojo.Role;
import com.phms.pojo.RoleExample;
import com.phms.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Collections;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleMapper roleMapper;
	private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

	/**
	 *      Description: 获取所有角色 <BR>
	 *      Remark: <BR>
	 * @return <BR>
	 */
	@Override
	public List<Role> getAllRole() {
		RoleExample example = new RoleExample();
		example.createCriteria().andRoleIdIsNotNull();
		try {
			return roleMapper.selectByExample(example);
		} catch (Exception e) {
			logger.error("获取所有角色异常", e);
			return Collections.emptyList();
		}
	}

	/**
	 *      Description: 添加角色 <BR>
	 *      Remark: <BR>
	 * @param name
	 * @return <BR>
	 */
	@Override
	@Transactional
	public String addRole(String name) {
		Role record = new Role();
		record.setName(name);
		try {
			roleMapper.insert(record);
			return "SUCCESS";
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			logger.error("添加角色异常", e);
			return "ERROR";
		}
	}

	/**
	 *      Description: 根据权限id删除权限 <BR>
	 *      Remark: <BR>
	 * @param id
	 * @return <BR>
	 */
	@Override
	@Transactional
	public boolean delRoleById(Integer id) {
		try {
			roleMapper.deleteByPrimaryKey(id);
			return true;
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			logger.error("根据权限id删除权限异常", e);
			return false;
		}
	}

	/**
	 *      Description: 根据权限id更新权限 <BR>
	 *      Remark: <BR>
	 * @param id
	 * @param name
	 * @return <BR>
	 */
	@Override
	@Transactional
	public int updateRoleById(Integer id, String name) {
		Role record = new Role();
		record.setName(name);

		RoleExample example = new RoleExample();

		example.createCriteria().andRoleIdEqualTo(id);

		try {
			roleMapper.updateByExampleSelective(record, example);
			return 1;
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			logger.error("根据权限id更新权限异常", e);
			return 0;
		}
	}
}