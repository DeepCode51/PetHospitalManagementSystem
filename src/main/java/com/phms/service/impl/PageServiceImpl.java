package com.phms.service.impl;

import com.phms.mapper.PageMapper;
import com.phms.model.ResultMap;
import com.phms.pojo.Page;
import com.phms.pojo.PageExample;
import com.phms.service.PageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Collections;
import java.util.List;

@Service
public class PageServiceImpl implements PageService {
	@Autowired
	private PageMapper pageMapper;
	/** logback日志记录 */
	private static final Logger logger = LoggerFactory.getLogger(PageServiceImpl.class);

	/**
	 *      Description: 根据用户id,获取用户角色下所有的页面 <BR>
	 *      Remark: <BR>
	 */
	@Override
	@Transactional
	public List<Page> getAllPageByRoleId(Integer roleId) {
		try {
			return pageMapper.getAllPageByRoleId(roleId);
		} catch (Exception e) {
			logger.error("查询角色下所有页面异常", e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return Collections.emptyList();
		}
	}

	/**
	 *      Description: 获取所有页面 <BR>
	 *      Remark: <BR>
	 * @return <BR>
	 */
	@Override
	@Transactional
	public List<Page> getAllPage() {
		try {
			return pageMapper.getAllPage();
		} catch (Exception e) {
			logger.error("获取所有页面异常", e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return Collections.emptyList();
		}
	}

	/**
	 *      Description: 根据页面id修改页面 <BR>
	 *      Remark: <BR>
	 * @param page
	 * @return <BR>
	 */
	@Override
	@Transactional
	public ResultMap updatePageById(Page page) {
		ResultMap map = new ResultMap();
		try {
			PageExample example = new PageExample();
			example.createCriteria().andPageIdEqualTo(page.getPageId());
			pageMapper.updateByExampleSelective(page, example);
			map.success().message("页面修改成功!");
		} catch (Exception e) {
			logger.error("页面修改异常", e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			map.fail().message("页面修改失败");
		}

		return map;
	}

	/**
	 *      Description: 添加页面<BR>
	 *      Remark: <BR>
	 * @param page
	 * @return <BR>
	 */
	@Override
	public Page addPage(Page page) {
		ResultMap map = new ResultMap();
		try {
			pageMapper.insert(page);
			map.success().message("页面添加成功!");
		} catch (Exception e) {
			logger.error("页面添加异常", e);
			map.fail().message("页面添加失败");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return page;
	}

	/**
	 *      Description: 更加页面id删除页面 <BR>
	 *      Remark: <BR>
	 * @param id
	 * @return <BR>
	 */
	@Override
	public ResultMap delPageById(Integer id) {
		Page page = new Page();
		page.setDeleteFlag(1);
		PageExample example = new PageExample();
		example.createCriteria().andPageIdEqualTo(id);
		ResultMap map = new ResultMap();

		try {
			pageMapper.deleteByPrimaryKey(id);
			pageMapper.updateByExampleSelective(page, example);
			map.success().message("页面删除成功!");
		} catch (Exception e) {
			logger.error("页面删除异常", e);
			map.fail().message("页面删除失败");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return map;
	}

	/**
	 *      Method name: getAllRolePageByUserId <BR>
	 *      Description: 根据用户id获取页面 <BR>
	 *      Remark: <BR>
	 * @param userId
	 * @return <BR>
	 */
	@Override
	public List<Page> getAllRolePageByUserId(String userId) {
		try {
			return pageMapper.getAllRolePageByUserId(userId);
		} catch (Exception e) {
			logger.error("根据用户id获取页面异常", e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return Collections.emptyList();
		}
	}

}
