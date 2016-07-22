package com.hxp.doctor.controller.api;

import com.hxp.base.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 医生端视频接口
 * Created by liuyang on 2016/7/12.
 */
@RestController
@RequestMapping("/app/v1_6/service/doctor")
public class DoctorVideoApiController extends BaseController {
//    //@Autowired
//    // private IDocDoctorInfoService docDoctorInfoService;
//
//    /**
//     * 设置token
//     * @param token
//     */
//
//    public static void setToken(String token) {
//        DocDoctorInfo docDoctorInfo = new DocDoctorInfo();
//        docDoctorInfo.setId(18612l);
//        docDoctorInfo.setName(token);
//        JedisManager.setObject(token, 1800000, docDoctorInfo);
//    }
//
//    /**
//     * 获得讲堂课程
//     *
//     * @param token
//     * @return
//     * @throws Exception
//     */
//
//    @RequestMapping(value = "/getVideosByName", method = RequestMethod.GET)
//    public CommonResult<Object> getVideosByName(String token) {
//        CommonResult<Object> commonResult = null;
//        try {
//
//
//            //验证医生端的token
//            commonResult = validationToken(token);
//
//            //commonResult的 returnStatus值为flase,出现异常,要返回
//            if(!commonResult.isReturnStatus()){
//                return commonResult;
//            }
//
//            // 获取过后数据,转成String字符串
//            String json = "";
//
//            commonResult.setResult(ConstantsStatus.SC2000, "获得讲堂课程成功", true, json);
//
//        } catch (Exception e) {
//            logger.error("获得讲堂课程出现异常！", e.fillInStackTrace());
//            commonResult.setResult(ConstantsStatus.SC4000, "获得讲堂课程出现异常", false);
//        }
//        return commonResult;
//    }
//
//    /**
//     * 讲堂视频详情
//     *
//     * @param token
//     * @param videoId
//     * @return
//     */
//    @RequestMapping(value = "/getVideoById", method = RequestMethod.POST)
//    public CommonResult<Object> getVideoById(String token, String videoId) {
//        CommonResult<Object> commonResult = null;
//        try {
//
//            //验证医生端的token
//            commonResult = validationToken(token);
//
//            //commonResult的 returnStatus值为flase,出现异常,要返回
//            if(!commonResult.isReturnStatus()){
//                return commonResult;
//            }
//
//            //验证视频id是否为空
//            if (StringUtil.isBlank(videoId)) {
//                commonResult.setResult(ConstantsStatus.SC5001, "视频id不能为空", false, token);
//                return commonResult;
//            }
//
//
//            // 获取过后数据,转成String字符串
//            String json = "aaaaaa";
//
//
//            commonResult.setResult(ConstantsStatus.SC2000, "获得讲堂视频详情成功", true, json);
//
//        } catch (Exception e) {
//            logger.error("登陆失败！", e.fillInStackTrace());
//            commonResult.setResult(ConstantsStatus.SC4000, "获得讲堂视频详情出现异常", false);
//        }
//        return commonResult;
//    }
//
//    /**
//     * 评论信息   根据videoId 视频id获取所有评论信息
//     *
//     * @param token   登录token
//     * @param videoId 视频id
//     * @return
//     */
//    @RequestMapping(value = "/getCommunicationList", method = RequestMethod.POST)
//    public CommonResult<Object> getCommunicationList(String token, String videoId) {
//        CommonResult<Object> commonResult = null;
//        try {
//
//            //验证医生端的token
//            commonResult = validationToken(token);
//
//            //commonResult的 returnStatus值为flase,出现异常,要返回
//            if(!commonResult.isReturnStatus()){
//                return commonResult;
//            }
//
//            //验证视频id是否为空
//            if (StringUtil.isBlank(videoId)) {
//                commonResult.setResult(ConstantsStatus.SC5001, "视频id不能为空", false, token);
//                return commonResult;
//            }
//
//            // 获取过后数据,转成String字符串
//            String json = "bbbbbbbbbbb";
//
//            commonResult.setResult(ConstantsStatus.SC2000, "获得视频评论信息成功", true, json);
//        } catch (Exception e) {
//            logger.error("登陆失败！", e.fillInStackTrace());
//            commonResult.setResult(ConstantsStatus.SC4000, "获得视频评论信息出现异常", false);
//        }
//        return commonResult;
//    }
//
//
//    /**
//     * 添加视频评论
//     *
//     * @param token
//     * @param userType
//     * @param objectId
//     * @param videoId
//     * @param context
//     * @return
//     */
//    @RequestMapping(value = "/addPlatformCommunication", method = RequestMethod.POST)
//    public CommonResult<Object> addPlatformCommunication(String token, String userType, String objectId, String videoId, String context) {
//        CommonResult<Object> commonResult = null;
//        try {
//
//            //验证医生端的token
//            commonResult = validationToken(token);
//
//            //commonResult的 returnStatus值为flase,出现异常,要返回
//            if(!commonResult.isReturnStatus()){
//                return commonResult;
//            }
//
//            //判断用户类型是否为空
//            if (StringUtil.isBlank(userType)) {
//                commonResult.setResult(ConstantsStatus.SC5001, "用户类型不能为空", false, token);
//                return commonResult;
//            }
//
//            //判断用户id是否为空
//            if (StringUtil.isBlank(objectId)) {
//                String message = "";
//
//                if (StringUtil.isNotBlank(userType)) {
//                    if (userType.equals("1")) {
//                        message = "医生id不能为空";
//                    } else {
//                        message = "患者id不能为空";
//                    }
//                }
//
//                commonResult.setResult(ConstantsStatus.SC5001, message, false);
//                return commonResult;
//            }
//
//            //验证视频id是否为空
//            if (StringUtil.isBlank(videoId)) {
//                commonResult.setResult(ConstantsStatus.SC5001, "视频id不能为空", false);
//                return commonResult;
//            }
//
//
//            //判断评论类容是否为空
//            if (StringUtil.isBlank(context)) {
//                commonResult.setResult(ConstantsStatus.SC5001, "评论类容不能为空", false);
//                return commonResult;
//            }
//
//
//            //调用 add 方法插入评论信息
//
//
//            // 获取过后数据,转成String字符串
//            String json = "";
//
//            commonResult.setResult(ConstantsStatus.SC2000, "添加视频评论信息成功", true, json);
//
//        } catch (Exception e) {
//            logger.error("添加视频评论信息失败！", e.fillInStackTrace());
//            commonResult.setResult(ConstantsStatus.SC4000, "添加视频评论信息失败", false);
//        }
//        return commonResult;
//    }


}
