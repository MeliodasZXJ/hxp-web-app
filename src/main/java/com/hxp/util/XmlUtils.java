package com.hxp.util;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/29.
 */
public class XmlUtils {
    /**
     * 根据Map组装xml消息体，值对象仅支持基本数据类型、String、BigInteger、BigDecimal，以及包含元素为上述支持数据类型的Map
     *
     * @param vo
     * @param rootElement
     * @return
     * @author 蔡政滦 modify by 2015-6-5
     */
    public static String map2xmlBody(Map<String, Object> vo, String rootElement) {
        org.dom4j.Document doc = DocumentHelper.createDocument();
        Element body = DocumentHelper.createElement(rootElement);
        doc.add(body);
        __buildMap2xmlBody(body, vo);
        return doc.asXML();
    }

    private static void __buildMap2xmlBody(Element body, Map<String, Object> vo) {
        if (vo != null) {
            Iterator<String> it = vo.keySet().iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                if (StringUtils.isNotEmpty(key)) {
                    Object obj = vo.get(key);
                    Element element = DocumentHelper.createElement(key);
                    if (obj != null) {
                        if (obj instanceof java.lang.String) {
                            element.setText((String) obj);
                        } else {
                            if (obj instanceof java.lang.Character || obj instanceof java.lang.Boolean || obj instanceof java.lang.Number
                                    || obj instanceof java.math.BigInteger || obj instanceof java.math.BigDecimal) {
                                org.dom4j.Attribute attr = DocumentHelper.createAttribute(element, "type", obj.getClass().getCanonicalName());
                                element.add(attr);
                                element.setText(String.valueOf(obj));
                            } else if (obj instanceof java.util.Map) {
                                org.dom4j.Attribute attr = DocumentHelper.createAttribute(element, "type", java.util.Map.class.getCanonicalName());
                                element.add(attr);
                                __buildMap2xmlBody(element, (Map<String, Object>) obj);
                            } else {
                            }
                        }
                    }
                    body.add(element);
                }
            }
        }
    }

    /**
     * 根据xml消息体转化为Map
     *
     * @param xml
     * @param rootElement
     * @return
     * @throws DocumentException
     * @author xiaohe
     */
    public static Map xmlBody2map(String xml, String rootElement) throws DocumentException {
        Document doc = DocumentHelper.parseText(xml);

        Element body = (Element) doc.selectSingleNode(rootElement);
        Map vo = __buildXmlBody2map(body);
        return vo;
    }

    private static Map __buildXmlBody2map(Element body) {
        Map vo = new HashMap();
        if (body != null) {
            List<Element> elements = body.elements();
            for (Element element : elements) {
                String key = element.getName();
                if (StringUtils.isNotEmpty(key)) {
                    String type = element.attributeValue("type", "java.lang.String");
                    String text = element.getText().trim();
                    Object value = null;
                    if (java.lang.String.class.getCanonicalName().equals(type)) {
                        value = text;
                    } else if (java.lang.Character.class.getCanonicalName().equals(type)) {
                        value = new java.lang.Character(text.charAt(0));
                    } else if (java.lang.Boolean.class.getCanonicalName().equals(type)) {
                        value = new java.lang.Boolean(text);
                    } else if (java.lang.Short.class.getCanonicalName().equals(type)) {
                        value = java.lang.Short.parseShort(text);
                    } else if (java.lang.Integer.class.getCanonicalName().equals(type)) {
                        value = java.lang.Integer.parseInt(text);
                    } else if (java.lang.Long.class.getCanonicalName().equals(type)) {
                        value = java.lang.Long.parseLong(text);
                    } else if (java.lang.Float.class.getCanonicalName().equals(type)) {
                        value = java.lang.Float.parseFloat(text);
                    } else if (java.lang.Double.class.getCanonicalName().equals(type)) {
                        value = java.lang.Double.parseDouble(text);
                    } else if (java.math.BigInteger.class.getCanonicalName().equals(type)) {
                        value = new java.math.BigInteger(text);
                    } else if (java.math.BigDecimal.class.getCanonicalName().equals(type)) {
                        value = new java.math.BigDecimal(text);
                    } else if (java.util.Map.class.getCanonicalName().equals(type)) {
                        value = __buildXmlBody2map(element);
                    } else {
                    }
                    vo.put(key, value);
                }
            }
        }
        return vo;
    }

    /**
     * 卡积分兑换请求报文封装成xml hejp
     * @param map
     * @param methodName
     * @return
     */
    public  String getXmlInfo(Map<String,Object> map,String methodName) {
        StringBuilder sb = new StringBuilder();
        sb.append("<loy:").append(methodName).append(">");
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()){
            Map.Entry entry = (Map.Entry)iter.next();
            sb.append("<"+entry.getKey()+">"+entry.getValue()+"</"+entry.getKey()+">");
        }
        sb.append("</loy:").append(methodName).append(">");
        return sb.toString();
    }

    /**
     * 电子充值卡map转换成xml
     * @param map
     * @return
     */
    public  String getXmlInfo(Map<String,Object> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?><SumavisionPAY-API><API><name>ZSY</name><version>1.0.0.0</version></API></SumavisionPAY-API>");
        sb.append("<input>");
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()){
            Map.Entry entry = (Map.Entry)iter.next();
            sb.append("<"+entry.getKey()+">"+entry.getValue()+"</"+entry.getKey()+">");
        }
        sb.append("</input></SumavisionPAY-API>");
        return sb.toString();
    }

    public static Map<String,Object> xmlBody1map(String xml) throws DocumentException{
        Document doc = DocumentHelper.parseText(xml);
        Element root=doc.getRootElement();
        List<Element> childElements = root.elements();
        Map<String,Object> map=new HashMap<>();

        for (Element  child :childElements){
            System.out.println(child.getName()+"---------"+child.getText());
            map.put(child.getName(),child.getText());
            List<Element> elementList = child.elements();
            for (Element ele : elementList) {
                map.put(ele.getName(), ele.getText());
            }
        }
        return  map;
    }
    public static void main(String [] a){
        //测试1
       // String xml="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><output><name>aaaa</name><age>bb</age></output>";
        String xml="<DATA><RESULT>成功标志</RESULT><ERROR>错误信息</ERROR><CONSSET><cardAsn>卡号</cardAsn><uniqueId>订单号</uniqueId></CONSSET></DATA>";
       try {
           Map<String,Object> map=xmlBody1map(xml);
           System.out.println(map.size()+"--------------------");
       }catch (Exception e){

       }


//        try {
//            Map map=xmlBody2map(xml, "/DATA");
//            Map map1=xmlBody2map(xml, "/DATA/CONSSET");
//            map.putAll(map1);
//            System.out.println(map.size());
//
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
//        Map map1=new HashMap();
//        map1.put("name","123");
//        map1.put("age", "xiaoming");
//        String result=new XmlUtils().getXmlInfo(map1,"getLoyTradeInfo");
//        System.out.println(result);

    }
}
