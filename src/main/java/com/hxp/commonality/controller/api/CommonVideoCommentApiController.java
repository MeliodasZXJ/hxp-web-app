package com.hxp.commonality.controller.api;

import com.github.pagehelper.PageHelper;
import com.hxp.base.BaseController;
import com.hxp.commonality.po.CommonVideoComment;
import com.hxp.commonality.service.ICommonVideoCommentService;
import com.hxp.util.CommonResult;
import com.hxp.util.ConstantsStatus;
import com.hxp.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by slyi on 2016/7/20.
 */
@RestController
@RequestMapping("/app/service/common")
public class CommonVideoCommentApiController extends BaseController {
	@Autowired
	private ICommonVideoCommentService commonVideoCommentService;

	/**
	 * 添加视频评论
	 * 
	 * @param token
	 * @param commonVideoComment
	 * @return
	 */
	@RequestMapping(value = "/addComment", method = RequestMethod.POST)
	public CommonResult<Object> addComment(String token, CommonVideoComment commonVideoComment) {
		CommonResult<Object> commonResult = new CommonResult<Object>();

		// 判断token
		commonResult = validationToken(token);

		// commonResult的 returnStatus值为flase,出现异常,要返回
		if (!commonResult.isReturnStatus()) {
			return commonResult;
		}

		// 视频ID为空判断
		if (StringUtils.isBlank(String.valueOf(commonVideoComment.getVideoId()))) {
			commonResult.setResult(ConstantsStatus.SC7005, "视频ID不能为空", false);
			return commonResult;
		}

		// 评论内容为空判断
		if (StringUtils.isBlank(String.valueOf(commonVideoComment.getProblemDesc()))) {
			commonResult.setResult(ConstantsStatus.SC7006, "评论内容不能为空", false);
			return commonResult;
		}

		//评论人类型 1:医生 2:患者
		if (StringUtils.isBlank(String.valueOf(commonVideoComment.getUserType()))) {
			commonResult.setResult(ConstantsStatus.SC7007, "评论人类型不能为空", false);
			return commonResult;
		}

		int i = commonVideoCommentService.insertComment(commonVideoComment);
		if (i > 0) {
			commonResult.setResult(ConstantsStatus.SC2000, "评论成功", true);
		} else {
			commonResult.setResult(ConstantsStatus.SC4001, "评论失败", false);
		}
		return commonResult;
	}

	/**
	 * 根据视频ID查询评论列表
	 * 
	 * @param token
	 * @param videoId
	 * @return
	 */
	@RequestMapping(value = "/commentList", method = RequestMethod.GET)
	public CommonResult<Object> commentList(String token, String videoId) {
		CommonResult<Object> commonResult = new CommonResult<Object>();

		// 判断token
		commonResult = validationToken(token);

		// commonResult的 returnStatus值为flase,出现异常,要返回
		if (!commonResult.isReturnStatus()) {
			return commonResult;
		}

		// 视频ID为空判断
		if (StringUtils.isBlank(videoId)) {
			commonResult.setResult(ConstantsStatus.SC5001, "视频ID不能为空!", false);
			return commonResult;
		}
		CommonVideoComment cvc = new CommonVideoComment();
		cvc.setVideoId(Long.parseLong(videoId));
		PageHelper.startPage(getPageNum(), getPageSize());
		List<CommonVideoComment> commentList = commonVideoCommentService.findCommentList(cvc);
		commonResult.setResult(ConstantsStatus.SC2000, "查询评论列表成功", true, commentList);
		return commonResult;
	}

}
