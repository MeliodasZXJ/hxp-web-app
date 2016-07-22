package com.hxp.util;


import com.hxp.common.fileUpload.FileUtils;
import com.hxp.common.fileUpload.config.MogolifsConfig;
import com.hxp.common.fileUpload.config.MogolifsConstant;
import com.hxp.util.IdentityHelper;
import com.hxp.util.ObjectUtils;
import com.hxp.util.StringUtil;
import fm.last.moji.MojiFile;
import fm.last.moji.spring.SpringMojiBean;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

public class UploadFileUtils {


    /**
     * 将上传文件保存到文件服务器，并由文件服务器返回http访问地址.
     *
     * @param file
     * @return {文件id,文件url地址,文件类型(后缀)}
     * @throws Exception
     */
    public static String[] uploadFileToServer(MultipartFile file, SpringMojiBean moji) throws Exception {
        InputStream is = null;
        try {
            is = file.getInputStream();
            return uploadFileToServer(is, file.getOriginalFilename(), moji);
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (is != null) is.close();
            } catch (Exception e) {
            	
            }
        }
    }

    /**
     * 上传本地文件至文件服务器
     *
     * @param filePath
     * @return {文件id,文件url地址,文件类型(后缀)}
     * @throws Exception
     */
    public static String[] uploadFileToServer(String filePath, SpringMojiBean moji) throws Exception {
        File file = new File(filePath);
        if (!file.exists()) return null;
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            return uploadFileToServer(is, file.getName(), moji);
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (is != null) is.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * 通过InputStream流和文件名称上传至文件服务器
     *
     * @param is
     * @param fileName
     * @return {文件id,文件url地址,文件类型(后缀)}
     */
    public static String[] uploadFileToServer(InputStream is, String fileName, SpringMojiBean moji) throws Exception {
        String suffix = StringUtil.trimToEmpty(FileUtils.getExtension(fileName));
        String id = IdentityHelper.uuid2();
        StringBuffer sbf = new StringBuffer(100);
        sbf.append(id);
        if (suffix.length() > 0) {
            sbf.append(".").append(suffix);
        }
        fileName = sbf.toString();
        List<URL> lst = uploadToMogilefs(id, is, moji);
        return ObjectUtils.isEmpty(lst) ? null
                : new String[]{id, formatUrl(lst.get(0).toString()), suffix};
    }

    private static List<URL> uploadToMogilefs(String uuid, InputStream in, SpringMojiBean moji) throws Exception {
        OutputStream out = null;
        MojiFile mf = moji.getFile("MyFileKey" + uuid);
        try {
            out = mf.getOutputStream();
            byte[] bs = new byte[1024];
            int bytesRead = 0;
            while (-1 != (bytesRead = in.read(bs, 0, bs.length))) {
                out.write(bs, 0, bytesRead);
            }
            out.flush();
        } catch (Exception e) {
            System.out.print("上传图片异常:" + e.getMessage());
            throw e;
        } finally {
            try {
                if (out != null) out.close();
            } catch (Exception e) {
            }
        }
        return mf.getPaths();
    }

    private static String formatUrl(String url) {
        if (ObjectUtils.isEmpty(url) || "null".equalsIgnoreCase(url)) return "";


        String imageUrl = MogolifsConfig.getString(MogolifsConstant.MOGOLIFS_IMAGEURL);
//        String imageUrl = "http://172.168.1.12:7500";
        if (ObjectUtils.isEmpty(imageUrl) || "null".equalsIgnoreCase(imageUrl)) return url;

        if ((url.startsWith("http://")) || (url.startsWith("https://"))) {
            url = url.substring(url.indexOf("://") + 3);
            url = url.substring(url.indexOf("/") + 1);
        }

        if (url.startsWith("/")) {
            url = url.substring(url.indexOf("/") + 1);
        }

        StringBuffer sbf = new StringBuffer(1000);
        sbf.append(imageUrl).append(imageUrl.startsWith("/") ? "" : "/").append(url);
        return sbf.toString();
    }
}
