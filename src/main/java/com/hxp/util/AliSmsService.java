package com.hxp.util;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.alibaba.fastjson.JSONArray;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;


/**
 * Created by liuyang on 2016/7/12.
 */
public class AliSmsService {

    //阿里大鱼短信url
    private static String serverUrl = "http://gw.api.taobao.com/router/rest";
    //阿里大鱼短信appKey
    private static String appKey = "23313071";
    //阿里大鱼短信appSecret
    private static String appSecret = "04e0020db36c4e3a6b47d0b532b258dc";
    //smsId
    private static String smsTempId = "SMS_5038609";



    public static void main(String[] args) throws Exception {

        String activeCode = getActivatingKey(5);

        String message = new StringBuffer(activeCode).append(",请及时使用此验证码。如非本人操作，请忽略此短信。【好心情医疗平台】").toString();

        boolean result = AliSmsService.sendRegCodeMobile("18612116276",message);

        System.out.println("发送短信:"+result);


/*        String jsonString = JSONArray.toJSONString(jsonMap);

        AliSmsService.sendSms("18612116276", jsonString, "SMS_5038609");*/

    }

    /**
     * 发送验证码短信
     *
     * @param activeCode
     * @return
     * @throws ServiceException
     */
    public static boolean sendRegCodeMobile(String mobile, String activeCode)
            throws Exception {
        // 发送验证码
        //String message = new StringBuffer("好心情医疗平台验证码为：").append(activeCode).append("，请及时使用此验证码。如非本人操作，请忽略此短信。【好心情医疗平台】").toString();
        Map<String,String> jsonMap = new HashMap<String,String>();
        jsonMap.put("activeCode", activeCode);
        String jsonString = JSONArray.toJSONString(jsonMap);;
        boolean result = false;
        try {
            //使用阿里大鱼短信通道发信息
            AliSmsService.sendSms(mobile, jsonString, smsTempId);
            result = true;
            //result = AppMessageManager.sendMobileMessage(mobile, message);
        } catch (Exception e) {
            result = false;
        }
        return result;
    }


    /**
     * 生成指定长度位数验证码
     *
     * @param length
     *            长度
     * @return string
     */
    public static String getActivatingKey(int length) {
        String[] str = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
        StringBuilder buffer = new StringBuilder("");
        Random r = new Random();
        for (int i = 0; i < length; i++) {
            buffer.append(str[r.nextInt(str.length)]);
        }

        return buffer.toString();

    }


    /**
     * 阿里大鱼短信通道
     *
     * @param mobile
     * @param smsTempMessage
     * @param smsTempId
     */
    public static void sendSms(String mobile, String smsTempMessage, String smsTempId) {

        TaobaoClient client = new DefaultTaobaoClient(serverUrl, appKey, appSecret);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        // 【可选】，公共回传参数，在“消息返回”中会透传回该参数；举例：用户可以传入自己下级的会员ID，在消息返回时，该会员ID会包含在内，用户可以根据该会员ID识别是哪位会员使用了你的应用
        req.setExtend("");
        // 【必须】短信类型，传入值请填写normal
        req.setSmsType("normal");
        // 【必须】短信签名，传入的短信签名必须是在阿里大鱼“管理中心-短信签名管理”中的可用签名。如“阿里大鱼”已在短信签名管理中通过审核，则可传入”阿里大鱼“（传参时去掉引号）作为短信签名。短信效果示例：【阿里大鱼】欢迎使用阿里大鱼服务。
        req.setSmsFreeSignName("好心情");
        // 【可选】短信模板变量，传参规则{"key":"value"}，key的名字须和申请模板中的变量名一致，多个变量之间以逗号隔开。示例：针对模板“验证码${code}，您正在进行${product}身份验证，打死不要告诉别人哦！”，传参时需传入{"code":"1234","product":"alidayu"}
        // req.setSmsParamString("{\"code\":\"1234\",\"product\":\"您在好心情的\",\"item\":\"阿里大鱼\"}");
        // {"product":"好心情医疗平台验证码","item":"阿里大鱼","code":"123456"}
        req.setSmsParamString(smsTempMessage);
        // 【必须】短信接收号码。支持单个或多个手机号码，传入号码为11位手机号码，不能加0或+86。群发短信需传入多个号码，以英文逗号分隔，一次调用最多传入200个号码。示例：18600000000,13911111111,13322222222
        req.setRecNum(mobile);
        // 【必须】短信模板ID，传入的模板必须是在阿里大鱼“管理中心-短信模板管理”中的可用模板。示例：SMS_585014
        req.setSmsTemplateCode(smsTempId);
        AlibabaAliqinFcSmsNumSendResponse rsp;
        try {
            rsp = client.execute(req);
            System.out.println("--------------阿里大鱼sendSms----end-------");
            System.out.println(rsp.getBody());
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

}