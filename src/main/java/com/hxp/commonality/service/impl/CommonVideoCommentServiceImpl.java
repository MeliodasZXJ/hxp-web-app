package com.hxp.commonality.service.impl;

import com.hxp.commonality.dao.CommonVideoCommentDao;
import com.hxp.commonality.po.CommonVideoComment;
import com.hxp.commonality.service.ICommonVideoCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by slyi on 2016/7/20.
 */
@Service
public class CommonVideoCommentServiceImpl implements ICommonVideoCommentService {
    @Autowired
    private CommonVideoCommentDao commonVideoCommentDao;

    @Override
    public List<CommonVideoComment> findCommentList(CommonVideoComment commonVideoComment) {
        return commonVideoCommentDao.find("selectCommentList",commonVideoComment);
    }

    @Override
    public int insertComment(CommonVideoComment commonVideoComment) {
        return commonVideoCommentDao.insert(commonVideoComment);
    }
}
