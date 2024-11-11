package com.phms.service.impl;

import com.phms.mapper.NoticeMapper;
import com.phms.model.MMGridPageVoBean;
import com.phms.pojo.Notice;
import com.phms.service.NoticeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    NoticeMapper noticeMapper;
    @Override
    public Object getAllByLimit(Notice po) {
        int size = 0;
        // 计算分页
        Integer begin = (po.getPage() - 1) * po.getLimit();
        po.setPage(begin);

        List<Notice> rows = new ArrayList<>();
        try {
            rows = noticeMapper.getAllByLimit(po);
            size = noticeMapper.countAllByLimit(po);
        } catch (Exception e) {
            logger.error("根据条件查询异常", e);
        }
        MMGridPageVoBean<Notice> vo = new MMGridPageVoBean<>();
        vo.setTotal(size);
        vo.setRows(rows);

        return vo;
    }

    @Override
    public Notice getById(Long id) {
        return noticeMapper.selectByPrimaryKey(id);
    }

    @Override
    public void view(Long id) {
        Notice notice = noticeMapper.selectByPrimaryKey(id);
        notice.setViewCount(notice.getViewCount() +1);
        noticeMapper.updateByPrimaryKey(notice);
    }

    @Override
    public void deleteById(Long id) {
        noticeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void add(Notice po) {
        noticeMapper.insert(po);
    }

    @Override
    public void update(Notice po) {
        noticeMapper.updateByPrimaryKeySelective(po);
    }
}
