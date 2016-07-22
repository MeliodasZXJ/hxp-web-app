package com.hxp.commonality.service;

import com.hxp.commonality.po.CommonVideoComment;

import java.util.List;

/**
 * Created by slyi on 2016/7/20.
 */
public interface ICommonVideoCommentService {

    /**
     * 查询评论列表
     * @param commonVideoComment
     * @return
     */
    List<CommonVideoComment> findCommentList(CommonVideoComment commonVideoComment);

    /**
     * 评论回复
     * @param commonVideoComment
     * @return
     */
    int insertComment(CommonVideoComment commonVideoComment);


}
